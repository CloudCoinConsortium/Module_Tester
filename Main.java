
/**
 * Main is a main method that allows users to decide which test they would 
 *Like to run. 
 * @author Sean H. Worthington
 * @author your name here
 * @version 1.1
 */

public class Main {

    public static String RootPath = "C:\\CloudCoinServer\\accounts\\default_account_password\\";
    
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
        String commands[] = {"Quit Tester","Test Echoer","Test Exporter","test backuper","Test Pay-Forward","Test ShowCoins","Test Depositer","Test Minder",
        		"Test Emailer","Test Vaulter","Test LossFixer","Test Grader","Test Translator","Test Unpacker"};
        
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
                //test  Exporter
                System.out.println("Testing Exporter");
                new Exporter();
                break;
            case 3:
                //Execute Quit Program
                System.out.println("Could run other commands.");
                //test back upper
                System.out.println("Testing BackUpper");
				new Backupper();
                
            break;
            case 4:
                //test  PayForward
                    System.out.println("Testing PayForward.");
                    
                break;
            case 5:
                //test  ShowCoins
                    System.out.println("Testing ShowCoins.");
                    
                break;
            case 6:
                //test Depositer
                    System.out.println("Testing Depositer.");
                    
                break;
            case 7:
                //test Minder
                    System.out.println("Testing Minder.");
                    
                break;
            case 8:
                //test Emailer
                    System.out.println("Testing Emailer.");
                    
                break;
            case 9:
                //test Vaulter
                    System.out.println("Testing Vaulter.");
               
               break;
            case 10:
                //test LossFixer
                    System.out.println("Testing LossFixer.");
               
               break;
            case 11:
                //test Grader
                System.out.println("Testing Grader.");
                new Grader();
                break;
            case 12:
                //test Translator
                    System.out.println("Testing Translator");
                    
                break;
            case 13:
                //test Translator
                System.out.println("Testing Unpacker");
                new Unpacker();
                break;
            default: 
               System.out.println("Error running command. Please try again. ");
            break;
        }//end switch
        
        //end execute Command
    }//end execute Command
    
}//end Main

