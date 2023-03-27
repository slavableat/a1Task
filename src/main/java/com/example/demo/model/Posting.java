package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "postings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Posting {
    @Id
    private Long matDoc;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account")
    private Account account;
    private Boolean isAuthorized;
    @OneToMany(mappedBy = "posting",targetEntity = Item.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
    @Column(name = "doc_date", columnDefinition = "DATE")
    private LocalDate docDate;
    @Column(name = "posting_date", columnDefinition = "DATE")
    private LocalDate postingDate;
}
