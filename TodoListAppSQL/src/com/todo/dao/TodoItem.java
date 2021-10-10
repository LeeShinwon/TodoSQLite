package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;
    


    public TodoItem(String title, String cate, String desc){//date가 정해지지 않은 경우 
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
        this.category=cate;
        this.due_date=current_date.split(" ")[0];
        
    }
    public TodoItem(String title, String cate, String desc, String date, String due){//date가 이미 정해진 경우 
        this.title=title;
        this.desc=desc;
        this.current_date=date;
        this.category=cate;
        this.due_date = due;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
    	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
    	this.current_date=f.format(new Date());
    }
    
    public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}
	@Override
	public String toString() {
		return id+ " ["+ category + "] "+ title+ " - "  + desc+ " - " + due_date+ " - "+current_date;
	}

	public String toSaveString() {
    	return category + "##" +title + "##" + desc + "##" + due_date+ "##"+ current_date + "\n";
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
