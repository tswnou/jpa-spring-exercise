package com.exercise.demo;

import com.exercise.demo.entity.*;
import com.exercise.demo.repository.*;
import com.exercise.demo.service.ProjectSevice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.exercise.demo.entity.Employee;
import com.exercise.demo.repository.EmployeeRepository;
import com.exercise.demo.repository.ProjectRepository;
import com.exercise.demo.service.ProjectSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


//test
//Boots the entire Spring context for testing
@SpringBootTest
class SBexerciseApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectSevice projectSevice;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired DepartmentRepository departmentRepository;

    //Assign Employee to Project
    @Test
    void testEmployeeAssignedToProject() {

        // Create an Employee
        Employee emp = new Employee();
        emp.setName("Ioanna Tsonou");
        emp.setAge(21);
        emp.setRole("Developer");
        employeeRepository.save(emp);

        //Create a Project

        Project project = new Project();
        project.setName("CBAM");
        project.setBudget(BigDecimal.valueOf(1000000));
        project.setStartDate(LocalDate.of(2025, 11, 3));
        project.setEndDate(LocalDate.of(2026, 5, 2));

        projectRepository.save(project);

        //Assign Employee → Project

        ProjectAssignments assignment = projectSevice.assignEmployeeToProject(project.getId(), emp.getId());

        //verify / Assertions
        assertNotNull(assignment);
        assertEquals(emp.getId(), assignment.getEmployee().getId());
        assertEquals(project.getId(), assignment.getProject().getId());

    }



    //Reference Data (Country → Address → Department)
    @Test
    void testReferenceDataCreation() {
        // Country
        Country country = new Country();
        country.setName("GR");
        country.setDescription("Greece");

        //Address
        Address address = new Address();
        address.setStreetName("Kifisias Avenue");
        address.setStreetNumber("44");
        address.setZipCode("11526");
        address.setCity("Athens");
        address.setCountry(country);

        //Department
        Department department = new Department();
        department.setName("Engineering");
        department.setCreationDate(LocalDateTime.now());
        department.setAddress(address);

        // Save to db
        countryRepository.save(country);
        addressRepository.save(address);
        departmentRepository.save(department);

        Department savedDept = departmentRepository.findById(department.getId()).orElse(null);
        assertNotNull(savedDept);
        assertNotNull(savedDept.getAddress());
        assertNotNull(savedDept.getAddress().getCountry());
        assertEquals("Engineering", savedDept.getName());
        assertEquals("Greece", savedDept.getAddress().getCountry().getDescription());

        System.out.println("Reference data (Country → Address → Department) saved successfully!");
    }





}
