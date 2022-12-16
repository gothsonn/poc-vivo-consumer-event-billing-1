package com.accenture.pocvivoconsumereventbilling1.service;

import com.accenture.pocvivoconsumereventbilling1.model.FinancialAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptedFinancialAccount {
    private final ObjectMapper objectMapper;

    private final AES256 aes256;

    public FinancialAccount objectToStringDecode(String account) throws JsonProcessingException {
        return objectMapper.readValue(decryptedString(account), FinancialAccount.class);
    }

    public String objectToStringEncoded(FinancialAccount account) throws JsonProcessingException {
        return encryptedString(objectMapper.writeValueAsString(account));
    }

    public String encryptedString(String accountString) {
        return aes256.encrypt(accountString);
    }

    public String decryptedString(String accountString) {
        return aes256.decrypt(accountString);
    }


}
