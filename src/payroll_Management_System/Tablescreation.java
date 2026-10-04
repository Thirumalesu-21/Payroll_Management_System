package payroll_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

public class Tablescreation {

    // =====================================================
    // CREATE TABLES
    // =====================================================

    public static void createTables(Connection con) {

        String employees = """
        CREATE TABLE IF NOT EXISTS employees (employee_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE, phone VARCHAR(10) NOT NULL UNIQUE, department VARCHAR(50) NOT NULL)
        """;

        String salaries = """
        CREATE TABLE IF NOT EXISTS salaries (salary_id INT AUTO_INCREMENT PRIMARY KEY, employee_id INT NOT NULL, basic_salary DECIMAL(10,2) NOT NULL, allowances DECIMAL(10,2) NOT NULL DEFAULT 0, deductions DECIMAL(10,2) NOT NULL DEFAULT 0, CONSTRAINT fk_salary_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id))
        """;

        String payroll = """
        CREATE TABLE IF NOT EXISTS payroll (payroll_id INT AUTO_INCREMENT PRIMARY KEY, employee_id INT NOT NULL, salary_month VARCHAR(20) NOT NULL, gross_salary DECIMAL(10,2) NOT NULL, net_salary DECIMAL(10,2) NOT NULL, CONSTRAINT fk_payroll_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id))
        """;

        try (Statement stmt =
                     con.createStatement()) {

            stmt.executeUpdate(employees);
            stmt.executeUpdate(salaries);
            stmt.executeUpdate(payroll);

            System.out.println(
                    "All tables created successfully.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // ADD EMPLOYEE
    // =====================================================

    public static void addEmployee(
            Connection con,
            Scanner sc) {

        String sql = """
        INSERT INTO employees (name, email, phone, department) VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            if (!Validation.isValidName(name)) {

                System.out.println(
                        "Invalid name.");

                return;
            }

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            if (!Validation.isValidEmail(email)) {

                System.out.println(
                        "Invalid email.");

                return;
            }

            System.out.print("Enter Phone: ");
            String phone = sc.nextLine();

            if (!Validation.isValidPhone(phone)) {

                System.out.println(
                        "Phone must contain 10 digits.");

                return;
            }

            System.out.print("Enter Department: ");
            String department = sc.nextLine();

            if (!Validation.isValidDepartment(
                    department)) {

                System.out.println(
                        "Invalid department.");

                return;
            }

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, department);

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            if (rs.next()) {

                System.out.println(
                        "Employee added successfully.");

                System.out.println(
                        "Employee ID: "
                                + rs.getInt(1));
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println(
                    "Email or phone already exists.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // VIEW EMPLOYEES
    // =====================================================

    public static void viewEmployees(
            Connection con) {

        String sql = """
        SELECT employee_id, name, email, phone, department FROM employees
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            System.out.println(
                    "\n========== EMPLOYEES ==========");

            while (rs.next()) {

                System.out.println(
                        "Employee ID : "
                                + rs.getInt("employee_id"));

                System.out.println(
                        "Name        : "
                                + rs.getString("name"));

                System.out.println(
                        "Email       : "
                                + rs.getString("email"));

                System.out.println(
                        "Phone       : "
                                + rs.getString("phone"));

                System.out.println(
                        "Department  : "
                                + rs.getString("department"));

                System.out.println(
                        "--------------------------------");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // SEARCH EMPLOYEE
    // =====================================================

    public static void searchEmployee(
            Connection con,
            Scanner sc) {

        String sql = """
        SELECT employee_id, name, email, phone, department FROM employees WHERE employee_id = ?
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            System.out.print(
                    "Enter Employee ID: ");

            int id =
                    Integer.parseInt(
                            sc.nextLine());

            if (!Validation.isValidId(id)) {

                System.out.println(
                        "Invalid ID.");

                return;
            }

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println(
                        "\n========== EMPLOYEE DETAILS ==========");

                System.out.println(
                        "ID : "
                                + rs.getInt(
                                "employee_id"));

                System.out.println(
                        "Name : "
                                + rs.getString(
                                "name"));

                System.out.println(
                        "Email : "
                                + rs.getString(
                                "email"));

                System.out.println(
                        "Phone : "
                                + rs.getString(
                                "phone"));

                System.out.println(
                        "Department : "
                                + rs.getString(
                                "department"));

            } else {

                System.out.println(
                        "Employee not found.");
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Enter a valid ID.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // UPDATE EMPLOYEE
    // =====================================================

    public static void updateEmployee(
            Connection con,
            Scanner sc) {

        String sql = """
        UPDATE employees SET name = ?, email = ?, phone = ?, department = ? WHERE employee_id = ?
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            System.out.print(
                    "Enter Employee ID: ");

            int id =
                    Integer.parseInt(
                            sc.nextLine());

            System.out.print(
                    "Enter New Name: ");

            String name =
                    sc.nextLine();

            System.out.print(
                    "Enter New Email: ");

            String email =
                    sc.nextLine();

            System.out.print(
                    "Enter New Phone: ");

            String phone =
                    sc.nextLine();

            System.out.print(
                    "Enter New Department: ");

            String department =
                    sc.nextLine();

            if (!Validation.isValidName(
                    name)) {

                System.out.println(
                        "Invalid name.");

                return;
            }

            if (!Validation.isValidEmail(
                    email)) {

                System.out.println(
                        "Invalid email.");

                return;
            }

            if (!Validation.isValidPhone(
                    phone)) {

                System.out.println(
                        "Invalid phone.");

                return;
            }

            if (!Validation.isValidDepartment(
                    department)) {

                System.out.println(
                        "Invalid department.");

                return;
            }

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, department);
            ps.setInt(5, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Employee updated successfully.");

            } else {

                System.out.println(
                        "Employee not found.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println(
                    "Email or phone already exists.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // DELETE EMPLOYEE
    // =====================================================

    public static void deleteEmployee(
            Connection con,
            Scanner sc) {

        String deletePayroll = """
        DELETE FROM payroll WHERE employee_id = ?
        """;

        String deleteSalary = """
        DELETE FROM salaries WHERE employee_id = ?
        """;

        String deleteEmployee = """
        DELETE FROM employees WHERE employee_id = ?
        """;

        try {

            System.out.print(
                    "Enter Employee ID: ");

            int id =
                    Integer.parseInt(
                            sc.nextLine());

            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 deletePayroll)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 deleteSalary)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 deleteEmployee)) {

                ps.setInt(1, id);

                int rows =
                        ps.executeUpdate();

                if (rows > 0) {

                    con.commit();

                    System.out.println(
                            "Employee and related payroll records deleted successfully.");

                } else {

                    con.rollback();

                    System.out.println(
                            "Employee not found.");
                }
            }

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // =====================================================
    // ADD SALARY
    // =====================================================

    public static void addSalary(Connection con, Scanner sc) {

        String checkEmployee = """
        SELECT employee_id FROM employees WHERE employee_id = ?
        """;

        String sql = """
        INSERT INTO salaries (employee_id, basic_salary, allowances, deductions) VALUES (?, ?, ?, ?)
        """;

        try {

            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(sc.nextLine());

            if (!Validation.isValidId(employeeId)) {
                System.out.println("Invalid Employee ID.");
                return;
            }

            // CHECK EMPLOYEE

            try (PreparedStatement check =
                         con.prepareStatement(checkEmployee)) {

                check.setInt(1, employeeId);

                ResultSet rs = check.executeQuery();

                if (!rs.next()) {

                    System.out.println(
                        "Employee ID " + employeeId +
                        " does not exist.");

                    System.out.println(
                        "Please add the employee first.");

                    return;
                }
            }

            System.out.print("Enter Basic Salary: ");
            double basicSalary =
                    Double.parseDouble(sc.nextLine());

            System.out.print("Enter Allowances: ");
            double allowances =
                    Double.parseDouble(sc.nextLine());

            System.out.print("Enter Deductions: ");
            double deductions =
                    Double.parseDouble(sc.nextLine());

            if (!Validation.isValidSalary(basicSalary)) {

                System.out.println(
                        "Basic salary must be greater than 0.");

                return;
            }

            if (allowances < 0 || deductions < 0) {

                System.out.println(
                        "Allowances and deductions cannot be negative.");

                return;
            }

            try (PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);
                ps.setDouble(2, basicSalary);
                ps.setDouble(3, allowances);
                ps.setDouble(4, deductions);

                ps.executeUpdate();

                System.out.println(
                        "Salary added successfully.");
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter valid numeric values.");

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage());
        }
    }    // =====================================================
    // VIEW SALARIES
    // =====================================================

    public static void viewSalaries(
            Connection con) {

        String sql = """
        SELECT s.salary_id, e.name, s.basic_salary, s.allowances, s.deductions FROM salaries s JOIN employees e ON s.employee_id = e.employee_id
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            System.out.println(
                    "\n========== SALARIES ==========");

            while (rs.next()) {

                System.out.println(
                        "Salary ID : "
                                + rs.getInt(
                                "salary_id"));

                System.out.println(
                        "Employee  : "
                                + rs.getString(
                                "name"));

                System.out.println(
                        "Basic     : "
                                + rs.getDouble(
                                "basic_salary"));

                System.out.println(
                        "Allowance : "
                                + rs.getDouble(
                                "allowances"));

                System.out.println(
                        "Deduction : "
                                + rs.getDouble(
                                "deductions"));

                System.out.println(
                        "------------------------------");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // UPDATE SALARY
    // =====================================================

    public static void updateSalary(
            Connection con,
            Scanner sc) {

        String sql = """
        UPDATE salaries SET basic_salary = ?, allowances = ?, deductions = ? WHERE salary_id = ?
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            System.out.print(
                    "Enter Salary ID: ");

            int salaryId =
                    Integer.parseInt(
                            sc.nextLine());

            System.out.print(
                    "Enter Basic Salary: ");

            double basic =
                    Double.parseDouble(
                            sc.nextLine());

            System.out.print(
                    "Enter Allowances: ");

            double allowances =
                    Double.parseDouble(
                            sc.nextLine());

            System.out.print(
                    "Enter Deductions: ");

            double deductions =
                    Double.parseDouble(
                            sc.nextLine());

            if (!Validation.isValidSalary(
                    basic)
                    || allowances < 0
                    || deductions < 0) {

                System.out.println(
                        "Invalid salary values.");

                return;
            }

            ps.setDouble(1, basic);
            ps.setDouble(2, allowances);
            ps.setDouble(3, deductions);
            ps.setInt(4, salaryId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Salary updated successfully.");

            } else {

                System.out.println(
                        "Salary record not found.");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // DELETE SALARY
    // =====================================================

    public static void deleteSalary(
            Connection con,
            Scanner sc) {

        String sql = """
        DELETE FROM salaries WHERE salary_id = ?
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            System.out.print(
                    "Enter Salary ID: ");

            int salaryId =
                    Integer.parseInt(
                            sc.nextLine());

            ps.setInt(1, salaryId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Salary deleted successfully.");

            } else {

                System.out.println(
                        "Salary record not found.");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // GENERATE PAYROLL
    // =====================================================

    public static void generatePayroll(
            Connection con,
            Scanner sc) {

        String salarySql = """
        SELECT basic_salary, allowances, deductions FROM salaries WHERE employee_id = ?
        """;

        String insertPayroll = """
        INSERT INTO payroll (employee_id, salary_month, gross_salary, net_salary) VALUES (?, ?, ?, ?)
        """;

        try {

            System.out.print(
                    "Enter Employee ID: ");

            int employeeId =
                    Integer.parseInt(
                            sc.nextLine());

            System.out.print(
                    "Enter Salary Month: ");

            String month =
                    sc.nextLine();

            if (!Validation.isValidMonth(
                    month)) {

                System.out.println(
                        "Invalid month.");

                return;
            }

            double basic;
            double allowances;
            double deductions;

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 salarySql)) {

                ps.setInt(1, employeeId);

                ResultSet rs =
                        ps.executeQuery();

                if (!rs.next()) {

                    System.out.println(
                            "Salary record not found.");

                    return;
                }

                basic =
                        rs.getDouble(
                                "basic_salary");

                allowances =
                        rs.getDouble(
                                "allowances");

                deductions =
                        rs.getDouble(
                                "deductions");
            }

            double gross =
                    basic + allowances;

            // TAX CALCULATION
            double tax = calculateTaxAmount(
                    gross);

            double net =
                    gross - deductions - tax;

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 insertPayroll)) {

                ps.setInt(1, employeeId);
                ps.setString(2, month);
                ps.setDouble(3, gross);
                ps.setDouble(4, net);

                ps.executeUpdate();
            }

            System.out.println(
                    "\nPayroll generated successfully.");

            System.out.println(
                    "Gross Salary : " + gross);

            System.out.println(
                    "Tax          : " + tax);

            System.out.println(
                    "Net Salary   : " + net);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // TAX CALCULATION
    // =====================================================

    public static void calculateTax(
            Connection con,
            Scanner sc) {

        String sql = """
        SELECT basic_salary, allowances FROM salaries WHERE employee_id = ?
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            System.out.print(
                    "Enter Employee ID: ");

            int employeeId =
                    Integer.parseInt(
                            sc.nextLine());

            ps.setInt(1, employeeId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                double basic =
                        rs.getDouble(
                                "basic_salary");

                double allowances =
                        rs.getDouble(
                                "allowances");

                double gross =
                        basic + allowances;

                double tax =
                        calculateTaxAmount(
                                gross);

                System.out.println(
                        "\n========== TAX DETAILS ==========");

                System.out.println(
                        "Gross Salary : "
                                + gross);

                System.out.println(
                        "Tax Amount   : "
                                + tax);

            } else {

                System.out.println(
                        "Salary record not found.");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // TAX RULE
    public static double calculateTaxAmount(
            double gross) {

        if (gross <= 25000) {

            return 0;

        } else if (gross <= 50000) {

            return gross * 0.05;

        } else if (gross <= 100000) {

            return gross * 0.10;

        } else {

            return gross * 0.20;
        }
    }

    // =====================================================
    // VIEW PAYSLIP
    // =====================================================

    public static void viewPayslip(
            Connection con,
            Scanner sc) {

        String sql = """
        SELECT p.payroll_id, e.name, p.salary_month, p.gross_salary, p.net_salary FROM payroll p JOIN employees e ON p.employee_id = e.employee_id WHERE p.employee_id = ?
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            System.out.print(
                    "Enter Employee ID: ");

            int employeeId =
                    Integer.parseInt(
                            sc.nextLine());

            ps.setInt(1, employeeId);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\n========== PAYSLIP ==========");

            while (rs.next()) {

                System.out.println(
                        "Payroll ID : "
                                + rs.getInt(
                                "payroll_id"));

                System.out.println(
                        "Employee     : "
                                + rs.getString(
                                "name"));

                System.out.println(
                        "Month        : "
                                + rs.getString(
                                "salary_month"));

                System.out.println(
                        "Gross Salary : "
                                + rs.getDouble(
                                "gross_salary"));

                System.out.println(
                        "Net Salary   : "
                                + rs.getDouble(
                                "net_salary"));

                System.out.println(
                        "------------------------------");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // AGGREGATE FUNCTIONS
    // =====================================================

    public static void aggregateReport(
            Connection con) {

        String sql = """
        SELECT COUNT(*) AS employee_count, SUM(gross_salary) AS total_gross, AVG(gross_salary) AS average_gross, MAX(gross_salary) AS highest_gross, MIN(gross_salary) AS lowest_gross FROM payroll
        """;

        try (PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            if (rs.next()) {

                System.out.println(
                        "\n========== PAYROLL AGGREGATE REPORT ==========");

                System.out.println(
                        "Employee Count : "
                                + rs.getInt(
                                "employee_count"));

                System.out.println(
                        "Total Gross    : "
                                + rs.getDouble(
                                "total_gross"));

                System.out.println(
                        "Average Gross  : "
                                + rs.getDouble(
                                "average_gross"));

                System.out.println(
                        "Highest Gross  : "
                                + rs.getDouble(
                                "highest_gross"));

                System.out.println(
                        "Lowest Gross   : "
                                + rs.getDouble(
                                "lowest_gross"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================================
    // BATCH PAYROLL PROCESSING
    // =====================================================

    public static void batchPayroll(
            Connection con) {

        String selectSalary = """
        SELECT employee_id, basic_salary, allowances, deductions FROM salaries
        """;

        String insertPayroll = """
        INSERT INTO payroll (employee_id, salary_month, gross_salary, net_salary) VALUES (?, ?, ?, ?)
        """;

        try {

            System.out.println(
                    "Batch payroll processing started.");

            System.out.print(
                    "Enter Salary Month: ");

            Scanner sc =
                    new Scanner(System.in);

            String month =
                    sc.nextLine();

            if (!Validation.isValidMonth(
                    month)) {

                System.out.println(
                        "Invalid month.");

                return;
            }

            con.setAutoCommit(false);

            try (PreparedStatement select =
                         con.prepareStatement(
                                 selectSalary);
                 ResultSet rs =
                         select.executeQuery();
                 PreparedStatement insert =
                         con.prepareStatement(
                                 insertPayroll)) {

                while (rs.next()) {

                    int employeeId =
                            rs.getInt(
                                    "employee_id");

                    double basic =
                            rs.getDouble(
                                    "basic_salary");

                    double allowances =
                            rs.getDouble(
                                    "allowances");

                    double deductions =
                            rs.getDouble(
                                    "deductions");

                    double gross =
                            basic + allowances;

                    double tax =
                            calculateTaxAmount(
                                    gross);

                    double net =
                            gross - deductions - tax;

                    insert.setInt(
                            1, employeeId);

                    insert.setString(
                            2, month);

                    insert.setDouble(
                            3, gross);

                    insert.setDouble(
                            4, net);

                    insert.addBatch();
                }

                insert.executeBatch();

                con.commit();

                System.out.println(
                        "Batch payroll processed successfully.");
            }

        } catch (Exception e) {

            try {

                con.rollback();

                System.out.println(
                        "Batch processing rolled back.");

            } catch (SQLException ex) {

                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {

                con.setAutoCommit(true);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}