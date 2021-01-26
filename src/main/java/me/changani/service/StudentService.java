package me.changani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import me.changani.entity.Student;
import me.changani.repository.StudentRepository;
import me.changani.request.CreateStudentRequest;
import me.changani.request.InQueryRequest;
import me.changani.request.UpdateStudentRequest;
import me.changani.response.StudentResponse;


@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
		
  public List<Student> getAllStudents() {
  	return studentRepository.findAll();
  }
  
  public Student createStudent(CreateStudentRequest createStudentRequest) {
  	Student student = new Student(createStudentRequest);
  	
  	student = studentRepository.save(student);
  	
  	return student;
  }
  
  public Student updateStudent(UpdateStudentRequest updateStudentRequest) {
  	Student student = studentRepository.findById(updateStudentRequest.getId()).get();
  	
  	if (updateStudentRequest.getFirstName() != null && !updateStudentRequest.getFirstName().isEmpty() ) {
  		student.setFirstName(updateStudentRequest.getFirstName());
  	}
  	
  	if (updateStudentRequest.getLastName() != null && !updateStudentRequest.getLastName().isEmpty() ) {
  		student.setLastName(updateStudentRequest.getLastName());
  	}
  	
  	student = studentRepository.save(student);
  	
  	return student;
  }
  
  public void deleteStudent(long id) {
  	studentRepository.deleteById(id);
  }
  
  public List<StudentResponse> getStudentsByFirstName(String firstName) {
  	List<Student> students = studentRepository.findByFirstName(firstName);
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
  
  public List<StudentResponse> getStudentByFirstNameAndLastName(String firstName, String lastName) {
  	List<Student> students = studentRepository.getByFirstNameAndLastName(firstName, lastName); // .findByFirstNameAndLastName(firstName, lastName);
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
  
  public List<StudentResponse> getSudentByFirstNameOrLastName(String firstName, String lastName) {
  	List<Student> students = studentRepository.findByFirstNameOrLastName(firstName, lastName);
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
  
  public List<StudentResponse> getSudentByFirstNameIn(InQueryRequest inQueryRequest) {
  	List<Student> students = studentRepository.findByFirstNameIn(inQueryRequest.getFirstNames());
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
  
  public List<StudentResponse> getSudentWithPagination(int perPage, int page) {
  	Pageable pageable = PageRequest.of(page - 1, perPage);
  	List<Student> students = studentRepository.findAll(pageable).getContent();
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
  
  // H
  public List<StudentResponse> getSudentWithSort() {
  	Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
  	
  	List<Student> students = studentRepository.findAll(sort);
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
  
  public Integer updateStudentFirstName(Long id, String firstName) {
  	return studentRepository.updateFirstName(id, firstName);
  }
  
  public Integer deleteStudentByFirstName(String firstName) {
  	return studentRepository.deleteByFirstName(firstName);
  }
}
