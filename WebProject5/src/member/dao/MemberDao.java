package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JDBCTemplate;
import member.vo.Member;

public class MemberDao {
	
	// 회원가입
	public int joinMember(Connection conn, Member mOne) {
		
		String query = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?)"; 
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mOne.getMember_ID());
			pstmt.setString(2, mOne.getMember_Pwd());
			pstmt.setString(3, mOne.getMember_Name());
			pstmt.setString(4, mOne.getMember_Address());
			pstmt.setString(5, mOne.getMember_Email());
			pstmt.setString(6, mOne.getMember_Phone());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	// 로그인
	public Member loginMember(Connection conn, String memberId, String memberPwd) {
		
		String query = "SELECT * FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PWD = ?"; 
		Member mOne = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				mOne = new Member();
				mOne.setMember_ID(rset.getString("MEMBER_ID"));
				mOne.setMember_Pwd(rset.getString("MEMBER_PWD"));
				mOne.setMember_Name(rset.getString("MEMBER_NAME"));
				mOne.setMember_Address(rset.getString("ADDRESS"));
				mOne.setMember_Email(rset.getString("EMAIL"));
				mOne.setMember_Phone(rset.getString("PHONE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return mOne;
	}

	// 중복체크
	public String overlapCheck(Connection conn, String memberId) {
		
		String query = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String overlapId = null; 
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				overlapId = rset.getString("MEMBER_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return overlapId;
	}
}


