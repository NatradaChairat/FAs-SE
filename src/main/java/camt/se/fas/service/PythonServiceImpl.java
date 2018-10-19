package camt.se.fas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class PythonServiceImpl implements PythonService {
    Logger LOGGER = LoggerFactory.getLogger(PythonServiceImpl.class);
    @Override
    public String runScript(String scriptFileName) throws IOException {

        Process process = Runtime.getRuntime().exec(new String[]{"py ", "D:/FAs/FacialAuthentication-SE/src/main/resources/calculateScript.py"});

        InputStream stdInput = process.getInputStream();
        InputStream stdError = process.getErrorStream();
        BufferedReader readerInput = new BufferedReader(new InputStreamReader(stdInput,StandardCharsets.UTF_8));
        BufferedReader readerError = new BufferedReader(new InputStreamReader(stdError,StandardCharsets.UTF_8));
        String line;
        while((line = readerInput.readLine()) != null){
            LOGGER.info("Result: "+line);
        }

        return line;
    }
}
