package com.github.hiling.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.regex.Pattern;

@Slf4j
public class AddressUtils {

    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String EMPTY_IP = "0.0.0.0";
    private static final Pattern IP_PATTERN = Pattern.compile("[0-9]{1,3}(\\.[0-9]{1,3}){3,}");

    public static boolean isAvailablePort(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            ss.bind(null);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isValidHostAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) return false;
        String name = address.getHostAddress();
        return (name != null && !EMPTY_IP.equals(name) && !LOCALHOST_IP.equals(name) && IP_PATTERN.matcher(name)
                .matches());
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
        InetAddress localAddress = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidHostAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    log.warn("Failed to retriving network card ip address. cause:" + e.getMessage());
                                }
                            }
                        }
                    } catch (Throwable e) {
                        log.warn("Failed to retriving network card ip address. cause:" + e.getMessage());
                    }
                }
            }
        } catch (Throwable e) {
            log.warn("Failed to retriving network card ip address. cause:" + e.getMessage());
        }
        log.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    public static String getHttpRequestIp(HttpServletRequest request) {

        String xFor = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = request.getHeader("X-Real-IP");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }
}
