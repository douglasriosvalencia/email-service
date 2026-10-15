package com.sena.emailservice.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.sena.emailservice.config.WorkerProperties;
import com.sena.emailservice.dto.EmailRequest;
import com.sena.emailservice.exception.EmailSendException;

class EmailServiceTest {

    private static final String URL = "https://example.workers.dev/send";
    private static final String TOKEN = "test-token";

    @Test
    void send_succeedsWhenWorkerReturns2xx() {
        WorkerProperties props = new WorkerProperties();
        props.setUrl(URL);
        props.setToken(TOKEN);

        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        RestClient client = builder.defaultHeader("X-API-KEY", TOKEN).build();
        EmailService service = new EmailService(client, props);

        server.expect(requestTo(URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());

        EmailRequest request = new EmailRequest("user@example.com", "Asunto", "Mensaje");
        assertDoesNotThrow(() -> service.send(request));
        server.verify();
    }

    @Test
    void send_throwsWhenWorkerFails() {
        WorkerProperties props = new WorkerProperties();
        props.setUrl(URL);
        props.setToken(TOKEN);

        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        RestClient client = builder.defaultHeader("X-API-KEY", TOKEN).build();
        EmailService service = new EmailService(client, props);

        server.expect(requestTo(URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withServerError());

        EmailRequest request = new EmailRequest("user@example.com", "Asunto", "Mensaje");
        assertThrows(EmailSendException.class, () -> service.send(request));
        server.verify();
    }
}
