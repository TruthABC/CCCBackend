package hk.hku.cs.shijian.cccbackend.controller;

import hk.hku.cs.shijian.cccbackend.entity.response.CommonResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RestController
public class FindPicController {

    //1. get FileInfoList by relative path
    @RequestMapping("/FindPic")
    @CrossOrigin
    public CommonResponse upload(HttpServletRequest request,
                         MultipartHttpServletRequest multiReq) {
        //Construct Response
        CommonResponse response;

        MultipartFile mFile = multiReq.getFile("file");
        if (mFile == null) {
            response = new CommonResponse(5, "No File");
            return response;
        }

        //Construct absolute root path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF/classes/static/data";
        absoluteRootPath += "/uploadedImage/";

        //Make sure the root directory exists
        File rootDir = new File(absoluteRootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        try {
            // Get the file and save it somewhere
            String pathStr = absoluteRootPath + mFile.getOriginalFilename();
            FileOutputStream fos = new FileOutputStream(new File(pathStr));
            FileInputStream fs = (FileInputStream) mFile.getInputStream();
            byte[] buffer=new byte[1024];
            int len = 0;
            while( (len = fs.read(buffer)) != -1){
                fos.write(buffer,  0, len);
            }
            fos.flush();
            fos.close();
            fs.close();
            response = new CommonResponse(0, "");
        } catch (Exception e) {
            e.printStackTrace();
            response = new CommonResponse(6, "File Server Write Error");
        }

        return response;
    }

}
