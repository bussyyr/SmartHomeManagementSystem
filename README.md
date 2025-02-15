# Smart Home Management System with Energy Reports

## Introduction
The **Smart Home Management System with Energy Reports** is a backend solution designed for managing and automating smart home devices. It provides an intuitive interface for users to track energy consumption, set automation rules, and control devices efficiently. The system follows **Hexagonal Architecture (Ports and Adapters Pattern)** and is implemented using **Java, Spring Boot, REST API, and GraphQL Schema**.

## Features
- Smart home device and energy report management
- Automation rule integration for energy efficiency
- REST API for seamless communication
- GraphQL Schema for flexible data queries
- Database persistence with MySQL
- Comprehensive unit and integration testing

## Conditions
Ensure that the following software is installed on your system:

- **Java** (version 17 or higher)
- **Maven**
- **Docker & Docker Compose**

Check the installed versions:
```bash
java -version
mvn -version
```

## Project Structure
```
SmartHomeManagementSystem
├── Dockerfile
├── README.md
├── docker-compose.yml
├── pom.xml
└── src
    ├── main
    │   └── java
    │       ├── application
    │       │   ├── Application.java
    │       │   └── controllers
    │       │       ├── AutomationRuleController.java
    │       │       ├── DatabaseController.java
    │       │       ├── DeviceController.java
    │       │       ├── EnergyReportController.java
    │       │       ├── RoomController.java
    │       │       └── UserController.java
    │       ├── config
    │       │   └── HibernateConfig.java
    │       ├── domain
    │       │   ├── models
    │       │   │   ├── AutomationRule.java
    │       │   │   ├── AutomationRuleType.java
    │       │   │   ├── Device.java
    │       │   │   ├── EnergyReport.java
    │       │   │   ├── Room.java
    │       │   │   └── User.java
    │       │   └── ports
    │       │       ├── AutomationRuleService.java
    │       │       ├── DeviceService.java
    │       │       ├── EnergyReportService.java
    │       │       ├── RoomService.java
    │       │       └── UserService.java
    │       ├── infrastructure
    │       │   ├── api
    │       │   │   └── dto
    │       │   │       ├── AutomationRuleInput.java
    │       │   │       ├── DeviceInput.java
    │       │   │       ├── EnergyReportInput.java
    │       │   │       ├── RoomInput.java
    │       │   │       └── UserInput.java
    │       │   └── persistence
    │       │       ├── adapters
    │       │       │   ├── AutomationRuleServiceImpl.java
    │       │       │   ├── DeviceServiceImpl.java
    │       │       │   ├── EnergyReportServiceImpl.java
    │       │       │   ├── RoomServiceImpl.java
    │       │       │   └── UserServiceImpl.java
    │       │       ├── entities
    │       │       │   ├── AutomationRuleEntity.java
    │       │       │   ├── DeviceEntity.java
    │       │       │   ├── EnergyReportEntity.java
    │       │       │   ├── RoomEntity.java
    │       │       │   └── UserEntity.java
    │       │       ├── mapper
    │       │       │   ├── AutomationRuleMapper.java
    │       │       │   ├── DeviceIdMapper.java
    │       │       │   ├── DeviceMapper.java
    │       │       │   ├── EnergyReportMapper.java
    │       │       │   ├── RoomMapper.java
    │       │       │   └── UserMapper.java
    │       │       └── repositories
    │       │           ├── AutomationRuleRepository.java
    │       │           ├── DeviceRepository.java
    │       │           ├── EnergyReportRepository.java
    │       │           ├── RoomRepository.java
    │       │           └── UserRepository.java
    │       └── resources
    │           ├── application.properties
    │           ├── graphql
    │           │   └── schema.graphqls
    │           └── logback.xml
    └── test
        └── java
            ├── CRUDOperations
            │   └── DomainModelsTest.java
            ├── integrationTests
            │   ├── AutomationRuleControllerIT.java
            │   ├── BaseIntegrationTest.java
            │   ├── DeviceControllerIT.java
            │   ├── EnergyReportControllerIT.java
            │   ├── RoomControllerIT.java
            │   └── UserControllerIT.java
            └── persistenceTests
                └── PersistenceComponentTest.java

```

- **/src/main/java/application:** Contains the main entry point and controllers to handle API requests.
- **/src/main/java/application/controllers:** Handles HTTP requests and maps them to service layer operations.
- **/src/main/java/domain/models:** Represents business entities such as Device, Room, User, and EnergyReport.
- **/src/main/java/domain/ports:** Defines interfaces for services to ensure separation of concerns.
- **/src/main/java/infrastructure/api/dto:** Data Transfer Objects (DTOs) for API request and response handling.
- **/src/main/java/infrastructure/persistence/adapters:** Implements repository interfaces for database interaction.
- **/src/main/java/infrastructure/persistence/entities:** Defines JPA entity mappings for database tables.
- **/src/main/java/infrastructure/persistence/repositories:** Interfaces for database queries using Spring Data JPA.
- **/src/test/integrationTests:** Contains integration test cases to validate API functionality.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/SmartHomeSystem.git
   cd SmartHomeSystem
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Start the services:
   ```bash
   docker-compose up -d --build
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Verify the setup:
   ```bash
   mvn verify
   ```

## API Usage
The application provides a REST API. Below are example requests:

### Device API (POST /api/devices)
```json
{
    "name": "Air Conditioner",
    "totalConsumptionPerHour": 2.5,
    "roomId": 1
}
```

### Room API (POST /api/rooms)
```json
{
    "name": "Living Room"
}
```

### User API (POST /api/users)
```json
{
    "name": "James Bond",
    "email": "jamesbond@example.com",
    "password": "securepass"
}
```

## Testing Strategy
The system is extensively tested:
- **Unit Tests:** Verify business logic.
- **Integration Tests:** Ensure API functionality.
- **Persistence Tests:** Validate database interactions.

## Contributors
- **Irmak Damla Özdemir**
- **Buse Okcu**

## References
- Backend Systems Course by **Prof. Dr. Peter Braun**
- Hexagonal Architecture by **Alistair Cockburn**
