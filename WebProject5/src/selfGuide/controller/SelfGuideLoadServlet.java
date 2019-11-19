package selfGuide.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import selfGuide.model.service.SelfGuideService;
import selfGuide.model.vo.SelfGuide;

/**
 * Servlet implementation class SelfGuideUpdateServlet
 */
@WebServlet("/selfGuideLoad")
public class SelfGuideLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelfGuideLoadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int selfNo = Integer.parseInt(request.getParameter("selfNo"));
		SelfGuide guideOne = new SelfGuideService().detailGuide(selfNo);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/selfGuide/selfGuideUpdate.jsp");
		request.setAttribute("guideOne",guideOne);
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
