package org.springframework.samples.mvc.views;

import org.apache.logging.log4j.core.lookup.MainMapLookup;

public class TotalObject {
	public Double totalCount = 0d;
	public Double totalScore = 0d;
	public Double avg = 0d;
	
	
	public double getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}


	public double getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}


	public double getAvg() {
		return avg;
	}


	public void setAvg(double avg) {
		this.avg = avg;
	}


	public void getAvgScore() {
		if(totalCount == 0) {
			avg = 0d;
		}else {
			avg = totalScore/totalCount;
		}
		
	}
	
}
