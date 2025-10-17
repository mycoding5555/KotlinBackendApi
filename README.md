# Student Attendance System - Kotlin Spring Boot API

A comprehensive REST API for managing student attendance in educational institutions. This system allows teachers to track student participation across different subjects, groups, and time sessions.

## Features

- 🔐 **Authentication & Authorization**: JWT-based authentication for teachers
- 👥 **Teacher Management**: Complete CRUD operations for teacher accounts
- 📚 **Student Management**: Full student lifecycle management
- 🏫 **Group Management**: Organize students into groups/classes
- 📖 **Subject Management**: Manage subjects and assign teachers
- ⏰ **Timeslot Management**: Schedule classes with time sessions (0-9)
- ✅ **Attendance Tracking**: Mark attendance with PRESENT/LATE/ABSENT status
- 📊 **Statistics**: Generate attendance reports and statistics

## Technology Stack

- **Backend**: Kotlin 2.0.21 + Spring Boot 3.4.0
- **Runtime**: Java 21 LTS (OpenJDK 21.0.7)
- **Database**: SQLite with Hibernate/JPA
- **Security**: Spring Security + JWT
- **Build Tool**: Gradle 8.5
- **Build Tool**: Gradle 8.5
- **Java Version**: 17

## Project Structure

```
src/main/kotlin/com/attendance/
├── controller/          # REST API endpoints
├── service/            # Business logic layer
├── repository/         # Data access layer
├── entity/             # JPA entities
├── dto/                # Data transfer objects
├── config/             # Configuration classes
└── util/               # Utility classes
```

## Database Schema

### Core Entities
- **Teachers**: User accounts for authentication and subject assignment
- **Students**: Student information and enrollment
- **Groups**: Class/group organization
- **Subjects**: Course subjects with teacher assignment
- **Timeslots**: Scheduled class sessions
- **Attendance**: Daily attendance records with status tracking

## API Endpoints

### Authentication (`/api/auth`)
- `POST /login` - Teacher login
- `POST /register` - Teacher registration
- `POST /forgot-password` - Password recovery

### Teachers (`/api/teachers`)
- `GET /` - List all teachers
- `GET /{id}` - Get teacher by ID
- `POST /` - Create new teacher
- `PUT /{id}` - Update teacher
- `DELETE /{id}` - Delete teacher

### Students (`/api/students`)
- `GET /` - List all students
- `GET /{id}` - Get student by ID
- `POST /` - Create new student
- `PUT /{id}` - Update student
- `DELETE /{id}` - Delete student

### Groups (`/api/groups`)
- `GET /` - List all groups
- `GET /{id}` - Get group by ID
- `GET /{id}/students` - Get students in group
- `POST /` - Create new group
- `POST /add-students` - Add students to group
- `PUT /{id}` - Update group
- `DELETE /{id}` - Delete group

### Subjects (`/api/subjects`)
- `GET /` - List all subjects
- `GET /{id}` - Get subject by ID
- `GET /teacher/{teacherId}` - Get subjects by teacher
- `POST /` - Create new subject
- `PUT /{id}` - Update subject
- `DELETE /{id}` - Delete subject

### Timeslots (`/api/timeslots`)
- `GET /` - List all timeslots
- `GET /{id}` - Get timeslot by ID
- `GET /group/{groupId}` - Get timeslots by group
- `GET /group/{groupId}/subject/{subjectId}` - Get timeslots by group and subject
- `POST /` - Create new timeslots
- `DELETE /{id}` - Delete timeslot

### Attendance (`/api/attendance`)
- `POST /mark` - Mark student attendance
- `GET /group/{groupId}/subject/{subjectId}` - Get attendance for class session
- `GET /student/{studentId}` - Get student's attendance history
- `GET /subject/{subjectId}` - Get attendance for subject
- `GET /stats/student/{studentId}/subject/{subjectId}` - Get attendance statistics

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 8.5 or higher

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd KotlinBackendApi
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

The API will be available at `http://localhost:8080/api`

### Database
- SQLite database file (`attendance.db`) will be created automatically
- Database schema is generated using Hibernate DDL auto-update

## API Usage Examples

### Authentication
```bash
# Register a new teacher
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "teacher1",
    "email": "teacher1@school.com",
    "password": "password123",
    "confirmPassword": "password123",
    "fullName": "John Doe"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "teacher1",
    "password": "password123"
  }'
```

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "name": "Alice Smith",
    "studentId": "STU001",
    "email": "alice@student.com"
  }'
```

### Mark Attendance
```bash
curl -X POST http://localhost:8080/api/attendance/mark \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "studentId": 1,
    "subjectId": 1,
    "date": "2024-01-15",
    "session": 0,
    "status": "PRESENT"
  }'
```

## Session Schedule
Sessions are numbered 0-9 representing different time periods:
- Session 0: 8:30 AM - 9:50 AM
- Session 1: 10:00 AM - 11:20 AM
- Session 2: 11:30 AM - 12:50 PM
- Session 3: 1:00 PM - 2:20 PM
- Session 4: 2:30 PM - 3:50 PM
- Session 5: 4:00 PM - 5:20 PM
- Session 6: 5:30 PM - 6:50 PM
- Session 7: 7:00 PM - 8:20 PM
- Session 8: 8:30 PM - 9:50 PM
- Session 9: 10:00 PM - 11:20 PM

## Configuration

Key configuration properties in `application.properties`:

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:sqlite:attendance.db
spring.jpa.hibernate.ddl-auto=update

# JWT
app.jwt.secret=MySecretKey
app.jwt.expiration=86400000
```

## Development Notes

- All endpoints return a consistent `ApiResponse<T>` wrapper
- Attendance can be updated multiple times for the same session
- Students can be assigned to multiple groups
- Teachers can be assigned to multiple subjects
- Full CORS support for frontend integration

## License

This project is developed for educational purposes.