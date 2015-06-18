package ua.pp.mmr.mymedicalrecord;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Date;

/**
 * Класс для хранения информации о заболевании
 * Created by Uliana on 28.05.2015.
 */
public class Disease {
    private String name;
    private ArrayList<Pills> pills;
    private String annotation;
    private Date startDate;
    private int status = 1;

    Disease(String name, String annotation) {
        this.name = name;
        this.annotation = (annotation == null) ? "Нет данных" : annotation;
    }

    Disease(String name) {
        this.name = name;
        this.annotation = "";
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setPills(ArrayList<Pills> pills) {
        this.pills = pills;
    }

    public String getName() {
        return name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
