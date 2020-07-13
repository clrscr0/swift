package base.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestResult;

public enum Status {
	PASSED(ITestResult.SUCCESS), FAILED(ITestResult.FAILURE), SKIPPED(ITestResult.SKIP);

	private int code;

	private static final Map<Integer, Status> lookup = new HashMap<Integer, Status>();

	static {
		for (Status s : EnumSet.allOf(Status.class))
			lookup.put(s.getCode(), s);
	}

	Status(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Status get(int code) {
		return lookup.get(code);
	}
}