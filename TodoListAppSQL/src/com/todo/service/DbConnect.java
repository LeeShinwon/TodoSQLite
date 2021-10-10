package com.todo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnect {
	private static Connection con = null;
	
	public static void closeConnecton() {
		if(con !=null) {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(con==null) {
			try {
				Class.forName("org.sqlite.JDBC");//SQLite JDBC 체크 
				con = DriverManager.getConnection("jdbc:sqlite:"+"todolist.db");//SQLite db 파일에 연결 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
}
