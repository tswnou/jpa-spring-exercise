package com.exercise.demo;

import com.exercise.demo.entity.*;
import com.exercise.demo.repository.*;
import com.exercise.demo.service.ProjectService;
import com.exercise.demo.web.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest loads only the controllers specified w/ Spring MVC infrastructure
@WebMvcTest({
        AddressController.class,
        CountryController.class,
        DepartmentController.class,
        PhoneController.class,
        ProjectController.class
})
class ControllersTests {

    @Autowired
    private MockMvc mockMvc;

    // Mock Repositories, controllers depend on repos, services
    @MockitoBean private AddressRepository addressRepo;
    @MockitoBean private CountryRepository countryRepo;
    @MockitoBean private DepartmentRepository departmentRepo;
    @MockitoBean private EmployeeRepository employeeRepo;
    @MockitoBean private EmployeePhoneRepository phoneRepo;
    @MockitoBean private ProjectRepository projectRepo;

    @MockitoBean private ProjectAssignmentRepository assignRepo;

    // Mock Service
    @MockitoBean private ProjectService projectService;


    //  COUNTRY CONTROLLER, tests POST /api/countries


    @Test
    void testCreateCountry() throws Exception {

        //fake country
        Country c = new Country();
        c.setName("GR");
        c.setDescription("Greece");


        when(countryRepo.save(any())).thenReturn(c);

        mockMvc.perform(post("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"GR\",\"description\":\"Greece\"}"))
                .andExpect(status().isCreated());

        verify(countryRepo, times(1)).save(any());
    }


    //  ADDRESS CONTROLLER


    //POST /api/addresses
    @Test
    void testCreateAddress() throws Exception {

        //fake address
        Address a = new Address();
        a.setId(1L);
        a.setCity("Athens");

        //checks if country exists
        when(addressRepo.save(any())).thenReturn(a);
        when(countryRepo.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "streetName":"fragkokklisias",
                              "streetNumber":"13",
                              "zipCode":"15125",
                              "city":"Athens",
                              "country":{"name":"GR"}
                            }
                        """))
                .andExpect(status().isCreated());

        verify(addressRepo, times(1)).save(any());
    }

    //  DEPARTMENT CONTROLLER

    //POST /api/departments
    @Test
    void testCreateDepartment() throws Exception {

        //fake address
        Address address = new Address();
        address.setId(5L);

        //simulates the address with ID 5, exists So  controller does not throw an exception
        when(addressRepo.findById(5L)).thenReturn(Optional.of(address));

        //fake department
        Department saved = new Department();
        //department w/ id 99 was saved
        saved.setId(99L);

        when(departmentRepo.save(any())).thenReturn(saved);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "DG1",
                              "addressId": 5
                            }
                        """))
                .andExpect(status().isCreated());

        //verifies the controller saved the dep. once
        verify(departmentRepo, times(1)).save(any());
    }


    //  PHONE CONTROLLER

    //POST /api/phones

    @Test
    void testCreatePhone() throws Exception {

        //fake employee
        Employee emp = new Employee();
        emp.setId(1L);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));

        //fake empl. phone
        EmployeePhone saved = new EmployeePhone();
        saved.setId(3L);

        when(phoneRepo.save(any())).thenReturn(saved);

        mockMvc.perform(post("/api/phones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "value":"6911111111",
                              "employeeId":1
                            }
                        """))
                .andExpect(status().isCreated());

        verify(phoneRepo, times(1)).save(any());
    }

    //  PROJECT CONTROLLER – CREATE


    //POST /api/projects
    @Test
    void testCreateProject() throws Exception {

        //fake project
        Project p = new Project();
        p.setId(1L);
        p.setName("CBAM");

        //Mock repository behavior
        when(projectRepo.save(any())).thenReturn(p);

        //HTTP POST request
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name":"CBAM",
                              "startDate":"2025-11-03",
                              "endDate":"2026-05-02",
                              "budget":2000000
                            }
                        """))
                .andExpect(status().isCreated());

        //Verify correct repository usage
        verify(projectRepo, times(1)).save(any());
    }

    // PROJECT CONTROLLER – ASSIGN EMPLOYEE TO PROJECT

    //POST /api/projects/{projectId}/assign/{employeeId}
    @Test
    void testAssignEmployee() throws Exception {

        //fake project assignment
        ProjectAssignments pa = new ProjectAssignments();

        //when controller calls projectService.assignEmployeeToProject(x, y) return the fake project pa
        when(projectService.assignEmployeeToProject(anyLong(), anyLong()))
                .thenReturn(pa);

        //performs a fake HTTP POST request to your controller endpoint, checks that the controller responds with HTTP 200 OK
        mockMvc.perform(post("/api/projects/10/assign/5"))
                .andExpect(status().isOk());

        //Mockito is checking that the controller called projectService.assignEmployeeToProject(10, 5) exactly one time, with these exact arguments.
        verify(projectService, times(1)).assignEmployeeToProject(10L, 5L);
    }
}

