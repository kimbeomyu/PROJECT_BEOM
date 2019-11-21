package selfGuide.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale.FilteringMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import selfGuide.model.service.SelfGuidePhotoService;
import selfGuide.model.service.SelfGuideService;
import selfGuide.model.vo.SelfGuide;
import selfGuide.model.vo.SelfGuidePhoto;

/**
 * Servlet implementation class InsertGuideServlet
 */
@WebServlet("/insertGuide")
public class InsertGuideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertGuideServlet() {
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

		int maxSize = 1024 * 1024 * 10;
		
		File directory = new File(saveDirectory);
		if(!directory.exists()){
            directory.mkdirs(); //디렉토리가 존재하지 않는다면 생성
        }

		// 아래와 같이 MultipartRequest를 생성만 해주면 지정된 경로로 파일이 업로드됨
		MultipartRequest mRequest = new MultipartRequest(request, saveDirectory, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

		// =============== 여기서부터 DB에 넣기위해 하는 과정 ====================
	
		// enctype을 "multipart/form-data"로 선언하고 submit한 데이터들은 request객체가 아닌
		// MultipartRequest객체로 불러와야함
		String writerId = mRequest.getParameter("writerId");
		String content = mRequest.getParameter("content");
		String title = mRequest.getParameter("title");

		// 파일명을 받는 메소드는 따로있으니 주의 getParameter가아님
		String photoOriginalFilename = mRequest.getOriginalFileName("up_file");
		String photoRenameFilename = mRequest.getFilesystemName("up_file");

		// 변수에 저장한 값들을 SelfGuide형 객체에 저장
		SelfGuide guideOne = new SelfGuide();
		guideOne.setWriterId(writerId);
		guideOne.setSelfContent(content);
		guideOne.setSelfTitle(title);
		guideOne.setPhotoOriginalFilename(photoOriginalFilename);
		guideOne.setPhotoRenameFilename(photoRenameFilename);

		// DB로 보내 작업을 수행한후 결과를 리턴받는곳
		int result = new SelfGuideService().insertSelfGuide(guideOne);
		int result2 = 0;
		
		// 정규식을 이용해서 콘텐츠에서 이미지태그 불러오기
		// Pattern pattern =
		// Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출
		// 정규표현식
		Pattern pattern = Pattern.compile("(?i)src[a-zA-Z0-9_.\\-%&=?!:;@\"'/]*"); // img태그의 src만 추출
		Matcher matcher = pattern.matcher(content);

		// matcher.find 는 정규식에 해당하는 값을 찾으면 true를 뱉음 퉤
		// 내용에서 이미지를 얻어와서 imgList에 저장
		// truetrue퉤퉤
		ArrayList<String> imgList = new ArrayList<>();
		while (matcher.find()) {
			String imgOne1 = matcher.group();
			System.out.println(imgOne1);
			// 경로명에서 파일명만 따내기위해 사용
			String[] imgOne2 = imgOne1.substring(0, imgOne1.length() - 1).split("/");
			System.out.println(imgOne2[imgOne2.length - 1]);

			imgList.add(imgOne2[imgOne2.length - 1]);
		}

		// 가짜 파일경로
		String saveDirectory2 = root + "upload/testphoto";
		System.out.println("saveDirectory => " + saveDirectory2);
		File directory2 = new File(saveDirectory2);
		
		// 폴더안의 파일 리스트 불러오기
		File path = new File(saveDirectory2);
		File[] fileList = path.listFiles();

		// 파일리스트 출력하면 경로까지 다나와서
		// 나한테 필요한건 파일명이니까 파일명만 뽑아내려면 getName써야함
		// 그리고 여기서 업로드하는 파일과 파일리스트에 추가된 파일들을 비교함
		// 쉽게말해 너가 추가한거 하나도삭제안하고 그대로 추가하는지 확인해주는곳
		// 확인해서 확인된 친구는 실제 이미지 저장공간으로 가고
		// 아닌친구들은 수고링당하는곳
		if (fileList.length > 0) {
			for (int i = 0; i < fileList.length; i++) {
				/* System.out.println(fileList[i].getName()) ; */
				for (String img : imgList) {
					if (fileList[i].getName().equals(img)) {
						// 콘텐츠의 사진값을 넣기위한 객체
						SelfGuidePhoto contentPhoto = new SelfGuidePhoto();
						
						String old_name = saveDirectory2 + "/" + fileList[i].getName();
						String new_name = saveDirectory + "/" + fileList[i].getName();

						FileInputStream fin = new FileInputStream(old_name);
						BufferedInputStream bfin = new BufferedInputStream(fin);

						FileOutputStream fout = new FileOutputStream(new_name);
						BufferedOutputStream bfout = new BufferedOutputStream(fout);

						// DB에 저장될 값 저장
						contentPhoto.setSelf_No(new SelfGuideService().insertSelfGuideSelfNo(guideOne));
						contentPhoto.setPhoto_Name(new_name);
						result2 = new SelfGuidePhotoService().insertCommentPhoto(contentPhoto);
						while (true) {
							int data = bfin.read(); // 한바이트씩 읽음
							if (data == -1) {
								break;
							}

							bfout.write(data);
						}
						if (result2 <= 0) {
							System.out.println("컨텐츠사진업로드실패");
						}
						fout.close();
						fin.close();
					}
					
				}
				File deFile = new File(saveDirectory2 + "/" + fileList[i].getName());
				deFile.delete();
				
				if(!directory2.exists()){
		            directory2.mkdirs(); //디렉토리가 존재하지 않는다면 생성
		        }
			}
		}

		if (result > 0) {
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
