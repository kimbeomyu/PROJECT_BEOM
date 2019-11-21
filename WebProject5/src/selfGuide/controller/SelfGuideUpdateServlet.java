package selfGuide.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		File directory = new File(saveDirectory);
		if(!directory.exists()){
            directory.mkdirs(); //디렉토리가 존재하지 않는다면 생성
        }


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
		String writerId = mRequest.getParameter("writerId");
		String content = mRequest.getParameter("content");
		String title = mRequest.getParameter("title");
		int selfNo = Integer.parseInt(mRequest.getParameter("selfNo"));
		// 파일명을 받는 메소드는 따로있으니 주의 getParameter가아님
		String photoOriginalFilename = mRequest.getOriginalFileName("up_file");
		String photoRenameFilename = mRequest.getFilesystemName("up_file");
		
		int result2 = 0;
		// 변수에 저장한 값들을 SelfGuide형 객체에 저장
		SelfGuide guideOne = new SelfGuide();
		guideOne.setWriterId(writerId);
		guideOne.setSelfContent(content);
		guideOne.setSelfTitle(title);
		guideOne.setSelfNo(selfNo);
		guideOne.setPhotoOriginalFilename(photoOriginalFilename);
		guideOne.setPhotoRenameFilename(photoRenameFilename);
		
		// 데배 변경
		int result = new SelfGuideService().updateSelfGuide(guideOne);

		
		// 내용 안의 값을 가져와 img태그의 src값을 꺼내는 과정
		Pattern pattern = Pattern.compile("(?i)src[a-zA-Z0-9_.\\-%&=?!:;@\"'/]*"); // img태그의 src만 추출
		Matcher matcher = pattern.matcher(content);
		
		ArrayList<String> imgList = new ArrayList<>();
		while (matcher.find()) {
			String imgOne1 = matcher.group();
			System.out.println(imgOne1);
			// 경로명에서 파일명만 따내기위해 사용
			String[] imgOne2 = imgOne1.substring(0, imgOne1.length() - 1).split("/");
			System.out.println(imgOne2[imgOne2.length - 1]);

			imgList.add(imgOne2[imgOne2.length - 1]);
		}
		
		// 꺼낸놈을 testphoto안의 파일리스트 다불러서 fileList에 저장
		String saveDirectory2 = root + "upload/testphoto";
		System.out.println("saveDirectory => " + saveDirectory2);
		File directory2 = new File(saveDirectory2);
		
		File path = new File(saveDirectory2);
		File[] fileList = path.listFiles();
		
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
						contentPhoto.setSelf_No(selfNo);
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
				
				// test/photo안의 값을 다지워버렷!
				File deFile = new File(saveDirectory2 + "/" + fileList[i].getName());
				deFile.delete();
				if(!directory2.exists()){
		            directory2.mkdirs(); //디렉토리가 존재하지 않는다면 생성
		        }
			}
		}
		
		
		//====================== 비교해서 삭제하는 곳 =======================
		// 1. 해당하는 self_no의 사진들을 불러와
		ArrayList<String> photoList = new SelfGuidePhotoService().selfNoPhotoSearch(selfNo);
		
		/*.substring(0, imgOne1.length() - 1).split("/");
		System.out.println(imgOne2[imgOne2.length - 1]);*/

		// 2. 해당 사진들과 컨텐츠의 사진들을 비교
		for(String photoOne : photoList) { // 해당 self_No에 속하는 사진리스트
			
			String[] photoTwo = photoOne.substring(0, photoOne.length()).split("/");
			// 포토리스트에 img가 있니 없지? 
			if(!imgList.contains(photoTwo[photoTwo.length-1])) { // 리스트와 리스트를 비교할때 사용 값이 있니 없니 따지는 문
				System.out.println(photoTwo[photoTwo.length-1]);
				System.out.println(imgList);
				// 3. imgList안에 photoOne이 없으면 데이터베이스에서 제거하여라
				System.out.println("성공 데베가서 확인해");
				File deFile = new File(photoOne);
				new SelfGuidePhotoService().contentPhotoRemove(photoOne);
				deFile.delete();
					
				if(!directory.exists()){
					directory.mkdirs(); //디렉토리가 존재하지 않는다면 생성
			    }
					
				else {
					System.out.println("실패ㅠㅠ 넌언제까지 이거에 매달릴거니");
				}
			}
		}

		if(result>0) {
			file.delete();
			
			if(!directory.exists()){
	            directory.mkdirs(); //디렉토리가 존재하지 않는다면 생성
	        }
			
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
