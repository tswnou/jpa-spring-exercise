package com.exercise.demo.web.dto;


//create new Projects

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateProjectRequest {

    public String name;
    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal budget;
}
