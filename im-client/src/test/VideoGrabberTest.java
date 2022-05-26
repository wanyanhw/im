import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * @author wanyanhw
 * @date 2022/5/26 17:16
 */
public class VideoGrabberTest {

    static {
        System.load("D:\\opencv\\opencv\\build\\java\\x64\\opencv_java455.dll");
    }
    private static CascadeClassifier faceDetector = new CascadeClassifier("D:\\opencv\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface_improved.xml");
    public static void main(String[] args) {

        String path = "E:\\resources\\video\\video2_sub.mp4";
//        path = "E:\\space\\idea\\im\\im-client\\src\\main\\resources\\lena.png";
        path = "E:\\resources\\video\\baby.mp4";
        new VideoGrabberTest().play(path);
    }

    private void play(String path) {
       try {
            FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(path);
            grabber.start();
            JFrame jFrame = new JFrame();
            jFrame.setSize(800, 600);
            jFrame.setVisible(true);
            JLabel jLabel = new JLabel();
            jFrame.add(jLabel);
            int total = 0;
            for (; ; total++) {
                Frame frame = grabber.grabImage();
                if (frame == null) {
                    break;
                }
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage image = converter.convert(frame);

                image = getDetectedBufferedImage(image);

                ImageIcon imageIcon = new ImageIcon(image);
                System.out.println("已添加:" + total);
                jLabel.setIcon(imageIcon);
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
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
