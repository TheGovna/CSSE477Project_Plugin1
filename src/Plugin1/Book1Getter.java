package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;


public class Book1Getter extends IServlet {

	public Book1Getter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {
			String[] uri = request.getUri().split("/");
			String author = uri[3];
			String title = uri[4];
			
			// Look through books.json
			StringBuilder sb = new StringBuilder();
			File books = new File("file:resources\\books.txt");
			Scanner sc = new Scanner(books);
			
			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}
			
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("product", Book.class);
//			Book book = (List<Book>)xstream.fromXML(sb.toString());
			
			File file = new File("file");

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("I am book 1 getter");
			bw.flush();
			bw.close();
			
			response = HttpResponseFactory.createRequestWithFile(file,
					Protocol.CLOSE);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
