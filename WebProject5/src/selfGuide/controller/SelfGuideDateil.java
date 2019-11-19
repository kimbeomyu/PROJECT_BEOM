package selfGuide.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import selfGuide.model.service.SelfGuideCommentService;
import selfGuide.model.service.SelfGuideService;
import selfGuide.model.vo.SelfGuide;
import selfGuide.model.vo.SelfGuideComment;

/**
 * Servlet implementation class SelfGuideDateil
 */
@WebServlet("/selfGuideDateil")
public class SelfGuideDateil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelfGuideDateil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int selfNo = Integer.parseInt(request.getParameter("selfNo"));
		new SelfGuideService().viewCount(selfNo);

		SelfGuide guideOne = new SelfGuideService().detailGuide(selfNo);
		SelfGuideComment commentList = new SelfGuideCommentService().selectCommentList(selfNo); // 세부창으로 가기전에 댓글리스트 받아오려고 만들어줬음
		
		RequestDispatcher view = request.getRequestDispatcher("/views/selfGuide/selfGuideDetail.jsp");
		request.setAttribute("commentList", commentList);
		request.setAttribute("guideOne", guideOne);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
