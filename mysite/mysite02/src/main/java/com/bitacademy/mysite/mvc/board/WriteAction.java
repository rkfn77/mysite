package com.bitacademy.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.util.WebUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardRepository repository=new BoardRepository();
		
		String contents = request.getParameter("content");
		String title = request.getParameter("title");
		
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long no = authUser.getNo();
		Long groupNo=0L;
		int orderNo=0;
		int depth=0;
		Long boardNo=0L;
		
		if(!"".equals(request.getParameter("no"))) {
			boardNo = Long.parseLong(request.getParameter("no"));
			BoardVo BoardVo = repository.view(boardNo);
			depth=BoardVo.getDepth()+1;
			groupNo=BoardVo.getGroupNo();
			orderNo=BoardVo.getOrderNo()+1;
		}
		
		BoardVo vo = new BoardVo();
		vo.setContents(contents);
		vo.setTitle(title);
		vo.setGroupNo(groupNo);
		vo.setNo(boardNo);
		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
		vo.setUserNo(no);
		
		repository.insert(vo);
		WebUtil.redirect(request, response, request.getContextPath()+ "/board" );
	}

}
