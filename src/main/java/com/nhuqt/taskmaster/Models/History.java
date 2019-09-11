package com.nhuqt.taskmaster.Models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class History {
  String date;
  String action;

  public History(){}
  public History(String action){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    this.date = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
    this.action = action;
  }

  public String getDate() {
    return date;
  }

  public String getAction() {
    return action;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
