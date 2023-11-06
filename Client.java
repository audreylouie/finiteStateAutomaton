import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Client {
	public static class Calculator {
		public static double calculate(String formula) {
			
			String[] elements = formula.split("[+\\-\\*\\/ =]"); // extracting the numbers of the equation and putting it in a string array
			
			 ArrayList <Double> num = new ArrayList <Double>(); 
			ArrayList <Character> operator = new ArrayList<Character>();
			
			for(String element : elements){
				double number = Double.parseDouble(element); // converts the string into a double 
		        num.add(number); // adds the number into a double arrayList
			}
			for (int i = 0; i < formula.length(); i++) {
	            char input = formula.charAt(i); 
	            if(input == '+' || input == '-' || input == '*' || input == '/') {      	
	            	operator.add(input); // puts operators in a separate char arrayList
	            }  
			}
			
			double num_1 = 0; 
			double num_2 = 0;
			int index = 0;
			double result = 0;
		
			while(!operator.isEmpty()) {
				if(operator.contains('*') || operator.contains('/')) { // checks to see if the equation has * or / to ensure order of operation is fulfilled
					for(char o: operator) {	 // goes through the arrayList to find the '*'						
						 if(o == '*') {
							index = operator.indexOf('*'); // gets index of '*'
							operator.remove(index); // removes it from the arrayList to indicate the multiplication is completed
							num_1 = num.get(index); // in the number arrayList, locate the number on the left of the multiplication operation
							num_2 = num.get(index + 1); // locate the number on the right of the multiplication operation
							num.remove(index + 1); //remove the right number first from arrayList
							num.remove(index); // remove the left number from the arrayList
							
							result = num_1 * num_2; 
							num.add(index, result); // basically replaces those two numbers with the result of the multiplication
						
						}
						 else {
							if(operator.contains('/')) { 
							index = operator.indexOf('/'); // gets index of '/'
							operator.remove(index); // removes it from the arrayList to indicate the division is completed
							num_1 = num.get(index); // locate the number to the left of division operation
							num_2 = num.get(index + 1); // locate the number to the right of the division operation
							num.remove(index + 1);
							num.remove(index);
							
							result = num_1 / num_2;
							num.add(index, result); // basically replaces those two numbers with the result of the division
							}
							
							else {
								continue; // when it is going through the arrayList of operations, if it comes across a '+' or '-' continue 
							}
						}
					
						break; 
						
					}
					
				 }
					
		
				else {
					for(char o: operator) {
						if(o == '+') {
							index = operator.indexOf(o); // gets index of '+'
							operator.remove(index); // removes it from the arrayList to indicate the addition is completed
							num_1 = num.get(index); // gets the number to the left of the addition operation
							num_2 = num.get(index + 1); // gets the number to the right of the addition operation
							num.remove(index + 1);
							num.remove(index);
							
							result = num_1 + num_2;
							num.add(index, result); // basically replaces those two numbers with the result of the addition
							
						}
						else {
							index = operator.indexOf(o); // gets index of '-'
							operator.remove(index); // removes it from the arrayList to indicate the subtraction is completed
							num_1 = num.get(index); // gets the number to the left of the subtraction operation
							num_2 = num.get(index + 1); // gets the number to the right of the subtraction operation
							num.remove(index + 1);
							num.remove(index);
							
							result = num_1 - num_2;
							num.add(index, result); // basically replaces those two numbers with the result of the subtraction
							
						}
						break;
					}
					
				}
				
			}

			
			return result;
		}
	}
	
	public static class Packetizer {

		 public String currentMessage; // stores data of current message
		 public int state; // current state 
		 public String formula; // grammatically correct formula
		
		public Packetizer() {
			currentMessage = "";
			state = 0;
			formula = "";
			
		}
		
		public String packetize(String message) {
		    for (int i = 0; i < message.length(); i++) {
	            char input = message.charAt(i); // going through each character of the string
	            
	            boolean isDigit = false;
	            if(input >= '0' && input <= '9') { // checks if input is a digit
	            	isDigit = true;
	            }
	            
	            boolean isOperator = false;
	            if(input == '+' || input == '-' || input == '*' || input == '/') { //checks if input is an operator
	            	isOperator = true;
	            }
	            
	            switch (state) {
	                case 0: // initial state
	                    if (input == '>') {
	                    	currentMessage = "";
	                        state = 1; 
	                    }
	                    break;
	                case 1: // making sure the formula starts with a number
	                    if (isDigit) {
	                    	isDigit = false;
	                    	currentMessage += input; // add number to the current message variable
	                        state = 2;
	                    } 
	                    else if (input == '<' || input == '>') {
	                    	currentMessage = ""; // clear current message variable 
	                        state = 0; // go back to initial state
	                    }
	                    break;
	                case 2: 
	                    if (isDigit) {
	                    	isDigit = false; 
	                    	currentMessage += input; // add digit to the current message variable
	                        state = 2; // stay at this state if another digit is inputed
	                    } 
	                    else if (isOperator) {
	                    	isOperator = false;
	                    	currentMessage += input; // add operator to the current message variable
	                        state = 3;
	                    } 
	                    else if (input == '<' || input == '>') {
	                    	currentMessage = ""; // clear current message variable
	                        state = 0; // go back to initial state
	                    } 
	                    break;
	                case 3: 
	                    if (isDigit) {
	                    	isDigit = false;
	                    	currentMessage += input; // add number to current message variable
	                        state = 4;
	                    } 
	                    else if (input == '<' || input == '>') {
	                    	currentMessage = ""; // clear current message variable
	                        state = 0; // go back to initial state
	                    } 
	                    else if(isOperator) {
	                    	isOperator = false;
	                    	currentMessage = ""; // if there is an operator clear message because that would mean there's two operators in a row
	                    	state = 0; // go back to initial state
	                    }
	                    break;
	                case 4: 
	                    if (isDigit) {
	                    	isDigit = false; 
	                    	currentMessage += input; // add another digit to current message variable 
	                        state = 4;
	                    } 
	                    else if (isOperator) {
	                    	isOperator = false;
	                    	currentMessage += input; // add operation to current message variable
	                        state = 3;
	                    } 
	                    else if (input == '<' || input == '>') {
	                    	currentMessage = ""; // clear current message variable
	                        state = 0; // go back to initial state
	                    } 
	                    else if (input == '=') {
	                    	currentMessage += input; // add equal sign to current message variable
	                    	state = 5; 
	                    }
	                    break;
	                case 5: // accepted state
	                	if(input == '<') {
	                		formula = currentMessage;
	                		currentMessage = ""; // clear current message variable for a new formula
	                        state = 0;  // go back to initial state
	                        return formula; // return grammatically correct formula.
	                	}
	                	break;
	            }
	        }
	        return null; 
	    }
	    
	}
    public static void main(String[] args) {
        if (args.length < 2) return;
 
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        
        Packetizer packetizer = new Packetizer();
        String formula = null;
        double result = 0;
 
        try (Socket socket = new Socket(hostname, port)) {
 
            while (true) {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String message = reader.readLine();
                System.out.println("Message Received: " + message);
                
                formula = packetizer.packetize(message);
                if (formula != null) {
                	result = Calculator.calculate(formula);
                	System.out.println("====================================");
                	System.out.println("Formula: " + formula);
                	System.out.println("Result : " + result);                	
                	System.out.println("====================================");
                }
            }
 
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
