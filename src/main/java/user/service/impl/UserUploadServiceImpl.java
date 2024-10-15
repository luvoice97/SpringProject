package user.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import user.bean.UserUploadDTO;
import user.dao.UserUploadDAO;
import user.service.ObjectStorageService;
import user.service.UserUploadService;

@Service
public class UserUploadServiceImpl implements UserUploadService {
	@Autowired
	private UserUploadDAO userUploadDAO;
	@Autowired
	private HttpSession session;
	@Autowired
	private ObjectStorageService objectStorageService;
	
	private String bucketName = "bitcamp-9th-bucket-133";

	@Override
	public void upload(List<UserUploadDTO> imageUploadList) {
		userUploadDAO.upload(imageUploadList);
	}

	@Override
	public List<UserUploadDTO> uploadList() {
		return userUploadDAO.uploadList();
	}

	@Override
	public UserUploadDTO getUploadDTO(String seq) {
		return userUploadDAO.getUploadDTO(seq);
	}

	@Override
	public void uploadUpdate(UserUploadDTO userUploadDTO, MultipartFile img) {
		//실제폴더
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제 폴더 = " + filePath);
		
		//Object Storate(NCP)는 이미지를 덮어쓰기 않는다.
		//DB에서 seq에 해당하는 imageFileName을 꺼내와서 Object Storate(NCP)의 이미지를 삭제하고,
		//새로운 이미지를 올린다.
		String imageFileName = userUploadDAO.getImageFileName(userUploadDTO.getSeq());
		System.out.println("imageFileName = " + imageFileName);
		
		//Object Storate(NCP)는 이미지 삭제
		objectStorageService.deleteFile(bucketName, "storage/", imageFileName);
		
		//Object Storate(NCP)는 새로운 이미지 올리기
		imageFileName = objectStorageService.uploadFile(bucketName, "storage/", img);
		
		
		String imageOriginalFileName = img.getOriginalFilename();
		File file = new File(filePath, imageOriginalFileName);
		
		try {
			img.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		userUploadDTO.setImageFileName(imageFileName);
		userUploadDTO.setImageOriginalFileName(imageOriginalFileName);
		
		//DB
		userUploadDAO.uploadUpdate(userUploadDTO);
	}

	@Override
	public void uploadDelete(Integer seq) {
		
		
		UserUploadDTO userUploadDTO = new UserUploadDTO();
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제 폴더 = " + filePath);
		String imageFileName = userUploadDAO.getImageFileName(seq);
		System.out.println("imageFileName = " + imageFileName);
		objectStorageService.deleteFile(bucketName, "storage/", imageFileName);
		
		userUploadDAO.uploadDelete(seq);
		
	}

}














