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

    // Mock Repositories
    @MockitoBean private AddressRepository addressRepo;
    @MockitoBean private CountryRepository countryRepo;
    @MockitoBean private DepartmentRepository departmentRepo;
    @MockitoBean private EmployeeRepository employeeRepo;
    @MockitoBean private EmployeePhoneRepository phoneRepo;
    @MockitoBean private ProjectRepository projectRepo;

    @MockitoBean private ProjectAssignmentRepository assignRepo;

    // Mock Service
    @MockitoBean private ProjectService projectService;

    // -----------------------------------------------------------
    //  COUNTRY CONTROLLER
    // -----------------------------------------------------------

    @Test
    void testCreateCountry() throws Exception {
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

    // -----------------------------------------------------------
    //  ADDRESS CONTROLLER
    // -----------------------------------------------------------

    @Test
    void testCreateAddress() throws Exception {
        Address a = new Address();
        a.setId(1L);
        a.setCity("Athens");

        when(addressRepo.save(any())).thenReturn(a);
        when(countryRepo.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "streetName":"Kifisias",
                              "streetNumber":"10",
                              "zipCode":"12345",
                              "city":"Athens",
                              "country":{"name":"GR"}
                            }
                        """))
                .andExpect(status().isCreated());

        verify(addressRepo, times(1)).save(any());
    }

    // -----------------------------------------------------------
    //  DEPARTMENT CONTROLLER
    // -----------------------------------------------------------

    @Test
    void testCreateDepartment() throws Exception {
        Address address = new Address();
        address.setId(5L);

        when(addressRepo.findById(5L)).thenReturn(Optional.of(address));

        Department saved = new Department();
        saved.setId(99L);

        when(departmentRepo.save(any())).thenReturn(saved);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "IT",
                              "addressId": 5
                            }
                        """))
                .andExpect(status().isCreated());

        verify(departmentRepo, times(1)).save(any());
    }

    // -----------------------------------------------------------
    //  PHONE CONTROLLER
    // -----------------------------------------------------------

    @Test
    void testCreatePhone() throws Exception {
        Employee emp = new Employee();
        emp.setId(1L);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));

        EmployeePhone saved = new EmployeePhone();
        saved.setId(3L);

        when(phoneRepo.save(any())).thenReturn(saved);

        mockMvc.perform(post("/api/phones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "value":"123456",
                              "employeeId":1
                            }
                        """))
                .andExpect(status().isCreated());

        verify(phoneRepo, times(1)).save(any());
    }

    // -----------------------------------------------------------
    //  PROJECT CONTROLLER – CREATE
    // -----------------------------------------------------------

    @Test
    void testCreateProject() throws Exception {
        Project p = new Project();
        p.setId(1L);
        p.setName("Project1");

        when(projectRepo.save(any())).thenReturn(p);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name":"Project1",
                              "startDate":"2025-01-01",
                              "endDate":"2025-02-02",
                              "budget":2000
                            }
                        """))
                .andExpect(status().isCreated());

        verify(projectRepo, times(1)).save(any());
    }

    // -----------------------------------------------------------
    // PROJECT CONTROLLER – ASSIGN EMPLOYEE
    // -----------------------------------------------------------

    @Test
    void testAssignEmployee() throws Exception {
        ProjectAssignments pa = new ProjectAssignments();

        when(projectService.assignEmployeeToProject(anyLong(), anyLong()))
                .thenReturn(pa);

        mockMvc.perform(post("/api/projects/10/assign/5"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).assignEmployeeToProject(10L, 5L);
    }
}

