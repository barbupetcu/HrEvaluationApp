package com.facultate.disertatie.BO;

public class HomeReport {

    private Long ID;
    private String NUME;
    private String PRENUME;
    private Integer TOTAL_PUNCTE;
    private Integer NIVEL;
    private Double PROGRES;
    private Integer PUNCTE_PANA_LA_NIVELUL_URMATOR;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNUME() {
        return NUME;
    }

    public void setNUME(String NUME) {
        this.NUME = NUME;
    }

    public String getPRENUME() {
        return PRENUME;
    }

    public void setPRENUME(String PRENUME) {
        this.PRENUME = PRENUME;
    }

    public Integer getTOTAL_PUNCTE() {
        return TOTAL_PUNCTE;
    }

    public void setTOTAL_PUNCTE(Integer TOTAL_PUNCTE) {
        this.TOTAL_PUNCTE = TOTAL_PUNCTE;
    }

    public Integer getNIVEL() {
        return NIVEL;
    }

    public void setNIVEL(Integer NIVEL) {
        this.NIVEL = NIVEL;
    }

    public Double getPROGRES() {
        return PROGRES;
    }

    public void setPROGRES(Double PROGRES) {
        this.PROGRES = PROGRES;
    }

    public Integer getPUNCTE_PANA_LA_NIVELUL_URMATOR() {
        return PUNCTE_PANA_LA_NIVELUL_URMATOR;
    }

    public void setPUNCTE_PANA_LA_NIVELUL_URMATOR(Integer PUNCTE_PANA_LA_NIVELUL_URMATOR) {
        this.PUNCTE_PANA_LA_NIVELUL_URMATOR = PUNCTE_PANA_LA_NIVELUL_URMATOR;
    }

    public HomeReport(Long ID, String NUME, String PRENUME, Integer TOTAL_PUNCTE, Integer NIVEL, Double PROGRES, Integer PUNCTE_PANA_LA_NIVELUL_URMATOR) {
        this.ID = ID;
        this.NUME = NUME;
        this.PRENUME = PRENUME;
        this.TOTAL_PUNCTE = TOTAL_PUNCTE;
        this.NIVEL = NIVEL;
        this.PROGRES = PROGRES;
        this.PUNCTE_PANA_LA_NIVELUL_URMATOR = PUNCTE_PANA_LA_NIVELUL_URMATOR;
    }
}
