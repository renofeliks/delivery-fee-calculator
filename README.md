# Fujitsu - Delivery Fee Calculator project

This is a Java Spring Boot application developed for the **Fujitsu Java Programming Trail Task 2025**. The application calculates the delivery fee for the food courires based on:
- City (Tallinn, Tartu, Pärnu)
- Vehicle type (Car, Scooter, Bike)
- Current weather conditions (fetched automatically from The Estonian Environment Agency)

- The fee consists of **regional base fee** and **extra fees** depending on temperature, wind speed, and weather phenomenon.

# Technologies used
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 In-Memory Database
- Spring Scheduling
- JUnit + Mockito (for testing)
- Maven

# Features
- Configurable schedules task to fetch weather data every hour
- Weather data retrieved from Estonian Environment Agency (XML)
- Data persisted to H2 database, history preserved
- Delivery fee calculation logic based on business rules
- Unit tested service layer (testing delibery fee logic)
- REST API to get deliver fee on demand
- Input validation with meaningful error responses

# How to run the application

1. Clone the repository
2. Build the project: ```mvn clean install```
3. Run the application: ```mvn spring-boot:run```
- Test the API (for example with Postman)

Endpoint: ```POST /api/delivery-fee/calculate```

Sample request body:
```
{
  "city": "Tartu",
  "vehicleType": "Bike"
}
```
- Configure Cron Job (Default schedule is set to run hourly at HH:15:00)

In WeatherDataFetcher class uncomment ```@Scheduled(cron = "0 * * * * ?")``` and comment ```@Scheduled(cron = "0 15 * * * ?")```

- H2 Database Console

URL: ```http://localhost:8080/h2-console```

JDBC URL: ```jdbc:h2:mem:deliverydb```

User: sa

Password: (leave empty)

- Run unit tests: ```mvn test```

# Future improvements
- Introduce **Lombok** for getters, setters, constructors
- Migrate from in-memory H2 to for example PostgreSQL
- Custom exception handling using ```@ControllerAdvice```

# Author
Reno Feliks Lindvere

Developed as part of Fujitsu’s Java Programming Trial Task 2025

Email: renofeliks.lindvere@gmail.com

# Note
In the project structure and package naming, the company name "Fujitsu" was mistakenly spelled as "Fuijtsu"

The incorrect spelling was not intentional and I apologize for this oversight.

Thank you for understanding.
