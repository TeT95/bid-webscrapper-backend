package com.tet.webscrapper.domain.bidding;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String agency;
    private String number;
    @Column(columnDefinition = "TEXT")
    private String object;
    @Column(columnDefinition = "TEXT")
    private String publication;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String telephone;
    private String proposalDelivery;

    @Column(name = "isRead", nullable = false, columnDefinition = "boolean default false")
    private boolean isRead = false;

}
