package com.springsecurity.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springsecurity.domain.Student;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("management/api/v1/students")
@Slf4j
public class StudentManagementController {
  private static final List<Student> STUDENTS = 
      Arrays.asList(new Student(1, "James Bond"),
          new Student(2, "Maria Jones"),
          new Student(3, "Anna Smith"));

  @GetMapping
  public List<Student> getAllStudents() {
    return STUDENTS;
  }

  @PostMapping
  public void registerNewStudent(@RequestBody Student student) {
    log.info("Student to be registered {}",student);
  }

  @DeleteMapping(path = "/{studentId}")
  public void deleteStudent(@PathVariable("studentId") Integer studentId) {
    log.info("Student to be deleted with student id {}", studentId);
  }

  @PutMapping(path = "/{studentId}")
  public void updateStudent(@PathVariable("studentId") Integer studentId, 
                            @RequestBody Student student) {
    log.info("Update student with id {} to {}", studentId, student);
  }
}
