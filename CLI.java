import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
          System.out.println("What ticket number would you like to view?");
          userInput = scnr.nextInt();
          try {
            System.out.println(viewOne(userInput));
          } catch (IOException e) {
            System.out.println(
                "The ticket number you are trying to access does not exist :(\nPlease try a new "
                    + "ticket number or enter 3 to exit the program\n");

          }
          break;

        case 3:
          run = false;
          scnr.close();
          break;
      }

    }

  }



  private static void printMenu() {
    System.out
        .println("[1] - view a specific ticket\n[2] - view all tickets\n[3] - quit the program");
  }



  public static String viewOne(int ticketId) throws IOException {

    URL url = new URL("https://zccreuss.zendesk.com/api/v2/tickets/" + ticketId + ".json");
    HttpURLConnection http = (HttpURLConnection) url.openConnection();
    http.setRequestProperty("Accept", "application/json");
    http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");
    InputStream inputStream = http.getInputStream();


    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
    }
    http.disconnect();
    String ticketStr = sb.toString();

    
    Ticket ticket = new Ticket(ticketStr);
    String retString = "ID: " + ticket.getID() + "\nSubject: " + ticket.getSubject()
        + "\nDescription: " + ticket.getDescription();

    return retString;

  }
}
