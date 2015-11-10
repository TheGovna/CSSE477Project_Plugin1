package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class BooksPutter extends IServlet {

	public BooksPutter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {		
			String[] uri = request.getUri().split("/");
			
			//System.out.println("uri: " + Arrays.toString(uri));
			
			String author = uri[3];
			String title = uri[4];
			
			String booksUrlString = "books.json";
			//URL url = this.getClass().getResource(booksUrlString);
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			//System.out.println("booksUrl: " + url);
			//File books = new File(new URI(url.toString()));
			File booksFile = new File(booksUrlString);

			Book newBook = new Book(title, author);
			
			XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
				public HierarchicalStreamWriter createWriter(Writer writer) {
		            return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
		         }
			});
	        xstream.setMode(XStream.NO_REFERENCES);
	        xstream.alias("book", Book.class);

	        String newBookJson = xstream.toXML(newBook);
	        
	        StringBuilder sb = new StringBuilder();
	        Scanner sc = new Scanner(booksFile);
	        
	        while (sc.hasNext()) {
	        	sb.append(sc.nextLine());
	        }
	        
	        sb.insert(sb.length() - 1, ", " + newBookJson);
	        
	        System.out.println("sb: " + sb.toString());
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile));
			bw.write(sb.toString());
			bw.flush();
			bw.close();
			
			response = HttpResponseFactory.createRequestWithFile(booksFile,
					Protocol.CLOSE);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
