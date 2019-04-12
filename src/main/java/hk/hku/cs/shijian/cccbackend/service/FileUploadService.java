package hk.hku.cs.shijian.cccbackend.service;

import hk.hku.cs.shijian.cccbackend.entity.response.CommonResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Service
public class FileUploadService {

    public CommonResponse storeUploadedFile(HttpServletRequest request, MultipartHttpServletRequest multiReq, String dir) {

        //Construct Response
        CommonResponse response;

        MultipartFile mFile = multiReq.getFile("file");
        if (mFile == null) {
            response = new CommonResponse(5, "No File");
            return response;
        }

        //Construct absolute root path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF/classes/static/data/";
        absoluteRootPath += dir;

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
