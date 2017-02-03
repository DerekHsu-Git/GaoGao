package com.arbo.lib.util;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtil {
	/**
	 * 创建文件夹
	 *
	 * @param folderPath
	 *
	 * @return
	 */
	public static boolean createFolder(String folderPath) {
		File file = new File(folderPath);
		if (file.exists()) {
			return true;
		}

		return file.mkdirs();
	}

	/**
	 * 创建文件
	 *
	 * @param fileName
	 *
	 * @return
	 */
	public static boolean createFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {

			return true;
		}
		try {
			return file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除本地文件
	 *
	 * @param filePath
	 *
	 * @return
	 */
	public static boolean delFile(String filePath) {
		boolean isDel = false;
		File file = new File(filePath);
		if (file.exists()) {
			isDel = file.delete();

		} else {
			LogUtil.e("文件不存在");
		}
		return isDel;
	}

	/** */
	/**
	 * 文件重命名
	 *
	 * @param path    文件目录
	 * @param oldName 原来的文件名
	 * @param newName 新文件名
	 */
	public static void reNameFile(String path, String oldName, String newName) {
		if (!oldName.equals(newName)) {//新的文件名和以前文件名不同时,才有必要进行重命名
			File oldFile = new File(path + "/" + oldName);
			File newFile = new File(path + "/" + newName);
			if (!oldFile.exists()) {
				return;//重命名文件不存在
			}
			if (newFile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newName + "已经存在！");
			else {
				oldFile.renameTo(newFile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	/**
	 * 写数据到文件
	 */
	public static void write(String path, String conent) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(conent.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}






	/**
	 * 判断本地文件
	 *
	 * @param filePath
	 *
	 * @return
	 */
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}


	/**
	 * 文件重命名
	 *
	 * @param oldName 原来的文件名
	 * @param newName 新文件名
	 */
	public static void reNameFile(String oldName, String newName) {
		if (!oldName.equals(newName)) {//新的文件名和以前文件名不同时,才有必要进行重命名
			File oldFile = new File(oldName);
			File newFile = new File(newName);
			if (!oldFile.exists()) {
				return;//重命名文件不存在
			}
			if (newFile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newName + "已经存在！");
			else {
				oldFile.renameTo(newFile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	public static  String uri2FileString(Activity activity, Uri uri) {
		File file = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
				null, null);
		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor
				.getString(actual_image_column_index);
		//file = new File(img_path);
		return img_path;
	}

}
