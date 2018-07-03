package camt.se.fas.service;

import camt.se.fas.entity.Account;

public interface EmailService {
    Boolean sendEmail(Account account);
}
