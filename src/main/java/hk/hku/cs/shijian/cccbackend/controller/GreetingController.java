package hk.hku.cs.shijian.cccbackend.controller;

import hk.hku.cs.shijian.cccbackend.Global;
import hk.hku.cs.shijian.cccbackend.entity.response.GreetingResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    @CrossOrigin
    public GreetingResponse greeting(@RequestParam(value="name", defaultValue="World") String name, HttpServletRequest request) {

        //File Mapping Test (relative path || absolute(real) path || system path || net path)
        String realPath = request.getRealPath("/");
        realPath += "WEB-INF/classes/static/data/";

        File f = new File(realPath + "greeting.txt");
        File dir = new File(realPath);
        if (f.exists())
            Global.deleteDir(f);
        try {
            dir.mkdirs();
            f.createNewFile();
            PrintWriter pw = new PrintWriter(f);
            pw.println(counter);
            pw.println(String.format(template, name));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GreetingResponse(counter.incrementAndGet(),
                String.format(template, name));
    }
}