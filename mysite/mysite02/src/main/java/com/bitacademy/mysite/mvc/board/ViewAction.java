package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardRepository repository = new BoardRepository();
		
		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo vo = repository.view(no);
		
		repository.hit(no); // 조회수 증가
		
		request.setAttribute("vo",vo );
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}