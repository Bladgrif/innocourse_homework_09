package learn.innopolis.university;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import learn.innopolis.university.pojo_from_json.Company;
import learn.innopolis.university.pojo_from_json.Employee;
import org.hamcrest.Matchers;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static learn.innopolis.university.EmployeeTest.TOKEN;
import static learn.innopolis.university.Endpoint.COMPANY;
import static learn.innopolis.university.Endpoint.EMPLOYEE;

public class Helpers {
    static Faker faker = new Faker();

    public static Integer addCompany(Company company) {
        int companyId = given()
                .contentType(ContentType.JSON)
                .body(company)
                .header("X-Client-Token", TOKEN)
                .when()
                .post(COMPANY.getPath())
                .then()
                .statusCode(201)
                .extract().path("id");
        return companyId;
    }

    public static Integer addEmployee(Employee employee) {
        int employeeIdInCompany = given()
                .contentType(ContentType.JSON)
                .body(employee)
                .header("X-Client-Token", TOKEN)
                .when()
                .post(EMPLOYEE.getPath())
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .extract().path("id");
        return employeeIdInCompany;
    }

    public static Employee getEmployeeInfo(String employeeIdInCompany) {
        Employee employee = given()
                .when()
                .get(EMPLOYEE.getPath() + "/" + employeeIdInCompany)
                .then()
                .statusCode(200)
                .body("companyId", Matchers.notNullValue())
                .extract().body().as(Employee.class);
        return employee;
    }

    public static Employee changeEmployeeInformation(String employeeIdInCompany, Employee object) {
        Employee employee = given()
                .contentType(ContentType.JSON)
                .body(object)
                .header("X-Client-Token", TOKEN)
                .when()
                .get(EMPLOYEE.getPath() + "/" + employeeIdInCompany)
                .then()
                .statusCode(200)
                .body("companyId", Matchers.notNullValue())
                .extract().body().as(Employee.class);
        return employee;
    }

    public static ArrayList getListEmployeesForCompany(String companyId) {
//        EmployeeList employeeList = given()
//                .when()
//                .get(EMPLOYEE.getPath() + "?company=" + companyId)
//                .then()
//                .statusCode(200)
//                .extract().body().as(EmployeeList.class);

        ArrayList<Integer> ids = given()
                .when()
                .get(EMPLOYEE.getPath() + "?company=" + companyId)
                .then()
                .statusCode(200)
                .extract()
                .path("id");
        return ids;
    }

    public static String getCompanyName() {
        String companyName = faker.company().name();
        return companyName;
    }

    public static String getCompanyDescription() {
        String companyDescription = faker.company().industry();
        return companyDescription;
    }

    public static Company getCompany() {
        Company company = new Company()
                .setName(Helpers.getCompanyName())
                .setDescription(Helpers.getCompanyDescription());
        return company;
    }

    public static Employee getEmployee() {

        Employee employee = new Employee()
                .setId(999)
                .setIsActive(true)
                .setCreateDateTime("2024-03-17T09:11:41.414Z")
                .setLastChangedDateTime("2024-03-17T09:11:41.414Z")
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setMiddleName("Ivanovich")
                .setPhone("8989898989")
                .setEmail("Ivanov@gmail.com")
                .setBirthdate("2024-03-17")
                .setAvatarUrl("avatar_url")
                .setCompanyId(123);
        return employee;
    }
}
