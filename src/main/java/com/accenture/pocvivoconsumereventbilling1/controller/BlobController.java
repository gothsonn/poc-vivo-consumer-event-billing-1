package com.accenture.pocvivoconsumereventbilling1.controller;

import com.accenture.pocvivoconsumereventbilling1.model.FinancialAccount;
import com.accenture.pocvivoconsumereventbilling1.service.SftpService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("blob")
public class BlobController {

    private final SftpService sftpService;

    public BlobController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @GetMapping("/readFile/{id}")
    public FinancialAccount readBlobFile(@PathVariable("id") String id) throws IOException, JSchException, SftpException {
        return sftpService.downloadFile(id);
    }

}
