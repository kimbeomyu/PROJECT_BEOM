package selfGuide.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import selfGuide.model.service.SelfGuideService;
import selfGuide.model.vo.SelfGuide;

/**
 * Servlet implementation class MoreGuideServlet
 */
@WebServlet("/moreGuide")
public class MoreGuideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoreGuideServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int start = Integer.parseInt(request.getParameter("start")); // 시작게시물번호
		int len = 0; // 몇개씩 게시물을 불러올것인지
		
		if(start == 1) {
			start = 1;
			len =8;
		} else {
			start = start+(7*(start-1)); // 2+7*1 = 9 3+7*2=17
			len = start+7; // 16 24
		}
		
		
		ArrayList<SelfGuide> guideList = new SelfGuideService().selectMoreGuide(start, len);
		
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(guideList, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
