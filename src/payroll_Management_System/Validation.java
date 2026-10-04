package payroll_Management_System;

public class Validation {

    // NAME VALIDATION
    public static boolean isValidName(String name) {

        return name != null
                && !name.trim().isEmpty()
                && name.matches("[A-Za-z ]+");
    }

    // EMAIL VALIDATION
    public static boolean isValidEmail(String email) {

        return email != null
                && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // PHONE VALIDATION
    public static boolean isValidPhone(String phone) {

        return phone != null
                && phone.matches("[0-9]{10}");
    }

    // ID VALIDATION
    public static boolean isValidId(int id) {

        return id > 0;
    }

    // DEPARTMENT VALIDATION
    public static boolean isValidDepartment(String department) {

        return department != null
                && !department.trim().isEmpty()
                && department.matches("[A-Za-z ]+");
    }

    // SALARY VALIDATION
    public static boolean isValidSalary(double salary) {

        return salary > 0;
    }

    // MONTH VALIDATION
    public static boolean isValidMonth(String month) {

        if (month == null || month.trim().isEmpty()) {
            return false;
        }

        return month.matches(
                "(?i)(January|February|March|April|May|June|July|August|September|October|November|December)");
    }
}