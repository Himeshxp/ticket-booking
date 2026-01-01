# IRCTC Ticket Booking System (Java)

A console-based Java project that simulates basic functionality of the IRCTC railway ticket booking system.
The project focuses on applying core Java and OOP concepts with a clean, modular structure and JSON-based data storage.

## Features
- User registration and login
- Train details with train number, stations, and timings
- Ticket booking simulation
- Data persistence using JSON files
- Modular package-based architecture

## Tech Stack
- Java
- JSON
- Gradle

## Project Structure
irctc/
├── src/
│ └── main/
│ ├── java/
│ │ ├── user/
│ │ │ └── User.java
│ │ ├── train/
│ │ │ └── Train.java
│ │ ├── booking/
│ │ │ └── TicketBooking.java
│ │ └── app/
│ │ └── Main.java
│ └── resources/
│ ├── users.json
│ └── trains.json
├── build.gradle
└── README.md


## How It Works
- User and train data are stored in JSON files.
- On application start, data is read and mapped to Java objects.
- Users can log in or register.
- Available trains are displayed.
- Ticket booking is handled via console interaction.

## Setup
1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Sync Gradle.
4. Run `Main.java`.

## Concepts Used
- Object-Oriented Programming
- Encapsulation
- Collections
- File handling with JSON
- Package-based design
