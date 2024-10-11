public class Book
{
    private String title;
    private String author;
    private int publicationDate;


    public Book()
    {
        title = "";
        author = "";
        publicationDate = 0;
    }

    public Book(String title, String author, int publicationDate)
    {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public int getPublicationDate()
    {
        return publicationDate;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setPublicationDate(int publicationDate)
    {
        this.publicationDate = publicationDate;
    }


    @Override
    public String toString()
    {
        // utile nel caso in cui si dovessero concatenare tante stringhe;
        // StringBuilder result = new StringBuilder(); 
        
        return "Titolo: " + title + ", Author: " + author + ", Publication Date: " + publicationDate;
    }
}