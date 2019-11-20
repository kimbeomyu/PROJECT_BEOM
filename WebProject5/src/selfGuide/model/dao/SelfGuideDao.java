package selfGuide.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import selfGuide.model.vo.SelfGuide;

public class SelfGuideDao {

	public int insertSelfGuide(Connection conn, SelfGuide guideOne) {

		String query = "INSERT INTO SELF_GUIDE VALUES(DEFAULT, ?, ?, DEFAULT, DEFAULT, SEQ_SELF_NO.NEXTVAL, ?, ?, ?)";
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guideOne.getWriterId());
			pstmt.setString(2, guideOne.getSelfContent().replaceAll("<img src=\"/upload/testphoto", "<img src=\"/upload/photo"));
			pstmt.setString(3, guideOne.getSelfTitle());
			pstmt.setString(4, guideOne.getPhotoOriginalFilename());
			pstmt.setString(5, guideOne.getPhotoRenameFilename());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}
	
	public int insertSelfGuideSelfNo(Connection conn, SelfGuide guideOne) {
		int selfNo = 0;
		String query = "SELECT SELF_NO FROM SELF_GUIDE WHERE WRITER_ID=? AND SELF_CONTENT=? AND SELF_TITLE=?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guideOne.getWriterId());
			pstmt.setString(2, guideOne.getSelfContent().replaceAll("<img src=\"/upload/testphoto", "<img src=\"/upload/photo"));
			pstmt.setString(3, guideOne.getSelfTitle());
			
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				selfNo = rset.getInt("SELF_NO");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		System.out.println();
		return selfNo;
	}

	public int selfGuideListCount(Connection conn) {

		String query = "SELECT COUNT(*) AS TOTAL FROM SELF_GUIDE";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int total = 0;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			rset.next();
			total = rset.getInt("TOTAL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return total;

	}

	public ArrayList<SelfGuide> selectMoreGuide(Connection conn, int start, int len) {

		ArrayList<SelfGuide> guideList = new ArrayList<SelfGuide>();
		String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY SELF_DATE DESC) AS RNUM, SELF_GUIDE.* FROM SELF_GUIDE) WHERE RNUM BETWEEN ? AND ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, len);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				SelfGuide guideOne = new SelfGuide();
				guideOne.setSelfDate(rset.getDate("SELF_DATE"));
				guideOne.setSelfTitle(rset.getString("SELF_TITLE"));
				guideOne.setWriterId(rset.getString("WRITER_ID"));
				guideOne.setSelfContent(rset.getString("SELF_CONTENT"));
				guideOne.setSelfViews(rset.getInt("SELF_VIEWS"));
				guideOne.setSelfLike(rset.getInt("SELF_LIKE"));
				guideOne.setSelfNo(rset.getInt("SELF_NO"));
				guideOne.setPhotoOriginalFilename(rset.getString("PHOTO_ORIGINAL_FILENAME"));
				guideOne.setPhotoRenameFilename(rset.getString("PHOTO_RENAME_FILENAME"));
				guideList.add(guideOne);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return guideList;
	}

	public SelfGuide detailGuide(Connection conn, int selfNo) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		SelfGuide guideOne = null;
		String query = "SELECT * FROM SELF_GUIDE WHERE SELF_NO = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				guideOne = new SelfGuide();
				guideOne.setSelfDate(rset.getDate("SELF_DATE"));
				guideOne.setSelfTitle(rset.getString("SELF_TITLE"));
				guideOne.setWriterId(rset.getString("WRITER_ID"));
				guideOne.setSelfContent(rset.getString("SELF_CONTENT"));
				guideOne.setSelfViews(rset.getInt("SELF_VIEWS"));
				guideOne.setSelfLike(rset.getInt("SELF_LIKE"));
				guideOne.setSelfNo(rset.getInt("SELF_NO"));
				guideOne.setPhotoOriginalFilename(rset.getString("PHOTO_ORIGINAL_FILENAME"));
				guideOne.setPhotoRenameFilename(rset.getString("PHOTO_RENAME_FILENAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return guideOne;
	}

	public int viewCount(Connection conn, int selfNo) {
		String query = "UPDATE SELF_GUIDE SET SELF_VIEWS=SELF_VIEWS+1 WHERE SELF_NO = ?";
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	// 아이디당 하나만 가능하니까 이게시물에 대해 좋아요를 했는지 안했는지
	public int likeCountCheck(Connection conn, String memberId, int selfNo) {
		String query = "SELECT * FROM LIKES WHERE MEMBER_ID =? AND SELF_NO =?";
		int result = 0;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, selfNo);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	// 좋아요가 안눌려있다면 올리는 메소드
	public int likeInsert(Connection conn, String memberId, int selfNo) {

		String query = "INSERT INTO LIKES VALUES(?,?)";
		int result = 0;

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			pstmt.setString(2, memberId);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	// 좋아요를 눌러놨다면 내리는 메소드
	public int likeDelete(Connection conn, String memberId, int selfNo) {

		String query = "DELETE FROM LIKES WHERE MEMBER_ID=? AND SELF_NO=?";
		int result = 0;

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, selfNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	// likes테이블의 게시물별 좋아요수 카운트
	public int likeTotalCount(Connection conn, int selfNo) {

		String query = "SELECT COUNT(*) AS TOTAL FROM LIKES WHERE SELF_NO = ?";
		ResultSet rset = null;
		int total = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			rset = pstmt.executeQuery();
			rset.next();
			total = rset.getInt("TOTAL");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return total;
	}

	// self_guide 테이블의 좋아요 수를 업데이트
	public int totalUpdate(Connection conn, int selfNo, int total) {

		String query = "UPDATE SELF_GUIDE SET SELF_LIKE=? WHERE SELF_NO=?";
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, total);
			pstmt.setInt(2, selfNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public int guideRemove(Connection conn, int selfNo) {
		
		String query = "DELETE FROM SELF_GUIDE WHERE SELF_NO =?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, selfNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public int updateSelfGuide(Connection conn, SelfGuide guideOne) {
		
		String query = "UPDATE SELF_GUIDE SET SELF_TITLE=?, SELF_CONTENT=?, PHOTO_ORIGINAL_FILENAME=?, PHOTO_RENAME_FILENAME=? WHERE SELF_NO=?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guideOne.getSelfTitle());
			pstmt.setString(2, guideOne.getSelfContent().replaceAll("<img src=\"/upload/testphoto", "<img src=\"/upload/photo"));
			pstmt.setString(3, guideOne.getPhotoOriginalFilename());
			pstmt.setString(4, guideOne.getPhotoRenameFilename());
			pstmt.setInt(5, guideOne.getSelfNo());
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
}
