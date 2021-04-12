package net.codejava.javaee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/helloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ISINno = request.getParameter("inputCode"); //Getting response from user
		PrintWriter writer = response.getWriter(); //Servlet response display 
		ISINno = ISINno.trim().toUpperCase();
		
		 //System.out.println(response.getStatus());
		String msg;
		msg= formatCheck(ISINno); // Validation function call
		writer.println("<h1>"+msg+ "</h1>"); // Servlet response display on web
		
		writer.close();
	}

	public static String formatCheck(String ISINno) {
		String msg=null;
		
		if(ISINno.isEmpty()) {// Null value validation
			msg="Please enter a value";
		}
		else if(ISINno.length()!=12) { // Input length validation
			msg="ISIN Code should have 12 characters";
		}
		else if(!ISINno.substring(0,2).matches("^[A-Z]*$")) { // Input country code validation
			msg="The first two characters should be a country code";
		}
		else if(!ISINno.substring(11).matches("^[0-9]*$")) { // Input check digit format validation
			msg="The last character should be a check digit";
		}
		else if (!ISINno.matches("^[A-Z]{2}[A-Z0-9]{9}\\d$")) // General format validation
		{
			msg="Enter valid ISIN Format";
		}
		else
		{
			boolean validation=codeValidator(ISINno); // Valid ISINcode validation method call
	       	if(validation)
	       		msg=ISINno+" is Valid";
	       	else
	       		msg=ISINno+" is Invalid";
		}
		return msg;
		//return true;
		
	} 
	public static boolean codeValidator(String isino) {
		StringBuilder sb = new StringBuilder(); // To modify java string
        for (char c : isino.substring(0, 12).toCharArray())
            sb.append(Character.digit(c, 36)); // Decimal value-55
        if (IsinDigitCheck(sb.toString())){
        	System.out.print("Valid ISIN no \n");
        	return true;
        }
        else
        	{
        	System.out.print("given ISIN no is invalid \n");
        	return false;
        	}
		
	}
	
	static boolean IsinDigitCheck(String number) {
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for (int i = 0; i < reverse.length(); i++){
            int digit = Character.digit(reverse.charAt(i), 10);
            //This is for odd digits, they are 1-indexed in the algorithm.
            if (i % 2 == 0){
                s1 += digit;
            } else { // Add 2 * digit for 0-4, add 2 * digit - 9 for 5-9.
                s2 += 2 * digit;
                if(digit >= 5){
                    s2 -= 9;
                }
            }
        }
        
        return (s1 + s2) % 10 == 0;
    }
	
}
