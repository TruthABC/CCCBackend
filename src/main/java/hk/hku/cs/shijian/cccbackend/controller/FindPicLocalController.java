package hk.hku.cs.shijian.cccbackend.controller;

import hk.hku.cs.shijian.cccbackend.entity.response.CommonResponse;
import hk.hku.cs.shijian.cccbackend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FindPicLocalController {

    private FileUploadService service;

    @Autowired
    public FindPicLocalController(FileUploadService service) {
        this.service = service;
    }

    @RequestMapping("/find_pic_local")
    @CrossOrigin
    public CommonResponse findPicLocal(HttpServletRequest request,
                         MultipartHttpServletRequest multiReq) {

        CommonResponse response = service.storeUploadedFile(request, multiReq, "uploadedImage/");

        if (response.getErrcode() != 0) {
            return response;
        }



        return response;
    }

}
