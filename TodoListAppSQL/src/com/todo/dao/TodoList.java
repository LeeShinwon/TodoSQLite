package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.*;

public class TodoList {
	Connection con;
	
	private List<TodoItem> list;

	public TodoList() {
		this.con = DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)"
				+" values (?,?,?,?,?);";
		PreparedStatement pstmt;
		
		int count=0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index, TodoList l) {
		String sql1 = "insert into deletelist (id,title, memo, category, current_date, due_date,is_completed, take_day, importance)"
				+" values (?,?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		
		int count=0;
		try {
			TodoItem t=l.getList().get(1);//initialize
			for(TodoItem t1:l.getList()) {
				if(t1.getId()==index) {
					t=t1;
					break;
				}
			}
			pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, t.getId());
			pstmt.setString(2, t.getTitle());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getCategory());
			pstmt.setString(5, t.getCurrent_date());
			pstmt.setString(6, t.getDue_date());
			pstmt.setInt(7, t.getIs_comp());
			pstmt.setInt(8, t.getTake_day());
			pstmt.setInt(9, t.getImportance());
			count = pstmt.executeUpdate();
			pstmt.close();
			
			if(count>0) {
				String sql = "delete from list where id=?;";
				count=0;
				try {
					pstmt= con.prepareStatement(sql);
					pstmt.setInt(1, index);
					count= pstmt.executeUpdate();
					pstmt.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
			
	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	public int  updateItem(TodoItem t){
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();//업데이트하고, 정상적으로 업데이트가 되었으면 양의 정수를 반환하기 때문에 count로 정상 업데이트가 되었는지 확인 할 수 있다.
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem>  list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from list";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				int take_day = rs.getInt("take_day");
				int importance = rs.getInt("importance");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, current_date, due_date);
				t.setId(id);
				t.setImportance(importance);
				t.setTake_day(take_day);
				t.setIs_comp(is_comp);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getList(String keyword){//functon overloading
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" +keyword +"%";
		try {
			String sql = "select * from list where title like ? or memo like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				int take_day = rs.getInt("take_day");
				int importance = rs.getInt("importance");
				int is_comp = rs.getInt("is_completed");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, category, description, current_date, due_date);
				t.setId(id);
				t.setImportance(importance);
				t.setTake_day(take_day);
				t.setIs_comp(is_comp);
				list.add(t);
			}
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = con.createStatement();
			String sql = "select count(id) from list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count= rs.getInt("count(id)");
			stmt.close();
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("[전체 목록, 총 "+list.size()+"개]");
		for(int i=0; i<list.size();i++) {
			System.out.println((i+1)+". "+list.get(i).toString());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		PreparedStatement pstmt;
	
		try {
			String sql = "select * from list where title like ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String vstitle = rs.getString("title");
				if (title.equals(vstitle)) {
					pstmt.close();
					return true;
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void importData(String filename) {//초기 데이터 이전 - todolist.txt에서 todolist.db로 데이터 이전 
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"
					+ " values (?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if(count >0) {
					records++;
				}
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select distinct category from list";//중복을 제외한 결과 받기
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String cate) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "select * from list where category = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cate);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				int take_day = rs.getInt("take_day");
				int importance = rs.getInt("importance");
				int is_comp = rs.getInt("is_completed");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, category, description, current_date, due_date);
				t.setId(id);
				t.setImportance(importance);
				t.setTake_day(take_day);
				t.setIs_comp(is_comp);
				list.add(t);
			}
			pstmt.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from list order by "+ orderby;
			if(ordering==0) {
				sql+=" desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				int take_day = rs.getInt("take_day");
				int importance = rs.getInt("importance");
				int is_comp = rs.getInt("is_completed");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, category, description, current_date, due_date);
				t.setId(id);
				t.setImportance(importance);
				t.setTake_day(take_day);
				t.setIs_comp(is_comp);
				list.add(t);
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int completeItem(int num) {
		System.out.println("완료하는 데 소요된 일 수 : ");
		Scanner sc = new Scanner(System.in);
		int day = sc.nextInt();
		Statement stmt;
		String sql = "update list set is_completed='1' "+", take_day='"+day+"' "
				+ "where id ='"+num+"';";
		int count=0;
		try {
			stmt = con.createStatement();
			count= stmt.executeUpdate(sql);//update된 것의 개수?
			
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}

	public int importantItem(int num) {
		Scanner sc = new Scanner(System.in);
		System.out.println("중요도 입력 : ");
		int importance= sc.nextInt();
		
		Statement stmt;
		String sql = "update list set importance = '"+importance+"' "
				+ "where id ='"+num+"';";
		int count=0;
		try {
			stmt = con.createStatement();
			count= stmt.executeUpdate(sql);//update된 것의 개수?
			
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}

	public ArrayList<TodoItem> getListDel() {
		ArrayList<TodoItem>  list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "select * from deletelist";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				int take_day = rs.getInt("take_day");
				int importance = rs.getInt("importance");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, category, description, current_date, due_date);
				t.setId(id);
				t.setImportance(importance);
				t.setTake_day(take_day);
				t.setIs_comp(is_comp);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int restore(int num) {
		int count=0;
		Statement stmt;
		
		try {
			stmt = con.createStatement();
			String sql = "select * from deletelist where id='"+num+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int id = rs.getInt("id");
			int take_day = rs.getInt("take_day");
			int importance = rs.getInt("importance");
			String category = rs.getString("category");
			String title = rs.getString("title");
			String description = rs.getString("memo");
			String due_date = rs.getString("due_date");
			String current_date = rs.getString("current_date");
			int is_comp = rs.getInt("is_completed");
			TodoItem t = new TodoItem(title, category, description, current_date, due_date);
			t.setId(id);
			t.setImportance(importance);
			t.setTake_day(take_day);
			t.setIs_comp(is_comp);
			if(t!=null) {
				String sql1 = "insert into list (id,title, memo, category, current_date, due_date,is_completed, take_day, importance)"
						+" values (?,?,?,?,?,?,?,?,?);";
				PreparedStatement pstmt;
				
				count=0;
				try {
					pstmt = con.prepareStatement(sql1);
					pstmt.setInt(1, t.getId());
					pstmt.setString(2, t.getTitle());
					pstmt.setString(3, t.getDesc());
					pstmt.setString(4, t.getCategory());
					pstmt.setString(5, t.getCurrent_date());
					pstmt.setString(6, t.getDue_date());
					pstmt.setInt(7, t.getIs_comp());
					pstmt.setInt(8, t.getTake_day());
					pstmt.setInt(9, t.getImportance());
					count = pstmt.executeUpdate();
					pstmt.close();
					
					if(count>0) {
						sql = "delete from deletelist where id=?;";
						count=0;
						try {
							pstmt= con.prepareStatement(sql);
							pstmt.setInt(1, num);
							count= pstmt.executeUpdate();
							pstmt.close();
							
						}catch(SQLException e) {
							e.printStackTrace();
						}
				}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				stmt.close();
			}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return count;
	}
}
