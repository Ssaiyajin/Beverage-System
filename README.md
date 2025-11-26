# Beverage-System

Comprehensive guide to build, run, inspect dependencies and debug this multi-module Gradle Java project.

## Project layout (important modules)
- Root: multi-module Gradle project (settings.gradle)
- Modules:
  - core/ — shared core utilities and models
  - beverage/ — main service exposing beverage API (contains JaxRsServer and resources)
  - management/ — management/admin service (similar structure)
- Common source locations:
  - src/main/java/jaxrs/ — JAX-RS Application, controllers, DTOs, error mappers
  - src/main/resources/ — config.properties, openapi.yaml (in root)

## Requirements
- Java JDK 11+ (Java 17 recommended)
- Git
- Use included Gradle wrapper (no system Gradle required)
- Windows: PowerShell/CMD, Unix: bash

## Build

From repository root:

Windows (PowerShell / CMD)
```powershell
.\gradlew.bat clean build
```

Unix / macOS
```bash
./gradlew clean build
```

This builds all modules (core, beverage, management) and produces artifacts under each module's `build/libs`.

To build a single module:
```bash
./gradlew :beverage:build
./gradlew :management:build
```

## Run

Several options depending on your workflow.

1) Run via Gradle (recommended during development)
Windows:
```powershell
.\gradlew.bat :beverage:run
```
Unix:
```bash
./gradlew :beverage:run
```

2) Run the generated start script (after build)
Windows:
```powershell
.\beverage\build\scripts\beverage.bat
```
Unix:
```bash
./beverage/build/scripts/beverage
```

3) Run the JAR directly (if a runnable/fat JAR is created)
Windows:
```powershell
java -jar .\beverage\build\libs\beverage-<version>.jar
```
Unix:
```bash
java -jar ./beverage/build/libs/beverage-<version>.jar
```
Replace `<version>` with the actual file name in `build/libs`.

4) Run management module (replace `beverage` with `management` above).

## Configuration

- Server and service configuration files are in:
  - beverage/src/main/resources/config.properties
  - management/src/main/resources/config.properties
  - root src/main/resources/config.properties
- Common properties: server.port, context path or other module-specific options.
- JAX-RS application base path is defined in `JaxRsServer.java` (look for `@ApplicationPath` or programmatic server setup). Effective endpoint = host:port + application base path + resource `@Path`.

## Finding effective endpoint paths

1) Check application base path:
- beverage/src/main/java/jaxrs/JaxRsServer.java

2) Check resource classes for paths (examples):
- beverage/src/main/java/jaxrs/controller/
- beverage/src/main/java/jaxrs/resources/

Common resource DTO/class names in this project:
- CrateDTO, BottleDTO, BeverageDTO
- Resource classes likely contain `@Path("crate")`, `@Path("bottle")` or plurals.

Quick search (repo root):

Windows (PowerShell)
```powershell
Get-ChildItem -Path "D:\Git project\Beverage-System" -Recurse -Include *.java,*.properties |
  Select-String -Pattern '(?i)\bcrate\b|\bbottle\b|@ApplicationPath|@Path' |
  Select-Object Path,LineNumber,Line
```

Unix
```bash
grep -RIn --include="*.java" --include="*.properties" -e "crate" -e "bottle" -e "@ApplicationPath" -e "@Path" .
```

Effective URL example:
- If @ApplicationPath("/api") and resource @Path("crate"):
  - http://localhost:8080/api/crate

## Test

Run unit and integration tests:
```bash
./gradlew test
./gradlew :beverage:test
```

Open test reports:
- beverage/build/reports/tests/test/index.html

## Inspect dependencies

To list dependencies for a module:
```bash
./gradlew :beverage:dependencies --configuration runtimeClasspath
```

To produce a dependency tree:
```bash
./gradlew :beverage:dependencies
```

## Common troubleshooting (404 / 500 / startup issues)

404 when hitting an endpoint:
- Verify correct full path: host:port + application base path + resource @Path.
- Confirm the module you started contains the resource (beverage vs management).
- Confirm the application started without deployment errors. Check console/Gradle output for exceptions.

404 quick checks:
```bash
curl -v http://localhost:8080/
curl -v http://localhost:8080/<applicationPath>/crate
```

If you see 415 or 400 on POST:
- Check Content-Type and DTO mapping. Use `Content-Type: application/json`.
- Ensure DTOs have default no-arg constructors and proper getters/setters (Jackson/Moxy requirements).

500 or startup exceptions:
- Inspect Gradle/console logs for stack traces.
- Tail logs in VS Code Debug/Output pane or terminal where service was started.

Enable more verbose logging (if using logging framework):
- Edit config/logging properties or pass JVM args:
```bash
java -Dlogging.level.root=DEBUG -jar ./beverage/build/libs/beverage-<version>.jar
```

## Debugging Locally

Run with remote debug enabled (attach debugger on port 5005):
```bash
# Gradle run with debug args
./gradlew :beverage:run --no-daemon -Dorg.gradle.jvmargs="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```
Then attach your IDE to localhost:5005.

## API sanity checks (examples)

GET crate (adjust base path if required):
```bash
curl -v http://localhost:8080/<appBasePath>/crate
```

GET bottle:
```bash
curl -v http://localhost:8080/<appBasePath>/bottle
```

POST crate (example body — change to match DTO fields):
```bash
curl -v -X POST http://localhost:8080/<appBasePath>/crate \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Test Crate"}'
```

If 404, locate the resource and application path as described above.

## Logs and console

- Gradle run prints logs to the terminal where you executed the command.
- The beverage module also produces a script in `beverage/build/scripts` that can be used to start the service and will print to the console.

## Useful files to inspect for debugging
- beverage/src/main/java/jaxrs/JaxRsServer.java — application bootstrap / base path
- beverage/src/main/java/jaxrs/controller/ or resources/ — resource endpoints
- beverage/src/main/resources/config.properties — port and other settings
- root/src/main/resources/openapi.yaml — documented endpoints and schemas

## Contributing & Development notes
- Use the Gradle wrapper for consistent builds (`./gradlew` / `.\gradlew.bat`).
- Run module-specific tasks with `:module:task`.
- Keep DTOs simple POJOs for JSON serialization compatibility.

## If you need help
When reporting issues, include:
- exact HTTP request and full response (status + body + headers)
- server/Gradle console logs including stack traces
- contents of the resource class that should serve the endpoint and JaxRsServer `@ApplicationPath` or bootstrap lines

## License
Project license: (add appropriate license information here if applicable)

---
End of README.
