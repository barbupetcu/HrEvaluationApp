package com.facultate.disertatie.BO;

import java.time.LocalDateTime;

public class IterationReport {

    private long ID_TASK;
    private LocalDateTime DATA_CREERI;
    private String USER;
    private Integer USER_LEVEL;
    private String TITLU;
    private String DIFICULTATE;
    private String STATUS;
    private LocalDateTime DEADLINE;
    private String PRIORITATE;
    private Integer POINTS;
    private String END_STATUS;

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

    public String getPRIORITATE() {
        return PRIORITATE;
    }

    public void setPRIORITATE(String PRIORITATE) {
        this.PRIORITATE = PRIORITATE;
    }

    public Integer getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(Integer POINTS) {
        this.POINTS = POINTS;
    }

    public String getEND_STATUS() {
        return END_STATUS;
    }

    public void setEND_STATUS(String END_STATUS) {
        this.END_STATUS = END_STATUS;
    }

    public Integer getUSER_LEVEL() {
        return USER_LEVEL;
    }

    public void setUSER_LEVEL(Integer USER_LEVEL) {
        this.USER_LEVEL = USER_LEVEL;
    }

    public IterationReport(long ID_TASK, LocalDateTime DATA_CREERI, String USER, Integer USER_LEVEL, String TITLU, String DIFICULTATE, String STATUS, LocalDateTime DEADLINE, String PRIORITATE, Integer POINTS, String END_STATUS) {
        this.ID_TASK = ID_TASK;
        this.DATA_CREERI = DATA_CREERI;
        this.USER = USER;
        this.USER_LEVEL = USER_LEVEL;
        this.TITLU = TITLU;
        this.DIFICULTATE = DIFICULTATE;
        this.STATUS = STATUS;
        this.DEADLINE = DEADLINE;
        this.PRIORITATE = PRIORITATE;
        this.POINTS = POINTS;
        this.END_STATUS = END_STATUS;
    }
}
