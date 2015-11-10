package Plugin1;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

//@XStreamAlias("BOOKS")
public class Books {
	//@XStreamAlias("BOOK")
	@XStreamImplicit(itemFieldName="BOOK")
	public List<Book> books;
}
