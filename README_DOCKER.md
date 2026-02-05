# Docker quickstart for this project

Prerequisites:
- Docker Engine and Docker Compose installed on your machine.

Build and run the app with a local Postgres:

```bash
docker compose up --build
```

Notes:
- The compose file creates a Postgres service with default credentials (`alpha_smartphone_db_user` / `examplepassword`). Change these before production.
- The Spring Boot app reads database config from environment variables set by compose, which override `application.properties`.
- To run only the app (using the existing remote DB), build the image and run it directly:

```bash
docker build -t alphasmartphone-backend .
docker run -p 8080:8080 --env SPRING_DATASOURCE_URL="jdbc:postgresql://db-host:5432/dbname" \
  --env SPRING_DATASOURCE_USERNAME=username --env SPRING_DATASOURCE_PASSWORD=password alphasmartphone-backend
```

Build without installing Maven locally

If you don't have Maven installed locally you can run Maven inside a container using the included scripts.

Windows (PowerShell / CMD):

```powershell
scripts\build.bat
```

Unix / Git Bash / WSL:

```bash
./scripts/build.sh
```

These scripts run `mvn clean package -DskipTests` inside the official Maven Docker image and place the artifact under `target/`.

If you'd prefer a Maven wrapper (`mvnw`), I can add it next — would you like that?
