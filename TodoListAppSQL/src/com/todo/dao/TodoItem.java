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
    private int is_comp;
    private int importance;
    private int take_day;
    


    public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public int getTake_day() {
		return take_day;
	}
	public void setTake_day(int take_day) {
		this.take_day = take_day;
	}
	public int getIs_comp() {
		return is_comp;
	}
	public void setIs_comp(int is_comp) {
		this.is_comp = is_comp;
	}
	public TodoItem(String title, String cate, String desc,String due_date){//date가 정해지지 않은 경우 
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
        this.category=cate;
        this.due_date=due_date;
        
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
		if(is_comp==0) {
			return id+  " [        ]"+ " ["+ category + "] "+ title+ " - "  + desc+ " - " + due_date+ " - "+current_date;
		}
		else{
			return id+  " [V"+": "+take_day+"days]"+" ["+ category + "] "+ title+" - "  + desc+ " - " + due_date+ " - "+current_date;
		}
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
