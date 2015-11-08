package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class BooksPutter extends IServlet {

	public BooksPutter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {
			String[] uri = request.getUri().split("/");
			String author = uri[3];
			String title = uri[4];
			
			String booksUrlString = "json.txt";
			URL url = this.getClass().getResource(booksUrlString);
			System.out.println(url);
			File books = new File(new URI(url.toString()));
			Book newBook = new Book(author, title);
			
			XStream xstream = new XStream();
	        //xstream.setMode(XStream.NO_REFERENCES);
	        xstream.alias("book", Book.class);

	        String newBookJson = xstream.toXML(newBook);
	        
	        StringBuilder sb = new StringBuilder();
	        Scanner sc = new Scanner(books);
	        
	        while (sc.hasNext()) {
	        	sb.append(sc.nextLine());
	        }
	        
	        sb.insert(sb.length() - 2, ", " + newBookJson);
			
//			File file = new File("file");
//
			BufferedWriter bw = new BufferedWriter(new FileWriter(books));
			bw.write(sb.toString());
			bw.flush();
			bw.close();
			
			response = HttpResponseFactory.createRequestWithFile(books,
					Protocol.CLOSE);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
