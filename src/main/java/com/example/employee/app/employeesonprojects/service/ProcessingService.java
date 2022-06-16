package com.example.employee.app.employeesonprojects.service;

import com.example.employee.app.employeesonprojects.model.EmployeesWorkRecordDto;
import com.example.employee.app.employeesonprojects.model.WorkRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ProcessingService {

  @Autowired
  private CsvWorkRecordReaderImpl csvWorkRecordReader;


  public List<EmployeesWorkRecordDto> findLongestWorkingEmployeesFromCsv(MultipartFile multipartFile) {

    //read the csv and store it into a list of objects
    List<WorkRecord> allWorkRecords = csvWorkRecordReader.read(multipartFile);
    //initialize variables needed to find the max working employee pair
    Long emp1Id = null;
    Long emp2Id = null;
    long maxWorkRecord = 0;
    //go trough each pair (not processing the same employee pair again) O(n^2/2)
    for(int i = 0; i < allWorkRecords.size(); i++) {
      for(int j = i + 1; j < allWorkRecords.size(); j++) {
        long currentWorkRecord = allWorkRecords.get(i).checkDaysTogether(allWorkRecords.get(j));
        if (currentWorkRecord > maxWorkRecord) {
          maxWorkRecord = currentWorkRecord;
          emp1Id = allWorkRecords.get(i).getEmpID();
          emp2Id = allWorkRecords.get(j).getEmpID();
        }
      }
    }

    List<EmployeesWorkRecordDto> resultList = new ArrayList<>();
    //get all the projects for the max working employee pair
    for(int i = 0; i < allWorkRecords.size(); i++) {
      for(int j = i + 1; j < allWorkRecords.size(); j++) {
        if ((allWorkRecords.get(i).getEmpID().equals(emp1Id) || allWorkRecords.get(i).getEmpID().equals(emp2Id))
          && (allWorkRecords.get(j).getEmpID().equals(emp1Id) || allWorkRecords.get(j).getEmpID().equals(emp2Id))) {
          long daysTogether = allWorkRecords.get(i).checkDaysTogether(allWorkRecords.get(j));
          if (daysTogether > 0) {
            resultList.add(new EmployeesWorkRecordDto(allWorkRecords.get(i).getEmpID(),
              allWorkRecords.get(j).getEmpID(), allWorkRecords.get(i).getProjectID(), daysTogether));
          }
        }

      }
    }
    //sort the list by days worked together
    resultList.sort(Comparator.comparing(EmployeesWorkRecordDto::getDaysWorkedTogether).reversed());
    //return
    return resultList;
  }
}
