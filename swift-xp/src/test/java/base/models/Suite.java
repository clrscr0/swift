package base.models;

import java.util.ArrayList;
import java.util.Date;

public class Suite {
	private ArrayList<Test> tests;
	private Status status;
	private Date start;
	private Date end;
	private long duration;
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public ArrayList<Test> getTests() {
		return tests;
	}
	public void setTests(ArrayList<Test> tests) {
		this.tests = tests;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
