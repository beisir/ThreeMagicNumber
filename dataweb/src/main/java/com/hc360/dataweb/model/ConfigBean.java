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
    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
