import java.util.*;

public class Constants {
    public class Building {
        String name;
        double latLoc;
        double longLoc;

        Building(String name, double latLoc, double longLoc) {
            this.name = name;
            this.latLoc = latLoc;
            this.longLoc = longLoc;
        }
    }

    public HashMap<String, Building> allBuildings = new HashMap<>();

    public void setAllBuildings() {
        Building building = new Building("RTC", 34.0201738, -118.2886748);
        Building building1 = new Building("ACC", 34.0191839,-118.2877323);
        Building building2 = new Building("THH", 34.0222316,-118.2867506);
        Building building3 = new Building("VKC", 34.0212585,-118.2861912);
        Building building4 = new Building("SAL", 34.0194829,-118.2916722);
        Building building5 = new Building("KAP", 34.0224089,-118.2932023);
        Building building6 = new Building("JFF", 34.0187184,-118.2846094);
        Building building7 = new Building("HOH", 34.0187226,-118.2874227);
        Building building8 = new Building("SGM", 34.0214389,-118.2912184);
        Building building9 = new Building("GFS", 33.9588286,-118.8619084);
        Building building10 = new Building("SLH", 34.0195725,-118.2897702);
        Building building11 = new Building("KDC", 34.023499,-118.2874897);
        Building building12 = new Building("WPH", 34.0219437,-118.2859994);
        Building building13 = new Building("ASC", 34.0219238,-118.3538509);
        Building building14 = new Building("LVL", 34.021853,-118.285136);
        Building building15 = new Building("DML", 34.020144,-118.2859253);
        Building building16 = new Building("LAW", 34.0201437,-118.2924914);
        Building building17 = new Building("GER", 34.020279,-118.2926275);

        allBuildings.put(building.name, building);
        allBuildings.put(building1.name, building1);
        allBuildings.put(building2.name, building2);
        allBuildings.put(building3.name, building3);
        allBuildings.put(building4.name, building4);
        allBuildings.put(building5.name, building5);
        allBuildings.put(building6.name, building6);
        allBuildings.put(building7.name, building7);
        allBuildings.put(building8.name, building8);
        allBuildings.put(building9.name, building9);
        allBuildings.put(building10.name, building10);
        allBuildings.put(building11.name, building11);
        allBuildings.put(building12.name, building12);
        allBuildings.put(building13.name, building13);
        allBuildings.put(building14.name, building14);
        allBuildings.put(building15.name, building15);
        allBuildings.put(building16.name, building16);
        allBuildings.put(building17.name, building17);
    }

}
