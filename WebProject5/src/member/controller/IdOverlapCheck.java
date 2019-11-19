package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.MemberService;

/**
 * Servlet implementation class IdOverlapCheck
 */
@WebServlet("/overlapCheck.do")
public class IdOverlapCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdOverlapCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String memberId =  request.getParameter("memberId");
		
		String overlapId = new MemberService().overlapCheck(memberId);
		
		String data = null;
		
		if(overlapId != null) {
			data = overlapId+"는 중복된 아이디 입니다.";
			response.getWriter().write(data);
		} else {
			data = memberId+"는 사용가능한 아이디 입니다.";
			response.getWriter().write(data);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
