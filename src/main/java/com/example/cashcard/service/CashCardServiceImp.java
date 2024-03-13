package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.entity.CashCard;
import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.exception.PrincipalForbiddenException;
import com.example.cashcard.mapper.SimpleMapper;
import com.example.cashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public CashCardDTO findCashCardByIdAndOwner(Long id, Principal owner) throws CashCardNotFoundException {
        CashCard cashCard = repository.findByCashCardIdAndOwner(id, owner.getName());

        if(cashCard != null){
            return mapper.entityToDTO(cashCard);
        } else {
            throw new CashCardNotFoundException(String.format("CashCard not found for id: %s", id));
        }
    }

    @Override
    public CashCardDTO saveCashCard(CashCardDTO cashCardDTO, Principal principal) throws PrincipalForbiddenException {
        if (principal.getName().equals(cashCardDTO.getOwner())){
            CashCard cashCard = mapper.dtoToEntity(cashCardDTO);
            CashCard savedCashCard = repository.save(cashCard);
            return mapper.entityToDTO(savedCashCard);
        } else {
            throw new PrincipalForbiddenException("You are not authorized to perform this action.");
        }

    }

    @Override
    public List<CashCardDTO> findAllCashCards(Principal principal, Pageable pageable) {
        List<CashCard> cashCardPage = repository.findByOwner( principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                )).getContent();
        return cashCardPage.stream().map(mapper::entityToDTO).toList();
    }

    @Override
    public void updateCashCard(Long requestId, CashCardDTO cashCardDTO, Principal principal) throws PrincipalForbiddenException {
        if(principal.getName().equals(cashCardDTO.getOwner())) {
            CashCard cashCard = mapper.dtoToEntity(cashCardDTO);
            CashCard byCashCardIdAndOwner = repository.findByCashCardIdAndOwner(requestId, cashCard.getOwner());
            CashCard updatedCashCard = CashCard.builder()
                    .cashCardId(byCashCardIdAndOwner.getCashCardId())
                    .amount(cashCard.getAmount())
                    .owner(cashCard.getOwner())
                    .build();
            repository.save(updatedCashCard);
        } else {
            throw new PrincipalForbiddenException("You are not authorized to perform this update.");
        }



    }


}
