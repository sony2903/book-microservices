package com.microservices.services.model.Request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.microservices.services.helper.ValidEmailDomain;
import com.microservices.services.model.Mst_User;
import com.microservices.services.model.Mst_User.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest extends BaseRequest{
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Role is required")
    private UserRole role; // You can use enum here if needed
    
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$", message = "Password must contain at least one capital letter and one number, and only alphanumeric characters")
    private String password;

    @NotBlank(message = "Email is required")
    @ValidEmailDomain(allowedDomains = {"google.com", "hotmail.com"}, message = "Email must be from google.com or hotmail.com")
    private String email;

	
}
