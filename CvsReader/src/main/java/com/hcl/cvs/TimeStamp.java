package com.hcl.cvs;

import java.sql.Timestamp;

public class TimeStamp {
    private String objectName;
    private int cellId;
    private Timestamp resultTime;
    private double callAttemps;
    private int granularityPeriod;
	
    public TimeStamp(String objectName, int cellId, Timestamp resultTime, double callAttemps, int granularityPeriod) {
		super();
		this.objectName = objectName;
		this.cellId = cellId;
		this.resultTime = resultTime;
		this.callAttemps = callAttemps;
		this.granularityPeriod = granularityPeriod;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public int getCellId() {
		return cellId;
	}
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}
	public Timestamp getResultTime() {
		return resultTime;
	}
	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}
	public double getCallAttemps() {
		return callAttemps;
	}
	public void setCallAttemps(double callAttemps) {
		this.callAttemps = callAttemps;
	}
	public int getGranularityPeriod() {
		return granularityPeriod;
	}
	public void setGranularityPeriod(int granularityPeriod) {
		this.granularityPeriod = granularityPeriod;
	}
    
	
    
}