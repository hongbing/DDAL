/**
 * 
 */
package com.weibo.newbie.ddal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.weibo.newbie.common.ShardingAlgorithm;

/**
 * @author hongbing
 *
 */
public class ConfigService {
	
	ShardingAlgorithm sAlgorithm = new ShardingAlgorithm();
	
	public List<String> getShardingInfoFromFile() throws IOException {
		String s = null;
		List<String> list = new ArrayList<String>();
		File file = new File("shardingConfig");
		if (file.isFile() && file != null) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			while( (s = bufferedReader.readLine()) != null) {
				list.add(s);
			}
		}
		return list;
	}
	

	public static void main(String[] args) throws IOException {
		ConfigService configService = new ConfigService();
		configService.getShardingInfoFromFile();
	}
}
