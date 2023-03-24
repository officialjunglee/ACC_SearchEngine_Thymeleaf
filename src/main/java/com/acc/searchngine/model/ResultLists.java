package com.acc.searchngine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ResultLists {
    public String blob;
    public String domain;
    public String url;
    public String occurances;

    public String getBlob() {
        return blob;
    }

    public String getDomain() {
        return domain;
    }

    public String getUrl() {
        return url;
    }

    public String getOccurances() {
        return occurances;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOccurances(String occurances) {
        this.occurances = occurances;
    }
}
