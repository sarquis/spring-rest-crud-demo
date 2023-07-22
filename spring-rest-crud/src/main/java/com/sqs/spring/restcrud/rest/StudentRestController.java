package com.sqs.spring.restcrud.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqs.spring.restcrud.entity.Student;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    /*
     * @PostConstruct is called only once! When it's given bean is contructed.
     */
    @PostConstruct
    public void loadData() {

	theStudents = new ArrayList<>();

	theStudents.add(new Student("Poornima", "Patel"));
	theStudents.add(new Student("Mario", "Rossi"));
	theStudents.add(new Student("Mary", "Smith"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
	return theStudents;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

	if ((studentId >= theStudents.size()) || (studentId < 0)) {
	    throw new StudentNotFoundException("Student id not found - " + studentId);
	}

	return theStudents.get(studentId);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {

	StudentErrorResponse error = new StudentErrorResponse();

	error.setStatus(HttpStatus.NOT_FOUND.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());

	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {

	StudentErrorResponse error = new StudentErrorResponse();

	error.setStatus(HttpStatus.BAD_REQUEST.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());

	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
