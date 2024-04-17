package com.tet.webscrapper.domain.bidding;

import com.tet.webscrapper.exceptions.BidNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiddingService {

    @Autowired
    private BiddingRepository biddingRepository;

    public List<Bid> listAll() {
        List<Bid> bids = biddingRepository.findAll();
        return bids;
    }

    public Bid markAsRead(String number) {
        Optional<Bid> bidOpt = biddingRepository.findByNumber(number);

        bidOpt.ifPresentOrElse(bid -> bid.setRead(!bid.isRead()), () -> {
            throw new BidNotFoundException("Licitação não encontrada!");
        });

        biddingRepository.save(bidOpt.get());


        return bidOpt.get();
    }

}
