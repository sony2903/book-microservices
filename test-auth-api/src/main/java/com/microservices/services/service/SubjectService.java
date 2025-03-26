package com.microservices.services.service;

import com.microservices.services.model.Mst_Subject;
import com.microservices.services.model.Mst_User;
import com.microservices.services.model.ResponseMdl;
import com.microservices.services.model.ResponseMdlPagination;
import com.microservices.services.model.Request.CreateSubjectRequest;
import com.microservices.services.model.Request.CreateUserRequest;
import com.microservices.services.model.Request.RequestTransaction;
import com.microservices.services.model.Request.SubjectPaginationRequest;
import com.microservices.services.model.Request.TransactionsPaginationRequest;
import com.microservices.services.model.Response.CustomPage;
import com.microservices.services.model.Response.ResponseMdlSubjectPagination;
import com.microservices.services.model.Response.SubjectPaginationResponse;
import com.microservices.services.model.Response.Transactions;
import com.microservices.services.model.Response.TransactionsPaginationResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SubjectService {

    @Value("${api.subject.service}")
    private String SERVER_SUBJECT_SERVICE;

    HttpHeaders headers;

    public Mst_Subject create(CreateSubjectRequest request){
        String endpoint = SERVER_SUBJECT_SERVICE;
        endpoint = endpoint + "/subject";
        RestTemplate restTemplate = new RestTemplate();
        Mst_Subject responseEntity = restTemplate.postForObject(endpoint, request, Mst_Subject.class);
        return responseEntity;
    }

    public CustomPage<Mst_Subject> pagination(SubjectPaginationRequest request) {
        String endpoint = SERVER_SUBJECT_SERVICE + "/subject/all";
        RestTemplate restTemplate = new RestTemplate();
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endpoint)
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize());

        @SuppressWarnings("unchecked")
        CustomPage<Mst_Subject> customPage = restTemplate.getForObject(builder.toUriString(), CustomPage.class);        
        return customPage;
    }

    public Transactions createTransaction(RequestTransaction request){
        String endpoint = SERVER_SUBJECT_SERVICE;
        endpoint = endpoint + "/transaction";
        RestTemplate restTemplate = new RestTemplate();
        Transactions responseEntity = restTemplate.postForObject(endpoint, request, Transactions.class);
        return responseEntity;
    }

    public List<Transactions> getTransactionsByUser(Long user_id){
        String endpoint = SERVER_SUBJECT_SERVICE;
        endpoint = endpoint + "/transaction/user?id=" + user_id;
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endpoint);
        ResponseEntity<List<Transactions>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Transactions>>() {});
        return responseEntity.getBody();
    }

    public Transactions getTransactionByIdTransactions(Long id){
        String endpoint = SERVER_SUBJECT_SERVICE;
        endpoint = endpoint + "/transaction?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        Transactions responseEntity = restTemplate.getForObject(endpoint, Transactions.class);
        return responseEntity;
    }

    public CustomPage<Transactions> paginationTransactions(TransactionsPaginationRequest request) {
        String endpoint = SERVER_SUBJECT_SERVICE + "/transaction/all";
        RestTemplate restTemplate = new RestTemplate();
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endpoint)
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize());

        @SuppressWarnings("unchecked")
        CustomPage<Transactions> customPage = restTemplate.getForObject(builder.toUriString(), CustomPage.class);        
        return customPage;
    }

}

