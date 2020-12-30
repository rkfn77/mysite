package com.bitacademy.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardRepository repository = new BoardRepository();
		int n;
		if(request.getParameter("n")==null) {
			n=1;
		}else {
			n = Integer.parseInt(request.getParameter("n"));
		}
		List<BoardVo> list = repository.findAll(n);
		int count=repository.count();
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp?n="+n);
	}

}