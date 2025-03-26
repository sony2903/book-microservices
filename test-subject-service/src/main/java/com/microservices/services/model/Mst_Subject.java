package com.microservices.services.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_subject")
public class Mst_Subject extends BaseStandartModel {
	
	@Id
	private Long id;

    @NotBlank(message = "Book Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Book name should contain only alphabetic characters")
    private String book_name;

	
}
