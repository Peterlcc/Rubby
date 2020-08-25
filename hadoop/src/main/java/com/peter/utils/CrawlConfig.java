package com.peter.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CrawlConfig {
	private static List<String> userAgentList=null;
	static {
		InputStream inputStream = CrawlConfig.class.getClassLoader().getResourceAsStream("user-agent.txt");
		try {
			userAgentList=IOUtils.readLines(inputStream,"utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<String> getUserAgent() {
		return userAgentList;
	}
}
