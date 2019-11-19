package member.service;


import java.sql.Connection;

import common.JDBCTemplate;
import member.dao.MemberDao;
import member.vo.Member;

public class MemberService {
 
	// 회원가입
	public int joinMember(Member mOne) {
		
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().joinMember(conn, mOne);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
	// 로그인
	public Member loginMember(String memberId, String memberPwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		Member mOne = new MemberDao().loginMember(conn, memberId, memberPwd);
		JDBCTemplate.close(conn);
		return mOne;
	}

	// 중복체크
	public String overlapCheck(String memberId) {
		Connection conn = JDBCTemplate.getConnection();
		String overlapId = new MemberDao().overlapCheck(conn, memberId);
		
		JDBCTemplate.close(conn);
		return overlapId;
	}
}
