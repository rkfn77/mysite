package com.bitacademy.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no=Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");
		new GuestbookRepository().delete(no, password);
		WebUtil.redirect(request, response, request.getContextPath()+ "/guestbook" );
	}

}
