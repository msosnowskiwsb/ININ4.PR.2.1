package pl.gda.wsb.employee;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeePrinter {
    static EmployeeRepository employeeRepository = new EmployeeRepository();

    static void printEmployees() {
        if (employeeRepository.getEmployees() == null) {
            System.out.println("Błąd pobierania pracowników");
        } else if (employeeRepository.getEmployees().size() == 0) {
            System.out.println("Brak pracowników");
        } else if (employeeRepository.getEmployees().size() > 0) {
            System.out.println("\nLista pracowników (" + employeeRepository.getEmployees().size() + "):");
            int i = 0;
            for (String employee : employeeRepository.getEmployees()) {
                System.out.println(employee);
                if (i++ == 5) {
                    System.out.println("...");
                    break;
                }
            }
        }
    }

    static void printLoggedEmployees() {
        if (employeeRepository.getEmployees(true).size() == 0) {
            System.out.println("Brak zalogowanych pracowników");
        } else if (employeeRepository.getEmployees(true).size() > 0) {
            System.out.println("\nZalogowani użytkownicy (" + employeeRepository.getEmployees(true).size() + "):");
            int i = 0;
            for (String loggedEmployee : employeeRepository.getEmployees(true)) {
                System.out.println(loggedEmployee);
                if (i++ == 5) {
                    System.out.println("...");
                    break;
                }
            }
        }
    }

    static void printWelcomeText() {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy HH:mm");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Hello ").append("!")
                .append("\nAktualna data: ").append(ft.format(new Date()))
                .append("\nFirma: ").append(EmployeeDemo.companyName)
                .append("\nOperator ").append(DataBase.getOperatorName())
                .append("\nLiczba pracowników: ").append(employeeRepository.getEmployees().size());
        System.out.println(stringBuilder);
    }
}
