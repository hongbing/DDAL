
 /**
 * 
 */
package com.weibo.newbie.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


 /**
 * @Classname：com.weibo.newbie.DAO.DBUtils 
 * @Description：
 *
 * @Author BING
 * @Date：2014-7-29
 *
 * @Copyright：Copyright (c) 2014
 */
public class DBHelper {

	public static final String JDBC_URL_PATTERN = "jdbc:mysql://%s:%s/%s";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "ddal_user";
	public static final String password = "123456";
	
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	
	public DBHelper() {
		
	}
	
	public PreparedStatement getStatement(String sql, String ip, String port, String dbName) {
		try {
			Class.forName(name);
			conn = DriverManager.getConnection(String.format(JDBC_URL_PATTERN, ip, port,dbName), user, password);
			if (conn != null) {
				pst = conn.prepareStatement(sql);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pst;
	}
	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
