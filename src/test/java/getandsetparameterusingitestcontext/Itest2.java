package getandsetparameterusingitestcontext;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class Itest2 {


	@Test(dependsOnMethods = "getandsetparameterusingitestcontext.Itest1.createBooking")
	public void updateBooking(ITestContext c) {
		//Fetch booking id using ItestContext interface method getAttribute
		int bookingId = (Integer) c.getAttribute("bookingId");
		System.out.println(bookingId);
	}

}
