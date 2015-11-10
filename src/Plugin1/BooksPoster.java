package Plugin1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
			
			// Create a new book
			Book newBook = new Book(title, author);
			
			StringBuilder sb = new StringBuilder();
			File books = new File(booksUrlString);
			Scanner sc = new Scanner(books);

			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}

			// Add this new book to the list of existing books
			Gson gson = new Gson();
			Book[] booksArray = gson.fromJson(sb.toString(), Book[].class);
			List<Book> booksList = new ArrayList<Book>(Arrays.asList(booksArray));
			booksList.add(newBook);
			
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


