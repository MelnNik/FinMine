package com.finmine.finmine;

import com.finmine.fastapi.FastAPIService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class FinmineService {

    private final FastAPIService fastAPIService;
    private final FinmineRepository finmineRepository;
    private final FinmineTickersRepository finmineTickersRepository;

    Iterable<Finmine> Finmine() {
        return finmineRepository.findAll();
    }

    public String FinmineUpdate() {
        String tickersList =
                Objects.requireNonNull(fastAPIService.localApiClient().get().uri("/finmine/").retrieve().bodyToMono(String.class).block());
        JSONArray tickersArray = new JSONArray(tickersList);
        final int n = tickersArray.length();
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
