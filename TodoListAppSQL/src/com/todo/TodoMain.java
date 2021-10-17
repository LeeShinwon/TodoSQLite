package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.*;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt");
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("제목순으로 정렬하였습니다. ");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다. ");
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date_asc":
				System.out.println("날짜순으로 정렬하였습니다. ");
				TodoUtil.listAll(l,"due_date",1);
				break;
			
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다. ");
				TodoUtil.listAll(l,"due_date",0);
				break;
			
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				TodoUtil.FindList(l, sc.next());
				break;
				
			case "find_cate":
				TodoUtil.FindCateList(l, sc.next());
				break;
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
				
			case "exit":
				quit = true;
				break;
				
			case "comp":
				TodoUtil.completeItem(l,sc.nextInt());
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l,1);
				break;
			case "importance":
				TodoUtil.importantItem(l,sc.nextInt());
				break;
			case "ls_importance":
				System.out.println("중요도순으로 정렬하였습니다. ");
				TodoUtil.listAll(l,"importance",0);
				break;
			case "ls_del":
				TodoUtil.listDel(l);
				break;
			case "restore":
				TodoUtil.restore(l,sc.nextInt());
				break;
				
			default:
				System.out.println("정확한 명령어를 입력하세요. (도움말 - help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l,"todolist.txt");
	}
}
