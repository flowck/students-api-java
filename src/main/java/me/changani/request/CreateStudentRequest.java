package me.changani.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateStudentRequest {
	
	@NotEmpty(message = "firstName should not be empty")
	private String firstName;

	@NotBlank(message = "lastName should not be empty")
	private String lastName;
	
	private String email;
}
