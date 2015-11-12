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


public class BooksGetter extends IServlet {

	public BooksGetter() {
	}

	@Override
	public HttpResponse processRequest(HttpRequest request, HttpResponse response) {
		try {
			String booksUrlString = "books.json";
			StringBuilder sb = new StringBuilder();
			File books = new File(booksUrlString);
			Scanner sc = new Scanner(books);

			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}

			Gson gson = new Gson();
			Book[] booksArray = gson.fromJson(sb.toString(), Book[].class);
			List<Book> booksList = new ArrayList<Book>(Arrays.asList(booksArray));
			String toWrite = "";
			for(Book b: booksList){
				toWrite = toWrite + "<br />Book:<br />Author: " + b.getAuthor() + "<br />Title: " + b.getTitle() + "<br />";
			}
//			Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
//			String arrayListToJson = gsonBuilder.toJson(booksList);

			File file = new File("file");

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(toWrite);
			bw.flush();
			bw.close();

			response = HttpResponseFactory.createRequestWithFile(file,
					Protocol.CLOSE, Protocol.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
