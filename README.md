# OrangeHRM Automation Framework

A complete end-to-end test automation solution for [OrangeHRM Demo](https://opensource-demo.orangehrmlive.com).

Built with **Java + Selenium WebDriver + TestNG + Maven** using the **Page Object Model (POM)** design pattern.

---

## 📁 Project Structure

```
orangehrm-automation/
├── pom.xml                                      ← Maven config & dependencies
├── README.md                                    ← This file
│
├── .github/
│   └── workflows/
│       └── ci.yml                               ← GitHub Actions CI/CD pipeline
│
└── src/
    └── test/
        ├── java/
        │   ├── pages/                           ← Page Object Classes
        │   │   ├── BasePage.java                ← Parent class for all pages
        │   │   ├── LoginPage.java               ← Login screen
        │   │   ├── DashboardPage.java           ← Main dashboard
        │   │   ├── PimPage.java                 ← PIM module
        │   │   ├── AddEmployeePage.java         ← Add employee form
        │   │   └── EmployeeProfilePage.java     ← Employee profile (verification)
        │   │
        │   ├── tests/                           ← Test Classes
        │   │   ├── BaseTest.java                ← Setup/teardown + reporting
        │   │   ├── LoginTest.java               ← Login test cases
        │   │   └── AddEmployeeTest.java         ← End-to-end employee test
        │   │
        │   └── utils/                           ← Utility Classes
        │       ├── DriverManager.java           ← Browser management
        │       ├── WaitHelper.java              ← Explicit wait methods
        │       └── TestDataProvider.java        ← Data-driven test data
        │
        └── resources/
            └── testng.xml                       ← TestNG suite configuration
```

---

## ✅ Prerequisites (Install These First)

| Tool | Version | Download |
|------|---------|----------|
| Java JDK | 11 or higher | https://adoptium.net |
| Maven | 3.6+ | https://maven.apache.org/download.cgi |
| Google Chrome | Latest | https://www.google.com/chrome |
| Git | Any | https://git-scm.com |

### Verify your installations:
```bash
java -version      # Should show: openjdk 11...
mvn -version       # Should show: Apache Maven 3.x...
google-chrome --version   # or: chrome --version
```

---

## 🚀 How to Run the Tests

### Step 1: Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/orangehrm-automation.git
cd orangehrm-automation
```

### Step 2: Run all tests
```bash
mvn clean test
```

### Step 3: View the report
After tests finish, open this file in your browser:
```
test-output/ExtentReport.html
```

---

## 🔧 Configuration

### To run WITH a visible browser (not headless):
Open `src/test/java/utils/DriverManager.java` and comment out these lines:
```java
// options.addArguments("--headless=new");
// options.addArguments("--no-sandbox");
// options.addArguments("--disable-dev-shm-usage");
```

### To run specific tests only:
Edit `src/test/resources/testng.xml` and include/exclude test methods.

---

## 🧪 Test Cases

### LoginTest.java
| Test | Description | Type |
|------|-------------|------|
| `testSuccessfulLogin` | Login with valid credentials → verify dashboard | Positive |
| `testFailedLogin` | Login with wrong password → verify error message | Negative |

### AddEmployeeTest.java
| Test | Description | Type |
|------|-------------|------|
| `testAddSingleEmployee` | Full flow: Login → PIM → Add → Verify → Logout | E2E |
| `testAddMultipleEmployees` | Same flow with 3 sets of employee data | Data-Driven |

---

## 🏗️ Key Design Decisions

### Page Object Model (POM)
Each web page has its OWN Java class. The class contains:
- **Locators** — how to find elements (CSS selectors, names)
- **Methods** — what actions users can do on that page

**Why?** If the UI changes, you update ONE class, not every test.

### Explicit Waits (No Thread.sleep!)
We use `WebDriverWait` with `ExpectedConditions`. This means:
- Selenium keeps trying until the condition is met
- Tests don't wait unnecessarily
- Tests are stable even on slow networks

### WebDriverManager
No manual ChromeDriver download needed!
`WebDriverManager.chromedriver().setup()` downloads the correct driver automatically.

---

## 📊 CI/CD Pipeline

The GitHub Actions workflow (`.github/workflows/ci.yml`) automatically:
1. Triggers on every `git push`
2. Installs Java 11 + Google Chrome
3. Builds the Maven project
4. Runs all tests
5. Uploads the HTML test report as a downloadable artifact

### View CI results:
`GitHub Repo → Actions tab → Click latest workflow run`

---

## 📬 API Testing (Postman)

See `API_Testing_Guide.md` for:
- How to capture requests from browser DevTools
- Complete Postman collection with login + add employee requests
- Test scripts for status codes, response body, and token handling

## ⚡ Performance Testing (JMeter)

See `Performance_Testing_Guide.md` for:
- Step-by-step JMeter setup
- 50-user concurrent test configuration
- How to read and interpret results

---

## ❓ Troubleshooting

**"ChromeDriver not found" error**
→ WebDriverManager handles this automatically. Ensure you have internet access on first run.

**Tests failing with "element not found"**
→ The OrangeHRM demo site is occasionally slow. Try increasing the timeout in `WaitHelper.java`:
```java
private static final int DEFAULT_TIMEOUT = 30; // increase from 20 to 30
```

**"Cannot find symbol: DriverManager"**
→ Make sure all files are in the correct package folders as shown in the structure above.
