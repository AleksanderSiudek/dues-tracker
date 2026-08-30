# Dues Tracker

[![CI](https://github.com/AleksanderSiudek/chosenone/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/AleksanderSiudek/chosenone/actions/workflows/ci.yml)

A backend application for tracking member balances based on charges and payments.
It is designed for communities where each member can have recurring obligations,
partial payments, and outstanding debt.

## Current capabilities

- Register and list members.
- Add and list charges.
- Calculate current member balance.
- Determine whether a member is settled.
- List debtors and calculate total debt.
- Calculate late fee based on overdue time.
- Expose debtor listing as an MCP tool.

## Domain model

- Member: identified by `id` and `fullName`.
- Charge: `idOfMember`, `amount`, `dueDate`, `title`.
- Payment: `idOfMember`, `amount`, `date`.

## API endpoints

### Members

- `GET /members` - list all members.
- `GET /members/{id}` - get a single member.
- `GET /members/{id}/balance` - get member balance as of today.
- `POST /members` - create a member.

Example:

```bash
curl -X POST http://localhost:8080/members \
	-H "Content-Type: application/json" \
	-d '{"id": 3, "fullName": "John Smith"}'
```

### Charges

- `GET /charges` - list all charges.
- `POST /charges` - create a charge.

Example:

```bash
curl -X POST http://localhost:8080/charges \
	-H "Content-Type: application/json" \
	-d '{"idOfMember": 1, "amount": 50.00, "dueDate": "2026-08-01", "title": "rent"}'
```

## Validation and errors

- Domain objects validate input and throw `IllegalArgumentException` for invalid data.
- `MemberNotFoundException` is returned as HTTP 404.
- `IllegalArgumentException` is returned as HTTP 400.

## Architecture and package layout

```text
com.duetracker
|- DueTrackerApplication
|- account      (business logic: balance, debtors, late fee)
|- bootstrap    (sample data loader)
|- charge       (charge model, repository, controller)
|- error        (exceptions and global handler)
|- member       (member model, repository, controller)
`- payment      (payment model, repository, MCP tools)
```

## Tech stack

- Java 25
- Spring Boot 4.1
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Spring AI MCP Server (tool endpoint support)
- Testcontainers + JUnit 5
- Maven Wrapper
- GitHub Actions CI

## Prerequisites

- JDK 25
- Docker (required for Testcontainers-based tests)

## Run locally

1. Start PostgreSQL for local development:

```bash
docker run --name duetracker-postgres \
	-e POSTGRES_DB=duestracker \
	-e POSTGRES_USER=dues \
	-e POSTGRES_PASSWORD=dues \
	-p 5434:5432 \
	-d postgres:17
```

2. Start the application:

```bash
./mvnw spring-boot:run
```

Application URL: `http://localhost:8080`

## Build and test

```bash
./mvnw clean verify
```

## Seed data

On startup, sample records are loaded if the database is empty:

- Member 1 pays in full.
- Member 2 underpays and remains a debtor.

## Documentation

- [Business description and data examples](docs/business-description-and-data-examples.md)

## Roadmap

- Add payment REST endpoints.
- Add paging and filtering for list endpoints.
- Introduce DTO-level request validation.
- Add profile-based control for seed data.
- Add API documentation (OpenAPI/Swagger).