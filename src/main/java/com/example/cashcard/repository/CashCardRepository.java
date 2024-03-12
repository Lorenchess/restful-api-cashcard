package com.example.cashcard.repository;

import com.example.cashcard.entity.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {
    CashCard findByCashCardIdAndOwner(Long id, String owner);
    Page<CashCard>findByOwner(String owner, PageRequest pageRequest);

}
