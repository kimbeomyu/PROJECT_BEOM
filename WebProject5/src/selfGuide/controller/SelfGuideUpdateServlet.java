package selfGuide.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import selfGuide.model.service.SelfGuideService;
import selfGuide.model.vo.SelfGuide;

/**
 * Servlet implementation class SelfGuideUpdateServlet
 */
@WebServlet("/guideUpdate")
public class SelfGuideUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelfGuideUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 현재 웹 컨테이너에서 구동중인 웹 어플리케이션
		// 루트 절대 경로 알아내기
		// Session객체 -> Servlet Context객체 => 절대경로
		String root = request.getSession().getServletContext().getRealPath("/");
		String saveDirectory = root + "upload/photo";
		System.out.println("saveDirectory => " + saveDirectory);

		int maxSize = 1024 * 1024 * 10;

		// 아래와 같이 MultipartRequest를 생성만 해주면 지정된 경로로 파일이 업로드됨
		MultipartRequest mRequest = new MultipartRequest(request, saveDirectory, maxSize, "UTF-8", new DefaultFileRenamePolicy());

		// 이미지를 수정했으니 존재하던 이미지를 제거해야함
		String fileName = mRequest.getParameter("fileName");
		
		String filePath = saveDirectory+"/"+fileName;
		File file = new File(filePath);
		
		
		
		// =============== 여기서부터 DB에 넣기위해 하는 과정 ====================

		// enctype을 "multipart/form-data"로 선언하고 submit한 데이터들은 request객체가 아닌
		// MultipartRequest객체로 불러와야함
		String content = mRequest.getParameter("content");
		String title = mRequest.getParameter("title");
		int selfNo = Integer.parseInt(mRequest.getParameter("selfNo"));
		// 파일명을 받는 메소드는 따로있으니 주의 getParameter가아님
		String photoOriginalFilename = mRequest.getOriginalFileName("up_file");
		String photoRenameFilename = mRequest.getFilesystemName("up_file");

		// 변수에 저장한 값들을 SelfGuide형 객체에 저장
		SelfGuide guideOne = new SelfGuide();
		guideOne.setSelfContent(content);
		guideOne.setSelfTitle(title);
		guideOne.setSelfNo(selfNo);
		guideOne.setPhotoOriginalFilename(photoOriginalFilename);
		guideOne.setPhotoRenameFilename(photoRenameFilename);

		// DB로 보내 작업을 수행한후 결과를 리턴받는곳
		int result = new SelfGuideService().updateSelfGuide(guideOne);
		if(result>0) {
			file.delete();
			response.sendRedirect("/views/selfGuide/selfGuideMain.jsp");
		} else {
			System.out.println("실패");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
