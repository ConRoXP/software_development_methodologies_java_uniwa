public class Island {
    private final String name;
    private final String complex;
    private final String area;
    private final String maxAltitude;
    private final String population;
    private final String region;
    private final String prefecture;

    public Island(String name, String complex, String area, String maxAltitude,
                  String population, String region, String prefecture) {
        this.name = name;
        this.complex = complex;
        this.area = area;
        this.maxAltitude = maxAltitude;
        this.population = population;
        this.region = region;
        this.prefecture = prefecture;
    }

    public String getName() { return name; }
    public String getComplex() { return complex; }
    public String getArea() { return area; }
    public String getMaxAltitude() { return maxAltitude; }
    public String getPopulation() { return population; }
    public String getRegion() { return region; }
    public String getPrefecture() { return prefecture; }

    //Reply for client
    @Override
    public String toString() {
        return "Ονομα: " + name + "\n" +
               "Συμπλεγμα: " + complex + "\n" +
               "Εκταση: " + area + "\n" +
               "Υψομετρο(μεγιστο): " + maxAltitude + "\n" +
               "Πληθυσμος: " + population + "\n" +
               "Περιφερεια: " + region + "\n" +
               "Νομος: " + prefecture;
    }
}
