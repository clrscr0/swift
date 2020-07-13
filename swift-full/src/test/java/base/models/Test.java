package base.models;

import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import base.enums.Status;
import base.helpers.DateTimeHelper;

public class Test {
	private ArrayList<Step> steps;
	private String name;
	private Status status;
	private String started;
	private String ended;
	private long duration;
	private ArrayList<Run> runs;
	private Row header;
	private int numPassed = 0;
	private int numFailed = 0;
	private int numSkipped = 0;
	
	public int getNumPassed() {
		return numPassed;
	}

	public void setNumPassed(int numPassed) {
		this.numPassed = this.numPassed + numPassed;
	}

	public int getNumFailed() {
		return numFailed;
	}

	public void setNumFailed(int numFailed) {
		this.numFailed = this.numFailed + numFailed;
	}

	public int getNumSkipped() {
		return numSkipped;
	}

	public void setNumSkipped(int numSkipped) {
		this.numSkipped = this.numSkipped + numSkipped;
	}

	public Test(){
		this.runs = new ArrayList<Run>();
	}
	
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
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public ArrayList<Run> getRuns() {
		return runs;
	}
	public void setRuns(ArrayList<Run> runs) {
		this.runs = runs;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void startRun(Map<String, String> input) throws Exception{
		Run run = new Run();
		run.setStatus(Status.PASSED); //default status
		run.merge(input);
		this.runs.add(run);
		
		this.getCurrentRun().set("last_run", DateTimeHelper.getCurrentDateTime("Y-M-d kk:mm:ss"));
	}
	public String getStarted() {
		return started;
	}
	public void setStarted(String started) {
		this.started = started;
	}
	public String getEnded() {
		return ended;
	}
	public void setEnded(String ended) {
		this.ended = ended;
	}

	public Run getCurrentRun() {
		return this.runs.get(this.runs.size()-1);
	}

	public Row getHeader() {
		return header;
	}

	public void setHeader(Row header) {
		this.header = header;
	}
}
