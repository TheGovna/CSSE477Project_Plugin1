package Plugin1;

public class Book {
	String author;
	String title;
	
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
