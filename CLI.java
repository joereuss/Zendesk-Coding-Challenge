import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
              System.out.println(viewOne(userInput));
            } catch (IOException e) {
              System.out.println(
                  "The ticket number you are trying to access does not exist :(\nPlease try a new "
                      + "ticket number or enter 3 to exit the program\n");

            }
            break;

          // case 2: user would like to view all tickets, split into 25 on each page
          case 2:
            viewAll(null, 1);
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



  /**
   * @param ticketId - the ticket number the user would like to see
   * @return - The ticket the user wanted to see from the Zendesk API
   * @throws IOException - Exception thrown if the API could not be accessed
   */
  public static String viewOne(int ticketId) throws IOException {

    // set up connection to the Zendesk API
    URL url = new URL("https://zccreuss.zendesk.com/api/v2/tickets/" + ticketId + ".json");
    HttpURLConnection http = (HttpURLConnection) url.openConnection();
    // ensure we get a returned json file
    http.setRequestProperty("Accept", "application/json");
    // authorization
    http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");
    // whatever the GET returned to us we put into an inputstream to eventually read
    InputStream inputStream = http.getInputStream();

    // code to read the inputstream received
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
    // disconnect from API
    http.disconnect();
    String ticketStr = sb.toString();

    // create a ticket object and return the ticket in a user-friendly way
    Ticket ticket = new Ticket(ticketStr);
    String retString = ticket.toString();

    return retString;

  }



  public static void viewAll(URL url, int pageNum) {
    int numOfTickets = 0;
    int numOfPages = 0;
    try {
      URL countUrl = new URL("https://zccreuss.zendesk.com/api/v2/tickets/count.json");
      HttpURLConnection http = (HttpURLConnection) countUrl.openConnection();
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
      // disconnect from API
      http.disconnect();
      String ticketStrCount = sb.toString();
      boolean forCount = true;
      Ticket count = new Ticket(ticketStrCount, forCount);
      numOfTickets = count.getCount();



      // get the number of pages to be displayed to the UI
      if (numOfTickets % 25 != 0) {
        numOfPages = (numOfTickets / 25) + 1;
      } else {
        numOfPages = numOfTickets / 25;
      }

      // display number of pages to the UI as well as the current page (1)
      System.out.println("\nShowing page " + pageNum + " of " + numOfPages + ":\n");


    } catch (MalformedURLException e) {
      System.out.println("not a valid URL for API");
      e.printStackTrace();
      return;

    } catch (IOException e) {
      System.out.println("problem connecting to the API");
      e.printStackTrace();
      return;
    }

    if (numOfTickets == 0) {
      System.out.println("no tickets available to display");
      return;
    }

    if (numOfTickets > 25) {
      try {
        if (url == null) {
          url = new URL("https://zccreuss.zendesk.com/api/v2/tickets.json?page[size]=25");
        }
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");
        System.out.println(http.getRequestProperties());

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
        // disconnect from API
        http.disconnect();
        String ticketStr = sb.toString();

        Ticket multiTicket = new Ticket(ticketStr, numOfPages);
        System.out.println(multiTicket.toString());

        boolean viewingAll = true;

        while (viewingAll) {
          Scanner s = new Scanner(System.in);
          String input = "";
          System.out.println(
              "ENTER 'next/prev' to view the next or previous page\nor enter 'back' to go back");
          input = s.next();

          switch (input) {
            case "next":
              
              if (pageNum == numOfPages) {
                System.out.println("There is no next page!");
                break;
              }
              if (pageNum != numOfPages) {
                pageNum++;
              }

              url = new URL(multiTicket.getAfterLink());
              // recursive call to viewAll
              viewAll(url, pageNum);
              return;


            case "prev":
              if (pageNum == 1) {
                System.out.println("There is no previous page!");
                break;
              }
              if (pageNum != 1) {
                pageNum--;
              }
              
              url = new URL(multiTicket.getBeforeLink());
              // recursive call to viewAll
              viewAll(url, pageNum);
              return;



            case "back":
              viewingAll = false;
              break;


            default:
              System.out.println("Invalid option, please try again!\n");
              break;

          }

        }


      } catch (MalformedURLException e) {
        System.out.println("incorrect URL for API");
        e.printStackTrace();
      } catch (IOException e) {
        System.out.println("problem connecting to API");
        e.printStackTrace();
      }
    }



    return;
  }
}
