
/**
 * Main is a main method that allows users to decide which test they would 
 *Like to run. 
 * @author Sean H. Worthington
 * @author 
 * @version 1.1
 */

public class Main {

    
    
    public static void main(String[] args) {
        greet();
        while(true)
        {
            executeCommand(getCommand());
        }

    }// end main

    public static void greet(){
        System.out.println("This is the Servant Tester");
        System.out.println("Used to test servant modules");
        System.out.println("As is from the CloudCoin Consortium");
        System.out.println("Last changed: 11/04/2018");
        System.out.println("");
    }//end greeting
    
    public static int getCommand(){
        //List all commands
        String commands[] = {"Quit Tester","Test Echoer","next command here"};
        
        System.out.println("Enter the number of the command you wish to execute");
        for(int i= 0; i < commands.length; i++)
        {
            int cn = i ;
            System.out.println(cn+". " + commands[i]);//list each command in the commands array
        }//end list all commands
        KeyboardReader reader = new KeyboardReader();
        int command = reader.readInt(0,commands.length);
        return command; 
    }

    public static void executeCommand(int commandNumber ){
        
        switch(commandNumber)
        {
            case 0: 
            //Execute Quit Program
                System.out.println("Quiting.");
                System.exit(0);
            break;
            case 1: 
            //Execute Test Echoer
               System.out.println("Testing Echoer");
            break;
            case 2: 
            //Execute Quit Program
                System.out.println("Could run other commands.");
            break;
            default: 
               System.out.println("Error running command. Please try again. ");
            break;
        }//end switch
        
        //end execute Command
    }//end execute Command
    
}//end Main

