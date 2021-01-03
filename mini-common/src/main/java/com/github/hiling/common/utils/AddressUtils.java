package com.github.hiling.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author wanghailiang
 * @version 1.0
 * @date 2020/11/24 6:32 PM
 */
public class AddressUtils {
    private static final Logger logger = LoggerFactory.getLogger(AddressUtils.class);
    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String EMPTY_IP = "0.0.0.0";
    private static final Pattern IP_PATTERN = Pattern.compile("[0-9]{1,3}(\\.[0-9]{1,3}){3,}");

    public AddressUtils() {
    }

    public static boolean isAvailablePort(int port) {
        ServerSocket ss = null;

        boolean var3;
        try {
            ss = new ServerSocket(port);
            ss.bind((SocketAddress)null);
            boolean var2 = true;
            return var2;
        } catch (IOException var13) {
            var3 = false;
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException var12) {
                }
            }

        }

        return var3;
    }

    private static boolean isValidHostAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress()) {
            String name = address.getHostAddress();
            return name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches();
        } else {
            return false;
        }
    }

    public static String getHostIp() {
        InetAddress address = getHostAddress();
        return address == null ? null : address.getHostAddress();
    }

    public static String getHostName() {
        InetAddress address = getHostAddress();
        return address == null ? null : address.getHostName();
    }

    public static InetAddress getHostAddress() {
        Object localAddress = null;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while(interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = (NetworkInterface)interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while(addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = (InetAddress)addresses.nextElement();
                                    if (isValidHostAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable var5) {
                                    logger.warn("Failed to retriving network card ip address. cause:" + var5.getMessage());
                                }
                            }
                        }
                    } catch (Throwable var6) {
                        logger.warn("Failed to retriving network card ip address. cause:" + var6.getMessage());
                    }
                }
            }
        } catch (Throwable var7) {
            logger.warn("Failed to retriving network card ip address. cause:" + var7.getMessage());
        }

        logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return (InetAddress)localAddress;
    }
}
