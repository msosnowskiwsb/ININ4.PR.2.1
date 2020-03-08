package pl.gda.wsb.employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeDemo {
    static String companyName = "WSB Gdańsk";
    static String fileName = System.getProperty("user.dir") + "\\utils\\db.txt";

    public static void main(String args[]) {
        String operatorName = "Mateusz";
        ArrayList<String> loggedEmployees = new ArrayList<>();
        ArrayList<String> employees = new ArrayList<>();

        File file = new File(fileName);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Błąd pobierania pliku!");
            return;
        }

        Pattern pattern = Pattern.compile("^(true|false) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String employee = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(employee);
            if (matcher.matches()) {
                employees.add(employee);
                if (Boolean.parseBoolean(matcher.group(1))) {
                    loggedEmployees.add(matcher.group(2));
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Hello ").append(args[1]).append("!")
                .append("\nAktualna data: ").append(new Date())
                .append("\nFirma: ").append(companyName)
                .append("\nOperator ").append(operatorName)
                .append("\nLiczba pracowników: ").append(employees.size());
        System.out.println(stringBuilder);

        if (employees == null) {
            System.out.println("Błąd pobierania pracowników");
        } else if (employees.size() == 0) {
            System.out.println("Brak pracowników");
        } else if (employees.size() > 0) {
            System.out.println("\nLista pracowników (" + employees.size() + "):");
            int i = 0;
            for (String loggedEmployee : loggedEmployees) {
                System.out.println(loggedEmployee);
                if (i++ == 5) {
                    System.out.println("...");
                    break;
                }
            }
        }

        if (loggedEmployees.size() == 0) {
            System.out.println("Brak zalogowanych pracowników");
        } else if (loggedEmployees.size() > 0) {
            System.out.println("\nZalogowani użytkownicy (" + loggedEmployees.size() + "):");
            int i = 0;
            for (String loggedEmployee : loggedEmployees) {
                System.out.println(loggedEmployee);
                if (i++ == 5) {
                    System.out.println("...");
                    break;
                }
            }
        }

    }
}
