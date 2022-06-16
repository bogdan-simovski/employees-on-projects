package com.example.employee.app.employeesonprojects.controller;

import com.example.employee.app.employeesonprojects.model.EmployeesWorkRecordDto;
import com.example.employee.app.employeesonprojects.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WorkRecordController {

  @Autowired
  private ProcessingService processingService;

  @GetMapping("/")
  public String homepage() {
    return "index";
  }

  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

    // check if file is empty
    if (file.isEmpty()) {
      attributes.addFlashAttribute("message", "Please select a file to upload.");
      return "redirect:/";
    }

    //call the service for the results
    List<EmployeesWorkRecordDto> employees = processingService.findLongestWorkingEmployeesFromCsv(file);


    //Get the longest working employees pair (the list is sorted the DESC)
    EmployeesWorkRecordDto longestWorkingEmployees = employees.get(0);

    //add the attributes
    attributes.addFlashAttribute("message",
      String.format("Emp1Id = %s, Emp2Id = %s, Days worked together: %s on project %s",
        longestWorkingEmployees.getEmployee1Id(), longestWorkingEmployees.getEmployee2Id(),
        longestWorkingEmployees.getDaysWorkedTogether(), longestWorkingEmployees.getProjectId()));

    attributes.addFlashAttribute("employees", employees);

    return "redirect:/";
  }
}
