package com.sena.emailservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailRequest(
        @NotBlank(message = "to es obligatorio")
        @Email(message = "to debe ser un correo válido")
        String to,

        @NotBlank(message = "subject es obligatorio")
        @Size(max = 150, message = "subject no puede superar 150 caracteres")
        String subject,

        @NotBlank(message = "message es obligatorio")
        @Size(max = 5000, message = "message no puede superar 5000 caracteres")
        String message
) {
}
