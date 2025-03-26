package com.microservices.services.model.Request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestReturnTransaction {
	
    private long id;

}

 enum StatusTransaction {
    BORROWED, RETURNED
}
