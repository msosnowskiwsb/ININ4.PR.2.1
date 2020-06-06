package pl.gda.wsb.employee;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeDemo {
    static String companyName = "WSB Gdańsk";
    static String fileName = System.getProperty("user.dir") + "\\utils\\db.txt";

    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Employee> loggedEmployees = new ArrayList<>();

    private static DataBase dataBase;
    private static EmployeeRepository employeeRepository;

    public static void main(String args[]) {
        dataBase = new DataBase();
        employeeRepository = new EmployeeRepository();

        Scanner fileScanner = dataBase.getFileScanner();
        if (fileScanner == null) return;

        Pattern pattern = Pattern.compile("^(true|false) - (.+) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String lineFromFile = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(lineFromFile);
            if (matcher.matches()) {

                boolean logged = Boolean.parseBoolean(matcher.group(1));
                String name = matcher.group(2);
                String position = matcher.group(3);

                switch (position) {
                    case "dyrektor":{
                        Employee employee = new Director(logged,name);
                        employeeRepository.getEmployees().add(employee);
                        if (logged) {
                            employeeRepository.getEmployees(true).add(employee);
                        }
                        break;
                    }
                    case "kierowca":{
                        Employee employee = new Driver(logged,name);
                        employeeRepository.getEmployees().add(employee);
                        if (logged) {
                            employeeRepository.getEmployees(true).add(employee);
                        }
                        break;
                    }
                    case "handlowiec":{
                        Employee employee = new Seller(logged,name);
                        employeeRepository.getEmployees().add(employee);
                        if (logged) {
                            employeeRepository.getEmployees(true).add(employee);
                        }
                        break;
                    }
                }

            }
        }

        EmployeePrinter.printWelcomeText();

        EmployeePrinter.printEmployees(employees);

        EmployeePrinter.printLoggedEmployees(loggedEmployees);

        employeeRepository.readEmployeeNameAndChangeStatus(employees);

    }

}
