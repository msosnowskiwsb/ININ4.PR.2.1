package pl.gda.wsb.employee;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void main(String args[]) {
        String operatorName = "Mateusz";

        Scanner fileScanner = getFileScanner();
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

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Hello ").append(args[1]).append("!")
                .append("\nAktualna data: ").append(new Date())
                .append("\nFirma: ").append(companyName)
                .append("\nOperator ").append(operatorName)
                .append("\nLiczba pracowników: ").append(getEmployees().size());
        System.out.println(stringBuilder);

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

        System.out.println("\n Podaj imię i nazwisko (exit = koniec):");
        Scanner inScanner = new Scanner(System.in);

        while (inScanner.hasNextLine()){
            String userName = inScanner.nextLine();

            if(userName.equals("exit")){
                FileWriter fw = null;
                try {
                    fw = new FileWriter(fileName,false);
                    for (String employee : getEmployees()){
                        fw.write(employee + "\n");
                    }
                    fw.close();
                }
                catch (IOException e) {
                    System.out.println("Błąd zapisu pliku");
                }
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

    private static Scanner getFileScanner() {
        File file = new File(fileName);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Błąd pobierania pliku!");
            return null;
        }
        return fileScanner;
    }

    private static ArrayList<String> getEmployees(Boolean onlyLooged){
        return onlyLooged ? loggedEmployees : employees;
    }

    private static ArrayList<String> getEmployees(){
        return getEmployees(false);
    }
}
