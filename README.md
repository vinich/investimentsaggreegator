# 🚀 Spring Boot CRUD User API with SQL Server and Docker

This project is a simple **Spring Boot REST API** that manages **User data**. It provides full **CRUD** functionality — you can **create**, **read/search**, **update**, and **delete** users. The app uses **SQL Server** for data storage and is fully containerized with **Docker**.

---

## 🧰 Tech Stack

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Microsoft SQL Server-CC2927?style=flat&logo=microsoftsqlserver&logoColor=white" alt="SQL Server" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/JUnit-25A162?style=flat&logo=java&logoColor=white" alt="JUnit" />
</p>

---

## 🌐 API Endpoints

| Method | Endpoint          | Description           |
| ------ | ----------------- | --------------------- |
| GET    | `/api/users`      | List/search all users |
| GET    | `/api/users/{id}` | Get user by ID        |
| POST   | `/api/users`      | Create new user       |
| PUT    | `/api/users/{id}` | Update user           |
| DELETE | `/api/users/{id}` | Delete user by ID     |
