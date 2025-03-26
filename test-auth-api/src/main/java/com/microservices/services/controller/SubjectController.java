package com.microservices.services.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.services.config.JwtUtil;
import com.microservices.services.config.SecurityConfig;
import com.microservices.services.model.Mst_Subject;
import com.microservices.services.model.Mst_User;
import com.microservices.services.model.ResponseMdl;
import com.microservices.services.model.Mst_User.UserRole;
import com.microservices.services.model.Request.CreateSubjectRequest;
import com.microservices.services.model.Request.RequestReturnTransaction;
import com.microservices.services.model.Request.RequestTransaction;
import com.microservices.services.model.Request.SignModel;
import com.microservices.services.model.Request.SubjectPaginationRequest;
import com.microservices.services.model.Request.TransactionsPaginationRequest;
import com.microservices.services.model.Response.CustomPage;
import com.microservices.services.model.Response.ResponseMdlTransactions;
import com.microservices.services.model.Response.SubjectPaginationResponse;
import com.microservices.services.model.Response.Transactions;
import com.microservices.services.model.Response.TransactionsPaginationResponse;
import com.microservices.services.service.SigninService;
import com.microservices.services.service.SubjectService;

import io.micrometer.core.ipc.http.HttpSender.Request;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SigninService signinService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SecurityConfig sc;

    @PostMapping("create")
    public ResponseEntity<?> createSubject(@Validated @RequestBody CreateSubjectRequest requestBody, HttpServletRequest requestHttp) throws Exception {
        
        ResponseMdl res = new ResponseMdl();
        // Check if user exists
        try {
            String user_code = jwtUtil.extractUsername(requestHttp.getHeader("Authorization").substring(7));
            Mst_User user = signinService.find(user_code);
            if(user.getRole().equals(UserRole.STUDENT)){
                res.setCode("400");
                res.setMessage("Student cant create subject");
                return ResponseEntity.status(400).body(res);
            }
            subjectService.create(requestBody);
            res.setMessage("Subject Created");
            return ResponseEntity.status(201).body(res);
            
        } catch (Exception e) {
            // TODO: handle exception
            res.setMessage(e.getLocalizedMessage());
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("all")
    public ResponseEntity<?> GetSubjects(@RequestParam(value = "size") int size, @RequestParam(value = "page") int page, HttpServletRequest requestHttp) throws Exception {
        
        SubjectPaginationResponse res = new SubjectPaginationResponse();
        try {
            SubjectPaginationRequest req = new SubjectPaginationRequest();
            req.setPage(page);
            req.setSize(size);
            CustomPage<Mst_Subject> data = subjectService.pagination(req);
            res.setMessage(ResponseMdl.SUCCESS);
            res.setData(data);
            return ResponseEntity.ok(res);
            
        } catch (Exception e) {
            // TODO: handle exception
            res.setMessage(e.getLocalizedMessage());
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("transaction")
    public ResponseEntity<?> createTransaction(@Validated @RequestBody RequestTransaction requestTransaction, HttpServletRequest requestHttp) throws Exception {
        
        ResponseMdl res = new ResponseMdl();
        // Check if user exists
        try {
            String user_code = jwtUtil.extractUsername(requestHttp.getHeader("Authorization").substring(7));
            Mst_User user = signinService.find(user_code);
            List<Transactions> transactions = (List<Transactions>) subjectService.getTransactionsByUser(user.getId());
            if(transactions.size() > 0){
                res.setCode("400");
                res.setMessage("User already has transaction");
                return ResponseEntity.status(400).body(res);
            }
            requestTransaction.setUser_id(user.getId());
            requestTransaction.setTransaction_date(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            requestTransaction.setStatus("BORROWED");
            subjectService.createTransaction(requestTransaction);
            res.setMessage("Transaction Created");
            return ResponseEntity.status(201).body(res);
            
        } catch (Exception e) {
            // TODO: handle exception
            res.setMessage(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("transaction")
    public ResponseEntity<?> getTransaction(@Validated HttpServletRequest requestHttp) throws Exception {
        
        ResponseMdlTransactions res = new ResponseMdlTransactions();
        // Check if user exists
        try {
            String user_code = jwtUtil.extractUsername(requestHttp.getHeader("Authorization").substring(7));
            Mst_User user = signinService.find(user_code);
            List<Transactions> transactions = (List<Transactions>) subjectService.getTransactionsByUser(user.getId());
            res.setMessage(ResponseMdl.SUCCESS);
            res.setData(transactions);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            // TODO: handle exception
            res.setMessage(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("transaction/all")
    public ResponseEntity<?> getAllTransactions(@Validated @RequestParam(value = "size") int size, @RequestParam(value = "page") int page, HttpServletRequest requestHttp) throws Exception {
        
        TransactionsPaginationResponse res = new TransactionsPaginationResponse();
        try {
            TransactionsPaginationRequest req = new TransactionsPaginationRequest();
            req.setPage(page);
            req.setSize(size);
            CustomPage<Transactions> data = subjectService.paginationTransactions(req);
            res.setMessage(ResponseMdl.SUCCESS);
            res.setData(data);
            return ResponseEntity.ok(res);
            
        } catch (Exception e) {
            // TODO: handle exception
            res.setMessage(e.getLocalizedMessage());
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("transaction/return")
    public ResponseEntity<?> returnTransaction(@Validated @RequestBody RequestReturnTransaction returnTransaction, HttpServletRequest requestHttp) throws Exception {
        
        ResponseMdl res = new ResponseMdl();
        // Check if user exists
        try {
            String user_code = jwtUtil.extractUsername(requestHttp.getHeader("Authorization").substring(7));
            Mst_User user = signinService.find(user_code);
            Transactions transaction = subjectService.getTransactionByIdTransactions(returnTransaction.getId());
            if(transaction == null || !transaction.getUser_id().equals(user.getId())){
                res.setCode("400");
                res.setMessage("Transaction not found");
                return ResponseEntity.status(400).body(res);
            }
            RequestTransaction requestTransaction = new RequestTransaction();
            requestTransaction.setId(transaction.getId());
            requestTransaction.setReturn_date_actual(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            requestTransaction.setStatus("RETURNED");
            requestTransaction.setTransaction_date(transaction.getTransaction_date());
            requestTransaction.setBook_id(transaction.getBook_id());
            requestTransaction.setUser_id(transaction.getUser_id());
            requestTransaction.setReturn_date(transaction.getReturn_date());
            subjectService.createTransaction(requestTransaction);
            res.setMessage("Transaction Returned");
            return ResponseEntity.ok(res);
            
        } catch (Exception e) {
            // TODO: handle exception
            res.setMessage(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }
}

