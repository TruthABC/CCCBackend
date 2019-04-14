package hk.hku.cs.shijian.cccbackend.service;

import hk.hku.cs.shijian.cccbackend.Global;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@Service
public class JavaCVLocalService {

    public long calMatchTimeStamp(String absoluteRootPath, String imagePath, String videoPath) {
//        this.DUMP_PATH_1 = absoluteRootPath + "/search_image_by_video";
//        initializeImage(imagePath);
//        initializeVideo(videoPath);
//        try {
//            searchImageByVideo();
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        }
        return 0;
    }

//    private final long SEC = 1000000;
//    private final long INTERVAL = 30 * SEC;
//
//    private String DUMP_PATH_1 = "search_image_by_video";
//    private String mVideoPath;
//    private String mImagePath;

//    private void initializeImage(String imagePath) {
//        mImagePath = imagePath;
//    }
//
//    private void initializeVideo(String videoPath) {
//        mVideoPath = videoPath;
//    }
//
//    private void searchImageByVideo() throws FrameGrabber.Exception {
//        // History Dump File delete
//        File dumpDir = new File(DUMP_PATH_1);
//        Global.deleteAndMkdirs(dumpDir);
//
//        // Init to get video length
//        FFmpegFrameGrabber grabber4Init = new FFmpegFrameGrabber(mVideoPath);
//        grabber4Init.start();
//        long length = grabber4Init.getLengthInTime();
//        grabber4Init.stop();
//
//        // Arrange start timestamps
//        List<Long> startTimeStamps = new ArrayList<>();
//        for (long i = 0;  i < length; i += INTERVAL) {
//            startTimeStamps.add(i);
//        }

        // Create a SparkContext to initialize
//        SparkConf conf = new SparkConf().setMaster("local").setAppName(DUMP_PATH_1);
//
//        // Create a Java version of the Spark Context
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        // Paralleling list to get RDD
//        JavaRDD<Long> startTimeStampRDD = sc.parallelize(startTimeStamps);
//
//        // Run Tasks of Searching
//        JavaPairRDD<Long, Double> similarityRDD = startTimeStampRDD.mapToPair((startTime)->{
//            System.out.println("Task [" + startTime + " to " + Math.min(startTime + INTERVAL, length) + ")");
//            double maxSimilarity = 0.0;
//            long maxSimTime = startTime;
//
//            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(mVideoPath);
//            grabber.start();
//            if (startTime != 0) {
//                grabber.setTimestamp(startTime);
//            }
//
//            opencv_core.Mat hist_ref = GlobalCV.getHSVHistogram(imread(mImagePath));
//            while (true) {
//                Frame frame = grabber.grabImage();
//                if (frame == null) {
//                    break;
//                }
//
//                // Dump image for debug
//                long timeNow = grabber.getTimestamp();
//                ImageIO.write(new Java2DFrameConverter().convert(frame) , "png", new File(DUMP_PATH_1 + "/" + timeNow + ".png"));
//
//                double similarity = calculateSimilarity(frame, hist_ref);
//                if (maxSimilarity < similarity) {
//                    maxSimilarity = similarity;
//                    maxSimTime = timeNow;
//                }
//
//                grabber.setTimestamp(timeNow + (long)(1.0 * SEC));
//                if (grabber.getTimestamp() >= startTime + INTERVAL) {  // 微秒
//                    break;
//                }
//            }
//            grabber.stop();
//
//            System.out.println("Time: " + maxSimTime);
//            System.out.println("Simi: " + maxSimilarity);
//            return new Tuple2<>(maxSimTime, maxSimilarity);
//        });
//
//        // Get max similarity
//        Tuple2<Long, Double> maxTuple = similarityRDD.reduce((x, y) -> {
//            if (x._2 < y._2) {
//                return y;
//            } else {
//                return x;
//            }
//        });
//
//        // show max similarity
//        System.out.println("MaxSimTime: " + maxTuple._1);
//        System.out.println("MaxSimilarity: " + maxTuple._2);

//    }

    /**
     *
     * @param frame the from some timestamp
     * @return the bigger, the more similar (0 to 1)
     */
//    private double calculateSimilarity(Frame frame, opencv_core.Mat hist_ref) {
//        // Convert Frame as source image, Transfer to HSV
//        final opencv_core.Mat src = new OpenCVFrameConverter.ToMat().convert(frame);
//        opencv_core.Mat hist_src = GlobalCV.getHSVHistogram(src);
//
//        double similarity = compareHist(hist_src, hist_ref, CV_COMP_CORREL);
//
//        return similarity;
//    }

}
