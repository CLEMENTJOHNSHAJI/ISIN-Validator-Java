package tests;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.servlet.http.*;


import net.codejava.javaee.*;
public class Testclass extends Mockito{

	
	HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class); 
    HelloServlet myServlet =new HelloServlet();
    StringWriter code = new StringWriter();
    PrintWriter msg = new PrintWriter(code); 
    
   
	@Test
	public void nullValueSuccessTest() throws Exception{// ISIN value empty check success case
		
		 when(request.getParameter("inputCode")).thenReturn(""); 
	        when(response.getWriter()).thenReturn(msg);
	 
	        myServlet.doPost(request, response);
	        String result = code.getBuffer().toString().trim();
	        assertTrue(result.equals("<h1>Please enter a value</h1>") );
	       
	}

	@Test
	public void nullValueFailureTest() throws Exception {// ISIN value empty check failure case
		
		when(request.getParameter("inputCode")).thenReturn("fgrt");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertFalse(result.equals("<h1>Please enter a value</h1>") );
        
		
	}

	
	@Test
	public void ISINLengthSuccessTest() throws Exception{// ISIN length check success case
		
		    when(request.getParameter("inputCode")).thenReturn("AB7565");
	        when(response.getWriter()).thenReturn(msg);
	 
	        myServlet.doPost(request, response);
	        String result = code.getBuffer().toString().trim();
	        assertTrue(result.equals("<h1>ISIN Code should have 12 characters</h1>") );
	}
	
	@Test
	public void ISINLengthFailureTest() throws Exception{// ISIN length check failure case
		
		when(request.getParameter("inputCode")).thenReturn("AB7565980823");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertFalse(result.equals("<h1>ISIN Code should have 12 characters</h1>") );
        
	}
	
	@Test
	public void ISINcountryCodeSuccessTest() throws Exception {// ISIN format check for country code success case
		
		when(request.getParameter("inputCode")).thenReturn("123456789191");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertTrue(result.equals("<h1>The first two characters should be a country code</h1>") );
		
	}
	
	@Test
	public void ISINcountryCodeFailureTest() throws Exception {// ISIN format check for country code failure case
		
		when(request.getParameter("inputCode")).thenReturn("AU0000XVGZA3");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertFalse(result.equals("<h1>The first two characters should be a country code</h1>") );
		
	}
	
	
	@Test
	public void ISINcountryCodeWithSpecialCharInputSuccessTest() throws Exception{//ISIN format check for country code with special character success case with spaces
		
		when(request.getParameter("inputCode")).thenReturn("$*0373831005");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertTrue(result.equals("<h1>The first two characters should be a country code</h1>") );
		
	}
	
	@Test
	public void ISINDigitCheckSuccessTest() throws Exception{// ISIN format check for last check digit success case
		when(request.getParameter("inputCode")).thenReturn("US345678919a");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertTrue(result.equals("<h1>The last character should be a check digit</h1>") );
       
	}
	
	@Test
	public void ISINDigitCheckFailureTest() throws Exception{// ISIN format check for last check digit failure case
		when(request.getParameter("inputCode")).thenReturn("US3456789197");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertFalse(result.equals("<h1>The last character should be a check digit</h1>") );
      
	}
	
	
	
	@Test
	public void ISINDigitCheckwithSpecialCharSuccessTest() throws Exception{// ISIN format check for last check digit with special character success case
		when(request.getParameter("inputCode")).thenReturn("US345678919$");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertTrue(result.equals("<h1>The last character should be a check digit</h1>") );
      
	}
	
	@Test
	public void ISINFormatSuccessTest() throws Exception{//  ISIN general structure check success case
		
		when(request.getParameter("inputCode")).thenReturn("US03.8331092");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        //System.out.print("bashdjs"+ result);
        assertTrue(result.equals("<h1>Enter valid ISIN Format</h1>") );
      
	}
	
	@Test
	public void ISINFormatWithSpaceSuccessTest() throws Exception{//  ISIN general structure with spaces check success case
		
		when(request.getParameter("inputCode")).thenReturn("US03   31092");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertTrue(result.equals("<h1>Enter valid ISIN Format</h1>") );
        
	}
	
	@Test
	public void ISINFormatWithSpecialCharSuccessTest() throws Exception{//  ISIN general structure with special character check success case
		
		when(request.getParameter("inputCode")).thenReturn("US03$5^83092");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertTrue(result.equals("<h1>Enter valid ISIN Format</h1>") );
       
	}
	
	@Test
	public void ISINFormatFailureTest() throws Exception{//  ISIN general structure check failure case
		when(request.getParameter("inputCode")).thenReturn("US0368331092");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertFalse(result.equals("<h1>Enter valid ISIN Format</h1>") );
        
	}
	
	 @Test
	    public void testServletSuccess() throws Exception { 
	       
	        when(request.getParameter("inputCode")).thenReturn("US0378331005");
	        when(response.getWriter()).thenReturn(msg);
	 
	        myServlet.doPost(request, response);
	        String result = code.getBuffer().toString().trim();
	        assertTrue(result.equals("<h1>US0378331005 is Valid</h1>") );
	      
	    }
	    
	
	@Test
	public void ISINInvalidTest() throws Exception{//  ISIN invalid code check
		
		when(request.getParameter("inputCode")).thenReturn("US0373831005");
        when(response.getWriter()).thenReturn(msg);
 
        myServlet.doPost(request, response);
        String result = code.getBuffer().toString().trim();
        assertFalse(result.equals("<h1>US0373831005  is Invalid</h1>") );
        
	}
	 
	 
}
