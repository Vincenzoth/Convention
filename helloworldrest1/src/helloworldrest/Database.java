package helloworldrest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Database {
	private String url;
	private String user;
	private String password;
	
	public Database(){
		url="jdbc:mysql://localhost:3306/";
		user="root";
		password="";
	}
	
	
	public Map<String,String> databaseConventionList(String sql) throws IOException {
	
	 Map<String,String> result = new HashMap<String,String>();
	
	 try
	 {
	     Class.forName("com.mysql.jdbc.Driver").newInstance();
	     Connection con = DriverManager.getConnection(url, user, password);
	     
	     Statement stt = con.createStatement();
	     stt.execute("USE test");
            
	     System.out.println(sql);
	     ResultSet res = stt.executeQuery(sql);	     
	     	     
	     while (res.next())
	     {	       	    	 
	    	 result.put(res.getString("id"),res.getString("nome"));	    	 
	     }	
	     
	     /**
	      * Free all opened resources
	      */
	     res.close();
	     stt.close();            
	     con.close();            
	     
	 }
	 catch (Exception e)
	 {
	     e.printStackTrace();
	 }
	    
	 	return result;
	
	}
	
	public String databaseJsonSelect(String sql) throws IOException {
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		 try
		 {
		     Class.forName("com.mysql.jdbc.Driver").newInstance();
		     Connection con = DriverManager.getConnection(url, user, password);
		     
		     Statement stt = con.createStatement();
		     stt.execute("USE test");
	            
		     System.out.println(sql);
		     ResultSet res = stt.executeQuery(sql);
		     ResultSetMetaData rsmd = res.getMetaData();

		     int columnsNumber = rsmd.getColumnCount();
		     System.out.println(columnsNumber);
		     while (res.next())
		     {	 
		    	 JSONObject mainObj = new JSONObject();
		    	 for(int i=1;i<=rsmd.getColumnCount();i++){		    	 
		    	 mainObj.put(rsmd.getColumnName(i), res.getString(i));
		    	 }
		    	// result.put(res.getString("id"),res.getString("nome"));
		    	 ja.put(mainObj);
		     }	
		     
	         jo.put(rsmd.getTableName(1), ja);
		     /**
		      * Free all opened resources
		      */
		     res.close();
		     stt.close();            
		     con.close();            
		     
		 }
		 catch (Exception e)
		 {
		     e.printStackTrace();
		 }
		    
		 	return jo.toString();
		
	}
	
	public void databaseInsert(String sql) throws IOException {

     try
     {
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         Connection con = DriverManager.getConnection(url, user, password);
         
         Statement stt = con.createStatement();        
         stt.execute("USE test");
                  
         System.out.println(sql);         
         stt.execute(sql);
      
         stt.close();            
         con.close();            
         
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
            
	}

}
