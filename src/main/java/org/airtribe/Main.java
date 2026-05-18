package org.airtribe;

import org.airtribe.Services.*;
import org.airtribe.exceptions.CSVFilesException;

import java.util.List;
import java.util.Scanner;

public class Main {

    // ANSI color codes for colored console output
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";

    public static void main(String[] args) {
        System.out.println(GREEN + "Welcome to AirTribe Healthcare Management System!" + RESET);
        Scanner scanner = new Scanner(System.in);

        // Instantiate services using default implementations
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService(new org.airtribe.repository.AppointmentRepository(), new EmailNotification());
        BillService billService = new BillService();

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> handlePatientsMenu(scanner, patientService);
                    case "2" -> handleDoctorsMenu(scanner, doctorService);
                    case "3" -> handleAppointmentsMenu(scanner, appointmentService, patientService, doctorService);
                    case "4" -> handleConsultingFlow(scanner, appointmentService, patientService, doctorService, billService);
                    case "5" -> handleSearch(scanner, patientService, doctorService);
                    case "6" -> handlingBillingManagement(scanner, billService);
                    case "0" -> {
                        System.out.println(BLUE + "Exiting..." + RESET);
                        running = false;
                    }
                    default -> System.out.println(YELLOW + "Invalid choice; please try again." + RESET);
                }
            } catch (CSVFilesException e) {
                // CSV I/O failures: print message and stacktrace
                System.err.println(RED + "CSV error: " + e.getMessage() + RESET);
                e.printStackTrace();
            } catch (Exception e) {
                // Other exceptions: print only the message
                System.err.println(RED + e.getMessage() + RESET);
            }
        }

        scanner.close();
    }

    // Print the main menu
    private static void printMainMenu() {
        System.out.println(MAGENTA + "\n=== Main Menu ===" + RESET);
        System.out.println("1) Patients Management");
        System.out.println("2) Doctors Management");
        System.out.println("3) Appointments Management");
        System.out.println("4) Consulting & Payment");
        System.out.println("5) Search Patients/Doctors");
        System.out.println("6) payment Management");
        System.out.println("0) Exit");
        System.out.print(BLUE + "Choose an option: " + RESET);
    }

    // Handle patient CRUD menu
    private static void handlePatientsMenu(Scanner scanner, PatientService patientService) throws Exception {
        boolean running = true;
        while (running) {
            System.out.println(MAGENTA + "\n--- Patients Menu ---" + RESET);
            System.out.println("1) Add patient");
            System.out.println("2) View patient by ID");
            System.out.println("3) Delete patient by ID");
            System.out.println("4) List all patients");
            System.out.println("0) Back to main menu");
            System.out.print(BLUE + "Choose an option: " + RESET);
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Medical history: ");
                    String mh = scanner.nextLine();
                    var p = patientService.addPatient(name, email, mh);
                    System.out.println(GREEN + "Added patient: " + p + RESET);
                }
                case "2" -> {
                    System.out.print("Patient ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    var p = patientService.getPatientById(id);
                    System.out.println(GREEN + p + RESET);
                }
                case "3" -> {
                    System.out.print("Patient ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    patientService.deletePatientById(id);
                }
                case "4" -> {
                    var list = patientService.getAllPatients();
                    System.out.println(GREEN + "Patients: " + list + RESET);
                }
                case "0" -> running = false;
                default -> System.out.println(YELLOW + "Invalid choice." + RESET);
            }
        }
    }

    // Handle doctor CRUD menu
    private static void handleDoctorsMenu(Scanner scanner, DoctorService doctorService) throws Exception {
        boolean running = true;
        while (running) {
            System.out.println(MAGENTA + "\n--- Doctors Menu ---" + RESET);
            System.out.println("1) Add doctor");
            System.out.println("2) View doctor by ID");
            System.out.println("3) Delete doctor by ID");
            System.out.println("4) List all doctors");
            System.out.println("0) Back to main menu");
            System.out.print(BLUE + "Choose an option: " + RESET);
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Specialization: ");
                    String spec = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    var d = doctorService.addDoctor(name, spec, email);
                    System.out.println(GREEN + "Added doctor: " + d + RESET);
                }
                case "2" -> {
                    System.out.print("Doctor ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    var d = doctorService.getDoctorById(id);
                    System.out.println(GREEN + d + RESET);
                }
                case "3" -> {
                    System.out.print("Doctor ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    doctorService.deleteDoctorById(id);
                }
                case "4" -> {
                    var list = doctorService.getAllDoctors();
                    System.out.println(GREEN + "Doctors: " + list + RESET);
                }
                case "0" -> running = false;
                default -> System.out.println(YELLOW + "Invalid choice." + RESET);
            }
        }
    }

    // Handle appointments menu
    private static void handleAppointmentsMenu(Scanner scanner, AppointmentService appointmentService, PatientService patientService, DoctorService doctorService) throws Exception {
        boolean running = true;
        while (running) {
            System.out.println(MAGENTA + "\n--- Appointments Menu ---" + RESET);
            System.out.println("1) Book appointment");
            System.out.println("2) View appointment by ID");
            System.out.println("3) Cancel appointments by patient ID");
            System.out.println("4) List all appointments");
            System.out.println("0) Back to main menu");
            System.out.print(BLUE + "Choose an option: " + RESET);
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> {
                    System.out.print("Patient ID: ");
                    String pid = scanner.nextLine().trim();
                    System.out.print("Doctor ID: ");
                    String did = scanner.nextLine().trim();
                    System.out.print("DateTime (e.g., 2026-05-18 10:00): ");
                    String dt = scanner.nextLine();
                    var ap = appointmentService.bookAppointment(pid, did, dt);
                    System.out.println(GREEN + "Booked: " + ap + RESET);
                }
                case "2" -> {
                    System.out.print("Appointment ID: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    var ap = appointmentService.getAppointmentDetails(id);
                    System.out.println(GREEN + ap + RESET);
                }
                case "3" -> {
                    System.out.print("Patient ID for cancellation: ");
                    int pid = Integer.parseInt(scanner.nextLine().trim());
                    List<?> appts = appointmentService.getAppointmentsByPatientId(pid);
                    appointmentService.cancelAppointments((List) appts);
                    System.out.println(GREEN + "Appointments canceled." + RESET);
                }
                case "4" -> {
                    var list = appointmentService.getAllAppointments();
                    System.out.println(GREEN + "Appointments: " + list + RESET);
                }
                case "0" -> running = false;
                default -> System.out.println(YELLOW + "Invalid choice." + RESET);
            }
        }
    }

    // Handle consulting flow with dynamic payment choice
    private static void handleConsultingFlow(Scanner scanner, AppointmentService appointmentService, PatientService patientService, DoctorService doctorService, BillService billService) throws Exception {
        System.out.println(MAGENTA + "\n--- Consulting & Payment ---" + RESET);
        System.out.print("Enter appointment ID: ");
        int apptId = Integer.parseInt(scanner.nextLine().trim());
        var appt = appointmentService.getAppointmentDetails(apptId);
        int patientId = appt.getPatientId();
        int doctorId = appt.getDoctorId();

        System.out.println("Choose payment method: 1) Card 2) UPI");
        String pm = scanner.nextLine().trim();
        PaymentService paymentService;
        if (pm.equals("1")) {
            System.out.print("Card number: ");
            String number = scanner.nextLine();
            System.out.print("Card holder name: ");
            String name = scanner.nextLine();
            System.out.print("Expiry: ");
            String expiry = scanner.nextLine();
            System.out.print("CVV: ");
            String cvv = scanner.nextLine();
            paymentService = new PaymentService(new CardPayment(number, name, expiry, cvv));
        } else {
            System.out.print("UPI ID: ");
            String upi = scanner.nextLine();
            paymentService = new PaymentService(new UPIPayment(upi));
        }

        DoctorConsultingService consultingService = new DoctorConsultingService(appointmentService, paymentService, billService);
        var bill = consultingService.consultDoctor(apptId, patientId, doctorId);
        System.out.println(GREEN + "Consultation completed. Bill total: " + bill.calculateTotalAmount() + RESET);
    }

    // Dynamic search for doctors/patients
    private static void handleSearch(Scanner scanner, PatientService patientService, DoctorService doctorService) throws Exception {
        System.out.println(MAGENTA + "\n--- Search ---" + RESET);
        System.out.print("Enter 'p' to search patients, 'd' for doctors: ");
        String t = scanner.nextLine().trim().toLowerCase();
        System.out.print("Search keyword (leave empty for all): ");
        String key = scanner.nextLine();
        if (t.equals("p")) {
            var res = patientService.searchPatients(key);
            System.out.println(GREEN + "Patients: " + res + RESET);
        } else if (t.equals("d")) {
            var res = doctorService.searchDoctors(key);
            System.out.println(GREEN + "Doctors: " + res + RESET);
        } else {
            System.out.println(YELLOW + "Unknown type." + RESET);
        }
    }

    public static void handlingBillingManagement(Scanner scanner, BillService billService) throws Exception {
        boolean running = true;
        while (running) {
            System.out.println(MAGENTA + "\n--- Payment Management ---" + RESET);
            System.out.println("1) View bill by Patient ID");
            System.out.println("0) Back to main menu");
            System.out.print(BLUE + "Choose an option: " + RESET);
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1" -> {
                    System.out.print("Patient ID: ");
                    int pid = Integer.parseInt(scanner.nextLine().trim());
                    var bill = billService.getBillsByPatientId(pid);
                    System.out.println(GREEN + bill + RESET);
                }
                case "0" -> running = false;
                default -> System.out.println(YELLOW + "Invalid choice." + RESET);
            }
        }
    }
}