// external jar imports to make reading the JSON files easier
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is to represent the Ticket object
 * 
 * @author Joseph Reuss
 *
 */
public class Ticket {

  // private variables for the object
  private JSONObject jobjectTicket;
  private JSONObject jobjectCount;
  private String subject;
  private int ID;
  private String description;
  private int count;
  private String info;
  private String beforeLink;
  private String afterLink;

  /**
   * This method represents the Ticket object constructor
   * 
   * @param ticket - the json string passed to the ticket constructor
   */
  public Ticket(String ticket) {
    try {
      // create a JSONObject to gather specific info
      jobjectTicket = new JSONObject(ticket);
      subject = jobjectTicket.getJSONObject("ticket").getString("subject");
      ID = jobjectTicket.getJSONObject("ticket").getInt("id");
      description = jobjectTicket.getJSONObject("ticket").getString("description");
      // put together the useful info into a string to be printed
      info = "ID: " + ID + "\nSubject: " + subject + "\nDescription: " + description;
      // catch an exception if problem making JSONObject
    } catch (JSONException e) {
      System.out.println("not a valid json passed");
      e.printStackTrace();
    }


  }

  /**
   * This method is for the Ticket object representing the total amount of tickets
   * 
   * @param Ticket   - the json string passed to the ticket constructor
   * @param forToken
   */
  public Ticket(String Ticket, boolean forCount) {
    try {
      // a JSONObject to get the count of the amount of tickets present
      jobjectCount = new JSONObject(Ticket);
      // get the count of the tickets from the JSON file
      count = jobjectCount.getJSONObject("count").getInt("value");
      // any exceptions to be caught
    } catch (JSONException e) {
      System.out.println("not a valid json passed");
      e.printStackTrace();
    }
  }

  /**
   * This method is a constructor for the Ticket object when displaying more than 1 ticket, will
   * display 1 page (25) of tickets max per call
   * 
   * @param Ticket - the json string passed to the ticket constructor
   * @param pages  - the amount of pages the total number of tickets creates
   */
  public Ticket(String Ticket, int pages) {
    info = "";
    try {
      jobjectTicket = new JSONObject(Ticket);
      JSONArray arr = jobjectTicket.getJSONArray("tickets");
      // for loop to go through the array in the JSON file to find certain elements
      for (int i = 0; i < arr.length(); i++) {
        // gather useful info for user
        ID = arr.getJSONObject(i).getInt("id");
        subject = arr.getJSONObject(i).getString("subject");
        description = arr.getJSONObject(i).getString("description");
        info += "ID: " + ID + "\nSubject: " + subject + "\nDescription: " + description + "\n\n";
      }
      // record the before and after links to be used for navigation for the user in the UI
      beforeLink = jobjectTicket.getJSONObject("links").getString("prev");
      afterLink = jobjectTicket.getJSONObject("links").getString("next");
      // catch any exception thrown related to the JSON
    } catch (JSONException e) {
      System.out.println("not a valid json passed");
      e.printStackTrace();
    }

  }


  /**
   * Getter for the String subject
   * 
   * @return - subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Getter for the int ID
   * 
   * @return - ID
   */
  public int getID() {
    return ID;
  }

  /**
   * Getter for the String description
   * 
   * @return - description
   */
  public String getDescription() {
    return description;
  }

  /**
   * getter for the count of tickets
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

  /**
   * Getter for beforeCursor
   * 
   * @return the beforeCursor
   */
  public String getBeforeLink() {
    return beforeLink;
  }

  /**
   * Getter for afterCursor
   * 
   * @return the afterCursor
   */
  public String getAfterLink() {
    return afterLink;
  }


  /**
   * A toString method for the ticket object
   * 
   * @return Ticket in a string form showing info valuable for the UI
   */
  @Override
  public String toString() {
    return info;
  }


}
