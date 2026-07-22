import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class BookBrowser extends JFrame{
    private static ArrayList<BookEntry> AL;
    private JTextField authorField, titleField, PYField, ISBNField, CatField, PubField, CvField;
    private JButton nextButton, backButton;
    private static int valid_books= 0;
    private static int currentBook= 1;

    //Loading books from a file
    public static void load(){
        try{
            BookEntry tmpEntry;
            AL= new ArrayList<BookEntry>();

            FileReader file= new FileReader("C:\\Books.txt");
            BufferedReader BR= new BufferedReader(file);
            boolean eof= false;

            while(!eof){
                String line= BR.readLine();
                if(line== null){
                    eof= true;
                }
                else{
                    int tempPub= 0;
                    String tokens[]= line.split("#");
                    if(tokens.length== 7){
                        try{
                            tempPub= Integer.parseInt(tokens[2]);
                        }
                        catch(NumberFormatException e){
                            System.out.println("Error: Publication year is not a number");
                        }
                        if(tempPub> 1900 && tempPub< 2100){
                            if(tokens[6].equals("HC")){
                                tmpEntry= new BookEntry(tokens[0], tokens[1], tempPub, tokens[3], tokens[4], tokens[5], "Hard Cover");
                                AL.add(tmpEntry);
                                valid_books++;
                            }
                            else if(tokens[6].equals("SC")){
                                tmpEntry= new BookEntry(tokens[0], tokens[1], tempPub, tokens[3], tokens[4], tokens[5], "Soft Cover");
                                AL.add(tmpEntry);
                                valid_books++;
                            }
                            else System.out.println("Invalid cover");
                        }
                        else System.out.println("Invalid publication year");
                    }
                    else System.out.println("Invalid number of attributes");
                }
            }
            BR.close();
        }
        catch(IOException e){
            System.out.println("Something went wrong reading the file");
        }
    }
    
    //Creating the GUI
    public BookBrowser(){

        setTitle("Book Browser");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Author:"));
        authorField= new JTextField(30);
        authorField.setEditable(false);
        add(authorField);

        add(new JLabel("Title:"));
        titleField= new JTextField(100);
        titleField.setEditable(false);
        add(titleField);

        add(new JLabel("Publication Year:"));
        PYField= new JTextField(4);
        PYField.setEditable(false);
        add(PYField);

        add(new JLabel("ISBN:"));
        ISBNField= new JTextField(15);
        ISBNField.setEditable(false);
        add(ISBNField);

        add(new JLabel("Category:"));
        CatField= new JTextField(30);
        CatField.setEditable(false);
        add(CatField);

        add(new JLabel("Publisher:"));
        PubField= new JTextField(40);
        PubField.setEditable(false);
        add(PubField);

        add(new JLabel("Cover:"));
        CvField= new JTextField(10);
        CvField.setEditable(false);
        add(CvField);

        nextButton= new JButton("Next Entry");
        backButton= new JButton("Back");
        add(nextButton);
        add(backButton);

        nextButton.addActionListener(e-> next());
        backButton.addActionListener(e-> back());

        if(valid_books> 0) show(currentBook);
        setVisible(true);
    }

    //This function makes the appropriate info show up on the GUI
    private void show(int BookNum){
        BookEntry tmp_Entry;
        tmp_Entry= AL.get(BookNum-1);

        authorField.setText(tmp_Entry.getAuthor());
        titleField.setText(tmp_Entry.getTitle());
        PYField.setText(tmp_Entry.getPY());
        ISBNField.setText(tmp_Entry.getISBN());
        CatField.setText(tmp_Entry.getCat());
        PubField.setText(tmp_Entry.getPub());
        CvField.setText(tmp_Entry.getCv());
    }

    private void next(){
        if(currentBook< valid_books){
            currentBook++;
            show(currentBook);
        }
    }

    private void back(){
        if(currentBook> 1){
            currentBook--;
            show(currentBook);
        }
        
    }

    public static void main(String[] args){
        load();
        BookBrowser BB= new BookBrowser();
    }
}
