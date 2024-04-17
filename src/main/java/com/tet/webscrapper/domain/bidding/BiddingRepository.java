package com.tet.webscrapper.domain.bidding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BiddingRepository extends JpaRepository<Bid, Long> {
    boolean existsByNumber(String number);

    Optional<Bid> findByNumber(String number);
}
