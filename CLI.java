import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class CLI {

  public static void main(String[] args) {
    boolean run = true;
    while (run) {
      Scanner scnr = new Scanner(System.in);
      int userInput = 0;
      System.out.println("Hello! How would you like to view your tickets?");
      System.out.println("Select an option below:");
      printMenu();
      userInput = scnr.nextInt();
      
      switch (userInput) {
        case 1:
          try {
            viewOne(2);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          break;
          
        case 3:
          run = false;
          break;
      }

    }

  }



  private static void printMenu() {
    System.out
        .println("[1] - view all tickets\n[2] - view a specific ticket\n[3] - quit the program");
  }
  
  public static String viewOne(int ticketId) throws IOException {
    String command = "curl https://zccreuss.zendesk.com/api/v2/tickets/" + ticketId + ".json \\  -v -u joereuss8@gmail.com:LucySandy1!";
    Process process = Runtime.getRuntime().exec(command);
    InputStream oneTicket = process.getInputStream();
    String result = new InputStreamReader(oneTicket).toString();
    System.out.println(result);
    return null;
  }
}

