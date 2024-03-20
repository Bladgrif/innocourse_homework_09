package learn.innopolis.university.pojo_from_json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeList {

    @JsonProperty("EmployeeList")
    private List<Employee> employeeList;

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public int size() {
        return employeeList.size();
    }
}