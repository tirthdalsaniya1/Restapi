package getandsetparameterusingitestcontext;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class Itest1 {

	@Test
	public void createBooking(ITestContext context) {

		context.setAttribute("bookingId", 124);
	}

	@Test(dependsOnMethods = "createBooking")
	public void updateBooking(ITestContext context) {

		// Fetch booking id using ItestContext interface method getAttribute
		int bookingId = (Integer) context.getAttribute("bookingId");
		System.out.println(bookingId);
	}

	@Test()
	public void getParameter(ITestContext it) {
		//run this using testng other wise it will print null key. We have passed key via itestcontext-testng.xml
		String str = it.getCurrentXmlTest().getParameter("key");
		// Fetch booking id using ItestContext interface method getAttribute
		System.out.println("found that key is " + str + " using getParameter method");
	}

}