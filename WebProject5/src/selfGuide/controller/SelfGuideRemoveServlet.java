package selfGuide.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import selfGuide.model.service.SelfGuidePhotoService;
import selfGuide.model.service.SelfGuideService;

/**
 * Servlet implementation class SelfGuideRemoveServlet
 */
@WebServlet("/guideRemove")
public class SelfGuideRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelfGuideRemoveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String root = request.getSession().getServletContext().getRealPath("/");
		String saveDirectory = root + "upload/photo";
		String saveDirectory2 = root + "upload/testphoto";
		String fileName = request.getParameter("fileName");
		int selfNo = Integer.parseInt(request.getParameter("selfNo"));
		
		String filePath = saveDirectory+"/"+fileName;
		System.out.println(filePath);
		
		File file = new File(filePath);
		
		int result = new SelfGuideService().guideRemove(selfNo);
		
		ArrayList<String> removeValueList = new SelfGuidePhotoService().guidePhotoRemoveValue(selfNo);
		if(!removeValueList.isEmpty()) {
			for(String removeValue : removeValueList) {
				System.out.println("성공?");
				System.out.println(removeValue);
				File file2 = new File(removeValue);
				file2.delete();
				File directory = new File(saveDirectory2);
				if(!directory.exists()){
		            directory.mkdirs(); //디렉토리가 존재하지 않는다면 생성
		        }

			}
		} else {
			System.out.println("풒킥풒킥");
		}
		
		
		int result2 = new SelfGuidePhotoService().guidePhotoRemove(selfNo);
		
		if(result > 0 && result2 >0) {
			file.delete();
			File directory = new File(saveDirectory);
			if(!directory.exists()){
	            directory.mkdirs(); //디렉토리가 존재하지 않는다면 생성
	        }

			response.sendRedirect("/views/selfGuide/selfGuideMain.jsp");
		} else {
			System.out.println("파일삭제실패");
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
