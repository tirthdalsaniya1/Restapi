package rest;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Test;


public class Itest1 {

	@Test
	public void createBooking(ITestContext context) {
		
		context.setAttribute("bookingId", 124);
	}

	@Test
	public void updateBooking(ITestContext context) {
		

		//Fetch booking id using ItestContext interface method getAttribute
		int bookingId = (Integer) context.getAttribute("bookingId");
		System.out.println(bookingId);
	}
	
	@Test
	public void getParameter(ITestResult ir) {
		
		String str = ir.getTestContext().getCurrentXmlTest().getParameter("key");
		//Fetch booking id using ItestContext interface method getAttribute
		
	System.out.println(str);
	}

}
