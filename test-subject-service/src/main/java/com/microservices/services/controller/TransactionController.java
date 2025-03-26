package com.microservices.services.controller;

import java.util.List;

import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.services.model.CustomPage;
import com.microservices.services.model.Mst_Subject;
import com.microservices.services.model.ResponseMdl;
import com.microservices.services.model.Transactions;
import com.microservices.services.service.SubjectService;
import com.microservices.services.service.TransactionService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @RequestMapping("/")
    public String index() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }

    // Create
    @PostMapping("")
public Transactions createSubject(@RequestBody Transactions emp) {
    ResponseMdl response = new ResponseMdl();
    boolean isId = emp.getId() == null;
    if (emp.getId() == null) emp.setId(service.subjectNextVal());
    System.out.println(emp);
    Transactions result = service.save(emp);
    return result;
}

    @GetMapping("")
    public Transactions getById(@RequestParam Long id) {
        Transactions Data = service.get(id);
        // ResponseMdl response = new ResponseMdl();
        return Data;
    }

    @GetMapping("/user")
    public List<Transactions> getByUserId(@RequestParam Long id) {
        List<Transactions> Data = service.getByUserId(id);
        // ResponseMdl response = new ResponseMdl();
        return Data;
    }

    @GetMapping("/all")
    public CustomPage<Transactions> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Transactions> data = service.Pagination(page, size);
        CustomPage<Transactions> customPage = new CustomPage<>();
        customPage.setTotalPages(data.getTotalPages());
        customPage.setTotalElements(data.getTotalElements());
        customPage.setNumber(data.getNumber());
        customPage.setSize(data.getSize());
        customPage.setContent(data.getContent());
        return customPage;
    }

    @DeleteMapping("")
    public Transactions deleteByCode(@RequestParam Long code) {
        Transactions data = service.softDelete(code);
        return data;
    }
    
}
