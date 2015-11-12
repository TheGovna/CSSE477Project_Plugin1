package Plugin1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Book1Getter extends IServlet {

	public Book1Getter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request,
			HttpResponse response) {
		try {
			String[] uri = request.getUri().split("/");
			
			String author = uri[3];
			String title = uri[4];
			
			String booksUrlString = "books.json";
			
			StringBuilder sb = new StringBuilder();
			File books = new File(booksUrlString);
			Scanner sc = new Scanner(books);

			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}
			
			// Get book from the list if it exists
			Gson gson = new Gson();
			Book[] booksArray = gson.fromJson(sb.toString(), Book[].class);
			List<Book> booksList = new ArrayList<Book>(Arrays.asList(booksArray));
			Book book = null;
			
			for (Book b : booksList) {
				if (b.getAuthor().equals(author) && b.getTitle().equals(title)) {
					book = b;
//					Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
//					String bookToJson = gsonBuilder.toJson(book);
					
					File file = new File("file");
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write("Author: " + book.getAuthor() + "<br />Title: " + book.getTitle());
					bw.flush();
					bw.close();
					
					response = HttpResponseFactory.createRequestWithFile(file,
							Protocol.CLOSE, Protocol.GET);
					break;
				}
			}

			if (book == null) {
				response = HttpResponseFactory.createRequest(Protocol.NOT_FOUND_CODE + "", Protocol.CLOSE);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
