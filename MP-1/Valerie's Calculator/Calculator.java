import java.util.*;

public class Calculator {
	//Fields:
	private String name;
	private static Scanner scanner = new Scanner(System.in);
	
	//Method to add two floating point numbers:
	public Float addition(Float A, Float B){
		Float sum = A + B;
		return sum;
	}

	//Method to subtract two floating point numbers:
	public Float subtraction(Float A, Float B){
		Float difference = A - B;
		return difference;
	}
	
	//Method to multiply two floating point numbers:
	public Float multiplication(Float A, Float B){
		Float product = A * B;
		return product;
	} 

	//Method to set name of group that created calculator:
	public void setname(String N){
		name = N;
	}

	//Method to get name of group that created calculator::
	public String getname(){
		return name;
	}
    
    	//The following are Functions (Methods) created to reduce the size of the code:
    		//Quit program if user input Q or q:
		public String quit(String str){
			if((str.equals("q")) || (str.equals("Q"))){
				System.out.println("\n Exiting Calculator...\n");;
				System.exit(0);
				return str;
        	        }
			else{
				return str;
			}
		}
		
		//Check if input argument is valid:
		public boolean isValid(String input){
			try{
				Float a = Float.parseFloat(input);
			}
			catch(Exception e){
				System.out.println("\n Invalid entry. Restarting Calculator...");
				return false;	
			}
			return true;
		}

	//Main:
	public static void main(String args[]){
		//Create new Calculator object:
		Calculator mycalc =new Calculator();
	
		//Set name of mycalc
                mycalc.setname("Group23");

		//While loop to loop calculator:
		while(true){
                	//Print welcome screen:
             		System.out.println("\n Welcome to the Calculator designed by " + mycalc.getname() + ".");
                	System.out.print(" Enter A to Add, S to Subtract, M to Multiply, and Q to quit.\n");
			System.out.print(" Type desired operation and press enter. Enter Q to quit at any time.");
			System.out.print("\n Input Operation: ");
		
			//Get type of mathematical operation wanted:
			String Operation = scanner.nextLine();
			
                        //Figure out which operation to use:
                        switch(Operation){
				case "a":
                                case "A":	//Add Arguments
                        	    //Get argument 1:
                       			System.out.print("\n Enter argument 1: ");
                        		String inputA = scanner.nextLine();
				    //Check if user wants to quit (input ‘Q’ or ‘q’):
					mycalc.quit(inputA);
				    //Check if argument 1 is a valid input:
					if(mycalc.isValid(inputA) == false){
						break;
					}
				    //Parse String to Float:
					Float a = Float.parseFloat(inputA);
				    //Get argument 2:
                        		System.out.print(" Enter argument 2: ");
        		                String inputB = scanner.nextLine();
				    //Check if user input Q or q:
					mycalc.quit(inputB);
				    //Check if argument 2 is a valid input:
                                        if(mycalc.isValid(inputB) == false){
                                                break;
                                        }	                        
				    //Parse String to Float:
					Float b = Float.parseFloat(inputB);
				    //Add arguments:
                                        Float result = mycalc.addition(a,b);
				    //Display result:
					System.out.println("\n The sum of " + a + " and " + b + " is " + result + ".");
                                        break;
				case "s":
                                case "S":	//Subtract Arguments
                                    //Get argument 1:
                                        System.out.print("\n Enter argument 1: ");
                                        inputA = scanner.nextLine();
				    //Check if user input Q or q:
				    	mycalc.quit(inputA);                                  
			            //Check if argument 1 is a valid input:
                                        if(mycalc.isValid(inputA) == false){
                                                break;
                                        }
				    //Parse String to Float:
					a = Float.parseFloat(inputA);
				    //Get argument 2:
                                        System.out.print(" Enter argument 2: ");
                                        inputB = scanner.nextLine();
				    //Check if user input Q or q:
					mycalc.quit(inputB);
                                    //Check if argument 2 is a valid input:
                                        if(mycalc.isValid(inputB) == false){
                                                break;
                                        }                                     
				    //Parse String to Float:
					b = Float.parseFloat(inputB);
				    //Subtract arguments:
                                        result = mycalc.subtraction(a,b);
				    //Display result:
					System.out.println("\n The difference of " + a + " and " + b + " is " + result + ".");
                                        break;
				case "m":
                                case "M":	//Multiply Arguments
                                    //Get argument 1:
                                        System.out.print("\n Enter argument 1: ");
                                        inputA = scanner.nextLine();
				    //Check if user input Q or q:
					mycalc.quit(inputA);
                                    //Check if argument 1 is a valid input:
                                        if(mycalc.isValid(inputA) == false){
                                                break;
                                        }                                     
				    //Parse String to Float:
					a = Float.parseFloat(inputA);
				    //Get argument 2:
                                        System.out.print(" Enter argument 2: ");
                                        inputB = scanner.nextLine();
				    //Check if user input Q or q:
					mycalc.quit(inputB);
                                    //Check if argument 2 is a valid input:
                                        if(mycalc.isValid(inputB) == false){
                                                break;
                                        }
				    //Parse String to Float:
                                        b = Float.parseFloat(inputB);                                     
				    //Multiply arguments:
					result = mycalc.multiplication(a,b);
				    //Display result:
					System.out.println("\n The product of " + a + " and " + b + " is " + result + ".");
                                        break;
				case "q":
                             	case "Q":	//Quit Calculator
				    //Call to quit program:
					mycalc.quit(Operation);
					break;
				default:	//Invalid Input
					System.out.println("\n Invalid entry. Restarting Calculator...");
                                        break;
			}
		}
	}
}
