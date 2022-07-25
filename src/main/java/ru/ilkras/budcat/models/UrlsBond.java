package ru.ilkras.budcat.models;

import ru.ilkras.budcat.BudcatApplication;

public class UrlsBond {
    private String origin;
    private String shortened;

    public UrlsBond(String origin, Long id) {
        this.origin = origin;
        // FIXME: Использование глобальной переменной, я бы формировал полный URL там где ты создаёшь инстанс этого класса.
        this.shortened = BudcatApplication.NOE.getBaseUrl() + "/u/" + id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getShortened() {
        return shortened;
    }
}
