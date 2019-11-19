package selfGuide.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import selfGuide.model.service.SelfGuideCommentService;
import selfGuide.model.vo.SelfGuideComment;

/**
 * Servlet implementation class GuideCommentDeleteServlet
 */
@WebServlet("/commentDelete")
public class GuideCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuideCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int scommentNo = Integer.parseInt(request.getParameter("scommentNo"));
		
		new SelfGuideCommentService().guideCommentDelete(scommentNo);
		int selfNo = Integer.parseInt(request.getParameter("selfNo"));
		ArrayList<SelfGuideComment> guideCommentList = new SelfGuideCommentService().selectCommentList2(selfNo);
		
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(guideCommentList, response.getWriter());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
