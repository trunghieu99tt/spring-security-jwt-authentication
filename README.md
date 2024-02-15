This is an example of spring security with Jwt authentication in a spring boot application.

```
Technologies
- Java 17
- Spring Boot 3.2.2
- Spring Security
- JWT
- H2 Database
- Gradle
- Lombok
```

## Build the application

```bash
./gradlew build
```

## Run the application

```bash
./gradlew bootRun
```

## Test the application

### SignUp

```bash
curl --location 'http://localhost:8080/api/auth/signup' \
--header 'Authorization: Bearer test' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5AC0EEFC397FC1AA2F51BA65FE076172' \
--data '{
    "username": "test",
    "password": "test",
    "name": "Your name"
}'
```

### Login

```bash
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5AC0EEFC397FC1AA2F51BA65FE076172' \
--data '{
    "username": "test",
    "password": "test"
}'
```

### Get List of Blogs

```bash
curl --location 'http://localhost:8080/api/posts' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aG9yaXRpZXMiOltdLCJleHAiOjE3MDc5MjQ4NDN9.5gV5ZAZcEVHgnhyqOJ2AHeSjqtu5WT7HRwS20ES-5od8GFHuFDUdZlyZxmzagUt0nOa6FHb40AbCtuUH6-z1Cw' \
--header 'Cookie: JSESSIONID=5AC0EEFC397FC1AA2F51BA65FE076172'
```
