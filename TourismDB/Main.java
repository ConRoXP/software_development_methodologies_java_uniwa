import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame{
    private static ArrayList<Cities> cityArray;
    private static ArrayList<Entities> entArray;
    private JTextField cityName;
    private JTextArea outputArea;
    private JButton searchButton;
    private JComboBox<String> comboList;

    //Loading data from files
    public static void load(){
        try{
            FileReader file= new FileReader("C:\\cities.txt");
            BufferedReader BR= new BufferedReader(file);
            boolean eof= false;
            cityArray= new ArrayList<Cities>();

            Cities tempCity;
            while(!eof){
                String line= BR.readLine();
                if(line== null){
                    eof= true;
                }
                else{
                    String tokens[]= line.split("#");
                    if(tokens.length== 6){
                        tempCity= new Cities(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                        cityArray.add(tempCity);
                    }
                    else System.out.println("Invalid entries for a city");
                }
            }
            BR.close();
        }
        catch(IOException e){
            System.out.println("Error reading Cities file");
        }

        try{
            FileReader file2= new FileReader("C:\\entities.txt");
            BufferedReader BR2= new BufferedReader(file2);
            boolean eof= false;
            entArray= new ArrayList<Entities>();
            
            Entities tempEntity;
            while(!eof){
                String line2= BR2.readLine();
                if(line2== null){
                    eof= true;
                }
                else{
                    String tokens2[]= line2.split("#");
                    if(tokens2.length== 5){
                        tempEntity= new Entities(tokens2[0], tokens2[1], tokens2[2], tokens2[3], tokens2[4]);
                        entArray.add(tempEntity);
                    }
                    else System.out.println("Invalid entity attributes");
                }
            }
            BR2.close();
        }
        catch(IOException e){
            System.out.println("Error reading Entities file");
        }
    }

    //Creating GUI
    public Main(){
                             //Museums, Historical Landmarks, Universities, Hospitals, Churches, Sports Facilities, Airports, Ports, Public Services, Industries, All Categories
        String Categories[]= {"Μουσεία", "Ιστορικά Μνημεία", "Πανεπιστήμια", "Νοσοκομεία", "Εκκλησίες", "Αθλητικές Εγκ.", "Αεροδρόμια", "Λιμάνια", "Δημόσιες Υπηρεσίες", "Βιομηχανίες", "Όλες οι κατηγορίες"};

        setTitle("Tourist Info");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("City Name:"));
        cityName= new JTextField(20);
        add(cityName);

        comboList= new JComboBox<String>(Categories);
        add(comboList);

        searchButton= new JButton("Search");
        add(searchButton);
        searchButton.addActionListener(e-> search());

        outputArea= new JTextArea(10, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    //Searching for tourists' criteria
    private void search(){
        String name= cityName.getText();
        String selectedCat= (String)comboList.getSelectedItem();
        String cityID= "NULL";
        String output;
        boolean entitiesFound= false;
        int index =-1;

        for(int i= 0; i< cityArray.size(); i++){
            if(cityArray.get(i).getName().equals(name)){
                cityID= cityArray.get(i).getID();
                index= i;
                break;
            }
        }
        if(index> -1){
                            //All categories
            if(selectedCat!= "Όλες οι κατηγορίες"){
                for(int i= 0; i< entArray.size(); i++){
                    if(entArray.get(i).getID().equals(cityID) && entArray.get(i).getCat().equals(selectedCat)){
                        output= "" + entArray.get(i).getName() + ", " + entArray.get(i).getAddress() + "\n";
                        outputArea.append(output);
                        entitiesFound= true;
                    }
                }
                if(entitiesFound== false) outputArea.append("Requested landmark not found in this city\n");
            }
            else{
                output= "" + cityArray.get(index).getName() + ", " + cityArray.get(index).getPop() + ", " + cityArray.get(index).getGeo() + ", " + cityArray.get(index).getPerif() + ", " + cityArray.get(index).getDesc() + "\n";
                outputArea.append(output);

                for(int i= 0; i< entArray.size(); i++){
                    if(entArray.get(i).getID().equals(cityID)){
                        output= "" + entArray.get(i).getName() + ", " + entArray.get(i).getAddress() + ", " + entArray.get(i).getCat() + "\n";
                        outputArea.append(output);
                        entitiesFound= true;
                    }
                }
                if(entitiesFound== false){
                    outputArea.append("No entities regarding city");
                }
            }
        }
        else outputArea.append("City not found\n");

    }
    public static void main(String[] args){
        load();
        Main mm= new Main();
    }
}
