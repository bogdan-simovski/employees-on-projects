package com.example.employee.app.employeesonprojects.service;

import com.example.employee.app.employeesonprojects.model.WorkRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class CsvWorkRecordReaderImpl {

  private static final int CSV_FILE_HEADERS_NUMBER = 4;

  private static final List<String> HEADERS = Arrays.asList("EmpID", "ProjectID", "DateFrom", "DateTo");

  public List<WorkRecord> read(MultipartFile multipartFile){
    List<WorkRecord> workRecords = new LinkedList<>();
    try (CsvBeanReader csvBeanReader = new CsvBeanReader(new InputStreamReader(multipartFile.getInputStream()),
      CsvPreference.STANDARD_PREFERENCE)) {
      String[] headers = csvBeanReader.getHeader(true);
      validateHeaders(headers);
      WorkRecord workRecord;
      log.info("Starting to read the csv file ...");
      while ((workRecord = csvBeanReader.read(WorkRecord.class, headers)) != null) {
        workRecords.add(workRecord);
      }
    } catch (IOException e) {
      log.error("Couldn't open the input stream. ", e);
    } catch (IllegalArgumentException e) {
      log.error("The headers of the csv file are not valid. ", e);
    }
    return workRecords;
  }

  private void validateHeaders(String[] headers) {

    if (headers != null && headers.length == CSV_FILE_HEADERS_NUMBER) {

      for (int i = 0; i < headers.length; i++) {

        if (!HEADERS.get(i).equals(headers[i])) {
          log.error("The header: '{}' is invalid. Check the configured column headers.", headers[i]);
          throw new IllegalArgumentException("The headers of the csv currency file are not valid.");
        }
      }

    } else {
      log.error("The headers of the csv currency file are either not valid or missing.");
      throw new IllegalArgumentException("The headers of the csv currency file are either not valid or missing.");
    }
  }
}
