# quarkus-case-api

Quarkus reactive (Mutiny) service that exposes `POST /api/v1/cases` and forwards
the request to the external Pega-style case API:

```
POST http://some-endpoint/prweb/v1/cases
Headers: client_id, client_secret
```

## Architecture

```
Caller -> CaseResource (JAX-RS, returns Uni<Response>)
            -> CaseService (builds CaseRequest, Base64-encodes HitsTagList)
                -> PegaCaseClient (MicroProfile reactive REST client, Mutiny Uni)
                    -> external POST /prweb/v1/cases
```

- **CreateCaseRequestDto** — what callers send to *us*. `hitsTagList` is a
  plain JSON node; callers don't need to know it has to be Base64-encoded.
- **CaseService.buildExternalRequest()** — Base64-encodes `hitsTagList` and
  assembles the outbound `CaseRequest`/`CaseContent` matching the external
  API's exact field names (`HitsTagList`, `DataSource`, `AlertType`, `TC`,
  `ApplicationId`).
- **PegaCaseClient** — non-blocking REST client; `client_id`/`client_secret`
  go out as headers, never logged.
- Failures from the external call are wrapped as `ExternalApiException` and
  mapped to **502 Bad Gateway** via `ExternalApiExceptionMapper`. Bean
  validation failures map to **400** via `ValidationExceptionMapper`.

## Configuration

`src/main/resources/application.properties`:

```properties
quarkus.rest-client.pega-case-api.url=http://some-endpoint
pega.client.id=${PEGA_CLIENT_ID:client-id-1234567890}
pega.client.secret=${PEGA_CLIENT_SECRET:some-secret67JVMBM7KBVVV9DFD77V6FVFDVFDMB3234}
```

In any real (non-dev) environment, set `PEGA_CLIENT_ID` / `PEGA_CLIENT_SECRET`
as env vars or pull them from a vault — don't ship the literal values.

## Run

```bash
mvn quarkus:dev
```

## Test

```bash
mvn clean test
```

- `CaseServiceTest` — pure Mockito unit test (no container) covering Base64
  encoding/round-trip, request assembly, and error-mapping logic.
- `Base64UtilTest` — focused test on the encoding utility.
- `CaseResourceTest` — `@QuarkusTest` + WireMock standing in for the external
  Pega API; verifies the full HTTP flow (success, 5xx -> 502, validation -> 400).

## Sample request

```bash
curl -X POST http://localhost:8080/api/v1/cases \
  -H "Content-Type: application/json" \
  -d '{
        "caseType": "Some-Case-Type",
        "processId": "SomeProcessID",
        "parentCaseId": "",
        "dataSource": "SomeDataSource",
        "alertType": "AlertT",
        "tc": "5",
        "applicationId": "Some-id-12345",
        "hitsTagList": { "alerts": [ { "id": 1, "name": "Suspicious activity" } ] }
      }'
```

The service Base64-encodes the `hitsTagList` object and forwards it as
`content.HitsTagList` in the outbound payload, with `client_id`/`client_secret`
headers attached, then returns the upstream response (id/status, plus any
other fields Pega returns) with HTTP 201.

## Notes / things to revisit before production

- `quarkus.platform.version` in `pom.xml` is pinned to `3.17.5` — check
  https://quarkus.io for the current LTS/stable release before building.
- `quarkus.http.limits.max-body-size` is set to 20M to accommodate a large
  `HitsTagList`; tune based on real payload sizes.
- Add retry/circuit-breaker (SmallRye Fault Tolerance) around `PegaCaseClient`
  if the upstream is flaky — not included here to keep the example focused.
- `client_secret` should come from a secrets manager (Vault, AWS Secrets
  Manager, etc.) in production, not a properties file default.
