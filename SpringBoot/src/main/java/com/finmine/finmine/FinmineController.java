package com.finmine.finmine;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class FinmineController {
    private final FinmineService finmineService;


    // TODO: CODE REFORMATTING FOR GITHUB
    @GetMapping("/finmine")
    public @ResponseBody
    Iterable<Finmine> Finmine() {
        return finmineService.Finmine();
    }


    @GetMapping("/finmine/update")
    public String FinmineUpdate() {
        return finmineService.FinmineUpdate();
    }
}