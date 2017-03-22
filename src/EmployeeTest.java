import java.io.*;
import java.util.*;

public class EmployeeTest {
    public static void main(String[] args) {

        //Creating and filling the array
        List<Employee> list = new ArrayList<>();
        list.add(new EmployeeFixed("John", 1000));
        list.add(new EmployeeHourly("Para", 10));
        list.add(new EmployeeHourly("Jess", 15));
        list.add(new EmployeeHourly("Tom", 5));
        list.add(new EmployeeHourly("Cart", 80));
        list.add(new EmployeeFixed("Sasha", 2000));
        list.add(new EmployeeHourly("Zach", 80));
        list.add(new EmployeeFixed("Tiy", 9000));
        list.add(new EmployeeHourly("Chu", 120));
        list.add(new EmployeeFixed("Gogog", 5000));
        list.add(new EmployeeHourly("Eugene", 20));
        list.add(new EmployeeFixed("Bred", 3000));

        //Printing out unsorted array
        for (Employee employee: list)
            System.out.println(employee);
        System.out.println(" ");

        //Sorting array
        Collections.sort(list, new EmployeeComparator());

        //Printing out first 5 sorted employee
        for (int i = 0; i < 5; i++) {
            System.out.println(list.get(i));
        }
        System.out.println(" ");

        //Ptinting out last 3 employee
        for (int i = list.size() - 1; i > list.size() - 1 - 3; i--) {
            System.out.println("ID: |" + list.get(i).getId() + "|");
        }
        System.out.println(" ");

        //Writing list to a file
        WriterReader.writeList(list);

        //Reading list from a file
        list = WriterReader.readList();

        //Printing list again
        for (Employee employee : list)
            System.out.println(employee);
    }
}

abstract class Employee implements Serializable {
    int id;
    String name;
    static int idCounter;

    String getName() {
        return name;
    }
    int getId() {
        return id;
    }

    abstract double getMonthlySalary();

    Employee() {
        idCounter++;
        id = idCounter;
    }

    @Override
    public String toString() {
        return "ID: |" + id + "| " + name + ": " + getMonthlySalary();
    }
}

class EmployeeFixed extends Employee {
    int fixedPayment;

    @Override
    double getMonthlySalary() {
        return fixedPayment;
    }

    EmployeeFixed(String name, int fixedPayment) {
        this.name = name;
        this.fixedPayment = fixedPayment;
    }
}

class EmployeeHourly extends Employee {
    int hourlyPayment;

    @Override
    double getMonthlySalary() {
        return 20.8 * 8 * hourlyPayment;
    }

    EmployeeHourly(String name, int hourlyPayment) {
        this.name = name;
        this.hourlyPayment = hourlyPayment;
    }
}

class EmployeeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        int compare = Double.compare(o2.getMonthlySalary(), o1.getMonthlySalary());
        if (compare != 0)
            return compare;
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}

class WriterReader {
    public static List<Employee> readList() {
        List<Employee> result = null;
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("File.txt"))) {
            result = (List<Employee>) stream.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void writeList(List<Employee> list) {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("File.txt"))) {
            stream.writeObject(list);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}