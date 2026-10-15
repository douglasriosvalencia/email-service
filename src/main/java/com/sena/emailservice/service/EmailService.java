package com.sena.emailservice.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.sena.emailservice.config.WorkerProperties;
import com.sena.emailservice.dto.EmailRequest;
import com.sena.emailservice.exception.EmailSendException;

@Service
public class EmailService {

    private final RestClient restClient;
    private final WorkerProperties workerProperties;

    public EmailService(RestClient restClient, WorkerProperties workerProperties) {
        this.restClient = restClient;
        this.workerProperties = workerProperties;
    }

    public void send(EmailRequest request) {
        try {
            restClient.post()
                    .uri(workerProperties.getUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException ex) {
            throw new EmailSendException("No se pudo enviar el correo", ex);
        } catch (Exception ex) {
            throw new EmailSendException("No se pudo enviar el correo", ex);
        }
    }
}
