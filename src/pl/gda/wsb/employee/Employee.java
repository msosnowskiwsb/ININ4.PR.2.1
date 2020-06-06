package pl.gda.wsb.employee;

public abstract class Employee {
    private boolean logged;
    private String name;

    public Employee(boolean logged, String name) {
        this.logged = logged;
        this.name = name;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getName() {
        return name;
    }

    public abstract String getPosition();

    @Override
    public String toString() {
        return logged + " - " + name + " - " + getPosition();
    }
}
