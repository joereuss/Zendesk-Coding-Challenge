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


  /**
   * Getter for the String subject
   * @return - subject
   */
  public String getSubject() {
    return subject;
  }

  /** 
   * Getter for the int ID
   * @return - ID
   */

  public int getID() {
    return ID;
  }

  /**
   * Getter for the String description
   * @return - description
   */

  public String getDescription() {
    return description;
  }
  
  public int getCount() {
    return count;
  }


}
