package com.tet.webscrapper.domain.scrapper;

import com.tet.webscrapper.domain.bidding.Bid;

import java.util.List;

public interface Scrapper {

    void scrap();

    void scrapeOnStartup();

}
