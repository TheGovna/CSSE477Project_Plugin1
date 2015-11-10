package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;

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
			// Look through books.json
			String booksUrlString = "books.json";
			StringBuilder sb = new StringBuilder();
			File books = new File(booksUrlString);
			Scanner sc = new Scanner(books);

			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}

			Gson gson = new Gson();
			Book[] booksArray = gson.fromJson(sb.toString(), Book[].class);
			System.out.println("booksArray: " + booksArray.length);

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
