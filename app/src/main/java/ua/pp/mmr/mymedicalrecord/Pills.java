package ua.pp.mmr.mymedicalrecord;

/**
 * Класс для хранения информации о препарате
 * Created by Uliana on 28.05.2015.
 */
public class Pills {
    public static final int DOSAGE_ML = 1;
    public static final int DOSAGE_MG = 2;
    public static final int DOSAGE_PILL = 3;

    private String name;
    private String annotation;
    private int periodOfReceipt;
    private int dosageType;
    private double dosage;

    Pills(String name, String annotation) {
        this.name = name;
        this.annotation = (annotation == null) ? "" : annotation;
    }

    public void setPeriodOfReceipt(int periodOfReceipt) {
        this.periodOfReceipt = periodOfReceipt;
    }

    public void setDosageType(int dosageType) {
        this.dosageType = dosageType;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }
}
