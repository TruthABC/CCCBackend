package hk.hku.cs.shijian.cccbackend.controller;

import hk.hku.cs.shijian.cccbackend.entity.response.CommonResponse;
import hk.hku.cs.shijian.cccbackend.entity.response.FindPicResponse;
import hk.hku.cs.shijian.cccbackend.GlobalSpark;
import hk.hku.cs.shijian.cccbackend.service.FileUploadService;
import hk.hku.cs.shijian.cccbackend.service.JavaCVSparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@RestController
public class FindPicSparkController {
    private FileUploadService uploadService;
    private JavaCVSparkService cvService;

    @Autowired
    public FindPicSparkController(FileUploadService service, JavaCVSparkService cvService) {
        this.uploadService = service;
        this.cvService = cvService;
    }

    @RequestMapping("/find_picture_spark")
    @CrossOrigin
    public CommonResponse findPicSpark(@RequestParam(value="videoname", defaultValue="") String videoname,
                                       HttpServletRequest request,
                                       MultipartHttpServletRequest multiReq) throws Exception {

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
        String filename = mFile.getOriginalFilename();
        String imagePath = absoluteRootPath + imageRelativeDirPath + filename;
        String timestamp = new Date().getTime() + "";
        String imageSparkPath  = GlobalSpark.HDFS_IMG_DIR + timestamp +  "_" + filename;

        // LFS to HDFS
        GlobalSpark.LFStoHDFS(imagePath, imageSparkPath);

        FindPicResponse findPicResponse = cvService.submitSpark(absoluteRootPath, imageSparkPath,
                videoname, timestamp);
        return findPicResponse;
    }
}
