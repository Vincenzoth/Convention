package helloworldrest;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//Sets  the path to base URL +  /users
@Path("/users")
public class RestPath {
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C:\\Convegno\\Logo\\";
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
			Scopus scopus = new Scopus("angelo","santoro");
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
*/
	@GET
	@Path("/convegni")
	public Response postStrMsg(String msg) {
		String output = null;
		try {
			String sql="SELECT * FROM convegni";
			Database db = new Database();
			output=db.databaseJsonSelect(sql);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String output = "POST  REQUEST:  " + msg;
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("upload")
	@Consumes("multipart/form-data")
	@Produces("application/json")
	public void uploadFile(@Context HttpServletRequest request) {		
		String fileName = "";
		ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator fileIterator;                
        
        
		File directoryRoot = new File("C:\\Convegno\\Logo\\");
		// if the directory does not exist, create it
		if (!directoryRoot.exists()) {
			directoryRoot.mkdirs();	
		}
        
		try {
			fileIterator = upload.getItemIterator(request);				
			while (fileIterator.hasNext()) {								
	            FileItemStream item = fileIterator.next();
	            if ("recFile".equals(item.getFieldName())){	              
	              fileName = SERVER_UPLOAD_LOCATION_FOLDER + item.getName();
	              //name=item.getName();
	              saveFile(item.openStream(),fileName);	    	              
	            }
	        }
		} catch (FileUploadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// save uploaded file to a defined location on the server
		private void saveFile(InputStream uploadedInputStream,
		String serverLocation) {
			try {			
				int read = 0;
				byte[] bytes = new byte[1024];
				OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
				System.out.println(serverLocation);
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					outpuStream.write(bytes, 0, read);
				}
				outpuStream.flush();
				outpuStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}