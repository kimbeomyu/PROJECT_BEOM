package selfGuide.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import common.JDBCTemplate;
import selfGuide.model.dao.SelfGuideDao;
import selfGuide.model.vo.SelfGuide;

public class SelfGuideService {
	
	public int insertSelfGuide(SelfGuide guideOne) {
		
		Connection conn = JDBCTemplate.getConnection();
		int result = new SelfGuideDao().insertSelfGuide(conn, guideOne);
		
		// 데이터베이스에 변경사항 적용
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public int insertSelfGuideSelfNo(SelfGuide guideOne) {
		Connection conn = JDBCTemplate.getConnection();
		int selfNo = new SelfGuideDao().insertSelfGuideSelfNo(conn, guideOne);
		JDBCTemplate.close(conn);
		return selfNo;
	}
	
	public int selfGuideListCount() {
		Connection conn = JDBCTemplate.getConnection();
		int total = new SelfGuideDao().selfGuideListCount(conn);
		JDBCTemplate.close(conn);
		return total;
	}
	
	public ArrayList<SelfGuide> selectMoreGuide(int start, int len) {
		
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<SelfGuide> guideList = new SelfGuideDao().selectMoreGuide(conn, start, len);
		JDBCTemplate.close(conn);
		return guideList;
	}
	
	public SelfGuide detailGuide(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		SelfGuide guideOne = new SelfGuideDao().detailGuide(conn, selfNo);
		JDBCTemplate.close(conn);
		return guideOne;
	}
	
	
	// 좋아요수
	public int likeTotalCount(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int total = new SelfGuideDao().likeTotalCount(conn, selfNo); // 게시물의 좋아요수 카운트
		int totalUpdate = new SelfGuideDao().totalUpdate(conn, selfNo, total); // self_guide테이블의 like컬럼에 좋아요수 업데이트
		
		if(totalUpdate>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return total;
	}
	
	// 좋아요가 있으면 삭제 없으면 추가하는 메소드
	public int likeCount(String memberId, int selfNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 존재하는지 않하는지 알기위해 체크하는 놈
		int result = new SelfGuideDao().likeCountCheck(conn, memberId, selfNo);
		
		// 만약 존재하고있다면
		// 삭제해버린다 ㅡㅡ
		if (result>0) {
			int result2 = new SelfGuideDao().likeDelete(conn, memberId, selfNo);
			if(result2>0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
			JDBCTemplate.close(conn);
			return result2;
			
		// 존재안하면 추가해줄게 ㅋ
		} else {
			int result2 = new SelfGuideDao().likeInsert(conn, memberId, selfNo);
			if(result2>0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
			JDBCTemplate.close(conn);
			return result2;
		}
	}
	
	public void viewCount(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new SelfGuideDao().viewCount(conn, selfNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
	}
	
	public int guideRemove(int selfNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new SelfGuideDao().guideRemove(conn, selfNo);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}
	
	public int updateSelfGuide(SelfGuide guideOne) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new SelfGuideDao().updateSelfGuide(conn, guideOne);
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}
}
