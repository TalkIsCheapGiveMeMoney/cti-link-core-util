package com.tinet.ctilink.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 * 文件相关工具类。
 * <p>
 * 文件名： FileUtils.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD. All rights reserved.
 * 
 * @author 周营昭
 * @author 何治
 * @author 安静波
 * @since 1.0
 * @version 1.0
 */
public class FileUtils {

	public static byte[] getFileToBytes(String file) throws IOException {
		ClassPathResource res = new ClassPathResource(file);// ① 获取图片资源
		return FileCopyUtils.copyToByteArray(res.getFile());// ② 读取图片文件的数据
	}

	/**
	 * Move a file. The renameTo method does not allow action across NFS mounted
	 * filesystems. this method is the workaround.
	 * 
	 * @param file
	 *            The existing File.
	 * @param fileName
	 *            The new File.
	 * @param toPath
	 *            The new File Path.
	 * @return <code>true</code> if and only if the moving is succeeded;
	 *         <code>false</code> otherwise
	 */
	public static Boolean moveFile(File file, String fileName, String toPath) {
		Boolean flag = true;
		try {
			if (file.renameTo(new File(toPath + "/" + fileName))) {
				return flag;
			}
			if (copy(file, new File(toPath + "/" + fileName))) {
				file.delete();
				return flag;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * Copy a file.
	 * 
	 * @param file
	 *            The existing File.
	 * @param fileName
	 *            The new File.
	 * @param toPath
	 *            The new File Path.
	 * @return <code>true</code> if and only if the moving is succeeded;
	 *         <code>false</code> otherwise
	 */
	public static Boolean copyFile(File file, String fileName, String toPath) {
		Boolean flag = true;
		try {
			SystemCmd.executeCmd("mkdir -p " + toPath);
			String cmdStr = "cp -f" + file.getPath() + " " + toPath;
			if ("".equals(SystemCmd.executeCmd(cmdStr))) {
				return flag;
			}
			if (copy(file, new File(toPath + fileName))) {
				return flag;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * Copy a File
	 * 
	 * @param fromFile
	 *            The existing File
	 * @param toFile
	 *            The new File
	 * @return <code>true</code> if and only if the writing is succeeded;
	 *         <code>false</code> otherwise
	 */
	private final static boolean copy(File fromFile, File toFile) {
		try {
			FileInputStream in = new FileInputStream(fromFile);
			FileOutputStream out = new FileOutputStream(toFile);
			BufferedInputStream inBuffer = new BufferedInputStream(in);
			BufferedOutputStream outBuffer = new BufferedOutputStream(out);

			int theByte = 0;

			while ((theByte = inBuffer.read()) > -1) {
				outBuffer.write(theByte);
			}

			outBuffer.close();
			inBuffer.close();
			out.close();
			in.close();

			// cleanup if files are not the same length
			if (fromFile.length() != toFile.length()) {
				toFile.delete();
				return false;
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public final static void rmFile(String fileName) {
		File file = new File(fileName);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return;
		} else {
			if (file.isFile()) {
				file.delete();
			}
		}
	}

	public final static void createFile(String fileName, String content) {
		File file = new File(fileName);
		try {
			file.createNewFile();
			if (StringUtils.isNotEmpty(content)) {
				FileWriter fileWriter = new FileWriter(fileName, true);
				fileWriter.write(content);
				fileWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * public void tarExcelFileAndExport(String path, HSSFWorkbook workbook,
	 * OutputStream os){ writeExcelFile(path, workbook);
	 * 
	 * String cmd = "/usr/local/bin/sox -V6 " + Const.SOUNDS_IVR_VOICE_ABS_PATH
	 * + srcFile + " -c 1 -b 16 -r 8k -S " + Const.SOUNDS_IVR_VOICE_ABS_PATH +
	 * dstFile; String result = SystemCmd.executeCmd(cmd); }
	 * 
	 * public void writeExcelFile(String path, HSSFWorkbook workbook){ File f =
	 * new File(path); OutputStream os = null; try{ os = new
	 * FileOutputStream(f); workbook.write(os); }catch(Exception e){
	 * e.printStackTrace(); }finally{ try{ os.close(); }catch(Exception e){
	 * e.printStackTrace(); } } }
	 */
}
