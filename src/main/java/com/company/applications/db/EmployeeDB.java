package com.company.applications.db;

import com.company.applications.api.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeDB {

    public static HashMap<Integer, Employee> employees = new HashMap<>();
    static{
        // TODO as if loading from real DB (Reids-lightSQL)
        employees.put(1, new Employee(1, "Ali", "Abdalla", "NL@email.com"));
        employees.put(2, new Employee(2, "John", "Gruber", "USA@email.com"));
        employees.put(3, new Employee(3, "Melcum", "Marshal", "AUS@email.com"));
        employees.put(4, new Employee(4, "Lokesh", "Gupta", "India@email.com"));
    }

    public static List<Employee> getEmployees(){
        return new ArrayList<Employee>(employees.values());
    }

    public static Employee getEmployee(Integer id){
        return employees.get(id);
    }

    public static void updateEmployee(Integer id, Employee employee){
        employees.put(id, employee);
    }

    public static void removeEmployee(Integer id){
        employees.remove(id);
    }
}