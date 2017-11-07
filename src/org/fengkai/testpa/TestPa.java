package org.fengkai.testpa;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fengkai.testpa.entity.JingDongShengXianEntity;

public class TestPa {

	private URL url = null;
	private URLConnection urlconn = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;

	LinkedBlockingQueue<JingDongShengXianEntity> queue = null;

	public static void main(String[] args) {
		long l1 = System.currentTimeMillis();
		TestPa test = new TestPa();
		String regex = "\\/\\/item.*html";
		String urlStr = "https://list.jd.com/list.html?cat=12218,12221";
		System.out.println("AAAAA");
		List<String> list = test.jingdongPaChongShengXian(urlStr, regex);
		System.out.println(list.size());
		test.writeToQueue(list);
		long l2 = System.currentTimeMillis();
		System.out.println(l2 - l1);
	}

	/**
	 * 获取京东的主页连接
	 * 
	 * @param urlStr
	 * @param regex
	 */
	public List<String> jingdongPaChongShengXian(String urlStr, String regex) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		try {
			url = new URL(urlStr);
			urlconn = url.openConnection();
			pw = new PrintWriter(new FileWriter("D:/SiteURL.txt"), true);
			urlconn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
			br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			String buf = null;
			while ((buf = br.readLine()) != null) {
				Matcher buf_m = p.matcher(buf);
				while (buf_m.find()) {
					String temp = buf_m.group();
					pw.write(buf_m.group());
					list.add(temp);
				}
			}
			System.out.println("爬虫结束");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.close();
		}

		return list;
	}

	public void writeToQueue(List<String> list) {// , String regexName, String
													// regexPrice, String
													// regexWeight) {
		queue = new LinkedBlockingQueue<JingDongShengXianEntity>();
		ExecutorService es = Executors.newFixedThreadPool(10);
		JingDongShengXianEntity entity = null;
		// Pattern patternName = Pattern.compile(regexName);
		// Pattern patternPrice = Pattern.compile(regexPrice);
		// Pattern patternWeight = Pattern.compile(regexWeight);
		for (final String str : list) {//
			es.execute(new Runnable() {
				@Override
				public void run() {
					try {
						url = new URL("https:" + str);
						urlconn = url.openConnection();
						urlconn.setRequestProperty("User-Agent",
								"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
						br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
						pw = new PrintWriter(new FileWriter("D:/testtt/aaa" + System.currentTimeMillis() + ".txt"),
								true);
						String buf = null;
						while ((buf = br.readLine()) != null) {
							pw.write(buf);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						pw.close();
					}
				}
			});

		}
	}

	public static void pachong() {
		URL url = null;
		URLConnection urlconn = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		// String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";
		String regex = "<img(.*?)>";// 识别所有的图片
		// src="https://img3.doubanio.com/view/photo/sqxs/public/p2499399150.jpg"
		// alt="鍥剧墖" />
		String regexJpg = "https://img[1,3]\\.doubanio\\.com\\/view\\/*jpg";
		Pattern p = Pattern.compile(regex);
		Pattern pJpg = Pattern.compile(regexJpg);
		try {
			url = new URL("https://movie.douban.com/subject/3604148/?tag=%E7%83%AD%E9%97%A8&from=gaia");
			urlconn = url.openConnection();
			pw = new PrintWriter(new FileWriter("D:/SiteURL.txt"), true);
			urlconn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
			br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			String buf = null;
			while ((buf = br.readLine()) != null) {
				Matcher buf_m = p.matcher(buf);
				while (buf_m.find()) {
					Matcher buf_jpg = pJpg.matcher(buf_m.group());
					while (buf_jpg.find()) {
						pw.println(buf_jpg.group());
					}
				}
			}
			System.out.println("鐖櫕缁撴潫");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.close();
		}
	}
}