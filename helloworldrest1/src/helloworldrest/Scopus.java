package helloworldrest;

import java.net.*; 
import java.io.*; 
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

public class Scopus  {
	
	private String name;
	private String surname;
	
	public Scopus(String name, String surname){
		this.name=name;
		this.surname=surname;
	}

       public static  String encodeForm(Map<String,String> data)  {
              StringBuffer  sb=new  StringBuffer();
              try  {
            	  	 sb.append('?');
                     for(String  key : data.keySet())  {
                    	 	if(key.contains("authSubject")){
                    	 		data.get(key).split(",");
                    	 		for(int i=0;i<4;i++){
	                    	 		sb.append(URLEncoder.encode(key,  "UTF-8"));
	                                sb.append('=');
	                                sb.append(URLEncoder.encode(data.get(key).split(",")[i],  "UTF-8"));                            
	                                sb.append('&');
                    	 		}
                    	 		continue;
                    	 	}
                            sb.append(URLEncoder.encode(key,  "UTF-8"));
                            sb.append('=');
                            sb.append(URLEncoder.encode(data.get(key),  "UTF-8"));                            
                            sb.append('&');
                     }
              }  catch  (UnsupportedEncodingException e)  {
                     throw new  RuntimeException("Unsupported encoding UTF-8");
              }
              return sb.toString();
       }
       
       public String webScraping()  throws  Exception  {
    	   String  baseUrl="https://www.scopus.com/results/authorNamesList.uri";
           Map<String,String>  form=new  HashMap<String,String>();
           
           form.put("sort",  "count-f");
           form.put("src",  "al");
           form.put("sid",  "9C81832D8E9BE10BF148E14B14D67774.wsnAw8kcdt7IPYLO0V48gA:220");
           form.put("sot",  "al");
           form.put("sdt",  "al");
           form.put("sl",  "54");
           form.put("s",  "AUTH--LAST--NAME(EQUALS("+surname+"))%20AND%20AUTH--FIRST("+name+")");
           form.put("st1",  surname);
           form.put("st2",  name);
           form.put("orcidId",  "");
           form.put("selectionPageSearch",  "anl");
           form.put("reselectAuthor",  "false");
           form.put("activeFlag",  "false");
           form.put("showDocument",  "false");
           form.put("resultsPerPage",  "20");
           form.put("offset",  "1");
           form.put("jtp",  "false");
           form.put("currentPage",  "1");
           form.put("previousSelectionCount",  "0");
           form.put("tooManySelections",  "false");
           form.put("previousResultCount",  "0");           
           form.put("authSubject",  "LFSC,HLSC,PHSC,SOSC");
           form.put("exactAuthorSearch",  "true");
           form.put("showFullList",  "false");
           form.put("authorPreferredName",  "");
           form.put("origin",  "searchauthorfreelookup");
           form.put("affiliationId",  "");
           form.put("txGid",  "9C81832D8E9BE10BF148E14B14D67774.wsnAw8kcdt7IPYLO0V48gA:22");
           
           String body=encodeForm(form);                                     
                      
           URL urls=new  URL(baseUrl);
           HttpURLConnection  conn=(HttpURLConnection)urls.openConnection();
           System.out.println(conn);
           conn.setRequestMethod("POST");
           conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
           conn.setDoOutput(true);
           
           PrintWriter  pw=new  PrintWriter(
                         new  OutputStreamWriter(
                                       conn.getOutputStream(),  "UTF-8"));
           pw.print(body);
           pw.close();
           
           int  status=conn.getResponseCode();
           System.out.println("RESPONSE  CODE:  "+status);
           System.out.println("CONTENT  TYPE:   "+conn.getContentType());
           String result = "";
           if (status>=200 &&   status<=299)  {
                  InputStreamReader  in=new  InputStreamReader(conn.getInputStream(), "UTF-8");
                  int c;
                  while ((c=in.read())!=-1)
                         result+=(char)c;
                  in.close();
           }
           Document doc = Jsoup.parse(result, "https://www.scopus.com");
           Elements elementi = doc.body().getElementsByAttributeValue("title","View author details with grouped authors");
           JSONArray ja = new JSONArray();           
           
           for (Element elemento: elementi){
        	   JSONObject mainObj = new JSONObject();
        	   mainObj.put("nome", elemento.text());
           	   Document doc2 = Jsoup.connect(elemento.attr("href")).get();
           	   Element hindex=doc2.body().getElementsByClass("addInfoRow row3").get(0);
           	   mainObj.put("h-index",hindex.getElementsByClass("valueColumn").text());
           	   mainObj.put("area",doc2.getElementById("subjAreas").child(0).text());      
           	   ja.put(mainObj);
           }
           
           System.out.println(result);
           return ja.toString();           
    }
}
