package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		request.setAttribute("no", no);
		WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
	}

}
