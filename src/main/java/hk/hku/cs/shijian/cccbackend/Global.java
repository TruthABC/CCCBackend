package hk.hku.cs.shijian.cccbackend;

import java.io.File;

public class Global {

//    public static final String SERVICE_ROOT_URL = "http://123.207.6.234:8080/aacloud";
//    public static final String DATA_ROOT_URL = "http://123.207.6.234:8080/aacloud/data";
//    public static final String DISK_ROOT_URL = "http://123.207.6.234:8080/aacloud/data/disk";//"/?user?/files"

    public static void deleteDir(File dir) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            for (File file: dir.listFiles()) {
                deleteDir(file);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }

}
