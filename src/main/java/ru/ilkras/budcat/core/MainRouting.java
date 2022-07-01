package ru.ilkras.budcat.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ilkras.budcat.BudcatApplication;
import ru.ilkras.budcat.models.UrlsBond;

import java.util.HashMap;

@RestController
public class MainRouting {
    private final HashMap<String, Integer> shortener = new HashMap<>(){{put("fuck", 228);}};
    private final HashMap<Integer, String> longerer = new HashMap<>(){{put(228, "fuck");}};

    @GetMapping("url/expand")
    public UrlsBond expandUrl(@RequestParam("id") Integer id) {
        if (longerer.containsKey(id))
            return new UrlsBond(longerer.get(id), BudcatApplication.NOE.baseUrl + "/u/" + id);
        throw new NullPointerException();
    }

}
