package camt.se.fas.service;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

import java.util.concurrent.TimeUnit;

public class SMSServiceImpl implements SMSService {
    private final String NEXMO_API_KEY = "ec11abd5";
    private final String NEXMO_API_SECRET ="OHhCught1lO3a8z5";
    @Override
    public Boolean sendSMS() {
        AuthMethod auth = new TokenAuthMethod(NEXMO_API_KEY, NEXMO_API_SECRET);
        NexmoClient client = new NexmoClient(auth);
        SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(new TextMessage(
                "Facial Authentication system",
                TO_NUMBER,
                "A text message sent using the Nexmo SMS API"));
        for (SmsSubmissionResult response : responses) {
            System.out.println(response);
        }
        return null;
    }
}
