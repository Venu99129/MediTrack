# MediTrack – Medical Records Management System

## Overview

MediTrack is a Java-based console application for managing patients, doctors, and appointments in a small clinic. All data is persisted in CSV files using the [OpenCSV](https://opencsv.sourceforge.net/) library.

---

## Project Architecture

```
MediTrack/
├── pom.xml                          # Maven build config (Java 21, OpenCSV, JUnit 5, Mockito)
├── README.md
└── src/
    ├── main/
    │   ├── java/org/airtribe/
    │   │   ├── Main.java                        ← Menu-driven console UI entry point
    │   │   ├── Constants/
    │   │   │   ├── FilePaths.java               ← CSV file path constants
    │   │   │   └── TaxRates.java                ← Billing tax rate constants
    │   │   ├── Interface/
    │   │   │   ├── Notification.java            ← Contract for notification senders
    │   │   │   ├── Payable.java                 ← Contract for payment strategies
    │   │   │   └── Searchable.java              ← Contract for searchable entities
    │   │   ├── entity/
    │   │   │   ├── Person.java                  ← Base class (name, email)
    │   │   │   ├── Patient.java                 ← Extends Person, implements Searchable
    │   │   │   ├── Doctor.java                  ← Extends Person, implements Searchable
    │   │   │   ├── Appointment.java             ← Builder-pattern appointment entity
    │   │   │   ├── BillSummary.java
    │   │   │   ├── Enum/
    │   │   │   │   └── AppointmentStatus.java   ← SCHEDULED | COMPLETED | CANCELED
    │   │   │   └── Stratergy/                   ← Decorator pattern for billing
    │   │   │       ├── Bill.java (interface)
    │   │   │       ├── BaseBill.java
    │   │   │       ├── BillDecorator.java
    │   │   │       ├── AppointmentBill.java
    │   │   │       ├── MedicalBill.java
    │   │   │       └── LabBill.java
    │   │   ├── exceptions/
    │   │   │   ├── CSVFilesException.java       ← Custom exception for CSV I/O failures
    │   │   │   ├── EntityNotFoundException.java ← Thrown when a record doesn't exist
    │   │   │   └── ContentNotFoundException.java
    │   │   ├── repository/
    │   │   │   ├── PatientRepository.java       ← CSV CRUD for patients
    │   │   │   ├── DoctorRepository.java        ← CSV CRUD for doctors
    │   │   │   ├── AppointmentRepository.java   ← CSV CRUD for appointments
    │   │   │   └── BillRepository.java
    │   │   ├── Services/
    │   │   │   ├── PatientService.java          ← Patient business logic
    │   │   │   ├── DoctorService.java           ← Doctor business logic
    │   │   │   ├── AppointmentService.java      ← Appointment business logic
    │   │   │   ├── SearchService.java           ← Generic Searchable<T> filter
    │   │   │   ├── BillService.java
    │   │   │   ├── DoctorConsultingService.java ← Simulates doctor consultation logic
    │   │   │   ├── EmailNotification.java       ← Implements Notification (simulated)
    │   │   │   ├── PaymentService.java
    │   │   │   ├── CardPayment.java
    │   │   │   └── UPIPayment.java
    │   │   └── util/
    │   │       ├── CSVUtil.java                 ← Factory for OpenCSV readers/writers
    │   │       └── Counters.java                ← Auto-incrementing ID counters
    │   └── resources/
    │       ├── PatientStorage.csv
    │       ├── DoctorStorage.csv
    │       ├── AppointmentStorage.csv
    │       └── BIllStorage.csv
    └── test/
        └── java/org/airtribe/Services/
            ├── PatientServiceTest.java          ← JUnit 5 tests for PatientService
            ├── DoctorServiceTest.java           ← JUnit 5 tests for DoctorService
            └── AppointmentServiceTest.java      ← JUnit 5 tests for AppointmentService
```

---

## Design Patterns Used

| Pattern      | Where                                          |
|--------------|------------------------------------------------|
| Builder      | `Appointment.Bilder` — constructs appointments |
| Decorator    | `Bill` hierarchy — stacks billing components   |
| Strategy     | `Payable` — card vs UPI payment                |
| Observer     | `Notification` — email alerts on changes       |

---

## Exception Handling Policy

| Exception type         | Behaviour in Main                              |
|------------------------|------------------------------------------------|
| `CSVFilesException`    | Print **message + full stack trace** to stderr |
| All other exceptions   | Print **message only** to stderr               |

---

## Running Commands

### Prerequisites

- Java 21+
- Maven 3.8+

### Compile the project

```bash
mvn compile
```

### Run the application

```bash
mvn exec:java -Dexec.mainClass="org.airtribe.Main"
```

Or build and run the JAR:

```bash
mvn package -DskipTests
java -cp target/MediTrack-1.0-SNAPSHOT.jar org.airtribe.Main
```

> **Important:** Run from the `MediTrack/` directory so the relative CSV paths
> (`src/main/resources/*.csv`) resolve correctly.

### Run unit tests

```bash
mvn test
```

### Run a specific test class

```bash
mvn test -Dtest=PatientServiceTest
mvn test -Dtest=DoctorServiceTest
mvn test -Dtest=AppointmentServiceTest
mvn test -Dtest=BillServiceTest
mvn test -Dtest=DoctorConsultingServiceTest
mvn test -Dtest=PatientServiceTest
```

---

## Console UI Navigation

```
Main Menu
 1  Patient Management      → Add / View / Update / Delete patients
 2  Doctor Management       → Add / View / Delete doctors
 3  Appointment Management  → Book / View / Update / Cancel appointments
 4  Doctor Consulting       → Simulate doctor consultation and billing
 5  Search                  → Full-text search across patients or doctors
 6  Billing                 → view bills for patients
 0  Exit
```

All sub-menus loop until you press `0` to go back.

---

## CSV Storage Format

### PatientStorage.csv
```
id,name,email,medicalHistory
```

### DoctorStorage.csv
```
id,name,email,specialization
```

### AppointmentStorage.csv
```
id,patientId,doctorId,dateTime,status
```

### BillStorage.csv
```
id,patientId,appointmentId,amount
```
