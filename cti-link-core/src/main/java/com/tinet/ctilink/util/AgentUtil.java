package com.tinet.ctilink.util;

public class AgentUtil {
	public static String getGwIp(String location){
		String[] gwStr = location.split("@");
		if(gwStr.length == 2){
			String[] ipStr = gwStr[1].split(":");
				return ipStr[0];
		}
		return null;
	}
}
