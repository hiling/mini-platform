package com.github.hiling.common.utils;

/**
 * Twitter 雪花算法生成器 2^53版
 * <p>生成小于2^53次方的唯一ID，最多部署8个节点，每毫秒可生产256个ID；</p>
 * <p>因为使用服务器IP地址取模产生workerId，因此同一服务请使用同一端口，部署在不同IP上，确保同一服务生成的ID唯一；</p>
 * <p>因为js的最大安全整数是2的53次方，因此把雪花算法默认生成的64位改为53位，避免Long类型经过json序列化后传输到前端时精度丢失问题。</p>
 * 调整后的ID结构如下(每部分用-分开，共53位):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 000 - 0000000000（1+41+3+8=53）
 * 各部分说明：
 * 1位标识：由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 * 41位时间截：毫秒级，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下面程序IdWorker类的startTime属性）。
 * 41位的时间截，可以使用69年， 年T * = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 * 0位数据中心：可以部署在0个数据中心
 * 3位设备号：可以部署在8个节点
 * 8位序列号：毫秒内可生成2^8=256个序列号
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，但必须确保服务器时间不回调
 *
 * @author hiling
 */
public class SnowflakeIdWorker {
    // ==============================Fields===========================================
    /**
     * 开始时间截 (2020-01-01)
     */
    private final long twepoch = 1577808000000L;

    /**
     * 机器id所占的位数 2^3=8
     */
    private final long workerIdBits = 3L;

    /**
     * 数据标识id所占的位数 （不支持多数据中心）
     */
    private final long datacenterIdBits = 0L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在id中占的位数，即每毫秒最大支持生成多少个序列号，即2^8=256个
     */
    private final long sequenceBits = 8L;

    /**
     * 机器ID向左移位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移11位(8+3)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(8+3+0)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~7)
     */
    private long workerId;

    /**
     * 数据中心ID(0~0)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~256)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private static class Singleton {
        private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
    }

    public static SnowflakeIdWorker getSingleton() {
        return Singleton.snowflakeIdWorker;
    }

    //==============================Constructors=====================================

    /**
     * 构造函数
     */
    public SnowflakeIdWorker() {

        long datacenterId = 0;

        String ip = AddressUtils.getHostIp();
        long workerId = Math.abs(ip.hashCode() % maxWorkerId);
        System.out.println("雪花算法：server ip：" + ip + " workerId:" + workerId);

        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}