package org.fengkai.testpa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadPic {

	public static void main(String[] args) throws IOException {
		File file = null;
		File fileOut = null;
		InputStream is = null;
		BufferedReader br = null;
		Reader reader = null;
		try {
			// 到底提交了么
			file = new File("D:/SiteURL.txt");
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String buff = null;
			while ((buff = br.readLine()) != null) {
				System.out.println(buff);
				is = getInputStreamByGet(buff);
				fileOut = new File("D:/out/" + System.currentTimeMillis() + ".jpg");
				saveData(is, fileOut);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static InputStream getInputStreamByGet(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = conn.getInputStream();
				return inputStream;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将服务器响应的数据流存到本地文件
	public static void saveData(InputStream is, File file) {
		try (BufferedInputStream bis = new BufferedInputStream(is);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));) {
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = bis.read(buffer)) != -1) {// 顺序往下读
				bos.write(buffer, 0, len);
				bos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
