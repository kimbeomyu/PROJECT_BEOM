package selfGuide.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import common.JDBCTemplate;
import selfGuide.model.dao.SelfGuidePhotoDao;
import selfGuide.model.vo.SelfGuidePhoto;

public class SelfGuidePhotoService {

	public int insertCommentPhoto(SelfGuidePhoto contentPhoto) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new SelfGuidePhotoDao().insertCommentPhoto(conn, contentPhoto);
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
	public ArrayList<String> guidePhotoRemoveValue(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<String> removeValue = new SelfGuidePhotoDao().guidePhotoRemoveValue(conn, selfNo);
		JDBCTemplate.close(conn);
		return removeValue;
	}
	
	public int guidePhotoRemove(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result2 = new SelfGuidePhotoDao().guidePhotoRemove(conn, selfNo);
		if(result2>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		} 
		JDBCTemplate.close(conn);
		return result2;
	}
	
	public ArrayList<String> selfNoPhotoSearch(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<String> photoList = new SelfGuidePhotoDao().selfNoPhotoSearch(conn, selfNo);
		JDBCTemplate.close(conn);
		return photoList;
	}
	
	public int contentPhotoRemove(String photoOne) {
		
		Connection conn = JDBCTemplate.getConnection();
		int result = new SelfGuidePhotoDao().contentPhotoRemove(conn, photoOne);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}


}
