package com.microservices.services.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservices.services.model.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    @Query(value = "select nextval('transaction_seq')", nativeQuery = true)
    public Long subjectNextVal();

    @Query(value = "SELECT e FROM Transactions e WHERE e.id = ?1 AND e.active_flg = 1")
    public Transactions findByIdTrx(Long id);

    @Query(value = "SELECT e FROM Transactions e WHERE e.user_id = ?1 AND e.active_flg = 1")
    public List<Transactions> findByUserId(Long id);

    // List<Mst_User> findByActive_flg(int active_flg, int offset, int limit, Sort sort);

}
