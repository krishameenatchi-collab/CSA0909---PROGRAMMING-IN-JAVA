# Assignment 5: Data Persistence Engineering – Files and Databases
**Course:** Programming in Java (CSA09 – CO5: Data Persistence Engineering)  
**Nature of Assignment:** Individual Portfolio & GitHub Repository Submission  
**Target Repository Structure:** `CSA09-<RegisterNumber>-Assignment5`  

---

## 1. Environment & Compilation Setup
- **Java Version:** JDK 17 / JDK 21
- **Database:** MySQL 8.0 Server
- **JDBC Driver:** MySQL Connector/J (`mysql-connector-j-8.0.33.jar`)

### Build & Compilation Commands
```bash
# Compile Part B Java Files
javac -d bin TaskB1/*.java TaskB2/*.java TaskB3/*.java TaskB4/*.java

# Run Task B1 File Store Demonstration
java -cp bin TaskB1.PassStore

# Run Task B2 Security Adversarial Test
java -cp bin:mysql-connector-j-8.0.33.jar TaskB2.AdversarialLogin

# Run Task B3 Statement vs Batch Benchmark
java -cp bin:mysql-connector-j-8.0.33.jar TaskB3.PassLoader

# Run Task B4 Migration & Database Inspector
java -cp bin:mysql-connector-j-8.0.33.jar TaskB4.DatabaseInspector
```

---

## 2. Directory Layout
```
CO5 ASSIGNMENT/
├── README.md
├── CO5_ASSIGNMENT_Deliverables.md
├── TASK-A1/
│   └── TASK-A1_Deliverables.md
├── TASK-A2/
│   └── TASK-A2_Deliverables.md
├── TASK-A3/
│   └── TASK-A3_Deliverables.md
├── TASK-A4/
│   └── TASK-A4_Deliverables.md
├── TASK-A5/
│   └── TASK-A5_Deliverables.md
├── TASK-B1/
│   ├── PassStore.java
│   ├── LineCounter.java
│   ├── TASK-B1_Deliverables.md
│   └── TASK-B1_output.png
├── TASK-B2/
│   ├── AdversarialLogin.java
│   ├── DamagedFileTester.java
│   ├── TASK-B2_Deliverables.md
│   └── TASK-B2_output.png
├── TASK-B3/
│   ├── PassLoader.java
│   ├── schema.sql
│   ├── TASK-B3_Deliverables.md
│   └── TASK-B3_output.png
└── TASK-B4/
    ├── PassMigration.java
    ├── DatabaseInspector.java
    ├── TASK-B4_Deliverables.md
    └── TASK-B4_output.png
```
