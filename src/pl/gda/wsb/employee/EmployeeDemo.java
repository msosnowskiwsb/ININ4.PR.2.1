package pl.gda.wsb.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeDemo {
    static String companyName = "WSB Gdańsk";
    static String fileName = System.getProperty("user.dir") + "\\utils\\db.txt";

    static ArrayList<String> employees = new ArrayList<>();
    static ArrayList<String> loggedEmployees = new ArrayList<>();

    private static DataBase dataBase;
    private static EmployeeRepository employeeRepository;

    public static void main(String args[]) {
        dataBase = new DataBase();
        employeeRepository = new EmployeeRepository();

        Scanner fileScanner = dataBase.getFileScanner();
        if (fileScanner == null) return;

        Pattern pattern = Pattern.compile("^(true|false) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String employee = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(employee);
            if (matcher.matches()) {
                employeeRepository.getEmployees().add(employee);
                if (Boolean.parseBoolean(matcher.group(1))) {
                    employeeRepository.getEmployees(true).add(matcher.group(2));
                }
            }
        }

        printWelcomeText();

        printEmployees();

        printLoggedEmployees();

        employeeRepository.readEmployeeNameAndChangeStatus();

    }

    private static void printEmployees() {
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

    private static void printLoggedEmployees() {
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

    private static void printWelcomeText() {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy HH:mm");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Hello ").append("!")
                .append("\nAktualna data: ").append(ft.format(new Date()))
                .append("\nFirma: ").append(companyName)
                .append("\nOperator ").append(DataBase.getOperatorName())
                .append("\nLiczba pracowników: ").append(employeeRepository.getEmployees().size());
        System.out.println(stringBuilder);
    }

}
