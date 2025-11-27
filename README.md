# 🍹 Beverage-System

The **Beverage-System** is a modular Java backend built using\
**Gradle**, **JAX-RS**, and clean multi-module architecture.

It includes:

-   **beverage** → Main REST API (bottles, beverages, crates)\
-   **management** → Internal admin service\
-   **core** → Shared DTOs, utilities, and mappers

------------------------------------------------------------------------

## 📁 Project Structure

    Beverage-System/
     ├── core/                 # Shared DTOs and utilities
     ├── beverage/             # Main JAX-RS API service
     ├── management/           # Admin REST service
     ├── build.gradle          # Root Gradle build
     ├── settings.gradle       # Multi-module setup
     └── README.md             # Documentation

------------------------------------------------------------------------

## 🚀 Features

✔ Multi-module Gradle setup\
✔ REST API using JAX-RS\
✔ Shared core module\
✔ Easy builds, tests, and debugging\
✔ Compatible with IntelliJ, VS Code, Eclipse

------------------------------------------------------------------------

## 🧰 Requirements

  Component   Version
  ----------- -------------------------
  Java JDK    **17+ recommended**
  Gradle      Use included wrapper
  OS          Windows / Linux / macOS

------------------------------------------------------------------------

## 🔨 Build

### Build all modules

``` bash
./gradlew clean build
```

### Build single module

``` bash
./gradlew :beverage:build
./gradlew :management:build
```

Artifacts:

    <module>/build/libs/

------------------------------------------------------------------------

## ▶️ Run the Application

### 1️⃣ Run via Gradle

``` bash
./gradlew :beverage:run
./gradlew :management:run
```

### 2️⃣ Run from scripts

``` bash
./beverage/build/scripts/beverage
./management/build/scripts/management
```

### 3️⃣ Run from JAR

``` bash
java -jar beverage/build/libs/beverage-<version>.jar
```

------------------------------------------------------------------------

## ⚙️ Configuration

    beverage/src/main/resources/config.properties
    management/src/main/resources/config.properties

Common values:

    server.port=8080
    application.path=/api

------------------------------------------------------------------------

## 🌐 API Endpoints

Examples:

    GET http://localhost:8080/api/crate
    GET http://localhost:8080/api/bottle
    GET http://localhost:8080/api/beverage

------------------------------------------------------------------------

## 🧪 Testing

``` bash
./gradlew test
./gradlew :beverage:test
```

Reports:

    build/reports/tests/test/index.html

------------------------------------------------------------------------

## 🔍 Inspect Dependencies

``` bash
./gradlew :beverage:dependencies
```

------------------------------------------------------------------------

## 🛠️ Debugging

``` bash
./gradlew :beverage:run --no-daemon -Dorg.gradle.jvmargs="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

Attach debugger:

    localhost:5005

------------------------------------------------------------------------

## 🤝 Contributing

1.  Use `./gradlew`\
2.  Keep logic inside **core**\
3.  DTOs must be serialization-friendly

------------------------------------------------------------------------

## ✨ Author

**Nihar Sawant** -- DevOps & Software Engineer passionate about
automation, backend systems, and cloud technologies.
