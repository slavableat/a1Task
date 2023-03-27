package com.example.demo.dto;

import com.example.demo.model.Account;
import com.example.demo.model.Item;
import com.example.demo.model.Posting;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostingMapper {
    private final AccountRepository accountRepository;
    private final ItemMapper itemMapper;
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d.MM.uuuu");

    @Autowired
    public PostingMapper(AccountRepository accountRepository, ItemMapper itemMapper) {
        this.accountRepository = accountRepository;
        this.itemMapper = itemMapper;
    }

    public Posting postingFromDTO(PostingDTO postingDTO) {
        Posting posting = new Posting();
        posting.setMatDoc(postingDTO.getMatDoc());
        Optional<Account> account = accountRepository.findByUsername(postingDTO.getAccountName());
        if (account.isPresent()) {
            posting.setAccount(account.get());
        } else posting.setAccount(new Account(null, postingDTO.getAccountName()));
        posting.setDocDate(LocalDate.parse(postingDTO.getDocDate(), formatters));
        posting.setPostingDate(LocalDate.parse(postingDTO.getPostingDate(), formatters));
        posting.setIsAuthorized(postingDTO.getIsAuthorized());

        posting.setItems(postingDTO.getItems().stream().map(itemDTO -> {
            Item item = itemMapper.itemFromDTO(itemDTO);
            item.setPosting(posting);
            return item;
        }).collect(Collectors.toList()));
        return posting;
    }

    public PostingDTO postingDtoFromPosting(Posting posting) {
        PostingDTO postingDTO = new PostingDTO();
        postingDTO.setAccountName(posting.getAccount().getUsername());
        postingDTO.setMatDoc(posting.getMatDoc());
        postingDTO.setIsAuthorized(posting.getIsAuthorized());
        postingDTO.setDocDate(posting.getDocDate().format(formatters));
        postingDTO.setPostingDate(posting.getPostingDate().format(formatters));
        postingDTO.setItems(
                posting.getItems().stream()
                        .map(itemMapper::itemDtoFromItem)
                        .collect(Collectors.toList())
        );
        return postingDTO;
    }
}
