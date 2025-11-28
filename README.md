# 📦 Beverage-System
*A modular Java backend built with Gradle, JAX-RS, and clean multi-module architecture.*

![Build](https://img.shields.io/badge/build-success-brightgreen)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Gradle](https://img.shields.io/badge/Gradle-8.x-orange)
---

## 🧭 Overview

The **Beverage-System** is a multi-module Java backend consisting of:

- **beverage** → Main REST API (bottles, beverages, crates)  
- **management** → Internal admin service  
- **core** → Shared DTOs, utilities, and mappers  

---

## 🗂 Project Structure

```
Beverage-System/
 ├── core/                 # Shared DTOs and utilities
 ├── beverage/             # Main JAX-RS API service
 ├── management/           # Admin REST service
 ├── build.gradle          # Root Gradle build
 ├── settings.gradle       # Multi-module setup
 └── README.md             # Documentation
```

---

## ⚡ Quick Start

### ▶️ Run (Linux/macOS)
```bash
./gradlew :beverage:run
./gradlew :management:run
```

### ▶️ Run (Windows)
```powershell
gradlew.bat :beverage:run
gradlew.bat :management:run
```

**▶️ Run (Windows) with gradle wrapper**
```powershell
gradle :beverage:run
gradle :management:run
```
---

## 🧰 Requirements

| Component | Version |
|----------|---------|
| Java JDK | **17+ (recommended)** |
| Gradle   | Use included wrapper |
| OS       | Windows / Linux / macOS |

---

## 🔨 Build

### Build all modules  
**Linux/macOS**
```bash
./gradlew clean build
```
**Windows**
```powershell
gradlew.bat clean build
```

### Build single module
**Linux/macOS**
```bash
./gradlew :beverage:build
./gradlew :management:build
```
**Windows**
```powershell
gradlew.bat :beverage:build
gradlew.bat :management:build
```

Artifacts are stored inside:
```
<module>/build/libs/
```

---

## ▶️ Run the Application

### 1️⃣ Run via Gradle  
**Linux/macOS**
```bash
./gradlew :beverage:run
./gradlew :management:run
```
**Windows**
```powershell
gradlew.bat :beverage:run
gradlew.bat :management:run
```

### 2️⃣ Run JAR directly  
```bash
java -jar beverage/build/libs/beverage-<version>.jar
```

---

## ⚙️ Configuration

Config files:

```
beverage/src/main/resources/config.properties
management/src/main/resources/config.properties
```

Common values:

```
server.port=8080
application.path=/api
```

---

## 🌐 API Endpoints

### Example Endpoints
```
GET http://localhost:8080/api/crate
GET http://localhost:8080/api/bottle
GET http://localhost:8080/api/beverage
```

### Example JSON response
```json
{
  "id": 1,
  "name": "Coca Cola",
  "volume": 0.5,
  "price": 1.99
}
```

---

## 🧪 Testing

**Linux/macOS**
```bash
./gradlew test
./gradlew :beverage:test
```

**Windows**
```powershell
gradlew.bat test
gradlew.bat :beverage:test
```

Reports:

```
build/reports/tests/test/index.html
```

---

## 🔍 Inspect Dependencies  
**Linux/macOS**
```bash
./gradlew :beverage:dependencies
```
**Windows**
```powershell
gradlew.bat :beverage:dependencies
```

---

## 🛠️ Debugging

Start service with remote debugger:

```bash
./gradlew :beverage:run --no-daemon -Dorg.gradle.jvmargs="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

Attach your IDE debugger:

```
localhost:5005
```

---

## 🧱 Architecture Diagram (Optional)

```
 ┌──────────────┐       ┌────────────────┐
 │   beverage   │◀──────│     core       │
 │  (REST API)  │       │ DTOs, mappers  │
 └──────────────┘       └────────────────┘
          ▲
          │
 ┌──────────────┐
 │  management  │
 │ (Admin API)  │
 └──────────────┘
```

---

## 🤝 Contributing

1. Use `./gradlew` (or `gradlew.bat` on Windows)  
2. Keep reusable logic inside **core**  
3. DTOs must be serialization-friendly  
4. Follow Java + JAX-RS clean coding conventions  

---

## ✨ Author

**Nihar Sawant**  
DevOps & Software Engineer passionate about automation, backend systems, and cloud technologies.
