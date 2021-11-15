package com.finmine.finmine;

import com.finmine.fastapi.FastAPIService;
import com.finmine.utilities.GetStockPrice;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class FinmineController {
    private final FastAPIService fastAPIService;
    private final FinmineRepository finmineRepository;
    private final FinmineTickersRepository finmineTickersRepository;


    // TODO: Main view for investors
    @GetMapping("/finmine")
    public @ResponseBody
    Iterable<Finmine> Investors() {
        return finmineRepository.findAll();
    }


    // TODO: View by investor
    @GetMapping("/investors/{investor}")
    public String InvestorsBy(@PathVariable String investor) {
        String tickersList =
                Objects.requireNonNull(fastAPIService.localApiClient().get().uri("/works/" + investor).retrieve().bodyToMono(String.class).block())
                        .replaceAll("[-+.^:\"\\[\\]]", "");
        String[] data = tickersList.split(",");

        Finmine finmine = finmineRepository.findByName(investor);
        try {
            List<FinmineTickers> currentInvestorList = finmine.getFinmineTickers();
            Set<String> tickersCurrentlyIn = new HashSet<>();
            for (FinmineTickers investor1 : currentInvestorList) {
                tickersCurrentlyIn.add(investor1.getTicker());
            }
            Set<String> incomingTickers = new HashSet<>(Arrays.asList(data));
            if (!tickersCurrentlyIn.equals(incomingTickers)) {

                Set<String> copyTickersCurrentlyIn = new HashSet<>(tickersCurrentlyIn);
                Set<String> copyIncomingTickers = new HashSet<>(incomingTickers);
                copyIncomingTickers.removeAll(tickersCurrentlyIn);
                copyTickersCurrentlyIn.removeAll(incomingTickers);
                for (String str : copyTickersCurrentlyIn) {
                    Double currentPrice = GetStockPrice.getCurrentPrice(str);
                    Double boughtPrice = finmineTickersRepository.findInvestorsTickersByTicker(str).getBuyPrice();
                    finmine.setPerformance(finmine.getPerformance() + ((currentPrice - boughtPrice) / boughtPrice) * 100);
                    finmineRepository.save(finmine);
                    finmineTickersRepository.deleteById(finmineTickersRepository.findInvestorsTickersByTicker(str).getId());
                }
                for (String str : copyIncomingTickers) {
                    Double currentPrice = GetStockPrice.getCurrentPrice(str);
                    finmineTickersRepository.save(
                            new FinmineTickers(str, currentPrice, currentPrice, finmineRepository.findByName(investor)));
                }
                return "Updated";
            }
            return "No changes";
        } catch (NullPointerException nullPointerException) {
            Finmine finmine1 = new Finmine(investor);
            finmine1.setName(investor);
            finmineRepository.save(finmine1);
            for (String str : data) {
                // TODO: Get price
                Double currentPrice = GetStockPrice.getCurrentPrice(str);
                finmineTickersRepository.save(new FinmineTickers(str, currentPrice, currentPrice, finmine1));
            }
            return Arrays.toString(data);
        }
    }
}