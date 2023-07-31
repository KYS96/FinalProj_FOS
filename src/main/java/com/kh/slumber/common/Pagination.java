package com.kh.slumber.common;

import com.kh.slumber.common.model.vo.PageInfo;

public class Pagination {
	public static PageInfo getPageInfo(int currentPage, int listCount, int boardLimit) {
		int pageLimit = 5;
		int maxPage;
		int startPage;
		int endPage;
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		if(maxPage == 0) {
			maxPage = 1;
		}
		
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		endPage = startPage + pageLimit - 1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
		
		return pi;
	}
}