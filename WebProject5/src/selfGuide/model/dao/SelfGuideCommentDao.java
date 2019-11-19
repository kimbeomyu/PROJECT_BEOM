package selfGuide.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import selfGuide.model.vo.SelfGuideComment;

public class SelfGuideCommentDao {
	
	public int commentWrite(Connection conn, String comment, int selfNo, String memberId) {
		
		String query = "INSERT INTO SELF_GUIDE_COMMENT VALUES(?, ?, SYSDATE, ?, SEQ_SCOMMENT_NO.NEXTVAL)";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setString(2, comment);
			pstmt.setInt(3, selfNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public ArrayList<SelfGuideComment> selectCommentList(Connection conn, int selfNo) {
		
		ArrayList<SelfGuideComment> commentList = new ArrayList<SelfGuideComment>();
		String query = "SELECT * FROM SELF_GUIDE_COMMENT WHERE SELF_NO =?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				SelfGuideComment commentOne = new SelfGuideComment();
				commentOne.setMemberId(rset.getString("MEMBER_ID"));
				commentOne.setScommentText(rset.getString("SCOMMENT_TEXT"));
				commentOne.setScommentDate(rset.getDate("SCOMMENT_DATE"));
				commentOne.setSelfNo(rset.getInt("SELF_NO"));
				commentOne.setScommentNo(rset.getInt("SCOMMENT_NO"));
				commentList.add(commentOne);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		} 
		return commentList;
		
	}
	
	public SelfGuideComment selectCommentList(Connection conn, String comment, int selfNo, String memberId) {
		String query = "SELECT * FROM SELF_GUIDE_COMMENT WHERE SELF_NO=? AND MEMBER_ID=? AND SCOMMENT_TEXT=?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		SelfGuideComment commentOne = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			pstmt.setString(2, memberId);
			pstmt.setString(3, comment);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				commentOne = new SelfGuideComment();
				commentOne.setMemberId(rset.getString("MEMBER_ID"));
				commentOne.setScommentText(rset.getString("SCOMMENT_TEXT"));
				commentOne.setScommentDate(rset.getDate("SCOMMENT_DATE"));
				commentOne.setSelfNo(rset.getInt("SELF_NO"));
				commentOne.setScommentNo(rset.getInt("SCOMMENT_NO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		} 
		return commentOne;
	}
	
	public int guideCommentDelete(Connection conn, int scommentNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "DELETE FROM SELF_GUIDE_COMMENT WHERE SCOMMENT_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, scommentNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}
	
}
