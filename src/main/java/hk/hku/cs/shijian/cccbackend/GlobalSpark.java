package hk.hku.cs.shijian.cccbackend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.deploy.yarn.Client;
import org.apache.spark.deploy.yarn.ClientArguments;

import java.io.IOException;
import java.util.Date;

public class GlobalSpark {
    public static String HDFS_IMG_DIR = "hdfs://10.42.2.33:9000/uploaded_images/";
    public static String HDFS_VIDEO_DIR = "hdfs://10.42.2.33:9000/uploaded_videos/";
    public static String HDFS_RES_DIR = "hdfs://10.42.2.33:9000/results/";
    public static String SPARK_HOME = "/opt/spark-2.4.0-bin-hadoop2.7";

    public static void HDFStoLFS(String src, String dst) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path("/opt/hadoop-2.7.5/etc/hadoop/core-site.xml"));
        FileSystem hdfsFileSystem = FileSystem.get(conf);
        Path local = new Path(dst);
        Path hdfs = new Path(src);
        hdfsFileSystem.copyToLocalFile(false, hdfs, local);
    }

    public static void LFStoHDFS(String src, String dst) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path("/opt/hadoop-2.7.5/etc/hadoop/core-site.xml"));
        FileSystem hdfsFileSystem = FileSystem.get(conf);
        Path local = new Path(src);
        Path hdfs = new Path(dst);
        hdfsFileSystem.copyFromLocalFile(false, local, hdfs);
    }

    public static void callCMD(String imgPath, String videoPath, String timestamp) {
        try {
            String cmd = "/opt/spark-2.4.0-bin-hadoop2.7/bin/spark-submit --class myjavacv.spark.VideoSearcher " +
                    "/home/hduser/id_student37_test/MyJavaCV-master/out/artifacts/MyJavaCV_jar/MyJavaCV.jar " +
                    imgPath + " " + videoPath + " " + timestamp;
            Process process = Runtime.getRuntime().exec(cmd);
            int status = process.waitFor();
            if (status != 0) {
                System.err.println("Failed to call shell's command and the return status's is: " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
