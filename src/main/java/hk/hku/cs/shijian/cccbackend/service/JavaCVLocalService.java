package hk.hku.cs.shijian.cccbackend.service;

import hk.hku.cs.shijian.cccbackend.Global;
import hk.hku.cs.shijian.cccbackend.GlobalCV;
import hk.hku.cs.shijian.cccbackend.entity.response.FindPicResponse;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@Service
public class JavaCVLocalService {

    private final long SEC = 1000000;
    private final long INTERVAL = 10 * SEC;

    private String DUMP_PATH_1;
    private String mVideoPath;
    private String mImagePath;
    private FindPicResponse findPicResponseCache;

    public FindPicResponse calMatchTimeStamp(String absoluteRootPath, String imagePath, String videoPath) {
        this.DUMP_PATH_1 = absoluteRootPath + "/search_image_by_video";
        this.mImagePath = imagePath;
        this.mVideoPath = videoPath;
        try {
            findPicResponseCache = null;
            searchImageByVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (findPicResponseCache == null) {
            return new FindPicResponse(-1, "Exception happen when local CV searching.");
        }
        return findPicResponseCache;
    }

    private void searchImageByVideo() throws IOException {
        // History Dump File delete
        File dumpDir = new File(DUMP_PATH_1);
        Global.deleteAndMkdirs(dumpDir);

        // Init to get video length
        FFmpegFrameGrabber grabber4Init = new FFmpegFrameGrabber(mVideoPath);
        grabber4Init.start();
        long length = grabber4Init.getLengthInTime();
        grabber4Init.stop();

        // Arrange start timestamps
        List<Long> startTimeStamps = new ArrayList<>();
        for (long i = 0;  i < length; i += INTERVAL) {
            startTimeStamps.add(i);
        }

        // Run Tasks of Searching
        Map<Double, Long> similarityPair = new TreeMap<>(Comparator.reverseOrder());
        for (long startTime: startTimeStamps) {
            System.out.println("Task [" + startTime + " to " + Math.min(startTime + INTERVAL, length) + ")");
            double maxSimilarity = 0.0;
            long maxSimTime = startTime;

            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(mVideoPath);
            grabber.start();
            if (startTime != 0) {
                grabber.setTimestamp(startTime);
            }

            opencv_core.Mat hist_ref = GlobalCV.getHSVHistogram(imread(mImagePath));
            while (true) {
                Frame frame = grabber.grabImage();
                if (frame == null) {
                    break;
                }

                // Dump image for debug
                long timeNow = grabber.getTimestamp();
                ImageIO.write(new Java2DFrameConverter().convert(frame) , "png", new File(DUMP_PATH_1 + "/" + timeNow + ".png"));

                double similarity = calculateSimilarity(frame, hist_ref);
                if (maxSimilarity < similarity) {
                    maxSimilarity = similarity;
                    maxSimTime = timeNow;
                }

                grabber.setTimestamp(timeNow + (long)(1.0 * SEC));
                if (grabber.getTimestamp() >= startTime + INTERVAL) {  // 微秒
                    break;
                }
            }
            grabber.stop();

            System.out.println("Time: " + maxSimTime);
            System.out.println("Simi: " + maxSimilarity);
            similarityPair.put(maxSimilarity, maxSimTime);
        }

        // show max similarity
        System.out.println("MaxSimTime: " + ((TreeMap)similarityPair).lastKey());
        System.out.println("MaxSimilarity: " + ((TreeMap)similarityPair).lastEntry());

        long[] timeStamps = new long[5];
        double[] similarities = new double[5];

        Iterator<Double> it = similarityPair.keySet().iterator();
        for (int i = 0; i < 5 && it.hasNext(); i++) {
            double simi = it.next();
            long time = similarityPair.get(simi);
            timeStamps[i] = time;
            similarities[i] = simi;
        }
        findPicResponseCache = new FindPicResponse(0, "CV behaved well");
        findPicResponseCache.setTimeStamps(timeStamps);
        findPicResponseCache.setSimilarities(similarities);
    }

    /**
     *
     * @param frame the from some timestamp
     * @return the bigger, the more similar (0 to 1)
     */
    private double calculateSimilarity(Frame frame, opencv_core.Mat hist_ref) {
        // Convert Frame as source image, Transfer to HSV
        final opencv_core.Mat src = new OpenCVFrameConverter.ToMat().convert(frame);
        opencv_core.Mat hist_src = GlobalCV.getHSVHistogram(src);

        double similarity = compareHist(hist_src, hist_ref, CV_COMP_CORREL);

        return similarity;
    }

}
