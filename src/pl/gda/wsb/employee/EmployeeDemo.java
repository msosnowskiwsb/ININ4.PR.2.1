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

    public static void main(String args[]) {
        dataBase = new DataBase();

        Scanner fileScanner = dataBase.getFileScanner();
        if (fileScanner == null) return;

        Pattern pattern = Pattern.compile("^(true|false) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String employee = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(employee);
            if (matcher.matches()) {
                getEmployees().add(employee);
                if (Boolean.parseBoolean(matcher.group(1))) {
                    getEmployees(true).add(matcher.group(2));
                }
            }
        }

        printWelcomeText();

        printEmployees();

        printLoggedEmployees();

        readEmployeeNameAndChangeStatus();

    }

    private static void readEmployeeNameAndChangeStatus() {
        System.out.println("\n Podaj imię i nazwisko (exit = koniec):");
        Scanner inScanner = new Scanner(System.in);

        while (inScanner.hasNextLine()){
            String userName = inScanner.nextLine();

            if(userName.equals("exit")){
                dataBase.saveToFile(getEmployees());
                break;
            }

            int i = 0;
            boolean searched = false;
            Pattern patternSearch = Pattern.compile("^(true|false) - " + userName + " - (.+)$");

            for (String employee : getEmployees()){
                Matcher matcher = patternSearch.matcher(employee);
                if (matcher.matches()){
                    searched = true;
                    boolean isLogged = Boolean.parseBoolean(matcher.group(1));
                    getEmployees().remove(i);
                    getEmployees().add(i,employee.replace(matcher.group(1), isLogged ? "false" : "true"));
                    break;
                }
                i++;
            }

            if (searched){
                System.out.println("Został zmieniony status dla pracownika: " + userName);
            } else {
                System.out.println("Błędnie podane imię i nazwisko!");
            }

        }
    }

    private static void printEmployees() {
        if (getEmployees() == null) {
            System.out.println("Błąd pobierania pracowników");
        } else if (getEmployees().size() == 0) {
            System.out.println("Brak pracowników");
        } else if (getEmployees().size() > 0) {
            System.out.println("\nLista pracowników (" + getEmployees().size() + "):");
            int i = 0;
            for (String employee : getEmployees()) {
                System.out.println(employee);
                if (i++ == 5) {
                    System.out.println("...");
                    break;
                }
            }
        }
    }

    private static void printLoggedEmployees() {
        if (getEmployees(true).size() == 0) {
            System.out.println("Brak zalogowanych pracowników");
        } else if (getEmployees(true).size() > 0) {
            System.out.println("\nZalogowani użytkownicy (" + getEmployees(true).size() + "):");
            int i = 0;
            for (String loggedEmployee : getEmployees(true)) {
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
                .append("\nLiczba pracowników: ").append(getEmployees().size());
        System.out.println(stringBuilder);
    }

    private static ArrayList<String> getEmployees(Boolean onlyLooged){
        return onlyLooged ? loggedEmployees : employees;
    }

    private static ArrayList<String> getEmployees(){
        return getEmployees(false);
    }

}
