package com.zhan.framework.utils;

import android.os.Environment;

import com.zhan.framework.common.context.GlobalContext;

import java.io.File;
import java.math.BigDecimal;

/**
 * 作者：伍岳 on 15-7-21 15:39
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public class PathUtils {
	//缓存路径
	private final static String CACHE_PATH = "Cache";

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	private static File getAvailableCacheDir() {
		if (isExternalStorageWritable()) {
			return GlobalContext.getInstance().getExternalCacheDir();
		} else {
			return GlobalContext.getInstance().getCacheDir();
		}
	}

	public static String checkAndMkdirs(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}
	
	public static File getExternalCacheFilesDir() {
		if (isExternalStorageWritable()) {
			return GlobalContext.getInstance().getExternalFilesDir(CACHE_PATH);
		} else {
			return null;
		}
	}

	
	/**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
	public static long recursionDeleteFile(File file) {
		if (file == null) {
			return 0;
		}
		long fileSize = 0;
		if (file.isFile()) {
			fileSize = file.length();
			file.delete();
			return fileSize;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return 0;
			}
			for (File f : childFile) {
				fileSize += recursionDeleteFile(f);
			}
			file.delete();
		}
		return fileSize;
	}
	
	/** 
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节) 
     *  
     * @param bytes 
     * @return 
     */  
	public static String bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
		if (returnValue > 1)
			return (returnValue + "MB");
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
		return (returnValue + "KB");
	} 
}
