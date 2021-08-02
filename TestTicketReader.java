import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.net.URL;
import org.junit.jupiter.api.Test;



/**
 * 
 * This class tests the functionality of the ticket reader, it relies on the account's tickets to
 * contain the tickets in tickets.json from ID 102-202
 * @author Joseph Reuss
 *
 */
public class TestTicketReader {

  TicketReader ticketReader = new TicketReader();

  @Test
  public void testViewOne() {

    // ensures program works inputting a ticket that does not exist
    try {
      URL url = new URL("https://zccreuss.zendesk.com/api/v2/tickets/999999.json");

      String test1 = ticketReader.viewOne(999999, url);
      assertEquals("Test one should be invalid", test1, "That ticket does not exist!");

      url = new URL("https://zccreuss.zendesk.com/api/v2/tickets/" + ticketReader.getFirstTicketID()
          + ".json");
      String test2 = ticketReader.viewOne(ticketReader.getFirstTicketID(), url);
      assertEquals("Test two should be valid", "ID: 102", test2.substring(0, 7));

      // ensures the program works using the wrong URL
      url = new URL("https://zccreuss.zendesk.com/wakjdfaskdhf");
      ticketReader.viewOne(ticketReader.getFirstTicketID(), url);

      // catches anything if the test fails
    } catch (Exception e) {
      System.out.println("ERROR should not have returned an exception, should have been handled!"
          + " - problem with testViewOne()");
      fail();
      e.printStackTrace();
    }



  }

  @Test
  public void testViewAll() {
    try {
      // ensures the program runs with invalid URL
      URL url = new URL("https://zccreuss.zendesk.com/aksfkajdhf");
      ticketReader.viewAll(url);

      // ensures the program runs without correct authorization (no domain)
      url = new URL("https://zendesk.com/api/v2/tickets.json?page[size]=25");
      ticketReader.viewAll(url);



    } catch (Exception e) {
      System.out.println("ERROR should not have returned an exception, should have been handled!"
          + " - problem with testViewAll()");
      fail();
      e.printStackTrace();
    }

  }


}
