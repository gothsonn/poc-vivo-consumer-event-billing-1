package com.accenture.pocvivoconsumereventbilling1.controller;

import com.accenture.pocvivoconsumereventbilling1.model.FinancialAccount;
import com.accenture.pocvivoconsumereventbilling1.service.BlobService;
import com.accenture.pocvivoconsumereventbilling1.service.SftpService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("blob")
public class BlobController {

    protected final BlobService blobService;

    private final SftpService sftpService;

    public BlobController(BlobService blobService, SftpService sftpService) {
        this.blobService = blobService;
        this.sftpService = sftpService;
    }

    @GetMapping("/readFile/{id}")
    public FinancialAccount readBlobFile(@PathVariable("id") String id) throws IOException, JSchException, SftpException {
//        return blobService.downloadFile(id);
        return sftpService.downloadFile(id);
    }


}
