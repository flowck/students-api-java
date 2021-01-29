package me.changani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import me.changani.entity.Address;
import me.changani.entity.Student;
import me.changani.repository.AddressRepository;
import me.changani.repository.StudentRepository;
import me.changani.request.CreateStudentRequest;
import me.changani.request.InQueryRequest;
import me.changani.request.UpdateStudentRequest;
import me.changani.response.StudentResponse;


@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	AddressRepository addressRepository;
		
  public List<Student> getAllStudents(int page, int perPage) {
  	Pageable pageable = PageRequest.of(page - 1, perPage);
  	return studentRepository.findAll(pageable).getContent();
  }
  
  public Student createStudent(CreateStudentRequest createStudentRequest) {
  	Student student = new Student(createStudentRequest);
  	
  	
  	Address address = new Address();
  	address.setCity(createStudentRequest.getCity());
  	address.setStreet(createStudentRequest.getStreet());
  	
  	address = addressRepository.save(address);
  	
  	student.setAddress(address);
  	student = studentRepository.save(student);
  	
  	return student;
  }
  
  public Student updateStudent(Long id, UpdateStudentRequest updateStudentRequest) {
  	Student student = studentRepository.findById(id).get();
  	
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
  
  public List<StudentResponse> getStudentsByCity(String city) {
  	// List<Student> students = studentRepository.findByAddressCity(city); -- JPA query generated by meta proxies
  	List<Student> students = studentRepository.getByAddressCity(city); // -- JPQL
  	List<StudentResponse> listOfStudents = new ArrayList<StudentResponse>();
  	
  	students.stream().forEach(student -> {
  		listOfStudents.add(new StudentResponse(student));
  	});
  	
  	return listOfStudents;
  }
}
