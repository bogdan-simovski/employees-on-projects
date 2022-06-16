package com.example.employee.app.employeesonprojects.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeesWorkRecordDto {
  private Long employee1Id;
  private Long employee2Id;
  private Long ProjectId;
  private Long daysWorkedTogether;

}
