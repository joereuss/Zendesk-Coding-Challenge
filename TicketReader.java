import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TicketReader {

  private int pageNumber = 0;

  /**
   * @param ticketId
   * @param url
   * @return
   * @throws IOException
   */
  public String viewOne(int ticketId, URL url) throws IOException {

    // set up connection to the Zendesk API
    try {
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      // ensure we get a returned json file
      http.setRequestProperty("Accept", "application/json");
      // authorization
      http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");
      // whatever the GET returned to us we put into an inputstream to read

      InputStream inputStream = http.getInputStream();
      String ticketStr = readInputStream(inputStream);


      // disconnect from API
      http.disconnect();


      // create a ticket object and return the ticket in a user-friendly way
      Ticket ticket = new Ticket(ticketStr);
      String retString = ticket.toString();

      return retString;
    } catch (FileNotFoundException e) {
      return "That ticket does not exist!";
    } catch (MalformedURLException e) {
      System.out.println("invalid URL for API");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("problem connecting to the API");
      // e.printStackTrace();
    }
    return null;
  }

  /**
   * @param url
   */
  public void viewAll(URL url) {
    int numOfTickets = 0;
    int numOfPages = 0;

    if (pageNumber == 0) {
      pageNumber = 1;
    }

    try {
      // set up connection to the Zendesk API
      URL countUrl = new URL("https://zccreuss.zendesk.com/api/v2/tickets/count.json");
      HttpURLConnection http = (HttpURLConnection) countUrl.openConnection();
      http.setRequestProperty("Accept", "application/json");
      http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");
      // whatever the GET returned to us we put into an inputstream to read
      InputStream inputStream = http.getInputStream();
      String ticketStrCount = readInputStream(inputStream);


      // disconnect from API
      http.disconnect();

      boolean forCount = true;
      // get a ticket object but adding the boolean to go to the correct constructor and give us the
      // number of tickets
      Ticket count = new Ticket(ticketStrCount, forCount);
      numOfTickets = count.getCount();


      // get the number of pages to be displayed to the UI
      if (numOfTickets % 25 != 0) {
        numOfPages = (numOfTickets / 25) + 1;
      } else {
        numOfPages = numOfTickets / 25;
      }



      // catch any thrown exceptions
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


    try {
      // default url for API if this method has not been recursively called
      if (url == null) {
        url = new URL("https://zccreuss.zendesk.com/api/v2/tickets.json?page[size]=25");
      }
      // usual API connection, similar to above
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestProperty("Accept", "application/json");
      http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");

      InputStream inputStream = http.getInputStream();
      String ticketStr = readInputStream(inputStream);
      http.disconnect();

      // display number of pages to the UI as well as the current page (1)
      System.out.println("\nShowing page " + pageNumber + " of " + numOfPages + ":\n");


      Ticket multiTicket = new Ticket(ticketStr, numOfPages);
      System.out.println(multiTicket.toString());


      // a while loop to run the UI for navigating pages, the user is able to go to the next page,
      // the previous page, or to go back to the main UI
      boolean viewingAll = true;
      while (viewingAll) {
        Scanner s = new Scanner(System.in);
        String input = "";
        System.out.println(
            "ENTER 'next/prev' to view the next or previous page\nor\nENTER 'back' to go back");
        input = s.next();

        switch (input) {

          // the case to show the user the next page if one exists
          case "next":

            if (pageNumber == numOfPages) {
              System.out.println("There is no next page!");
              break;
            }
            if (pageNumber != numOfPages) {
              pageNumber++;
            }

            url = new URL(multiTicket.getAfterLink());
            // recursive call to viewAll
            viewAll(url);
            return;

          // the case to show the user the previous page if one exists
          case "prev":
            if (pageNumber == 1) {
              System.out.println("There is no previous page!");
              break;
            }
            if (pageNumber != 1) {
              pageNumber--;
            }

            url = new URL(multiTicket.getBeforeLink());
            // recursive call to viewAll
            viewAll(url);
            return;
          // this breaks us out of the loop and method, to return back to the main UI
          case "back":
            viewingAll = false;
            break;

          // the case if the user input is not one of the given options in the UI
          default:
            System.out.println("Invalid option, please try again!\n");
            break;
        }
      }

      // any exceptions to be caught
    } catch (MalformedURLException e) {
      System.out.println("incorrect URL for API");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("problem connecting to API");
      // e.printStackTrace();
    }
  }

  /**
   * This method is designed to read any given InputStreams passed to it and return it in String
   * form. In our case, it returns the String in a JSON file format
   * 
   * @param inputStream - the given InputStream to be read
   * @return - the given InputStream as a String
   */
  public String readInputStream(InputStream inputStream) {
    // set up the reader and string builder
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      // a while loop to read through the entire InputStream
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      System.out.println("invalid InputStream");
      e.printStackTrace();
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        System.out.println("invalid InputStream");
        e.printStackTrace();
      }
    }

    // the InputStream in string form
    String ticketStr = sb.toString();
    return ticketStr;
  }

  /**
   * This method is mostly for testing purposes, to get the first ticket's ID
   * 
   * @return - The first ticket's ID
   */
  public int getFirstTicketID() {
    try {
      // default url for API if this method has not been recursively called

      URL url = new URL("https://zccreuss.zendesk.com/api/v2/tickets.json?page[size]=1");

      // usual API connection, similar to above
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestProperty("Accept", "application/json");
      http.setRequestProperty("Authorization", "Basic am9lcmV1c3M4QGdtYWlsLmNvbToyUG90YXRvZQ==");

      InputStream inputStream = http.getInputStream();
      String ticketStr = readInputStream(inputStream);
      http.disconnect();

      Ticket ticket = new Ticket(ticketStr, 1);
      return ticket.getID();

    } catch (MalformedURLException e) {
      System.out.println("incorrect URL for API");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("problem connecting to API");
      e.printStackTrace();
    }

    return -1;
  }


}


