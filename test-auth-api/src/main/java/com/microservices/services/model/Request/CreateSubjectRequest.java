package com.microservices.services.model.Request;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubjectRequest {

    @NotBlank(message = "Book Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Book name should contain only alphabetic characters")
    String book_name;
	
}
