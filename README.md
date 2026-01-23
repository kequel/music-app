# Music Manager

Full Stack Microservices Project developed as part of the Internet Services Architectures laboratory course at Gdańsk University of Technology. It evolved from a simple Java SE application into a containerized, distributed microservices system. UI is inspired by Spotify App.


## Author
- Karolina Glaza [GitHub](https://github.com/kequel)


## System Architecture
The project implements a Music Management system consisting of:

**Album Service:** Manages music albums (Categories).

**Song Service:** Manages songs (Elements) with Load Balancing across two instances.

**Gateway Service:** Spring Cloud Gateway acting as a single entry point.

**Eureka Service:** Service discovery for all microservices.

**Angular App:** Frontend application for managing the music library.

**Persistence:** Dedicated MySQL databases for each service with volume persistence.


## Prerequisites
- Docker and Docker Desktop
- Node.js & Angular CLI (for local development)
- Java 17+ (for local development)


## Running with Docker Compose
To launch the entire system:
```bash
docker-compose up -d --build
```

To shut down:
```bash
docker-compose down -v
```


## Access Points
Frontend: [http://localhost:3000](http://localhost:3000)

API Gateway: [http://localhost:8080](http://localhost:8080)

Eureka Dashboard: [http://localhost:8761](http://localhost:8761)
