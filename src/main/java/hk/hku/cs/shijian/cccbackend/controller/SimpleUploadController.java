package hk.hku.cs.shijian.cccbackend.controller;

import hk.hku.cs.shijian.cccbackend.entity.response.CommonResponse;
import hk.hku.cs.shijian.cccbackend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

public class SimpleUploadController {

    private FileUploadService service;

    @Autowired
    public SimpleUploadController(FileUploadService service) {
        this.service = service;
    }

    @RequestMapping("/simple_upload")
    @CrossOrigin
    public CommonResponse upload(HttpServletRequest request,
                                 MultipartHttpServletRequest multiReq) {
        CommonResponse response = service.storeUploadedFile(request, multiReq, "");

        return response;
    }

}
