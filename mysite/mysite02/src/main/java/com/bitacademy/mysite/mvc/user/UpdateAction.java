package com.bitacademy.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.repository.UserRepository;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long no = authUser.getNo();
		
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		
		UserVo vo = new UserVo();
		vo.setNo(no);
		vo.setPassword(password);
		vo.setName(name);
		vo.setGender(gender);
		
		new UserRepository().update(vo);
		vo.setName(name);
		
		WebUtil.redirect(request, response, request.getContextPath()+ "/user?a=updateform" );
	}

}
