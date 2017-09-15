package com.hc360.dataweb.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by home on 2016/9/1.
 */
@Service
public class ConfigBean {

    @Value("${emails}")
    private String emails;

    @Override
    public String toString() {
        return "ConfigBean{" +
                "emails='" + emails + '\'' +
                '}';
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
