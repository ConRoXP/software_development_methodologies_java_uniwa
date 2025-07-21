public class BookEntry {
    private String Author;
    private String Title;
    private int PubYear;
    private String ISBN;
    private String Category;
    private String Publisher;
    private String Cover;

    //Constructor
    public BookEntry(String A, String T, int PY, String I, String cat, String pub, String cv){
        Author= A;
        Title= T;
        PubYear= PY;
        ISBN= I;
        Category= cat;
        Publisher= pub;
        Cover= cv;
    }

    //Getters
    public String getAuthor(){
        return Author;
    }

    public String getTitle(){
        return Title;
    }

    public String getPY(){
        return Integer.toString(PubYear);
    }

    public String getISBN(){
        return ISBN;
    }

    public String getCat(){
        return Category;
    }

    public String getPub(){
        return Publisher;
    }

    public String getCv(){
        return Cover;
    }
}
