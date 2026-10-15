# email-service

Spring Boot backend that receives an email send request and forwards it to a Cloudflare Worker (which uses SendGrid).

## Requirements

- Java 21
- Maven 3.9+

## Configuration

Set these environment variables before running:

- `WORKER_URL` - URL of the Cloudflare Worker endpoint.
- `WORKER_TOKEN` - API key sent to the Worker in the `X-API-KEY` header.
- `ALLOWED_ORIGIN` - allowed CORS origin (default: `http://localhost:4200`).

## Requirements

- Java 21
- No need to install Maven: the project includes the Maven Wrapper.

## Run

```bash
.\mvnw spring-boot:run
```

Or build and run:

```bash
.\mvnw clean package
java -jar target/email-service-0.0.1-SNAPSHOT.jar
```

## Tests

```bash
.\mvnw test
```

## Endpoint

`POST /api/email/send`

Body:

```json
{ "to": "user@example.com", "subject": "Hello", "message": "Hi there" }
```

Responses:

- `200 OK`: `{ "ok": true, "message": "Correo enviado" }`
- `400 Bad Request`: validation errors per field
- `502 Bad Gateway`: `{ "ok": false, "message": "No se pudo enviar el correo" }` when the Worker fails or does not respond
