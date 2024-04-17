package com.tet.webscrapper.domain.scrapper;

import com.tet.webscrapper.domain.bidding.Bid;
import com.tet.webscrapper.domain.bidding.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bidding")
public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @GetMapping
    public ResponseEntity<List<Bid>> listBiddings() {
        List<Bid> bids = biddingService.listAll();

        if(bids.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(bids);
    }

    @PutMapping("/{number}")
    public ResponseEntity<Bid> listBiddings(@PathVariable("number") String number) {

        return ResponseEntity.ok(biddingService.markAsRead(number));
    }

}
