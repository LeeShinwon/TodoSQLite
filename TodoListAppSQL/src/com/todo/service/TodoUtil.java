package com.todo.service;

import java.io.Writer;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, cate;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[항목 추가]\n"
				+ "제목 > ");
		
		title = sc.next();
		/*if (list.isDuplicate(title)) {
			System.out.printf("\n이미 존재하는 제목은 사용할 수 없습니다.");
			return;
		}*/
		System.out.println("카테고리 > ");
		cate = sc.next();
		
		System.out.println("내용 > ");
		sc.nextLine();
		desc = sc.nextLine().trim();
		/*System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		등록 시, 마감일자는 자동으로 current_date와 동일하다고 생각하여 사용자 입력이 아닌 자동 등록이 되도록 해놓았습니다.*/
		
		TodoItem t = new TodoItem(title, cate, desc);
		if(list.addItem(t)>0) {
			System.out.println("추가되었습니다. ");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		if(l.deleteItem(num)>0) {
			System.out.println("삭제되었습니다.");
		}
		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		
			System.out.println("새 제목 > ");
			String new_title = sc.next().trim();
		
			System.out.println("새 카테고리 > ");
			String cate = sc.next();
			
			sc.nextLine();
			
			System.out.println("새 내용 > ");
			String new_description = sc.nextLine().trim();
			
			System.out.println("새 마감일자 > ");
			String due_date = sc.nextLine().trim();
			
			String current_date = l.getList().get(num).getCurrent_date();
			
			
			TodoItem t = new TodoItem(new_title, cate,new_description,current_date,due_date);
			t.setId(num);
			
			if(l.updateItem(t)>0) {
				System.out.println("수정되었습니다. ");
			}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.getList().size()+"개]");
		for(TodoItem item: l.getList()) {
			System.out.println(item.toString());
		}
	}
	public static void saveList(TodoList l, String filename) {
		//FileWriter
		//todolist.txt에 
		try {
			Writer w = new FileWriter(filename);
			for(int i=0; i<l.getList().size(); i++) {
				w.write(l.getList().get(i).toSaveString());
			}
			w.close();
			System.out.println("모든 데이터가 저장되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadList(TodoList l, String filename) {
		//BufferedReader, FileReader, StringTokenizer 사용 
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String oneline;
			while((oneline = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String cate = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due = st.nextToken();
				String date = st.nextToken();
				
				
				TodoItem item = new TodoItem(title, cate, desc, date,due);
				l.addItem(item);
				//System.out.println(item.toString());
			}
			System.out.println(l.getList().size()+"개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("todolist.txt 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void FindList(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item: l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
	}
	public static void FindCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item:l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
	}
	public static void listCate(TodoList l) {
		int count=0;
		for(String item: l.getCategories()) {
			System.out.println(item +" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
}
