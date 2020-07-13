package base.models;

import java.util.ArrayList;
import java.util.Date;

public class Test {
	private ArrayList<Step> steps;
	private Status status;
	private Date start;
	public ArrayList<Step> getSteps() {
		return steps;
	}
	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
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
	private Date end;
	private long duration;
}
