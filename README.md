# Document Manager Service (Docman)

A robust REST API for managing documents with an advanced tagging system and dynamic search capabilities.

---

## API Documentation

Explore the interactive API documentation (Swagger UI):

**Live API Docs:** *[View Live API Docs](https://sepehrghr.github.io/Docman/)*  

---

## Quick Start

### **Prerequisites**
- Docker & Docker Compose  
- Java 21+

---

## Running the Application

### **1. Start the Database**

```bash
docker-compose up -d
```

### **2. Run the Application**

```bash
./mvnw spring-boot:run
```

---

## Verify Local Instance

- **Local API URL:**  
  http://localhost:8080

- **Local Swagger UI:**  
  http://localhost:8080/swagger-ui/index.html

---

## Testing

To run the unit tests:

```bash
./mvnw test
```
