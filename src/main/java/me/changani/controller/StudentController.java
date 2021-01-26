package me.changani.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.changani.entity.Student;
import me.changani.request.CreateStudentRequest;
import me.changani.request.InQueryRequest;
import me.changani.request.UpdateStudentRequest;
import me.changani.response.*;
import me.changani.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	@Value("${app.name}")
	private String appName;
	
	@Autowired
	StudentService studentService;
	

	@GetMapping("")
	public List<StudentResponse> getAllStudents() {
		 List<Student> listStudents = studentService.getAllStudents();
		 List<StudentResponse> studentReponseList = new ArrayList<StudentResponse>();
		 
		 listStudents.stream().forEach(student -> {
			 studentReponseList.add(new StudentResponse(student));
		 });
		 
		 return studentReponseList;
	}
	
	@PostMapping("")
	public StudentResponse createStudent(@Valid @RequestBody  CreateStudentRequest student) {
		Student newStudent = studentService.createStudent(student);
		
		return new StudentResponse(newStudent);
	}
	
	@PutMapping("")
	public StudentResponse updateStudent(@Valid @RequestBody UpdateStudentRequest updateStudentRequest) {
		Student student = studentService.updateStudent(updateStudentRequest);
		
		return new StudentResponse(student);
	}
	
	@DeleteMapping("{id}")
	public void deleteStudent(@PathVariable("id")  long id) {
		studentService.deleteStudent(id);
	}
	
	@GetMapping("getByFirstName/{firstName}")
	public List<StudentResponse> getStudentByName(@PathVariable("firstName") String firstName) {
		return studentService.getStudentsByFirstName(firstName);
	}
	
	@GetMapping("getByFirstNameAndLastName/{firstName}/{lastName}")
	public List<StudentResponse> getStudentByFirstNameAndLastName(
			@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName
	) {
		return studentService.getStudentByFirstNameAndLastName(firstName, lastName);
	}
	
	@GetMapping("getByFirstNameOrLastName/{firstName}/{lastName}")
	public List<StudentResponse> getStudentByFirstNameOrLastName(
			@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName
	) {
		return studentService.getSudentByFirstNameOrLastName(firstName, lastName);
	}
	
	@GetMapping("getByFirstNameIn")
	public List<StudentResponse> getStudentByFirstName(@RequestBody InQueryRequest inQueryRequest) {
		return studentService.getSudentByFirstNameIn(inQueryRequest);
	}
	
	@GetMapping("getWithPagination")
	public List<StudentResponse> getStudentWithPagination(
			@RequestParam("per_page") int perPage,
			@RequestParam("page") int page
	){
		return studentService.getSudentWithPagination(perPage, page);
	}
	
	@GetMapping("getAllWithSorting")
	public List<StudentResponse> getAllStudentsWithSorting() {
		return studentService.getSudentWithSort();
	}
	
	@PutMapping("updateFirstName/{id}/{firstName}")
	public String updateFirstName(
			@PathVariable("id") Long id,
			@PathVariable("firstName") String firstName
	) {
		Integer numberOfUpdatedRecords = studentService.updateStudentFirstName(id, firstName);
		return numberOfUpdatedRecords + " was/were updated successfully";
	}
}
