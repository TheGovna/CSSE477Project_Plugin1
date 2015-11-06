package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;


public class BooksGetter extends IServlet {

	public BooksGetter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {
			File file = new File("file");

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("I am books getter");
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
