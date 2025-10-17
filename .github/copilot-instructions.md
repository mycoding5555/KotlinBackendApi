# Student Attendance System - Kotlin Spring Boot API

This project provides REST API endpoints for a student attendance management system designed for teachers to track student participation in classes.

## Key Features
- Teacher authentication and registration
- Student and group management
- Subject and timeslot management  
- Attendance tracking and statistics
- Multi-session daily attendance support

## Project Structure
- **Entities**: JPA entities for database models
- **Repositories**: Data access layer using Spring Data JPA
- **Services**: Business logic layer
- **Controllers**: REST API endpoints
- **Configuration**: Database and security configuration

## API Endpoints Overview
- `/api/auth/*` - Authentication endpoints
- `/api/teachers/*` - Teacher management
- `/api/students/*` - Student management
- `/api/groups/*` - Group management
- `/api/subjects/*` - Subject management
- `/api/attendance/*` - Attendance tracking
- `/api/timeslots/*` - Schedule management

## Technology Stack
- Kotlin + Spring Boot
- Spring Data JPA
- Spring Security
- SQLite Database
- Gradle for dependency management