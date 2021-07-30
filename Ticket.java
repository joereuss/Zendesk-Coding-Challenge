import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class is to represent the Ticket object
 * 
 * @author Joseph Reuss
 *
 */
public class Ticket {

  JSONObject jobject;
  private String subject;
  private int ID;
  private String description;
  private String createdTime;


  /**
   * This method represents the Ticket object constructor
   * 
   * @param ticket - the json string passed to the ticket constructor
   */
  public Ticket(String ticket) {
    try {
      jobject = new JSONObject(ticket);
      subject = jobject.getJSONObject("ticket").getString("subject");
      ID = jobject.getJSONObject("ticket").getInt("id");
      description = jobject.getJSONObject("ticket").getString("description");
    } catch (JSONException e) {
      System.out.println("not a valid json object");
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


}
