package pl.gda.wsb.employee;

public class WrongEmployee extends Exception {
    public WrongEmployee(){
        System.out.println("Nie znaleziono pracownika w bazie!\n\nPodaj poprawne imię i nazwisko (exit = koniec):");
    }
}
