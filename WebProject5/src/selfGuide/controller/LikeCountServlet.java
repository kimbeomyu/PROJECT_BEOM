package selfGuide.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import selfGuide.model.service.SelfGuideService;

/**
 * Servlet implementation class LikeCountServlet
 */
@WebServlet("/likesCount")
public class LikeCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String memberId = request.getParameter("memberId");
		int selfNo = Integer.parseInt(request.getParameter("selfNo"));
		
		// 좋아요가 있으면 삭제해서 1의 결과를 들고오고 없으면 0의 결과를 들고오는 놈
		int result = new SelfGuideService().likeCount(memberId, selfNo);
		
		// 좋아요수
		int total = new SelfGuideService().likeTotalCount(selfNo);
	
		if(result>0) {
			response.setContentType("application/json; charset=UTF-8");
			new Gson().toJson(total, response.getWriter());
		} else {
			System.out.println("실패!");
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
