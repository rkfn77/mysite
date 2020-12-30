<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>댓글</th>
						<th>삭제</th>
					</tr>
					<c:forEach items='${list }' var='vo' varStatus='status'>
					<tr>
						<td>${count-status.index-(param.n-1)*10 }</td>
						<c:choose>
							<c:when test="${vo.orderNo>0 }">
								<td style='text-align:left; padding-left:${vo.depth*20 }px'><a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}"><img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' /> ${vo.title }</a></td>
							</c:when>
							<c:otherwise>
								<td style='text-align:left; padding-left:0px'><a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a></td>
							</c:otherwise>
						</c:choose>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td><a href="${pageContext.request.contextPath }/board?a=writeform&no=${vo.no}"><img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'/></a></td>
						<td>
						<c:choose>
							<c:when test="${authUser.name == vo.userName }">
								<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a>
							</c:when>
						</c:choose>
						</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${param.n>1 }">
								<li><a href="${pageContext.request.contextPath }/board?a=list&n=${param.n-1}">◀</a></li>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${param.n>3 }">
								<c:set var='begin' value='${param.n-2 }'></c:set>
							</c:when>
							<c:otherwise>
								<c:set var='begin' value='1'></c:set>
							</c:otherwise>
						</c:choose>
						<c:forEach begin='${begin }' end='${begin+4 }' var='c'>
							<c:choose>
								<c:when test="${param.n==c }">
									<li class="selected"><a href="${pageContext.request.contextPath }/board?a=list&n=${c}">${c }</a></li>
								</c:when>
								<c:when test="${count/10+1 > c }">
									<li><a href="${pageContext.request.contextPath }/board?a=list&n=${c}">${c }</a></li>
								</c:when>
								<c:otherwise>
									<li>${c }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${param.n<count/10 }">
								<li><a href="${pageContext.request.contextPath }/board?a=list&n=${param.n+1}">▶</a></li>
							</c:when>
						</c:choose>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:choose>
					<c:when test="${not empty authUser }">
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
						</div>
					</c:when>
				</c:choose>
						
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>