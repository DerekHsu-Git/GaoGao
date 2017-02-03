package com.arbo.lib.util;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;



public class NetUtil {

	public static String getNetIP() {
		String ipAddress = "";

		Enumeration<NetworkInterface> networkInterface = null;
		try {
			networkInterface = NetworkInterface.getNetworkInterfaces();

			while (networkInterface.hasMoreElements()) {
				NetworkInterface netInterface = networkInterface.nextElement();

				String name = netInterface.getName().toLowerCase();

				if (!"eth0".equals(name) && !"wlan0".equals(name)) {
					continue;
				}

				Enumeration<InetAddress> enumIpAddr = netInterface.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = enumIpAddr.nextElement();
//					if (inetAddress.isLoopbackAddress()) {
//						continue;
//					}
					ipAddress = inetAddress.getHostAddress().toString();
					if (ipAddress.contains("::")) {// ipV6的地址

						continue;
					}
//					return ipAddress;
				}


			}


		} catch (SocketException e) {
			e.printStackTrace();
		}


		return ipAddress;
	}

}
