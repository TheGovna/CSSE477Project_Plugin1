package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import plugins.IServlet;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;


public class BooksDeleter extends IServlet {

	public BooksDeleter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {
			String[] uri = request.getUri().split("/");
			
			String author = uri[3];
			String title = uri[4];
			
			String booksUrlString = "books.json";
			File booksFile = new File(booksUrlString);
			
			StringBuilder sb = new StringBuilder();
			File books = new File(booksUrlString);
			Scanner sc = new Scanner(books);

			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}
			
			// Delete this new book from the list if it exists
			Gson gson = new Gson();
			Book[] booksArray = gson.fromJson(sb.toString(), Book[].class);
			List<Book> booksList = new ArrayList<Book>(Arrays.asList(booksArray));
			
			for (Book b : booksList) {
				if (b.getAuthor().equals(author) && b.getTitle().equals(title)) {
					booksList.remove(b);
					break;
				}
			}
			
			// Serialize new list
			Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			String arrayListToJson = gsonBuilder.toJson(booksList);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile));
			bw.write(arrayListToJson);
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
