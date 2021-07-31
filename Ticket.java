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

  JSONObject jobjectTicket;
  JSONObject jobjectCount;
  private String subject;
  private int ID;
  private String description;
  private String createdTime;
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
      jobjectTicket = new JSONObject(ticket);
      subject = jobjectTicket.getJSONObject("ticket").getString("subject");
      ID = jobjectTicket.getJSONObject("ticket").getInt("id");
      description = jobjectTicket.getJSONObject("ticket").getString("description");
      info = "ID: " + ID + "\nSubject: " + subject + "\nDescription: " + description;
    } catch (JSONException e) {
      System.out.println("not a valid json passed");
      e.printStackTrace();
    }


  }

  /**
   * This method is for the Ticket object representing the total amount of tickets
   * 
   * @param Ticket
   * @param forToken
   */
  public Ticket(String Ticket, boolean forCount) {
    try {
      jobjectCount = new JSONObject(Ticket);
      count = jobjectCount.getJSONObject("count").getInt("value");
    } catch (JSONException e) {
      System.out.println("not a valid json passed");
      e.printStackTrace();
    }
  }

  public Ticket(String Ticket, int pages) {
    try {
      jobjectTicket = new JSONObject(Ticket);
      JSONArray arr = jobjectTicket.getJSONArray("tickets");
      for (int i = 0; i < arr.length(); i++) {
          ID = arr.getJSONObject(i).getInt("id");
          subject = arr.getJSONObject(i).getString("subject");
          description = arr.getJSONObject(i).getString("description");
          info += "ID: " + ID + "\nSubject: " + subject + "\nDescription: " + description + "\n\n";
      }
      beforeLink = jobjectTicket.getJSONObject("links").getString("prev");
      afterLink = jobjectTicket.getJSONObject("links").getString("next");
    } catch (JSONException e) {
      // TODO Auto-generated catch block
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
