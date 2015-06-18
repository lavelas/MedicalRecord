package ua.pp.mmr.mymedicalrecord;

import android.content.Context;

import java.util.List;
import java.util.zip.DataFormatException;

public interface IDatabaseHandler {
    public int addDisease(Disease disease);
    public Disease getDisease(int id);
    public List<Disease> getAllDiseases(int status);
}