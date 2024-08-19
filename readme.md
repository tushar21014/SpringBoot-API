# JournalApp - Tour for Springboot API's

JournalApp is a Spring Boot-based web application that allows users to create, read, update, and delete their personal journals. The application provides secure user authentication with JWT, ensuring that only authorized users can access their journals.

## Features

- **User Authentication:**
  - Secure JWT-based authentication system.
  - Users can register and log in to access their journals.
  
- **CRUD Operations:**
  - **Create:** Users can write new journal entries.
  - **Read:** Users can view their existing journal entries.
  - **Update:** Users can modify their journal entries.
  - **Delete:** Users can remove journal entries they no longer need.
  
- **User Management:**
  - Manage user profiles, including updating user information.
  - Each user's journals are securely associated with their account.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 11 or higher
- **Maven** 3.6+
- **MySQL** or any other supported relational database
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/tushar21014/JournalApp.git
   cd JournalApp
   ```

2. **Set up the database:**
   - Create a new database in MySQL (or your preferred RDBMS).
   - Update the `application.properties` file with your database credentials.
   
   Example:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/journaldb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**
   - The application will run on `http://localhost:8080`.
   - Use Postman or any other API client to test the CRUD operations and JWT authentication.

### API Endpoints

- **User Registration:**
  - `POST /api/auth/register`
  
- **User Login:**
  - `POST /api/auth/login`

- **Create Journal:**
  - `POST /api/journals`
  
- **Get All Journals:**
  - `GET /api/journals`
  
- **Get Journal by ID:**
  - `GET /api/journals/{id}`
  
- **Update Journal:**
  - `PUT /api/journals/{id}`
  
- **Delete Journal:**
  - `DELETE /api/journals/{id}`

### Security

- **JWT Authentication:**
  - All API requests are secured with JWT tokens.
  - Ensure you pass the `Authorization` header with the token for secured endpoints.
  
### Future Enhancements

- Develop a frontend for easier interaction with the API.

## Acknowledgments

- Thanks to the open-source community for their contributions and support.
