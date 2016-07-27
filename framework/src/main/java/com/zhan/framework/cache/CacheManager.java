package com.zhan.framework.cache;

import android.util.Log;
import com.zhan.framework.utils.PathUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CacheManager {

	/**
	 * 缓存文件的后缀
	 */
	private static final String CACHE_EXTENSION = ".cache";

	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 */
	public static void saveObject(CacheData ser, String file) {
		String cacheFilePath=getCacheFilePath(file);
		if(cacheFilePath==null){
			return;
		}

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos=new FileOutputStream(cacheFilePath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
		} catch (Exception e) {

		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (Exception ignored) {
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception ignored) {
			}
		}
	}

	/**
	 * 读取对象
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static CacheData readObject(String fileName) {
		String cacheFilePath=getCacheFilePath(fileName);

		if(cacheFilePath==null){
			return null;
		}

		File data = new File(cacheFilePath);
		if (!data.exists()){
			return null;
		}

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(cacheFilePath);
			ois = new ObjectInputStream(fis);
			return (CacheData) ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			// 反序列化失败 - 删除缓存文件
			if (e instanceof InvalidClassException) {
				data.delete();
			}
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}


	/**
	 * 清除所有的缓存
	 */
	public static void clearAllCache(){
		File cacheFile=PathUtils.getExternalCacheFilesDir();
		if(cacheFile==null){
			Log.e("CacheManager", "getExternalCacheFilesDir is null");
			return ;
		}
		PathUtils.recursionDeleteFile(cacheFile);
	}

	private static String getCacheFilePath(String fileName){
		File cacheFile=PathUtils.getExternalCacheFilesDir();
		if(cacheFile==null){
			Log.e("CacheManager", "getExternalCacheFilesDir is null");
			return null;
		}

		String cacheDir=cacheFile.getAbsolutePath();

		return  String.format("%s/%s%s",cacheDir,fileName,CACHE_EXTENSION);

	}
}
