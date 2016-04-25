package com.tinet.ctilink.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行Linux系统命令工具类
 * <p>
 * 文件名： SystemCmd.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD. All rights reserved.
 * 
 * @author 安静波
 * @since 1.0
 * @version 1.0
 */

public class SystemCmd {
	public static List<String> cmdHistory = new ArrayList<String>();

	public static String executeCmd(String cmd) {
		Runtime r = Runtime.getRuntime();
		try {
			System.out.println("[执行cmd=" + cmd + "]");
			cmdHistory.add(cmd);
			Process p = r.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String inline;
			while (null != (inline = br.readLine())) {
				sb.append(inline).append("\n");
			}
			br.close();
			br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while (null != (inline = br.readLine())) {
				sb.append(inline).append("\n");
			}
			br.close();
			p.destroy();
			System.out.println("[执行cmd=" + cmd + "] 返回结果:" + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	/**
	 * 执行特殊命令,如带通配符*号等
	 * 
	 * @param cmd
	 *            命令，如 String[] delCmd = {"sh", "-c",
	 *            "/bin/rm -f /var/nfs/logo/logo_tmp/3000001-2001-*"};
	 * @return
	 */
	public static String executeCmd(String[] cmd) {
		Runtime r = Runtime.getRuntime();
		try {
			System.out.println("[执行cmd=");
			for (int i = 0; i < cmd.length; i++) {
				System.out.println(cmd[i]);
				cmdHistory.add(cmd[i]);
			}
			System.out.println("]");
			Process p = r.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String inline;
			while (null != (inline = br.readLine())) {
				sb.append(inline).append("\n");
			}
			br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while (null != (inline = br.readLine())) {
				sb.append(inline).append("\n");
			}
			System.out.println("[执行cmd=");
			for (int i = 0; i < cmd.length; i++) {
				System.out.println(cmd[i]);
			}
			System.out.println("]");
			System.out.println("返回结果:" + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public static void main(String argv[]) {
		String[] delCmd = { "sh", "-c", "/bin/rm -f /var/nfs/logo/logo_tmp/3000001-2001-*" };
		System.out.println("[执行cmd=");
		for (int i = 0; i < delCmd.length; i++) {
			System.out.println(delCmd[i]);
		}
		System.out.println("]");
	}
}
