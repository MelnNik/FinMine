package com.finmine.finmine;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finmine.fastapi.FastAPIService;
import com.finmine.utilities.GetStockPrice;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.google.gson.*;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.*;

@RestController
@AllArgsConstructor
public class FinmineController {
    private final FastAPIService fastAPIService;
    private final FinmineRepository finmineRepository;


    // TODO: Main view for investors
    @GetMapping("/finmine")
    public @ResponseBody
    Iterable<Finmine> Investors() {
        return finmineRepository.findAll();
    }


    // TODO: View by investor
    @GetMapping("/finmine/update")
    public String InvestorsBy() {
        Gson gson = new Gson();
        String tickersList =
                Objects.requireNonNull(fastAPIService.localApiClient().get().uri("/finmine/").retrieve().bodyToMono(String.class).block())
                        .replaceAll("[\\\\+^\\[\\]\s\n]", "");

        tickersList = tickersList.substring(1, tickersList.length() - 1);

        JsonElement stu = gson.fromJson(tickersList, JsonElement.class);
        System.out.println(stu);

        return tickersList;
    }
}