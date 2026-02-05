#!/usr/bin/env bash
set -e
echo "Building project with Maven (inside Docker)..."
docker run --rm -v "$(pwd)":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean package -DskipTests
echo "Build succeeded."
