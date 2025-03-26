#!/bin/bash

# Stop if any command fails
set -e

echo "Building API service..."
cd test-auth-api && mvn clean package && cd ..

echo "Building User Service..."
cd test-user-service && mvn clean package && cd ..

echo "Building Subject Service..."
cd test-subject-service && mvn clean package && cd ..

echo "Starting Docker Compose..."
docker-compose up --build -d