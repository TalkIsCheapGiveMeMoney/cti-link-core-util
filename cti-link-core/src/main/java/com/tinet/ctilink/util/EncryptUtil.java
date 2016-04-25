package com.tinet.ctilink.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class EncryptUtil {
	private static String[] A0 = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	private static String[] A1 = { "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T" };
	private static String[] A2 = { "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D" };
	private static String[] A3 = { "E", "F", "G", "H", "I", "J", "K", "L", "M", "N" };
	private static String[] A4 = { "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X" };
	private static String[] A5 = { "Y", "Z", "A", "B", "C", "D", "E", "F", "G", "H" };
	private static String[] A6 = { "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R" };
	private static String[] A7 = { "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B" };
	private static String[] A8 = { "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };
	private static String[] A9 = { "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V" };

	public static String encryp(int enterpriseId, String number) {
		// 号码小于11位, 且号码中有非数字, 暂不进行加密
		if (number == null || (number.length() < 11 || number.length() > 15) || !StringUtils.isNumeric(number)) {
			return number;
		}
		// seed企业号最后一位, sessionId企业号前四位 , middleNumber电话号码中间4位 ,
		// tailNumber电话号码最后四位
		String e = enterpriseId + "";
		int seed = Integer.parseInt(e.substring(e.length() - 1, e.length()));
		int sessionId = Integer.parseInt(e.substring(0, 4));

		String begin = number.substring(0, number.length() - 8);
		String middle = number.substring(number.length() - 8, number.length() - 4);
		String tail = number.substring(number.length() - 4, number.length());

		String sec = encryp(seed, sessionId, middle, tail);

		// 加密后的号码 格式为 111ABCD1111
		return begin + sec + tail;
	}

	public static String decryp(int enterpriseId, String number) {
		// 号码小于11位, 中间不包含非数字, 暂不进行解密
		if (number == null || (number.length() < 11 || number.length() > 15) || StringUtils.isNumeric(number)) {
			return number;
		}

		String begin = number.substring(0, number.length() - 8);
		String sec = number.substring(number.length() - 8, number.length() - 4);
		String tail = number.substring(number.length() - 4, number.length());

		// 判断号码是否为111ABCD5555格式的
		if (!StringUtils.isNumeric(begin) || !StringUtils.isNumeric(tail) || !Pattern.matches("^[A-Z]{4}$", sec)) {
			return number;
		}

		// seed企业号最后一位, sessionId企业号前四位 , middleNumber电话号码中间4位 ,
		// tailNumber电话号码最后四位

		String e = enterpriseId + "";
		int seed = Integer.parseInt(e.substring(e.length() - 1, e.length()));
		int sessionId = Integer.parseInt(e.substring(0, 4));

		String middle = decryp(seed, sessionId, sec, tail);

		return begin + middle + tail;
	}

	public static String encryp(int seed, int sessionId, String number, String tailNumber) {
		// seed 使用企业id 相关，使得每个企业的之间不同 5-9
		// sessionId 当前登录的session 位数和number一致
		// number 实际号码支持4-5位
		// tailNumber 尾号使得每个号码加密后的密文和尾号有关系支持3-4位顶
		// 进行字母加密 根据tailNumber第一位来获取A数字
		String[] encrypArray = getEncryptArray(Integer.parseInt(tailNumber.substring(0, 1)));
		// System.out.println("加密前number=" + number + " tail=" + tailNumber);
		int numberLength = number.length();
		int src;
		try {
			src = Integer.parseInt(number);
		} catch (Exception e) {
			return "error";
		}
		byte a[] = new byte[numberLength];
		for (int i = 0; i < numberLength; i++) {
			a[i] = (byte) (number.charAt(i) - '0');
		}

		byte b[] = new byte[numberLength];
		for (int i = 0; i < numberLength; i++) {
			if (a[i] > seed) {
				b[i] = (byte) (seed + 10 - a[i]);
			} else {
				b[i] = (byte) (seed - a[i]);
			}
		}
		byte t = b[0];
		for (int i = 0; i < numberLength; i++) {
			if (i == numberLength - 1) {
				b[i] = t;
			} else {
				b[i] = b[i + 1];
			}
		}
		int tail;
		try {
			tail = Integer.parseInt(tailNumber);
		} catch (Exception e) {
			return "error";
		}
		tail += sessionId;
		int p = (int) Math.pow(10, tailNumber.length());
		if (tail > p) {
			tail -= p;
		}

		int tailNumberLength = tailNumber.length();
		tailNumber = String.valueOf(tail);
		for (int i = String.valueOf(tail).length(); i < tailNumberLength; i++) {
			tailNumber = "0" + tailNumber;
		}
		byte ta[] = new byte[tailNumberLength];
		for (int i = 0; i < tailNumberLength; i++) {
			ta[i] = (byte) (tailNumber.charAt(i) - '0');
		}
		for (int i = 0; i < tailNumberLength; i++) {
			b[i] = (byte) ((b[i] + ta[i]) % 10);
		}
		// System.out.println("加密后:" + b[0] + b[1] + b[2] +b[3]);

		// System.out.println("字母加密后:" + encrypArray[b[0]] + encrypArray[b[1]] +
		// encrypArray[b[2]] +encrypArray[b[3]]);

		return "" + encrypArray[b[0]] + encrypArray[b[1]] + encrypArray[b[2]] + encrypArray[b[3]];
	}

	public static String decryp(int seed, int sessionId, String fake, String tailNumber) {
		// seed 使用企业id 相关，使得每个企业的之间不同 5-9
		// sessionId 当前登录的session 位数和number一致
		// number 加密后号码支持4-5位
		// tailNumber 尾号使得每个号码加密后的密文和尾号有关系支持3-4位
		// System.out.println("字母解密前number=" + fake + " tail=" + tailNumber);
		// 将字母解密为数字
		String number = "";
		String[] encrypArray = getEncryptArray(Integer.parseInt(tailNumber.substring(0, 1)));
		char[] charArray = fake.toCharArray();
		for (int x = 0; x < charArray.length; x++) {
			for (int y = 0; y < encrypArray.length; y++) {
				if (encrypArray[y].equals(charArray[x] + "")) {
					number += y + "";
					break;
				}
			}
		}
		// System.out.println("数字解密前number=" + number + " tail=" + tailNumber);
		int numberLength = number.length();
		int src;
		try {
			src = Integer.parseInt(number);
		} catch (Exception e) {
			return "error";
		}
		byte a[] = new byte[numberLength];
		for (int i = 0; i < numberLength; i++) {
			a[i] = (byte) (number.charAt(i) - '0');
		}
		int tail;
		try {
			tail = Integer.parseInt(tailNumber);
		} catch (Exception e) {
			return "error";
		}
		tail += sessionId;
		int p = (int) Math.pow(10, tailNumber.length());
		if (tail > p) {
			tail -= p;
		}

		int tailNumberLength = tailNumber.length();
		tailNumber = String.valueOf(tail);
		for (int i = String.valueOf(tail).length(); i < tailNumberLength; i++) {
			tailNumber = "0" + tailNumber;
		}
		byte ta[] = new byte[tailNumberLength];
		for (int i = 0; i < numberLength; i++) {
			ta[i] = (byte) (tailNumber.charAt(i) - '0');
		}
		for (int i = numberLength - 1; i >= 0; i--) {
			a[i] = (byte) ((a[i] + 10 - ta[i]) % 10);
		}
		byte t = a[numberLength - 1];
		for (int i = numberLength - 1; i >= 0; i--) {
			if (i == 0) {
				a[i] = t;
			} else {
				a[i] = a[i - 1];
			}
		}
		byte b[] = new byte[numberLength];
		for (int i = 0; i < numberLength; i++) {
			if (a[i] <= seed) {
				b[i] = (byte) (seed - a[i]);
			} else {
				b[i] = (byte) (seed + 10 - a[i]);
			}
		}

		// System.out.println("解密后:" + b[0] + b[1] + b[2] +b[3]);
		return "" + b[0] + b[1] + b[2] + b[3];
	}

	private static String[] getEncryptArray(int n) {
		switch (n) {
		case 0:
			return A0;
		case 1:
			return A1;
		case 2:
			return A2;
		case 3:
			return A3;
		case 4:
			return A4;
		case 5:
			return A5;
		case 6:
			return A6;
		case 7:
			return A7;
		case 8:
			return A8;
		case 9:
			return A9;
		}
		return null;
	}

	public static String generateAuthCode(int enterpriseId, int seed2) {
		String e = enterpriseId + "";
		// seed企业号最后一位
		int sessionId = Integer.parseInt(e.substring(e.length() - 5, e.length()));
		int seed1 = Integer.parseInt(e.substring(e.length() - 1, e.length()));

		String authCode = MD5Encoder.encode(seed2 + "&$ WeBCaLl+-^%" + sessionId + enterpriseId + "LlAcBeW")
				.substring(5, 32 - seed1).toUpperCase();

		return getEncryptArray(seed2)[seed1] + authCode + getEncryptArray(seed1)[seed2];
	}

	public static void main(String[] argv) {
		/*
		 * DESUtil des = new DESUtil("3000021");
		 * 
		 * String str1 = "2630"; // DES 加密字符串 String str2 =
		 * des.encryptStr(str1); // DES 解密字符串 String deStr =
		 * des.decryptStr(str2); System.out.println(" 加密前： " + str1);
		 * System.out.println(" 加密后： " + str2); System.out.println(" 解密后： " +
		 * deStr);
		 * 
		 * byte[] sourceByte = new byte[4]; byte high = (byte)((2630 &
		 * 0xff00)>>8); byte low = (byte)(2630 & 0x00ff); sourceByte[0] = high;
		 * sourceByte[1] = low;
		 * 
		 * byte[] encryptByte = des.encryptByte(sourceByte); byte[] decryptByte
		 * = des.decryptByte(encryptByte); System.out.println(" 密文长度: " +
		 * encryptByte.length); System.out.println(" 加密前： " + sourceByte[0] +
		 * sourceByte[1]); System.out.println(" 加密后： " + encryptByte[0] +
		 * encryptByte[1]); System.out.println(" 解密后： " + decryptByte[0] +
		 * decryptByte[1] + decryptByte[2] + decryptByte[3]);
		 */
		/*
		 * encryp(5,2631,7922); encryp(5,2631,1234);
		 * 
		 * encryp(5,1362,7922); encryp(5,1362,1234);
		 * 
		 * encryp(5,9999,7922); encryp(5,9999,1234);
		 * 
		 * encryp(5,1111,7922); encryp(5,1111,1234);
		 * 
		 * encryp(5,1212,7922); encryp(5,3131,1234);
		 * 
		 * encryp(5,1212,0000); encryp(5,3131,8888);
		 * 
		 * encryp(5,9600,0000,0000); encryp(5,9600,0000,9999);
		 * 
		 * encryp(6,9600,0000,0000); encryp(6,9600,0000,9999);
		 * 
		 * encryp(1,9600,000,0000); encryp(1,9600,0000,9999);
		 */
		String s;
		s = encryp(9, 9617, "0000", "0000");
		s = decryp(9, 9617, s, "0000");

		s = encryp(7, 9617, "0000", "8888");
		s = decryp(7, 9617, s, "8888");
		s = encryp(7, 9617, "0000", "7777");
		s = decryp(7, 9617, s, "7777");
		s = encryp(7, 9617, "0000", "1111");
		s = decryp(7, 9617, s, "1111");
		s = encryp(7, 9617, "0000", "9999");
		s = decryp(7, 9617, s, "9999");
		s = encryp(7, 9617, "0000", "7893");
		s = decryp(7, 9617, s, "7893");

		s = encryp(7, 9617, "2630", "7893");
		s = decryp(7, 9617, s, "7893");
		//
		//// String x= "8001234";
		//// System.out.println(x.substring(0, x.length()-8));
		//// System.out.println(x.substring(x.length()-4, x.length()));
		//// System.out.println(x.substring(x.length()-8, x.length()-4));
		////
		//// x= "134*1111*1234";
		//// System.out.println(x.substring(0, x.length()-10));
		//// System.out.println(x.substring(x.length()-9, x.length()-5));
		//// System.out.println(x.substring(x.length()-4, x.length()));
		//
		//
		// s = encryp(7,2002,"2630","1111");
		// s = decryp(7,2002,s,"1111");
		//
		// s = encryp(7,2002,"2630","1112");
		// s = decryp(7,2002,s,"1112");
		//
		// s = encryp(7,2002,"2630","1113");
		// s = decryp(7,2002,s,"1113");
		//
		// s = encryp(7,2002,"2630","1114");
		// s = decryp(7,2002,s,"1114");
		//
		// s = encryp(7,2002,"2630","1115");
		// s = decryp(7,2002,s,"1115");
		//
		// s = encryp(7,2002,"2630","1116");
		// s = decryp(7,2002,s,"1116");

		s = decryp(2100003, "134AAAA0000");
		System.out.println(s);
		s = decryp(2100003, "13AAAAA0000");
		System.out.println(s);
		s = decryp(2100003, "测试号码");
		System.out.println(s);
		s = decryp(2100003, "13AAAAA0AA0");
		System.out.println(s);
		s = decryp(2100003, "13400001234");
		System.out.println(s);

		s = decryp(2100003, "11111113400001ADB4");
		System.out.println(s);
	}
}
