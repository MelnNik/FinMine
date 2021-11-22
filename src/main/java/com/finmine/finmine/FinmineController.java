package com.finmine.finmine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import com.google.gson.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class FinmineController {
    private final FastAPIService fastAPIService;
    private final FinmineRepository finmineRepository;
    private final FinmineTickersRepository finmineTickersRepository;


    @GetMapping("/finmine")
    public @ResponseBody
    Iterable<Finmine> Finmine() {
        return finmineRepository.findAll();
    }


    @GetMapping("/finmine/update")
    public String FinmineBy() {
        String tickersList =
                Objects.requireNonNull(fastAPIService.localApiClient().get().uri("/finmine/").retrieve().bodyToMono(String.class).block());
        JSONArray tickersArray = new JSONArray(tickersList);
        final int n = tickersArray.length();
        // TODO: Update performance on update
        try {
            Finmine finmine = finmineRepository.findByName("main");
            List<FinmineTickers> finmineTickersList = finmine.getFinmineTickers();
            return finmineTickersList.toString();
        } catch (NullPointerException nullPointerException) {
            Finmine finmine = new Finmine("main");
            finmineRepository.save(finmine);
            for (int i = 0; i < n; ++i) {
                final JSONObject ticker = tickersArray.getJSONObject(i);
                finmineTickersRepository.save(
                        new FinmineTickers(ticker.getString("Ticker"), ticker.getDouble("Price"), ticker.getDouble("total_multiplier"), finmine));

            }
        }


        return tickersList;
    }
}