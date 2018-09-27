package camt.se.fas.service;

import java.io.IOException;

public interface PythonService {
    String runScript(String scriptFileName) throws IOException;
}
