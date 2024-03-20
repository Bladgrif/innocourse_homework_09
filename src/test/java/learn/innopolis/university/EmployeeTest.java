package learn.innopolis.university;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import learn.innopolis.university.pojo_from_json.AuthRequest;
import learn.innopolis.university.pojo_from_json.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static learn.innopolis.university.Endpoint.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Здравствуйте!
//Вопросы:
//    1) Не смог распарсить body в getListEmployeesForCompany.
//        Так, чтобы получался список с объектами Employee.
//        Как это можно было сделать? И нужно ли?
//    2) Все, что повторялось вынес в класс Helpers, но сделал методы статичными.
//       Наверно это не очень правильно)


public class EmployeeTest {

    public static String TOKEN;

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://x-clients-be.onrender.com/";

        AuthRequest authRequest = new AuthRequest()
                .setUsername("bloom")
                .setPassword("fire-fairy");

        TOKEN = given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post(AUTH_LOGIN.getPath())
                .then()
                .statusCode(201)
                .extract().path("userToken");
    }

    @Test
    @DisplayName("Create a company, add an employee there, check that the employee has been added to the company")
    void AddEmployeeAndGetInfo() {

        int companyId = Helpers.addCompany(Helpers.getCompany());
        Employee employee = Helpers.getEmployee().setCompanyId(companyId);

        int employeeIdInCompany = Helpers.addEmployee(employee);
        int companyIdFromEmployeeInfo = Helpers.getEmployeeInfo(String.valueOf(employeeIdInCompany)).getCompanyId();

        assertEquals(companyId, companyIdFromEmployeeInfo);
    }

    @Test
    @DisplayName("Create an employee, change last names, check that the last name has changed")
    void RenameEmployee() {

        int companyId = Helpers.addCompany(Helpers.getCompany());
        Employee employee = Helpers.getEmployee().setCompanyId(companyId);
        int employeeIdInCompany = Helpers.addEmployee(employee);
        String oldName = employee.getMiddleName();
        employee.setMiddleName("Joe");

        String newEmployeeName = Helpers.changeEmployeeInformation(String.valueOf(employeeIdInCompany), employee).getMiddleName();
        assertEquals(oldName, newEmployeeName);

    }

    @Test
    @DisplayName("Get a list of employees for a company")
    void getAllEmployees() {
        int companyId = Helpers.addCompany(Helpers.getCompany());
        Employee employee = Helpers.getEmployee().setCompanyId(companyId);
        int employeeIdInCompany = Helpers.addEmployee(employee);
//        EmployeeList employees = Helpers.getListEmployeesForCompany(String.valueOf(companyId));
//        assert(employees.size() > 0);
        ArrayList<Integer> ids = Helpers.getListEmployeesForCompany(String.valueOf(companyId));
        assert (ids.size() > 0);
    }
}

