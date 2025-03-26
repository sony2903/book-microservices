package com.microservices.services.model.Response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMdlTransactions {
    // @JsonIgnore
    public static String SUCCESS = "Success";

    private String code;
    private String message;
    private List<Transactions> data;

}
