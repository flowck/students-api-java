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

	@Autowired
	StudentService studentService;

	private List<StudentResponse> prepareStudentsResponse(List<Student> students) {
		List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
		
		students.stream().forEach(student -> {
			listOfStudents.add(new StudentResponse(student));
		});

		return listOfStudents;
	}

	@GetMapping
	public List<StudentResponse> getAllStudents(@RequestParam("page") int page, @RequestParam("per_page") int perPage) {
		List<Student> students = studentService.getAllStudents(page, perPage);
		
		return this.prepareStudentsResponse(students);
	}

	@PostMapping
	public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest student) {
		Student newStudent = studentService.createStudent(student);

		return new StudentResponse(newStudent);
	}

	@PutMapping("{id}")
	public StudentResponse updateStudent(@PathVariable("id") Long id,
			@Valid @RequestBody UpdateStudentRequest updateStudentRequest) {
		Student student = studentService.updateStudent(id, updateStudentRequest);

		return new StudentResponse(student);
	}

	@DeleteMapping("{id}")
	public void deleteStudent(@PathVariable("id") long id) {
		studentService.deleteStudent(id);
	}
}
