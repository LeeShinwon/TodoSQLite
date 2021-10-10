package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1.  항목 추가 ( add )");
        System.out.println("2.  항목 삭제 ( del )");
        System.out.println("3.  항목 수정 ( edit )");
        System.out.println("4.  전체 목록 ( ls )");
        System.out.println("5.  제목순  정렬 ( ls_name_asc )");
        System.out.println("6.  제목역순 정렬 ( ls_name_desc )");
        System.out.println("7.  날짜순  정렬 ( ls_date_asc )");
        System.out.println("8.  날짜역순 정렬 ( ls_date_desc )");
        System.out.println("9.  키워드 목록 ( find <키워드> )");
        System.out.println("10. 카테고리 목록 ( find_cate <키워드> )");
        System.out.println("11. 카테고리 목록 ( ls_cate )");
        System.out.println("12. 종료 (exit)");
    }
    public static void prompt() {
    	System.out.println("\nCommand > ");
    }
}
