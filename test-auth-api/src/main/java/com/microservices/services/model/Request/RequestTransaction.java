package com.microservices.services.model.Request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTransaction {
	
    private Long id;
    private Long user_id;
    private Long book_id;
    private String transaction_date;
    
    @NotBlank(message = "Return Date is required")
    private String return_date;

    private String return_date_actual;
    private String status;
	
}