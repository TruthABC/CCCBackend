package hk.hku.cs.shijian.cccbackend.service;

import hk.hku.cs.shijian.cccbackend.GlobalSpark;
import hk.hku.cs.shijian.cccbackend.entity.response.FindPicResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
public class JavaCVSparkService {
    private static FindPicResponse findPicResponseCache = null;

    public static FindPicResponse submitSpark(String absoluteRootPath, String imagePath, String videoname, String timestamp) throws Exception {
        String videoPath = GlobalSpark.HDFS_VIDEO_DIR + videoname + ".mp4" ;
        GlobalSpark.callCMD(imagePath, videoPath, timestamp);

        // result file hdfs -> lfs
        String hdfs_path = GlobalSpark.HDFS_RES_DIR + timestamp + ".txt";
        String local_path = absoluteRootPath + "sim_results/" + timestamp + ".txt";
        GlobalSpark.HDFStoLFS(hdfs_path, local_path);

        // read result
        FileInputStream inputStream = new FileInputStream(local_path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String DUMP_PATH_1 = timestamp + "search_image_by_video/";

        String str = null;
        double[] sim = new double[5];
        long[] time = new long[5];
        int i = 0;
        while((str = bufferedReader.readLine()) != null){
            String[] res = str.split(":");
            sim[i] = Double.valueOf(res[0].trim());
            time[i] = Long.parseLong(res[1].trim());
            String dst = absoluteRootPath + "/search_image_by_video/" + timestamp + "_" + time[i] + ".png" ;
            String src = "hdfs://10.42.2.33:9000/" + DUMP_PATH_1 + time[i] + ".png";
            GlobalSpark.HDFStoLFS(src, dst);
            i++;
        }
        //String video_local = absoluteRootPath + "/videos/" + videoname + ".mp4";
        //GlobalSpark.HDFStoLFS(videoPath, video_local);

        // pack response
        findPicResponseCache = new FindPicResponse(0, "CV behaved well");
        findPicResponseCache.setTimeStamps(time);
        findPicResponseCache.setSimilarities(sim);
        findPicResponseCache.setTimestamp(timestamp);

        if (findPicResponseCache == null) {
            return new FindPicResponse(-1, "Exception happen when spark CV searching.");
        }
        return findPicResponseCache;
    }
}
