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
		else if(!checkCountryCode(ISINno)) {
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
			/*boolean validation=codeValidator(ISINno); // Valid ISINcode validation method call
	       	if(validation)
	       		msg=ISINno+" is Valid";
	       	else
	       		msg=ISINno+" is Invalid";*/
			if(validateCode(ISINno))
				msg=ISINno+" is Valid";
			else
	       		msg=ISINno+" is Invalid";
		}
		return msg;
		//return true;
		
	} 
	
	public static boolean validateCode(String cc){
        String converted=convertAscii(cc);
        char lastChar=cc.charAt(11);
        int checkdigit=Character.digit(lastChar,10);
        int s1 = 0, s2 = 0;
        int digit;
        //char ch1;
        int evenArray[];
        int oddArray[];
        evenArray = new int[10];
        oddArray = new int[10];
        String reverse = new StringBuffer(converted).reverse().toString();
        //System.out.println("Reversed value "+reverse);
        
        for(int i=0,j=0,k=0; i<reverse.length();i++){
            //ch1=reverse.charAt(i);
            digit = Character.digit(reverse.charAt(i), 10);
            //int no=(int)ch1;
            if (i % 2 == 0){
                digit=digit*2;
                evenArray[j++]=digit;
                if(digit>9){
                    while(digit>0){
                        
                        s1=s1+digit%10;
                        digit=digit/10;
                    }
                }
                else
                    s1=s1+digit;
            }
            
            else {
                //digit=no;
                oddArray[k++]=digit; 
                    s2=s2+digit;
            }
        }
       
        //System.out.println(Arrays.toString(evenArray));
        //System.out.println(Arrays.toString(oddArray));
        //System.out.println(s1);
        //System.out.println(s2);
        
        int sum=s1+s2;
        //System.out.println(sum);
        if((findNum(sum, 10)-sum)==checkdigit)
            return true;
        else
        return false;
    }
     
     public static String convertAscii(String code){
        code=code.toUpperCase();
        int intArray[]; 
        String converted="";
        int digit;//declaring array
        intArray = new int[20];
        //StringBuilder sb = new StringBuilder(code);
        for(int i=0; i<code.length()-1;){
            char ch=code.charAt(i);
            if(Character.isDigit(ch)){
                //intArray[i]=ch;
                converted = converted + ch;
            }
            else{
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
		String [] CCODES = Locale.getISOCountries();

	    String [] SPECIALS = {
	            "EZ","EU","XA","XB","XC","XS"}; 
	    String [] combined= new String[CCODES.length+SPECIALS.length];
	    System.arraycopy(CCODES,0,combined,0,CCODES.length);
	    System.arraycopy(SPECIALS,0,combined,0,SPECIALS.length);
	    String CC=code.substring(0,2);
	    CC=CC.toUpperCase();
        return Arrays.asList(combined).contains(CC);
        
    }
	
	
	
}
