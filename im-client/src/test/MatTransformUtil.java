import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author wanyanhw
 * @date 2022/5/26 17:50
 */
public class MatTransformUtil {

    public static BufferedImage mat2BufferImage(Mat matrix) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode("tmp.jpg", matrix, matOfByte);
        byte[] bytes = matOfByte.toArray();
        BufferedImage bufferedImage = null;
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            bufferedImage = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public static Mat bufferImage2Matrix(BufferedImage original) {
        BufferedImage bufferedImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.drawImage(original, 0, 0, null);
        graphics.dispose();
        DataBufferByte dataBuffer = (DataBufferByte) original.getRaster().getDataBuffer();
        byte[] bufferData = dataBuffer.getData();
        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, bufferData);
        return mat;
    }
}
