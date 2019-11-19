package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.MemberService;
import member.vo.Member;

/**
 * Servlet implementation class MemberJoinServlet
 */
@WebServlet("/memberJoinServlet")
public class MemberJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member mOne = new Member();
		mOne.setMember_ID(request.getParameter("id"));
		mOne.setMember_Pwd(request.getParameter("pw"));
		mOne.setMember_Name(request.getParameter("name"));
		mOne.setMember_Email(request.getParameter("email")+"@"+request.getParameter("email2"));
		mOne.setMember_Address(request.getParameter("address"));
		mOne.setMember_Phone(request.getParameter("phone"));
		
		int result = new MemberService().joinMember(mOne);
		
		if(result > 0) {
			HttpSession session = request.getSession(); // 세션 생성
			session.setAttribute("member", mOne);
			response.sendRedirect("/index.jsp");
		} else {
			System.out.println("조졌쥬?");
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
