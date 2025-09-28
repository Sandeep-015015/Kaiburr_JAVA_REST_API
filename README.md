Here’s your revised `README.md` in a **clean, professional format** with proper markdown styling, spacing, and emojis for readability:

---

# 🚀 Kaiburr Task 1: Java REST API for Task Management

This repository contains the solution for **Task 1**, a RESTful API built with **Java** and **Spring Boot** to manage and execute shell command tasks persisted in **MongoDB**.

---

## 🌟 Features

* **CRUD Operations**: Create, Read, and Delete `Task` objects.
* **Database Integration**: Uses **Spring Data MongoDB** to persist task data.
* **Shell Command Execution**: Executes the task’s command and stores the `TaskExecution` log (start time, end time, output).
* **Custom Search**: Find tasks by name (case-insensitive search).
* **Secure Validation**: Prevents execution of unsafe/malicious commands (e.g., `rm`, `sudo`).

---

## 🏛️ Architecture Overview

The project follows a standard three-tier architecture:

* **Java Spring Boot (Application Layer)**: Handles HTTP routing, business logic, and command execution.
* **MongoDB (Data Layer)**: Stores `Task` and `TaskExecution` objects.

> ⚠️ The application expects a **local MongoDB server** running on **port 27017**.

---

## 🛠️ Prerequisites

Ensure the following are installed on your machine:

* Java **JDK 17+**
* **Apache Maven 3.9+**
* A running **Local MongoDB Server** (default port: `27017`)
* **VS Code** with:

  * Spring Boot Extension Pack
  * REST Client extension (for easy testing)

---

## 🚀 How to Run the Application

1. **Start MongoDB** locally (ensure it listens on port `27017`).

2. **Run the Spring Boot app using Maven** (inside the project root where `pom.xml` is located):

   ```bash
   mvn spring-boot:run
   ```

   Alternatively, run the `Task1Application.java` file directly from VS Code.

3. The application starts at:

   ```
   http://localhost:3000
   ```

   (Check console logs if a different port is used.)

---

## ⚙️ API Endpoints

Base URL: **`http://localhost:3000/tasks`**

| Method     | Endpoint                    | Description                                           |
| ---------- | --------------------------- | ----------------------------------------------------- |
| **PUT**    | `/tasks`                    | **Create** a new task (JSON body required).           |
| **GET**    | `/tasks`                    | Retrieve **all tasks**.                               |
| **GET**    | `/tasks?id={taskId}`        | Retrieve a single task by ID.                         |
| **GET**    | `/tasks/search?name={name}` | Search tasks by **name** (case-insensitive).          |
| **PUT**    | `/tasks/{taskId}/execute`   | **Execute** the shell command for the specified task. |
| **DELETE** | `/tasks/{taskId}`           | **Delete** the task with the specified ID.            |


