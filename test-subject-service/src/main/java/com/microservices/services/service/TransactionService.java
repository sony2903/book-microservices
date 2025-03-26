package com.microservices.services.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.microservices.services.model.Transactions;
import com.microservices.services.repo.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository repo;

    public Transactions save(Transactions req) {
        return repo.save(req);
    }

    public Transactions get(Long Id) {
        return repo.findByIdTrx(Id);
    }

    public Transactions softDelete(Long code){
        Transactions data = repo.findById(code).orElse(null);
        if(data != null && data.getDelete_flag() == 0){
            data.setDelete_flag(1);
            data.setDelete_date(new Date());
            data.setActive_flg(0);
            repo.save(data);
            return data;
        } else {
            return null;
        }
    }

    public Long subjectNextVal(){
        return repo.subjectNextVal();
    }
    
    public Page<Transactions> Pagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repo.findAll(pageRequest);
    }

    public List<Transactions> getByUserId(Long id) {
        return repo.findByUserId(id);
    }
    
}
