import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is to represent the command line interface (CLI) for the ticket-reading program.
 * 
 * @author Joseph Reuss
 *
 */
public class CLI {

  static TicketReader ticketReader = new TicketReader();

  public static void main(String[] args) throws InputMismatchException {
    boolean run = true;
    System.out.println("Hello! How would you like to view your tickets?");
    // a while loop to run while the program is running
    while (run) {
      try {
        // initiate scanner for user input
        Scanner scnr = new Scanner(System.in);
        int userInput = 0;

        System.out.println("Select an option below:");
        printMenu();
        // gather user input
        userInput = scnr.nextInt();

        switch (userInput) {
          // case 1: user would like to view a specific ticket number
          case 1:
            System.out.println("What ticket number would you like to view?");
            userInput = scnr.nextInt();
            try {
              URL url = new URL("https://zccreuss.zendesk.com/api/v2/tickets/" + userInput + ".json");
              System.out.println(ticketReader.viewOne(userInput, url));
            } catch (IOException e) {
              System.out.println(
                  "The ticket number you are trying to access does not exist :(\nPlease try a new "
                      + "ticket number or enter 3 to exit the program\n");

            }
            break;

          // case 2: user would like to view all tickets, split into 25 on each page
          case 2:
            ticketReader.viewAll(null);
            break;

          // case 3: user would like to exit the program
          case 3:
            run = false;
            System.out.println("\nThank you! Goodbye!");
            scnr.close();
            break;

          // default case for if the user does not input a valid number option
          default:
            System.out.println("Please enter a valid option");
            break;
        }
        // catch any input that is not a valid int
      } catch (InputMismatchException e) {
        System.out.println("Please enter a valid option.");
      }
    }
  }



  /**
   * Simple method to generate the menu for the user
   */
  private static void printMenu() {
    System.out
        .println("[1] - view a specific ticket\n[2] - view all tickets\n[3] - quit the program");
  }
}


