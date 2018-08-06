package camt.se.fas.service;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class SMSServiceImpl implements SMSService {
    private final String NEXMO_API_KEY = "ec11abd5";
    private final String NEXMO_API_SECRET ="OHhCught1lO3a8z5";
    @Override
    public Boolean sendSMS(String phonenumber, String refCOde, int otp) throws IOException, NexmoClientException {
        AuthMethod auth = new TokenAuthMethod(NEXMO_API_KEY, NEXMO_API_SECRET);
        NexmoClient client = new NexmoClient(auth);
        SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
                "FacialAuthen",
                phonenumber,
                "[Your verification code="+otp+" ref="+refCOde+"] X"));
        for (SmsSubmissionResult response : responses) {
            System.out.println(response);
        }
        return null;
    }
}
