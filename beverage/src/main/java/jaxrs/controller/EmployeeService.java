package jaxrs.controller;

import jaxrs.dto.EmployeeDTO;
import jaxrs.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    public static final EmployeeService instance = new EmployeeService();

    private final Map<Integer, Employee> employees = new HashMap<>();
    private int nextId = 1;

    private EmployeeService() {
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    public Employee createEmployee(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        employee.setId(nextId++);
        employee.setName(employeeDto.getName());
        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee updateEmployee(int id, Employee employee) {
        if (employees.containsKey(id)) {
            employee.setId(id);
            employees.put(id, employee);
            return employee;
        }
        return null;
    }

    public boolean deleteEmployee(int id) {
        return employees.remove(id) != null;
    }
}
