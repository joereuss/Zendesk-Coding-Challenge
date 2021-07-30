import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/*
 * This class is to represent the Ticket object
 */
public class Ticket {

  private JSONArray jarray;
  JSONObject jobject;
  private String subject;
  private String ID = "0";
  private String description = "";


  public Ticket(String ticket) {

//    try {
//      jobject = new JSONObject(ticket);
//      jarray = jobject.getJSONArray("tickets");
//      for (int i = 0; i < jarray.length(); i++) {
//        subject = jarray.getJSONObject(i).getString("subject");
//        ID = jarray.getJSONObject(i).getString("ID");
//        description = jarray.getJSONObject(i).getString("description");
//        
//      }
//    } catch (JSONException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }



     try {
     jobject = new JSONObject(ticket);
     subject = jobject.getJSONObject("ticket").getString("subject");
     ID = jobject.getJSONObject("ticket").getString("id");
     description = jobject.getJSONObject("ticket").getString("description");
     } catch (JSONException e) {
     // TODO Auto-generated catch block
     System.out.println("not a valid json object");
     e.printStackTrace();
     }

  }

  public String getSubject() {
    return subject;
  }

  public String getID() {
    return ID;
  }

  public String getDescription() {
    return description;
  }


}
