# RGB: A Linear Algebra Library for Java
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

![CLI Demo](https://i.ibb.co.com/Js6vQjw/Command-Prompt-2024-10-21-06-56-28.gif)

**RGB** is a Java-based application that performs essential linear algebra operations. Initially developed as a command-line tool for the Linear Algebra and Geometry course at Institut Teknologi Bandung, it now includes a graphical user interface (GUI) for enhanced user experience. RGB supports operations like solving linear systems, performing regression and interpolation, matrix transformations, and more.

⚠️ **Note:** While most features are fully functional, some advanced features may still require further optimization and testing.

---

## 📦 Installation & Usage

### Requirements
- Java 21 or later
- Gradle 8.8 or later (if building from source)

### Running the Application

You can now run the application in two modes: via **Command-Line Interface (CLI)** or **Graphical User Interface (GUI)**.

#### **1. Command-Line Interface (CLI) Mode**
1. **Download the JAR file** from the [releases page](https://github.com/l0stplains/Algeo01-23002/releases/download/v1/Algeo01-23002.jar).
2. Open a terminal and navigate to the directory containing the downloaded JAR.
3. Run the following command to start the application in CLI mode:
   ```bash
   java -jar Algeo01-23002.jar -cli
   ```

Alternatively, if you have cloned the repository and want to run it using Gradle:
   ```bash
   ./gradlew run --args="-cli"
   ```

#### **2. Graphical User Interface (GUI) Mode**
1. Follow the same steps as above to download the JAR file.
2. To run the application in GUI mode, use the following command:
   ```bash
   java -jar Algeo01-23002.jar
   ```

Or, if using Gradle:
   ```bash
   ./gradlew run
   ```

---

Here's a **Features** section you can add to your README:

---

## ✨ Features

### 1. **Linear System Solver**
   - Solve systems of linear equations using Gaussian elimination, Gauss-Jordan elimination, Cramer's rule, and matrix inversion methods.

### 2. **Matrix Operations**
   - Perform operations such as matrix multiplication, transposition, finding the determinant, and calculating the inverse of matrices.

### 3. **Regression Analysis**
   - Supports both **Multiple Linear Regression** and **Quadratic Regression** for data analysis.

### 4. **Interpolation**
   - Polynomial interpolation and **Bicubic Spline Interpolation** for estimating values between data points.

### 5. **Graphical User Interface (GUI)**
   - An intuitive GUI for easier interaction with the library, allowing users to perform operations without needing to use the command line.

### 6. **Command-Line Interface (CLI)**
   - A fully-featured CLI for power users who prefer or need to interact with the application through terminal commands.

### 7. **Image Resizing and Stretching**
   - Resize images using **Bicubic Spline Interpolation**, improving image quality and scaling.

### 8. **Extensive Error Handling**
   - Handles invalid inputs and matrix dimension mismatches gracefully with detailed error messages.

---


## 📝 Notes & Limitations
- This release (v1.0) includes the first iteration of the GUI, which is fully functional for most features.
- Matrix operations involving larger matrices may still be slower than desired.
- Bicubic Spline Interpolation and image resizing functionality have been added.
  
---
