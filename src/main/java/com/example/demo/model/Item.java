package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "item")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
//    @JsonView(Views.Full.class)
    @ManyToOne(targetEntity = Posting.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_posting", nullable = false)
    private Posting posting;
    private Long quantity;
    private String BUn;
    private String Crcy;
    private Double amountLC;
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;
}
