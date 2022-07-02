package ru.ilkras.budcat.utilities;

import ru.ilkras.budcat.BudcatApplication;

public class UUrlGen {
    String data;

    public UUrlGen(long Id) {
        data = BudcatApplication.NOE.baseUrl + "/u/" + Id;
    }

    public String toString() {
        return data;
    }
}
