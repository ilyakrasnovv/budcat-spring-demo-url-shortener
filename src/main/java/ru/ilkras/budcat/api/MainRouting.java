package ru.ilkras.budcat.api;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ilkras.budcat.models.UrlsBond;
import ru.ilkras.budcat.utilities.DoubleMap;
import ru.ilkras.budcat.utilities.UUrlGen;

import java.util.HashMap;

@RestController
public class MainRouting {
    DoubleMap<String, Integer> tryDMap = new DoubleMap<>(new HashMap<>(), new HashMap<>(),
            (DoubleMap.OnAdd<String, Integer>) ((String s, Integer i) -> {
                LoggerFactory.getLogger(MainRouting.class).info(s + i);
            }));
    // TODO -- implement DataController and move this map there

    @GetMapping("url/expand")
    public UrlsBond expandUrl(@RequestParam("id") Integer id) {
        if (tryDMap.rget(id) == null)
            throw new RuntimeException("Cannot find shortened url with id " + id);
        return new UrlsBond(tryDMap.rget(id), new UUrlGen(id).toString());
    }
}
