package com.digitalLab.Util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.net.UnknownHostException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpUtil {
    public static String getIpAddress(){
        String result = null;
        try {
            result = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            result = null;
        }
        return result;
    }
}
