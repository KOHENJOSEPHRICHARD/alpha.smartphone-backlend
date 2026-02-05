@echo off
REM Build the project using the official Maven Docker image (Windows)
echo Building project with Maven (inside Docker)...
docker run --rm -v "%cd%":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
  echo Build failed.
  exit /b %ERRORLEVEL%
)
echo Build succeeded.
