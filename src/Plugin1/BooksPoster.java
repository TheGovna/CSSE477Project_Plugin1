package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;


public class BooksPoster extends IServlet {

	public BooksPoster() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {		
			String[] uri = request.getUri().split("/");

			String author = uri[3];
			String title = uri[4];

			String booksUrlString = "books.json";
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


