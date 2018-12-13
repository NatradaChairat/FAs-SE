package camt.se.fas.service.old;

import java.io.IOException;

public interface PythonService {
    String runScript(String loginImage, String originalImage) throws IOException;
}
