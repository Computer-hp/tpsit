import java.util.ArrayList;


public class Library
{
    ArrayList<Book> books;

    public Library()
    {
        this.books = new ArrayList<Book>();
    }


    public void addBook(Book book)
    {
        books.add(book);
    }

    public Book getBook(String title)
    {
        for (Book book : books)
            if (book.getTitle() == title)
                return book;
        
        return null;
    }

    public void removeBook(String title)
    {
        for (int i = 0; i < books.size(); ++i)
            if (books.get(i).getTitle() == title)
                books.remove(i);
    }
}
