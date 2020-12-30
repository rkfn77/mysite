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

public class UpdateformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 세션에서 authUser 가져오기
		// 2. authUser에서 no 가져오기
		// 3. no를 가지고 Repository를 통해 UserVo 가져오기
		// 4. jsp로 UserVo 전달하면서 FORWARDING 하기
		HttpSession session = request.getSession(true);
		
		UserVo vo = (UserVo)session.getAttribute("authUser");
		if(vo == null) {
			WebUtil.redirect(request, response, request.getContextPath());
			return;
		}
		
		UserVo userVo = new UserRepository().findByNo(vo.getNo());
		request.setAttribute("authUser", userVo);
		
		WebUtil.forward(request, response, "/WEB-INF/views/user/updateform.jsp");

	}

}
