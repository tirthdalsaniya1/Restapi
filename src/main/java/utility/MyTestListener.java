package utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener {

	public void onStart(ITestContext context) {
		System.out.println("Test started: " + context.getName());
	}

	public void onFinish(ITestContext context) {
		System.out.println("Test finished: " + context.getName());
	}

	public void onTestStart(ITestResult result) {
		System.out.println("Test method started: " + result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Test method succeeded: " + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Test method failed: " + result.getName());
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Test method skipped: " + result.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test method failed but is within success percentage: " + result.getName());
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}
}
