package helloworldrest;

import java.io.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//Sets  the path to base URL +  /users
@Path("/users")
public class Hello {
	/*
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Users written in TEXT format!";
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Users written  in  XML  format" + "</hello>";
	}
*/
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {		
		try {			
			Scopus scopus = new Scopus("mario","vento");
			return scopus.webScraping();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return "<html> " + "<title>" + "Hello Users" + "</title>" + "<body><h1>"
			//	+ "Hello Users written  in HTML  format" + "</body></h1>" + "</html> ";
		return null;
		
	}
/*loca
	@Path("/{user}/")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHelloUser(@PathParam("user") String user_name) {
		return "<html> " + "<title>" + "Hello " + user_name + "</title>" + "<body><h1>" + "Hello  " + user_name
				+ " written in HTML  format" + "</body></h1>" + "</html> ";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHelloWithParams(@QueryParam("user") String user_name) {
		return "<html> " + "<title>" + "Hello " + user_name + "</title>" + "<body><h1>" + "Hello  " + user_name
				+ " written in HTML  format" + "</body></h1>" + "</html> ";
	}
/*
	@POST
	@Path("/post")
	public Response p ostStrMsg(String msg) {
		String output = "POST  REQUEST:  " + msg;
		return Response.status(200).entity(output).build();
	}
*/
}