package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.HttpClientBuilder;


public class ServerConnection {
	private static final String BASE_URL = "http://localhost:8080/helloworldrest1/users/";
	private static final String pathInsert = "dbInsert";
	private static final String pathSelect = "dbSelect";
	
	
	public void databaseInsert(String sql){

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        
        try {

        		sql = URLEncoder.encode(sql, "UTF-8");
        	    HttpGet httpGet = new HttpGet(BASE_URL+pathInsert+"?sql="+sql);

        	    HttpResponse response = httpClient.execute(httpGet);
        	    System.out.println(response.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}
	
	public String databaseSelect(String sql){

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        
        try {

        		sql = URLEncoder.encode(sql, "UTF-8");
        	    HttpGet httpGet = new HttpGet(BASE_URL+pathSelect+"?sql="+sql);

        	    HttpResponse response = httpClient.execute(httpGet);
        	    System.out.println(response.toString());
        	    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        	    String content="", line;
        	    while ((line = br.readLine()) != null) {
        	         content = content + line;
        	    }
        	    //System.out.print(content);
        	    return content;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
	}
	
	public String scopusAuthor(String nome, String cognome){

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        
        try {

        		nome = URLEncoder.encode(nome, "UTF-8");
        		cognome = URLEncoder.encode(cognome, "UTF-8");
        	    HttpGet httpGet = new HttpGet(BASE_URL+"scopus?nome="+nome+"&cognome="+cognome);

        	    HttpResponse response = httpClient.execute(httpGet);
        	    System.out.println(response.toString());
        	    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        	    String content="", line;
        	    while ((line = br.readLine()) != null) {
        	         content = content + line;
        	    }
        	    //System.out.print(content);
        	    return content;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
	}
	
}
