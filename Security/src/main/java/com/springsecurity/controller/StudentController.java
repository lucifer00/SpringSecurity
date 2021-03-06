package com.springsecurity.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springsecurity.domain.Student;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

  private static final List<Student> STUDENTS = 
      Arrays.asList(new Student(1, "James Bond"),
          new Student(2, "Maria Jones"),
          new Student(3, "Anna Smith"));
  @GetMapping(path = "/{studentID}")
  public Student getStudent(@PathVariable("studentID")Integer studentId) {
    Student studentToRet = null;
    studentToRet = STUDENTS.stream()
                      .filter(student -> student.getStudentId()
                                         .equals(studentId))
                      .findFirst()
                      .orElseThrow(() -> new IllegalStateException("Student not found"));
    return studentToRet;
  }
}
