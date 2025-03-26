package com.microservices.services.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Transactions")
public class Transactions extends BaseStandartModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_gen")
	private Long id;
    private Long user_id;
    private Long book_id;
    private String transaction_date;
    private String return_date;
    private String return_date_actual;
    private String status;
	
}
