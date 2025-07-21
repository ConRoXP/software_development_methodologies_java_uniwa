public class Entities {
    private String ID;
    private String name;
    private String address;
    private String category;
    private String cityID;

    //Constructor
    public Entities(String id, String nam, String addr, String cat, String cid){
        ID= id;
        name= nam;
        address= addr;
        category= cat;
        cityID= cid;
    }

    //Getters
    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getCat(){
        return category;
    }

    public String getID(){
        return cityID;
    }
}
