package base.models;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import base.enums.Status;
import base.helpers.DateTimeHelper;

public class Suite {
	private final Logger log = Logger.getLogger(Suite.class);
	
	private ArrayList<Test> tests;
	private String name;
	private Status status;
	private String started;
	private String ended;
	private long duration;
	private int numTestRuns = 0;
	private float numPassed = 0;
	
	public Suite(){
		started = DateTimeHelper.getCurrentDateTime("Y-M-d_kkmmss");
		tests = new ArrayList<Test>();
		log.info("Suite Created: " + started);
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Test startTest(){
		Test test = new Test();
		test.setStatus(Status.PASSED);//default
		this.tests.add(test);
		return test;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Test getCurrentTest(){
		return this.tests.get(this.tests.size()-1);
	}

	public int getNumTestRuns() {
		return numTestRuns;
	}

	public void setNumTestRuns(int numRuns) {
		this.numTestRuns = numRuns;
	}

	public float getNumPassed() {
		return numPassed;
	}

	public void setNumPassed(float passed) {
		this.numPassed = passed;
	}

}