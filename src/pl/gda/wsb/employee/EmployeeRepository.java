package pl.gda.wsb.employee;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeRepository {
    DataBase dataBase = new DataBase();

    void readEmployeeNameAndChangeStatus() {
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

    ArrayList<String> getEmployees(Boolean onlyLooged){
        return onlyLooged ? EmployeeDemo.loggedEmployees : EmployeeDemo.employees;
    }

    ArrayList<String> getEmployees(){
        return getEmployees(false);
    }
}
