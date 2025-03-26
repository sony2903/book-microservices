package com.microservices.services.model.Response;

import com.microservices.services.model.BaseStandartModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transactions extends BaseStandartModel {

	private Long id;
    private Long user_id;
    private Long book_id;
    private String transaction_date;
    private String return_date;
    private String return_date_actual;
    private StatusTransaction status;
	
}

 enum StatusTransaction {
    BORROWED, RETURNED
}
