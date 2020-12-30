package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.vo.GuestbookVo;

public class GuestbookRepository {

	public List<GuestbookVo> findAll(){
		List<GuestbookVo> list = new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// 1. JDBC Driver 로딩
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="select no,name,message,date_format(reg_date,'%y-%m-%d') as reg_date"+
			" from guestbook"+
			" order by reg_date";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			
			
			// 5. sql문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			while(rs.next()) {
				Long no=rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String reg_date = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage(message);
				vo.setDate(reg_date);
				
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
	
	public boolean insert(GuestbookVo vo) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="insert"+
					" into guestbook"+
					" values(null,?,?,?,now())";
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 바인딩
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			
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
	
	public boolean delete(Long no, String password) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn = getConnection();
		
			// 3. SQL 준비
			String sql="delete"+
					" from guestbook"+
					" where no=? and password=?";
			
			pstmt = conn.prepareStatement(sql);
			// 4. 바인딩
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
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
