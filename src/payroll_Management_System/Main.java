package payroll_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/payroll_db";
        String user = "root";
        String password = "System";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                    DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully.");

            // CREATE TABLES
            Tablescreation.createTables(con);

            Scanner sc = new Scanner(System.in);

            while (true) {

                System.out.println("\n==========================================");
                System.out.println("       PAYROLL MANAGEMENT SYSTEM");
                System.out.println("==========================================");

                System.out.println("1. Employee Management");
                System.out.println("2. Salary Management");
                System.out.println("3. Generate Payroll");
                System.out.println("4. View Payslip");
                System.out.println("5. Tax Deduction");
                System.out.println("6. Payroll Aggregate Report");
                System.out.println("7. Batch Payroll Processing");
                System.out.println("8. Exit");

                System.out.print("Enter your choice: ");

                int choice;

                try {

                    choice =
                            Integer.parseInt(sc.nextLine());

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Enter a valid number.");

                    continue;
                }

                switch (choice) {

                    case 1:
                        employeeMenu(con, sc);
                        break;

                    case 2:
                        salaryMenu(con, sc);
                        break;

                    case 3:
                        Tablescreation.generatePayroll(
                                con, sc);
                        break;

                    case 4:
                        Tablescreation.viewPayslip(
                                con, sc);
                        break;

                    case 5:
                        Tablescreation.calculateTax(
                                con, sc);
                        break;

                    case 6:
                        Tablescreation.aggregateReport(
                                con);
                        break;

                    case 7:
                        Tablescreation.batchPayroll(
                                con);
                        break;

                    case 8:

                        System.out.println(
                                "Thank you.");

                        sc.close();
                        con.close();

                        return;

                    default:

                        System.out.println(
                                "Invalid choice.");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // EMPLOYEE MENU
    // =====================================================

    public static void employeeMenu(
            Connection con,
            Scanner sc) {

        while (true) {

            System.out.println(
                    "\n========== EMPLOYEE MANAGEMENT ==========");

            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");

            int choice;

            try {

                choice =
                        Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Enter a valid number.");

                continue;
            }

            switch (choice) {

                case 1:

                    Tablescreation.addEmployee(
                            con, sc);

                    break;

                case 2:

                    Tablescreation.viewEmployees(
                            con);

                    break;

                case 3:

                    Tablescreation.searchEmployee(
                            con, sc);

                    break;

                case 4:

                    Tablescreation.updateEmployee(
                            con, sc);

                    break;

                case 5:

                    Tablescreation.deleteEmployee(
                            con, sc);

                    break;

                case 6:

                    return;

                default:

                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // SALARY MENU
    // =====================================================

    public static void salaryMenu(
            Connection con,
            Scanner sc) {

        while (true) {

            System.out.println(
                    "\n========== SALARY MANAGEMENT ==========");

            System.out.println("1. Add Salary");
            System.out.println("2. View Salaries");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Salary");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");

            int choice;

            try {

                choice =
                        Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Enter a valid number.");

                continue;
            }

            switch (choice) {

                case 1:

                    Tablescreation.addSalary(
                            con, sc);

                    break;

                case 2:

                    Tablescreation.viewSalaries(
                            con);

                    break;

                case 3:

                    Tablescreation.updateSalary(
                            con, sc);

                    break;

                case 4:

                    Tablescreation.deleteSalary(
                            con, sc);

                    break;

                case 5:

                    return;

                default:

                    System.out.println(
                            "Invalid choice.");
            }
        }
    }
}
