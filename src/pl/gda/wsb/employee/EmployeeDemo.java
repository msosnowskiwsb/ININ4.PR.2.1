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

        System.out.println("\n Podaj imię i nazwisko (exit = koniec):");
        Scanner inScanner = new Scanner(System.in);

        while (inScanner.hasNextLine()){
            String userName = inScanner.nextLine();

            if(userName.equals("exit")){
                FileWriter fw = null;
                try {
                    fw = new FileWriter(fileName,false);
                    for (String employee : employees){
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

            for (String employee : employees){
                Matcher matcher = patternSearch.matcher(employee);
                if (matcher.matches()){
                    searched = true;
                    boolean isLogged = Boolean.parseBoolean(matcher.group(1));
                    employees.remove(i);
                    employees.add(i,employee.replace(matcher.group(1), isLogged ? "false" : "true"));
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
}
