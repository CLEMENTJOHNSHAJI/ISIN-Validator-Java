package net.codejava.javaee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.Arrays;
import java.util.Locale;

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

	public static String formatCheck(String ISINno) { // First checking for format of the input string
		String msg=null;
		
		if(ISINno.isEmpty()) {// Null value validation
			msg="Please enter a value";
		}
		else if(ISINno.length()!=12) { // Input length validation
			msg="ISIN Code should have 12 characters";
		}
		else if(!checkCountryCode(ISINno)) { // Country Code validation
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
			if(validateCode(ISINno))  // If the format  is correct, check for ISIN validation
				msg=ISINno+" is Valid";
			else
	       		msg=ISINno+" is Invalid";
		}
		return msg;
		//return true;
		
	} 
	
	public static boolean validateCode(String cc){
        String converted=convertAscii(cc); // function for converting Alphabets to corresponding ASCII value-55 
        char lastChar=cc.charAt(11); // Saving check digit for reference
        int checkdigit=Character.digit(lastChar,10); // converting character to integer type
        int s1 = 0, s2 = 0;
        int digit;
        
        //int evenArray[];
        //int oddArray[];
        //evenArray = new int[10]; // These arrays are for storing the digits at odd and even positions for checking 
        //oddArray = new int[10];
        String reverse = new StringBuffer(converted).reverse().toString(); //Reversing the string
        //System.out.println("Reversed value "+reverse);
        
        for(int i=0,j=0,k=0; i<reverse.length();i++){
            
            digit = Character.digit(reverse.charAt(i), 10); //Taking each value from string and checking with ISIN check logic
            //int no=(int)ch1;
            if (i % 2 == 0){// Digits at even positions
                digit=digit*2;
                //evenArray[j++]=digit;
                if(digit>9){   // If the value after multiplication is double digit, add the digits of number with the s1
                    while(digit>0){
                        
                        s1=s1+digit%10;
                        digit=digit/10;
                    }
                }
                else
                    s1=s1+digit;
            }
            
            else { // Digits at odd positions
              
                //oddArray[k++]=digit; 
                    s2=s2+digit;
            }
        }
       
        //System.out.println(Arrays.toString(evenArray));
        //System.out.println(Arrays.toString(oddArray));
        //System.out.println(s1);
        //System.out.println(s2);
        
        int sum=s1+s2;
        //System.out.println(sum);
        // FindNum is Checking the smallest value greater than sum divisible by 10
        if((findNum(sum, 10)-sum)==checkdigit) //Subtract SUM from VALUE giving the check digit:
            return true;
        else
        return false;
    }
     
     public static String convertAscii(String code){
        code=code.toUpperCase(); //Converting to uppercase
        //int intArray[]; 
        String converted="";
        int digit;
        //intArray = new int[20];
        //StringBuilder sb = new StringBuilder(code);
        for(int i=0; i<code.length()-1;){
            char ch=code.charAt(i);
            if(Character.isDigit(ch)){ //Checking the character is a digit
                //intArray[i]=ch;
                converted = converted + ch;
            }
            else{ //if character is not digit find its ASCII and substract 55 from it
                digit=(int) ch-55;
                converted = converted + Integer.toString(digit);
                //intArray[i]=digit;
            }i++;
         }
         //String converted=Arrays.toString(intArray);
         //System.out.println(converted);
         return converted;  
     }
     
     
     
     private static int findNum(int N, int K)
    {
        int rem = (N + K) % K;
      
        if (rem == 0)
            return N;
        else
            return N + K - rem;
    }
	
	
	private static boolean checkCountryCode(String code) {
		String [] CCODES = Locale.getISOCountries(); // Getting already valid ISO country codes

	    String [] SPECIALS = {
	            "EZ","EU","XA","XB","XC","XS"}; 
	    // By adding more special codes to the list it is possible to check more valid codes
	    String [] combined= new String[CCODES.length+SPECIALS.length];
	    System.arraycopy(CCODES,0,combined,0,CCODES.length);
	    System.arraycopy(SPECIALS,0,combined,0,SPECIALS.length);
	    String CC=code.substring(0,2);
	    CC=CC.toUpperCase();
        return Arrays.asList(combined).contains(CC); // Checking whether the given code is in the list
        
    }
	
	
	
}
