package camt.se.fas.service.old;

import org.jcodec.api.JCodecException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface VideoService {
    void captureFramesFromVideo(String filename) throws IOException, JCodecException, Exception;
    boolean uploadImageToStorage();
}
