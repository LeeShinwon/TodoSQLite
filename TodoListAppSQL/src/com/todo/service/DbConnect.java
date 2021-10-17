package com.todo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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
				//Statement stat2 = con.createStatement();
				//String sql2 = "alter table list add column importance integer default 0;";
				//int cnt = stat2.executeUpdate(sql2);//update된 것의 개수?
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
}
