# PayRecon — Payment Reconciliation API

PayRecon is a small fintech portfolio project that demonstrates how a payment platform can compare customer transaction records with settlement records and identify reconciliation exceptions.

## Why this project

Payment reliability is not only about accepting a transaction. A payment platform also needs to know whether the transaction was settled correctly. PayRecon models that workflow with a Java/Spring Boot REST API and a lightweight dashboard.

## Features

- Create payment transactions through a REST API
- Create settlement records through a REST API
- Reconcile transactions by payment reference
- Detect `MATCHED`, `UNMATCHED`, `AMOUNT_MISMATCH`, and `CURRENCY_MISMATCH` cases
- Summary endpoint for reconciliation status counts
- Simple browser dashboard
- Bean validation and duplicate-reference protection
- Unit tests for reconciliation logic
- H2 for zero-configuration local development; PostgreSQL dependency included for production-style deployment

## Tech stack

- Java 17
- Spring Boot 3
- Spring Web / REST
- Spring Data JPA / Hibernate
- PostgreSQL-ready persistence
- H2 for local demo
- Maven
- JUnit 5 + Mockito
- HTML/CSS/JavaScript dashboard

## Run locally

```bash
mvn spring-boot:run
```

Open `http://localhost:8080` for the dashboard.

## API examples

Create a payment:

```bash
curl -X POST http://localhost:8080/api/payments -H "Content-Type: application/json" -d '{"reference":"TX1001","merchant":"Demo Shop","amount":1500.00,"currency":"KES","channel":"MOBILE_MONEY"}'
```

Create the corresponding settlement:

```bash
curl -X POST http://localhost:8080/api/settlements -H "Content-Type: application/json" -d '{"reference":"TX1001","settledAmount":1500.00,"currency":"KES"}'
```

Reconcile:

```bash
curl -X POST http://localhost:8080/api/reconcile/TX1001
```

View status summary:

```bash
curl http://localhost:8080/api/summary
```

## Example workflow

`TX1001` payment = KES 1,500 → settlement = KES 1,500 → `MATCHED`

`TX1002` payment = KES 2,000 → settlement = KES 1,950 → `AMOUNT_MISMATCH`

`TX1003` payment exists but no settlement → `UNMATCHED`

## Portfolio talking points

1. Designed a RESTful payment reconciliation service using Java and Spring Boot.
2. Implemented business rules for transaction-to-settlement matching and exception detection.
3. Used JPA repositories to separate persistence from business logic.
4. Added validation, duplicate-reference protection, and unit tests.
5. Built a lightweight dashboard consuming the REST API.

## Next improvements

- JWT authentication and role-based access
- PostgreSQL deployment
- Merchant-level access controls
- CSV settlement-file ingestion
- Idempotency keys for payment requests
- Audit logs and structured error responses
- Docker and CI/CD
