# Campaign Management App

A full-stack application for managing advertising campaigns and tracking internal Emerald Account balances.

## Architecture & Design Patterns

* **DTO (Data Transfer Object):** Implemented using Java `record` types to separate database entities (`Campaign`) from the REST API payload (`CampaignDto`).
* **Service Layer / 3-Tier Architecture:** Clear separation of concerns between REST Controllers, Business Logic (Services), and Data Access (Repositories).
* **Transactional Operations:** Utilizes Spring's `@Transactional` to guarantee atomic operations, preventing campaign creation if the account balance deduction fails.
* **Global Exception Handling:** Uses `@ControllerAdvice` to intercept business and validation exceptions, returning standardized JSON error responses.

## Tech Stack

* **Backend:** Java 25, Spring Boot 3, Data JPA, H2 Database (In-Memory)
* **Testing:** JUnit 5, Mockito
* **Frontend:** React (Vite), React Router, Axios

## Gallery

### Dashboard & Campaigns

<img width="2627" height="1354" alt="image" src="https://github.com/user-attachments/assets/07eecbd3-f05c-4de4-bbb3-33b1fc030256" />

### Adding Form

<img width="2287" height="1697" alt="image" src="https://github.com/user-attachments/assets/d0ecbac1-fab2-4838-b2e6-930e658134f8" />

### Editing Form

<img width="2278" height="1683" alt="image" src="https://github.com/user-attachments/assets/77076b6a-fa81-4a2f-9418-f9ce07f6307a" />

