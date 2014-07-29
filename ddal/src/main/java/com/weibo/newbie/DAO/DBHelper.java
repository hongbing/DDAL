
 /**
 * 
 */
package com.weibo.newbie.DAO;

import java.net.URL;
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
	public static final String user = "root";
	public static final String password = "root";
	
	
	public Connection conn = null;
	public PreparedStatement pst = null;
	
	public DBHelper(String sql, String ip, String port, String DBName) {
		try {
			Class.forName(name);
			conn = DriverManager.getConnection(String.format(JDBC_URL_PATTERN, ip, port, DBName), user, password);
			pst = conn.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
