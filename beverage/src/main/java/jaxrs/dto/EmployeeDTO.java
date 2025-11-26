package jaxrs.dto;

import jaxrs.model.Employee;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDTO {
    private int id;
    private String name;
    private String href;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee, URI baseUri) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.href = baseUri.toString() + "employees/" + employee.getId();
    }

    public static List<EmployeeDTO> marshall(List<Employee> employees, URI baseUri) {
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            dtos.add(new EmployeeDTO(employee, baseUri));
        }
        return dtos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
