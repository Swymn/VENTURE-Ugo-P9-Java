# Medilab Readme - Backend

# Useful request (testing purpose)

```bash
curl -X PATCH http://localhost:8080/patients/550e8400-e29b-41d4-a716-446655440000 -H "Content-Type: application/json" -d '{
  "identifier": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "John",
  "lastName": "Doe",
  "birthDate": "2023-10-01T00:00:00Z",
  "gender": "Male",
  "address": null,
  "phoneNumber": null
}'
```