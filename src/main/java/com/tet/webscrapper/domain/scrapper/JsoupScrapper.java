package com.tet.webscrapper.domain.scrapper;


import com.tet.webscrapper.domain.bidding.Bid;
import com.tet.webscrapper.domain.bidding.BiddingRepository;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsoupScrapper implements Scrapper {

    @Autowired
    BiddingRepository biddingRepository;

    @Override
    public void scrap() {
        String html = "http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp";
        Document doc = null;
        try {
            doc = Jsoup.connect(html).parser(Parser.xmlParser()).get();
            doc.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
            doc.outputSettings().charset("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements forms = doc.select("form[name^=Form]");

        List<Bid> bids = new ArrayList<>();

        for (Element form : forms) {
            String orgao = form.select("td b").first().text();
            String numero = form.select("tr.mensagem td").first().text();
            String objeto = form.select("td b:containsOwn(Objeto:)").first().nextSibling().toString();
            String edital = form.select("td b:containsOwn(Edital a partir de:)").first().nextSibling().toString();
            String endereco = form.select("td b:containsOwn(Endereço:)").first().nextSibling().toString();
            String telefone = form.select("td b:containsOwn(Telefone:)").first().nextSibling().toString();
            String entregaProposta = form.select("td b:containsOwn(Entrega da Proposta:)").first().nextSibling().toString();

            orgao = orgao.replaceAll("\\s+", " ");
            numero = numero.replaceAll("\\s+", " ");
            objeto = objeto.replaceAll("\\s+", " ").replaceAll("&#xa0;", "").replaceAll("Objeto:", ""); // Remover &#xa0;
            edital = edital.replaceAll("\\s+", " ").replaceAll("&#xa0;", ""); // Remover &#xa0;
            endereco = endereco.replaceAll("\\s+", " ").replaceAll("&#xa0;", ""); // Remover &#xa0;
            telefone = telefone.replaceAll("\\s+", " ").replaceAll("[^0-9]", ""); // Remover caracteres não numéricos
            entregaProposta = entregaProposta.replaceAll("\\s+", " ").replaceAll("&#xa0;", ""); // Remover &#xa0;

            // Remover caracteres indesejados e limpar o número de telefone
            telefone = telefone.replaceAll("\\s+", " ").replaceAll("[^0-9]", ""); // Remover caracteres não numéricos

            // Adicionar DDD e hífen para números com código de área
            if (telefone.startsWith("0") && telefone.length() > 1) {
                String ddd = telefone.substring(2, 4); // Extrair o DDD do número
                telefone = telefone.substring(4); // Remover o DDD do número
                if(telefone.length() >= 8) {
                    telefone = "(" + ddd + ") " + telefone.substring(0, telefone.length() - 4) + "-" + telefone.substring(telefone.length() - 4); // Adicionar DDD e hífen
                } else {
                    telefone = "(" + ddd + ") " + telefone; // Adicionar DDD e hífen

                }
            } else {
                telefone = telefone.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
            }

            Bid licitacao = new Bid(null, orgao, numero, objeto, edital, endereco, telefone, entregaProposta, false);
            bids.add(licitacao);
        }

        List<Bid> bidsToSave = bids.stream().filter(bid ->
                !biddingRepository.existsByNumber(bid.getNumber())
        ).toList();

        biddingRepository.saveAll(bidsToSave);
    }

    @Override
    @PostConstruct
    public void scrapeOnStartup() {
        this.scrap();
    }
}
