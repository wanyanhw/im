import com.wanyan.imclient.ExecutorConfig;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.task.AsyncTaskExecutor;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * @author wanyanhw
 * @date 2022/5/26 17:16
 */
public class VideoGrabberTest {
    private static AsyncTaskExecutor executor;
    static {
        System.load("D:\\opencv\\opencv\\build\\java\\x64\\opencv_java455.dll");
        ExecutorConfig executorConfig = new ExecutorConfig();
        executor = executorConfig.asyncTaskExecutor();
    }

    /**
     * 缓冲池
     */
    private static transient LinkedList<ImageIcon> imageBuffer = new LinkedList<>();

    private static CascadeClassifier faceDetector = new CascadeClassifier("D:\\opencv\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface_improved.xml");

    public static void main(String[] args) {

        String path = "E:\\resources\\video\\video2_sub.mp4";
//        path = "E:\\space\\idea\\im\\im-client\\src\\main\\resources\\lena.png";
//        path = "E:\\resources\\video\\baby.mp4";
        new VideoGrabberTest().play(path);
    }

    private void play(String path) {
       try {
            FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(path);
            grabber.start();
            int total = 0;
            for (; ; total++) {
                long begin = System.currentTimeMillis();
                Frame frame = grabber.grabImage();
                if (frame == null) {
                    break;
                }
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage image = converter.convert(frame);

                image = getDetectedBufferedImage(image);

                ImageIcon imageIcon = new ImageIcon(image);
                System.out.println(String.format("已添加: %d, cost: %d", total, System.currentTimeMillis() - begin));
                imageBuffer.offer(imageIcon);
                if (total == 200) {
                    executor.submit(this::show);
                }
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }

    private void show() {
        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 600);
        jFrame.setVisible(true);
        JLabel jLabel = new JLabel();
        jFrame.add(jLabel);
        ImageIcon imageIcon;
        while (true) {
            imageIcon = imageBuffer.poll();
            if (imageIcon == null) {
                try {
                    Thread.sleep(10000);
                    if (imageBuffer.size() == 0) {
                        // 不再继续加载图片时，跳出循环
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long begin = System.currentTimeMillis();
            jLabel.setIcon(imageIcon);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("剩余: %d, cost: %d", imageBuffer.size(), System.currentTimeMillis() - begin));
        }
        System.out.println("播放结束");
        jFrame.dispose();
    }

    /**
     * 定位人脸位置，生成新图像
     * @param originalImage 原图
     * @return BufferedImage 新图
     */
    private BufferedImage getDetectedBufferedImage(BufferedImage originalImage) {
        Mat mat = MatTransformUtil.bufferImage2Matrix(originalImage);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(mat, faceDetections);
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }
        return MatTransformUtil.mat2BufferImage(mat);
    }
}
