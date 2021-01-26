package me.changani.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.changani.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	// Specific column
	List<Student> findByFirstName(String firstName);
	
	// AND operator
	List<Student> findByFirstNameAndLastName(String firstName, String lastName);
	
	// OR operator
	List<Student> findByFirstNameOrLastName(String firstName, String lastName);
	
	// In operator
	List<Student> findByFirstNameIn(List<String> firstNames);
	
	@Query("FROM Student WHERE firstName = :firstName AND lastName = :lastName")
	List<Student> getByFirstNameAndLastName(String firstName, String lastName);
	
	@Modifying
	@Transactional
	@Query("UPDATE Student SET firstName = :firstName WHERE id = :id")
	Integer updateFirstName(Long id, String firstName);
	
	@Modifying
	@Transactional
	@Query("DELETE Student WHERE firstName = :firstName")
	public Integer deleteByFirstName(String firstName);
}
