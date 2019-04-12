package hk.hku.cs.shijian.cccbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Service Launcher Class (Local IDE Run without tomcat)
 *  warning: should revise pom.xml and run
 *  本地无tomcat时测试用Launcher类，可以打开启web服务
 *  警告: 需要更改pom.xml中文注释处
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
