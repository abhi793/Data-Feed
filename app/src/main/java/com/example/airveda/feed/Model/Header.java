package com.example.airveda.feed.Model;

/**
 * Created by airveda on 04/08/17.
 */

public class Header  extends ListData{
    private String dateString;

    public Header() {
    }

    public String getDateString() {

        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
