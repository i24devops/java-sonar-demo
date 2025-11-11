## Commands to run 

### Option A — **Maven-native Sonar scan** (recommended for Java)

```bash
cd /opt/java-sonar-demo

# Build, test, and generate JaCoCo XML
mvn clean verify

# Send analysis to SonarQube
mvn sonar:sonar \
  -Dsonar.host.url=http://SONARQUBE_PUBLIC_URL:9000 \
  -Dsonar.login=<YOUR_TOKEN>
```

### Option B — **CLI scanner** (like your Python flow)

```bash
cd /opt/java-sonar-demo

# Build, test, and generate JaCoCo XML
mvn -clean verify

# Make sure sonar-project.properties exists, then:
sonar-scanner \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<YOUR_TOKEN>
```

> Both ways read the **JaCoCo XML** at `target/site/jacoco/jacoco.xml` and will show **Coverage** in SonarQube.

---

## 5) Observations

1. Observer couple of `intentional issues` (hardcoded secret, broad catch, System.out, SQL injection, duplication).
2. Run `mvn clean verify` → Observe **JaCoCo** created `target/site/jacoco/jacoco.xml`.
3. Run the Sonar scan (Maven or CLI).
4. In SonarQube UI:

   * **Issues** → Bugs & Code Smells
   * **Security Hotspots** (SQL injection)
   * **Duplications** (Calculator vs Utils)
   * **Coverage** (from JaCoCo)
5. Fix Code Smells live 
```java
//Replace `Calculator.java`
package com.i27.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    // Keep one obvious issue so we can still show remaining smells in Sonar
    public static final String HARDCODED_PASSWORD = "P@ssw0rd"; // Noncompliant: hardcoded secret

    public int add(int a, int b) {
        // removed unused variable 'temp'
        return a + b;
    }

    public Integer divide(Integer a, Integer b) {
        try {
            return a / b;
        } catch (ArithmeticException ex) { // specific exception instead of catch (Exception)
            log.warn("Division error: a={}, b={}", a, b, ex); // logging instead of System.out
            return null; // Keep behavior to satisfy existing unit tests
        }
    }

    public boolean isEven(int n) {
        return n % 2 == 0; // remove redundant else branch
    }

    public String duplicateLogic(int x) {
        // Duplicate logic remains (to keep a duplications example)
        if (x > 10) return "BIG";
        else if (x > 5) return "MID";
        else return "SMALL";
    }

    public boolean unreachableExample(boolean flag) {
        // remove truly unreachable code; keep method simple
        return true;
    }
}
```
```java
//Replace  `Database.java`
package com.i27.demo;

import java.sql.*;

public class Database {
    public String findUserByName(String jdbcUrl, String user, String pass, String name) throws Exception {
        String query = "SELECT id, name FROM users WHERE name = ?"; // parameterized query

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id") + ":" + rs.getString("name");
                }
                return null;
            }
        }
    }
}
```
* Add the below dependency to your pom.xml file
```xml
<!-- SLF4J API + simple binding (console) -->
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <version>2.0.13</version>
</dependency>
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-simple</artifactId>
  <version>2.0.13</version>
</dependency>
```
* Re run the project
```bash
# Build + tests + JaCoCo XML
mvn -q clean verify

# If using Maven scanner:
mvn sonar:sonar \
  -Dsonar.host.url=http://SONARQUBE_PUBLIC_IP:9000 \
  -Dsonar.login=<YOUR_TOKEN>

# If using CLI scanner (ensure sonar-project.properties exists):
# sonar-scanner \
#   -Dsonar.host.url=http://localhost:9000 \
#   -Dsonar.login=<YOUR_TOKEN>
```

---

## Troubleshooting quickies

* **Coverage = 0%**
  Ensure `mvn verify` ran and `target/site/jacoco/jacoco.xml` exists. Confirm property `sonar.coverage.jacoco.xmlReportPaths` is correct (CLI) or just use the Maven scanner.

* **Unauthorized**
  Use `-Dsonar.login=<TOKEN>` (not `-Dsonar.token` for SonarQube server).

* **Java version issues**
  Match your installed JDK (e.g., 17). Update `maven.compiler.source/target` if needed.

---