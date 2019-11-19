package selfGuide.model.service;

import java.sql.Connection;

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
	
}
