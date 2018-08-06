package camt.se.fas.service;

import com.nexmo.client.NexmoClientException;

import java.io.IOException;

public interface SMSService {
    Boolean sendSMS(String phonenumber, String refCOde, int otp) throws IOException, NexmoClientException;
}
