package camt.se.fas.service;


public interface EmailService {

    Boolean sendVerifyEmail(String email, String uid);

    Boolean sendSuccessEmail(String email);

    Boolean sendResultAuthenProcessEmail(String email, String status,
                                         String reason, String uid);
}
