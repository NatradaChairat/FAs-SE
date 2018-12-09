package camt.se.fas.service;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoServiceImpl implements VideoService {

    @Override
    public void captureFramesFromVideo(String filename) throws IOException, JCodecException, Exception {
        double startSec = 5;
        int frameCount = 100;

        File file = new File(filename);
        FrameGrab grab = null;
        grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        grab.seekToSecondPrecise(startSec);

        for (int i = 0; i < frameCount; i++) {
            Picture picture = grab.getNativeFrame();
            System.out.println("Capture Video: "+picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());
            //for JDK (jcodec-javase)
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bufferedImage, "png", new File("video-frame-" + i+"-"+System.currentTimeMillis() + ".png"));
        }


    }

    @Override
    public boolean uploadImageToStorage() {

         return true;
    }
}
