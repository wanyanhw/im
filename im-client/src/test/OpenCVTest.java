import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * @author wanyanhw
 * @date 2022/5/26 15:47
 */
public class OpenCVTest {

    public void markFaceInImage() {
        System.out.println("\nRunning DetectFaceDemo");
        // Create a face detector from the cascade file in the resources
        // directory.
        CascadeClassifier faceDetector = new CascadeClassifier("D:\\opencv\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml");
        String[] picPathArr = new String[25];
        for (int i = 0; i < picPathArr.length; i++) {
            picPathArr[i] = "E:/space/idea/im/im-client/target/classes/girls.jpg";
        }
//        picPathArr[0] = "E:/space/idea/im/im-client/target/classes/girls.jpg";
//        picPathArr[1] = "E:/space/idea/im/im-client/target/classes/lena.png";
        Mat[] mats = new Mat[picPathArr.length];
        for (int i = 0; i < picPathArr.length; i++) {
            long begin = System.currentTimeMillis();
            Mat image = mats[i] = Imgcodecs.imread(picPathArr[i]);
            long readPicEnd = System.currentTimeMillis();

            // Detect faces in the image.
            // MatOfRect is a special container class for Rect.
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(image, faceDetections);
            long detectPicEnd = System.currentTimeMillis();

            System.out.println(String.format("Detected %s faces, read cost %d ms, detect cost %d ms", faceDetections.toArray().length, readPicEnd - begin, detectPicEnd - readPicEnd));
            // Draw a bounding box around each face.
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
            }
        }

        new Thread(() -> {
            for (int i = 0; i < mats.length; i++) {
                // Save the visualized detection.
                long begin = System.currentTimeMillis();
                String filename = "faceDetection" + i + ".png";
                Imgcodecs.imwrite(filename, mats[i]);
                long end = System.currentTimeMillis();
                System.out.println(String.format("Writing %s, cost %d ms", filename, end - begin));
            }
        }).start();
    }

    public static class HelloOpenCV {
        public static void main(String[] args) {
            System.out.println("Hello, OpenCV");
            System.load("D:\\opencv\\opencv\\build\\java\\x64\\opencv_java455.dll");
            new OpenCVTest().markFaceInImage();
        }
    }
}
