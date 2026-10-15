package com.sena.emailservice.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.emailservice.dto.EmailRequest;
import com.sena.emailservice.dto.EmailResponse;
import com.sena.emailservice.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> send(@Valid @RequestBody EmailRequest request) {
        emailService.send(request);
        return ResponseEntity.ok(new EmailResponse(true, "Correo enviado"));
    }
}
