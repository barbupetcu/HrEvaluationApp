package com.facultate.disertatie.BO;

import java.time.LocalDateTime;

public class TaskReport {

    private long ID_TASK;
    private LocalDateTime DATA_CREERI;
    private String USER;
    private String TITLU;
    private String DIFICULTATE;
    private LocalDateTime ITERATIE_START;
    private LocalDateTime ITERATIE_END;
    private String STATUS;
    private LocalDateTime DEADLINE;
    private String PRIORITATE;
    public boolean validate;

    public long getID_TASK() {
        return ID_TASK;
    }

    public void setID_TASK(long ID_TASK) {
        this.ID_TASK = ID_TASK;
    }

    public LocalDateTime getDATA_CREERI() {
        return DATA_CREERI;
    }

    public void setDATA_CREERI(LocalDateTime DATA_CREERI) {
        this.DATA_CREERI = DATA_CREERI;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getTITLU() {
        return TITLU;
    }

    public void setTITLU(String TITLU) {
        this.TITLU = TITLU;
    }

    public String getDIFICULTATE() {
        return DIFICULTATE;
    }

    public void setDIFICULTATE(String DIFICULTATE) {
        this.DIFICULTATE = DIFICULTATE;
    }

    public LocalDateTime getITERATIE_START() {
        return ITERATIE_START;
    }

    public void setITERATIE_START(LocalDateTime ITERATIE_START) {
        this.ITERATIE_START = ITERATIE_START;
    }

    public LocalDateTime getITERATIE_END() {
        return ITERATIE_END;
    }

    public void setITERATIE_END(LocalDateTime ITERATIE_END) {
        this.ITERATIE_END = ITERATIE_END;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public LocalDateTime getDEADLINE() {
        return DEADLINE;
    }

    public void setDEADLINE(LocalDateTime DEADLINE) {
        this.DEADLINE = DEADLINE;
    }

    public boolean getValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getPRIORITATE() {
        return PRIORITATE;
    }

    public void setPRIORITATE(String PRIORITATE) {
        this.PRIORITATE = PRIORITATE;
    }

    public TaskReport(long ID_TASK, LocalDateTime DATA_CREERI, String USER, String TITLU, String DIFICULTATE, LocalDateTime ITERATIE_START, LocalDateTime ITERATIE_END, String STATUS, LocalDateTime DEADLINE, String PRIORITATE, Boolean validate) {
        this.ID_TASK = ID_TASK;
        this.DATA_CREERI = DATA_CREERI;
        this.USER = USER;
        this.TITLU = TITLU;
        this.DIFICULTATE = DIFICULTATE;
        this.ITERATIE_START = ITERATIE_START;
        this.ITERATIE_END = ITERATIE_END;
        this.STATUS = STATUS;
        this.DEADLINE = DEADLINE;
        this.PRIORITATE= PRIORITATE;
        this.validate = validate;
    }
}
