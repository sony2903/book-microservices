Write-Host "Building API service..."
cd test-auth-api
mvn clean package
cd ..

Write-Host "Building User Service..."
cd test-subject-service
mvn clean package
cd ..

Write-Host "Building Subject Service..."
cd test-user-service
mvn clean package
cd ..

Write-Host "Starting Docker Compose..."
docker-compose up --build -d
