package selfGuide.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import selfGuide.model.vo.SelfGuidePhoto;

public class SelfGuidePhotoDao {


	public int insertCommentPhoto(Connection conn, SelfGuidePhoto contentPhoto) {
		int result = 0;
		
		String query = "INSERT INTO SELF_GUIDE_PHOTO VALUES(?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, contentPhoto.getSelf_No());
			pstmt.setString(2, contentPhoto.getPhoto_Name());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { 
			JDBCTemplate.close(pstmt);
		}
	
		return result;
	}
	
	public int guidePhotoRemove(Connection conn, int selfNo) {
		
		int result2 = 0;
		String query = "DELETE FROM SELF_GUIDE_PHOTO WHERE SELF_NO=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			result2 = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally { 
			JDBCTemplate.close(pstmt);
		}
		return result2;
	}
	
	public ArrayList<String> guidePhotoRemoveValue(Connection conn, int selfNo) {
		String query = "SELECT PHOTO_NAME FROM SELF_GUIDE_PHOTO WHERE SELF_NO=?";
		ArrayList<String> valueList = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				String value = rset.getString("PHOTO_NAME");
				valueList.add(value);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return valueList;
	}
	
	public ArrayList<String> selfNoPhotoSearch(Connection conn, int selfNo) {
		String query = "SELECT PHOTO_NAME FROM SELF_GUIDE_PHOTO WHERE SELF_NO=?";
		ArrayList<String> photoList = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				String value = rset.getString("PHOTO_NAME");
				photoList.add(value);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return photoList;
	}
	
	public int contentPhotoRemove(Connection conn, String photoOne) {
		String query = "DELETE FROM SELF_GUIDE_PHOTO WHERE PHOTO_NAME =?";
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, photoOne);
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
