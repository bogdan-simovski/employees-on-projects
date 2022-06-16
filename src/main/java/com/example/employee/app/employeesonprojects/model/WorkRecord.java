package com.example.employee.app.employeesonprojects.model;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

@NoArgsConstructor
@ToString
public class WorkRecord {

  private Long empID;
  private Long projectID;
  private LocalDate dateFrom;
  private LocalDate dateTo;


  public Long getEmpID() {
    return empID;
  }

  public void setEmpID(String empID) {
    this.empID = Long.parseLong(empID);
  }

  public Long getProjectID() {
    return projectID;
  }

  public void setProjectID(String projectID) {
    this.projectID = Long.parseLong(projectID);
  }

  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = LocalDate.parse(dateFrom);
  }

  public LocalDate getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    if("NULL".equals(dateTo)) {
      this.dateTo = LocalDate.now();
    } else {
      this.dateTo = LocalDate.parse(dateTo);
    }
  }

  public long checkDaysTogether(WorkRecord colleague) {
    if (!projectID.equals(colleague.projectID) || dateTo.isBefore(dateFrom) || colleague.dateTo.isBefore(colleague.dateFrom)) {
      return 0;
    } else {
      long numberOfOverlappingDates;
      if (dateTo.isBefore(colleague.dateFrom) || colleague.dateTo.isBefore(dateFrom)) {
        return 0;
      } else {
        LocalDate laterStart = Collections.max(Arrays.asList(dateFrom, colleague.dateFrom));
        LocalDate earlierEnd = Collections.min(Arrays.asList(dateTo, colleague.dateTo));
        numberOfOverlappingDates = ChronoUnit.DAYS.between(laterStart, earlierEnd);
      }
      return numberOfOverlappingDates;
    }
  }
}
