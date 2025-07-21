public class Cities {
    private String cityID;
    private String name;
    private String population;
    private String geo;
    private String perif; //state
    private String desc;

    //Constructor
    public Cities(String id, String nam, String pop, String ge, String per, String dsc){
        cityID= id;
        name= nam;
        population= pop;
        geo= ge;
        perif= per;
        desc= dsc;
    }

    //Getters
    public String getID(){
        return cityID;
    }

    public String getName(){
        return name;
    }

    public String getPop(){
        return population;
    }

    public String getGeo(){
        return geo;
    }

    public String getPerif(){
        return perif;
    }

    public String getDesc(){
        return desc;
    }
}
