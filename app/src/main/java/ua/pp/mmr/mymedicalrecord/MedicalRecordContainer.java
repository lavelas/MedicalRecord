package ua.pp.mmr.mymedicalrecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitin on 18.06.15. MTaxi
 */
public class MedicalRecordContainer {
    private Disease disease;
    private ArrayList<Pills> pills = new ArrayList<Pills>();

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public void addPill (Pills pill){
        pills.add(pill);
    }

    public int getCountPills() {
        return pills.size();
    }

    public ArrayList<Pills> getPills() {
        return pills;
    }
}
