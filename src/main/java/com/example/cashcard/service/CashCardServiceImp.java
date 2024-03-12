package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.entity.CashCard;
import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.mapper.SimpleMapper;
import com.example.cashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CashCardServiceImp implements CashCardService{

    private final CashCardRepository repository;

    private final SimpleMapper mapper;
    @Autowired
    public CashCardServiceImp(CashCardRepository repository, SimpleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CashCardDTO findCashCardById(Long id) throws CashCardNotFoundException {
        CashCard cashCard = repository.findByCashCardId(id);

        if(cashCard != null){
            return mapper.entityToDTO(cashCard);
        } else {
            throw new CashCardNotFoundException(String.format("CashCard not found for id: %s", id));
        }
    }

    @Override
    public CashCardDTO saveCashCard(CashCardDTO cashCardDTO) {
        CashCard cashCard = mapper.dtoToEntity(cashCardDTO);
        CashCard savedCashCard = repository.save(cashCard);
        return mapper.entityToDTO(savedCashCard);
    }

    @Override
    public List<CashCardDTO> findAllCashCards(Pageable pageable) {
        List<CashCard> cashCardPage = repository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                )).getContent();
        return cashCardPage.stream().map(mapper::entityToDTO).toList();
    }


}
