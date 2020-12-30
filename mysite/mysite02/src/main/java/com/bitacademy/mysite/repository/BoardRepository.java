package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.vo.BoardVo;

public class BoardRepository {
	
	public int count(){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		try {
			// 1. JDBC Driver 로딩
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="select count(*) from board";
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			
			// 5. sql문 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 가져오기
			while(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return count;
	}
	
	public List<BoardVo> findAll(int n){
		List<BoardVo> list = new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// 1. JDBC Driver 로딩
			conn = getConnection();
			int start = (n-1)*10;
			// 3. SQL 준비
			String sql="select a.no, a.title, a.reg_date, a.hit, b.name, a.order_no, a.depth, a.group_no "
					+ "from board a, user b "
					+ "where a.user_no = b.no "
					+ "order by a.group_no desc, a.order_no asc "
					+ "limit ?, 10";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			pstmt.setLong(1, start);
			
			// 5. sql문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			while(rs.next()) {
				Long no=rs.getLong(1);
				String title = rs.getString(2);
				String regDate = rs.getString(3);
				Long hit = rs.getLong(4);
				String name = rs.getString(5);
				int orderNo=rs.getInt(6);
				int depth=rs.getInt(7);
				Long groupNo=rs.getLong(8);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setUserName(name);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setGroupNo(groupNo);
				list.add(vo);
			}
		}catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public BoardVo view(Long no){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		BoardVo vo = new BoardVo();
		try {
			// 1. JDBC Driver 로딩
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="select title, contents, group_no, order_no, depth from board where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			pstmt.setLong(1, no);
			// 5. sql문 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 가져오기
			while(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long group_no = rs.getLong(3);
				int order_no = rs.getInt(4);
				int depth = rs.getInt(5);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setGroupNo(group_no);
				vo.setOrderNo(order_no);
				vo.setDepth(depth);
			}
		}catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return vo;
	}
	
	public boolean insert(BoardVo vo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn = getConnection();
			if(vo.getGroupNo()==0) {
				String sql="insert"+
						" into board"+
						" values(null,?,?,now(),1,(SELECT IFNULL(MAX(group_no) + 1, 1) FROM board b),0,0,?)";
				
				pstmt = conn.prepareStatement(sql);
				
				
				// 4. 바인딩
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getUserNo());
			}else {
				commendUpdate(vo);
				String sql="insert"+
						" into board"+
						" values(null,?,?,now(),1,?,?,?,?)";
				
				pstmt = conn.prepareStatement(sql);
				
				
				// 4. 바인딩
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getGroupNo());
				pstmt.setLong(4, vo.getOrderNo());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUserNo());
			}
			
			// 5. sql문 실행
			int count = pstmt.executeUpdate();
			
			result = count==1;
		} catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public int delete(List<BoardVo> list) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		int count=0;
		try {
			conn = getConnection();
			// 3. SQL 준비
			String sql="";
			if(list.get(0).getDepth()==0) {
				sql="delete from board where group_no=?";
				pstmt = conn.prepareStatement(sql);
				
				// 4. 바인딩
				pstmt.setLong(1, list.get(0).getGroupNo());
				// 5. sql문 실행
				pstmt.executeUpdate();
			}
			else if(list.size()>1) {
				sql="delete from board where group_no=? and order_no>=? and order_no<? and depth>=?";
				pstmt = conn.prepareStatement(sql);
				
				// 4. 바인딩
				pstmt.setLong(1, list.get(0).getGroupNo());
				pstmt.setInt(2, list.get(0).getOrderNo());
				pstmt.setInt(3, list.get(1).getOrderNo());
				pstmt.setInt(4, list.get(0).getDepth());
				// 5. sql문 실행
				pstmt.executeUpdate();
			}
			else {
				sql="delete from board where group_no=? and order_no>=? and depth>=?";
				pstmt = conn.prepareStatement(sql);
				
				// 4. 바인딩
				pstmt.setLong(1, list.get(0).getGroupNo());
				pstmt.setInt(2, list.get(0).getOrderNo());
				pstmt.setInt(3, list.get(0).getDepth());
				// 5. sql문 실행
				pstmt.executeUpdate();
			}
			
		} catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return count;
	}
	
	public List<BoardVo> deletefind(BoardVo vo) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardVo> list = new ArrayList<>();
		try {
			conn = getConnection();
			// 3. SQL 준비
			
			String sql="select no, group_no, order_no, depth from board where depth=? and group_no=? and order_no>=? order by order_no";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			pstmt.setInt(1,vo.getDepth());
			pstmt.setLong(2,vo.getGroupNo());
			pstmt.setInt(3,vo.getOrderNo());
			// 5. sql문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			while(rs.next()) {
				Long no=rs.getLong(1);
				Long groupNo=rs.getLong(2);
				int orderNo=rs.getInt(3);
				int depth=rs.getInt(4);
				
				BoardVo vo2 = new BoardVo();
				vo2.setNo(no);
				vo2.setGroupNo(groupNo);
				vo2.setOrderNo(orderNo);
				vo2.setDepth(depth);
				list.add(vo2);
			}
			
		} catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public boolean update(BoardVo vo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="update board set title=?, contents=? where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			// 5. sql문 실행
			int count = pstmt.executeUpdate();
			
			result = count==1;
		} catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public boolean commendUpdate(BoardVo vo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="update board set order_no=order_no+1 where group_no=? and order_no>=?";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			pstmt.setLong(1, vo.getGroupNo());
			pstmt.setInt(2, vo.getOrderNo());
			// 5. sql문 실행
			int count = pstmt.executeUpdate();
			
			result = count==1;
		} catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public boolean hit(Long no) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="update board set hit=hit+1 where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			pstmt.setLong(1, no);
			// 5. sql문 실행
			int count = pstmt.executeUpdate();
			
			result = count==1;
		} catch(SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn=null;
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url="jdbc:mysql://192.168.1.44:3307/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url,"webdb","12502026");
		}catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : "+e);
		}
		
		return conn;
	}
}
