# Selenium POM Framework
![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.18-green?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.9-red)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)
![Build](https://img.shields.io/badge/build-passing-brightgreen)

A professional **QA Automation Framework** built with Java, Selenium WebDriver, TestNG, and Maven - following the **Page Object Model (POM)** design pattern for clean, maintainable, and scalable test automation.

---

## Features
- Page Object Model architecture for clean separation of concerns
- ThreadLocal WebDriver for safe parallel test execution
- BDD support with Cucumber/Gherkin syntax
- API testing with REST Assured
- Zero-config driver management via WebDriverManager
- Centralized test suite configuration with TestNG XML
- Reusable base classes to minimize code duplication

---

## Tech Stack
| Tool | Version | Purpose |
|------|---------|---------|
| Java | 17 | Core language |
| Selenium WebDriver | 4.18 | Browser automation |
| TestNG | 7.9 | Test execution & reporting |
| Maven | 3.9 | Build & dependency management |
| Cucumber/BDD | latest | Behavior-driven testing |
| REST Assured | latest | API test automation |
| WebDriverManager | latest | Auto driver setup |

---

## Project Structure
```
SeleniumFramework/
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/        # Page Object Model classes
│       │   ├── tests/        # Test classes
│       │   └── utils/        # Base classes & Driver Manager
│       └── resources/
│           └── testng.xml    # TestNG suite configuration
└── pom.xml                   # Maven dependencies
```

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- Chrome / Firefox browser installed

### Installation
```bash
git clone https://github.com/FaraazSuffla/selenium-pom-framework.git
cd selenium-pom-framework
mvn install
```

### Running Tests
```bash
# Run full test suite
mvn clean test

# Run specific test class
mvn clean test -Dtest=LoginTest

# Run with specific browser
mvn clean test -Dbrowser=firefox
```

---

## Design Patterns

### Page Object Model (POM)
Each page of the application has a dedicated class that encapsulates its elements and actions - keeping test logic completely separate from UI interactions.

### ThreadLocal WebDriver
Uses ThreadLocal<WebDriver> to ensure each test thread gets its own browser instance, enabling safe and reliable parallel test execution.

### Base Test Class
A shared base class handles WebDriver initialization and teardown, reducing boilerplate across all test classes.

---

## Test Application
Tests are run against **[SauceDemo](https://www.saucedemo.com)** - a purpose-built e-commerce practice app covering login, product browsing, cart, and checkout flows.

---

## Contact
- GitHub: [@FaraazSuffla](https://github.com/FaraazSuffla)
- LinkedIn: [in/faraazsuffla](https://www.linkedin.com/in/faraazsuffla)
