package helloworldrest;

import java.net.*; 
import java.io.*; 
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
           //URL  url=new  URL("https://www.scopus.com/results/authorNamesList.uri?sort=count-f&src=al&sid=12ABEFC7BA5E633A2CA3A12D8253D8A4.wsnAw8kcdt7IPYLO0V48gA%3a110&sot=al&sdt=al&sl=54&s=AUTH--LAST--NAME%28EQUALS%28vento%29%29+AND+AUTH--FIRST%28mario%29&st1=vento&st2=mario&orcidId=&selectionPageSearch=anl&reselectAuthor=false&activeFlag=false&showDocument=false&resultsPerPage=20&offset=1&jtp=false&currentPage=1&previousSelectionCount=0&tooManySelections=false&previousResultCount=0&authSubject=LFSC&authSubject=HLSC&authSubject=PHSC&authSubject=SOSC&exactAuthorSearch=true&showFullList=false&authorPreferredName=&origin=searchauthorfreelookup&affiliationId=&txGid=12ABEFC7BA5E633A2CA3A12D8253D8A4.wsnAw8kcdt7IPYLO0V48gA%3a11&");
    	   URL  url=new  URL("https://www.scopus.com/results/authorNamesList.uri?sort=count-f&src=al&sid=12ABEFC7BA5E633A2CA3A12D8253D8A4.wsnAw8kcdt7IPYLO0V48gA%3a110&sot=al&sdt=al&sl=54&s=AUTH--LAST--NAME%28EQUALS%28"+surname+"%29%29+AND+AUTH--FIRST%28"+name+"%29&st1="+surname+"&st2="+name+"&orcidId=&selectionPageSearch=anl&reselectAuthor=false&activeFlag=false&showDocument=false&resultsPerPage=20&offset=1&jtp=false&currentPage=1&previousSelectionCount=0&tooManySelections=false&previousResultCount=0&authSubject=LFSC&authSubject=HLSC&authSubject=PHSC&authSubject=SOSC&exactAuthorSearch=true&showFullList=false&authorPreferredName=&origin=searchauthorfreelookup&affiliationId=&txGid=12ABEFC7BA5E633A2CA3A12D8253D8A4.wsnAw8kcdt7IPYLO0V48gA%3a11&");
           //URL  url=new  URL("https://www.scopus.com/search/submit/authorFreeLookup.uri");
    	   //String  url="https://www.scopus.com/results/authorNamesList.uri";
           Map<String,String>  form=new  HashMap<String,String>();
          
           form.put("sort",  "count-f");
           form.put("src",  "al");
           form.put("sid",  "9C81832D8E9BE10BF148E14B14D67774.wsnAw8kcdt7IPYLO0V48gA:220");
           form.put("sot",  "al");
           form.put("sdt",  "al");
           form.put("sl",  "54");
           form.put("s",  "AUTH--LAST--NAME(EQUALS("+surname+")) AND AUTH--FIRST("+name+")");
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
           form.put("authSubject",  "LFSC");
           form.put("authSubject",  "HLSC");
           form.put("authSubject",  "PHSC");
           form.put("authSubject",  "SOSC");
           form.put("exactAuthorSearch",  "true");
           form.put("showFullList",  "false");
           form.put("authorPreferredName",  "");
           form.put("origin",  "searchauthorfreelookup");
           form.put("affiliationId",  "");
           form.put("txGid",  "9C81832D8E9BE10BF148E14B14D67774.wsnAw8kcdt7IPYLO0V48gA:22");
           /*
           form.put("origin",  "searchauthorfreelookup");
           form.put("freeSearch",  "true");
           form.put("src",  "");
           form.put("edit",  "");
           form.put("poppUp",  "");
           form.put("exactSearch",  "on");
           form.put("searchterm1",  "vento");
           form.put("searchterm2",  "mario");
           form.put("institute",  "");
           form.put("submitButtonName",  "Search");
           form.put("orcidId",  "");
           form.put("authSubject",  "LFSC");
           form.put("_authSubject",  "on");
           form.put("authSubject",  "HLSC");
           form.put("_authSubject",  "on");
           form.put("authSubject",  "PHSC");
           form.put("_authSubject",  "on");
           form.put("authSubject",  "SOSC");
           form.put("_authSubject",  "on");
           */
           
           
           
           String body=encodeForm(form);                                     
           
           //HttpURLConnection  conn=(HttpURLConnection)new URL(url+body).openConnection();
           HttpURLConnection  conn=(HttpURLConnection)url.openConnection();
           System.out.println(conn);
           conn.setRequestMethod("POST");
           conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
           conn.setDoOutput(true);
           
           PrintWriter  pw=new  PrintWriter(
                         new  OutputStreamWriter(
                                       conn.getOutputStream(),  "UTF-8"));
          // pw.print(body);
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
           String nomi="";
           for (Element elemento: elementi){
        	   nomi+="Nome "+elemento.outerHtml();
           	   nomi+="URL ID "+elemento.attr("href");
           	   Document doc2 = Jsoup.connect(elemento.attr("href")).get();
           	   Element hindex=doc2.body().getElementsByClass("addInfoRow row3").get(0);
           	   //for (Element elementos: hindex){
           		nomi+="h-index "+hindex.getElementsByClass("valueColumn");           	  
           	   //}
           	              	  
           }
           
           System.out.println(result);
           return Jsoup.parse(nomi).text();           
    }
}
