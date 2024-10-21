# RGB: A Linear Algebra Library for Java
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

![CLI Demo](https://i.ibb.co.com/Js6vQjw/Command-Prompt-2024-10-21-06-56-28.gif)

**RGB** is currently a command-line application for performing essential linear algebra operations, created as a milestone project for the Linear Algebra and Geometry course at Institut Teknologi Bandung. It provides linear algebra utilities such as linear system solver, regression, interpolation, matrix transformations, and many more.

⚠️ **Note:** This release is just the beginning, and while many features work, some are still in development or may not be fully optimized.

---

## 📦 Installation & Usage

### Requirements
- Java 21 or later
- Gradle 8.8 or later (if building from source)

### Running the Application
1. **Download the JAR file** from the [releases page](https://github.com/l0stplains/Algeo01-23002/releases/download/v0.1.0/Algeo01-23002.jar).
2. Open a terminal and navigate to the directory containing the downloaded JAR.
3. Run the following command:
   ```bash
   java -jar Algeo01-23002.jar
   ```
   
   Alternatively, if you have cloned the repository and want to run it using Gradle:
   ```bash
   ./gradlew run
   ```

---

## 📝 Notes & Limitations
- This is an early version (v0.1.0). Not all operations are fully optimized, and some edge cases may not be handled gracefully.
- Matrix operations involving larger matrices might be slower or prone to formatting issues.
- Bicubic Spline Interpolation is functional but still requires more extensive testing.

---

## 🛠️ Planned Improvements
- More robust error handling
- GUI version for more intuitive usage.
- Application on resizing image.
- Optimized performance for large matrices
- Extensive unit testing for more edge cases.

--- 
