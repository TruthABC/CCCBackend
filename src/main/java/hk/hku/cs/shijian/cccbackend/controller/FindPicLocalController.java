package hk.hku.cs.shijian.cccbackend.controller;

import hk.hku.cs.shijian.cccbackend.entity.response.CommonResponse;
import hk.hku.cs.shijian.cccbackend.entity.response.FindPicResponse;
import hk.hku.cs.shijian.cccbackend.service.FileUploadService;
import hk.hku.cs.shijian.cccbackend.service.JavaCVLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FindPicLocalController {

    private FileUploadService uploadService;
    private JavaCVLocalService cvService;

    @Autowired
    public FindPicLocalController(FileUploadService service, JavaCVLocalService cvService) {
        this.uploadService = service;
        this.cvService = cvService;
    }

    @RequestMapping("/find_picture_local")
    @CrossOrigin
    public CommonResponse findPicLocal(HttpServletRequest request,
                         MultipartHttpServletRequest multiReq) {

        //Construct Absolute Path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF/classes/static/data/";

        //Assign Relative Path and Store files
        String imageRelativeDirPath = "uploadedImage/";
        CommonResponse interCommonResponse = uploadService.storeUploadedFile(request, multiReq, imageRelativeDirPath);
        if (interCommonResponse.getErrcode() != 0) {
            return interCommonResponse;
        }

        //Assign paths and do CV oprations
        MultipartFile mFile = multiReq.getFile("file");
        String imagePath = absoluteRootPath + imageRelativeDirPath + mFile.getOriginalFilename();
        FindPicResponse findPicResponse = cvService.calMatchTimeStamp(absoluteRootPath, imagePath, absoluteRootPath + "videos/EP00.mp4");
        return findPicResponse;
    }

}
