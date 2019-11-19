package selfGuide.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
