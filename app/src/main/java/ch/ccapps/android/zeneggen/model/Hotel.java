package ch.ccapps.android.zeneggen.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.ccapps.android.zeneggen.R;

/**
 * Created by celineheldner on 30.07.15.
 */
public class Hotel implements Parcelable, Serializable{
    private String name;
    private int drawableResource;
    private String description;
    private String phonenumber;
    private String location;
    private String address;
    private String link;
    private String email;
    private String plz;
    private List<String> type = new ArrayList<String>();

    public Hotel(){}


    public Hotel(String nm, int drawable, String descr, String phone, String loc, List<String> typ, String addr, String plz, String link, String email){
        this.name = nm;
        this.drawableResource = drawable;
        this.description = descr;
        this.phonenumber = phone;
        this.location = loc;
        this.type = typ;
        this.address = addr;
        this.link = link;
        this.email = email;
        this.plz = plz;

    }

    public Hotel(String nm, int drawable, String descr, String phone, String loc, List<String> typ){
        this.name = nm;
        this.drawableResource = drawable;
        this.description = descr;
        this.phonenumber = phone;
        this.location = loc;
        this.type = typ;
    }


    public static List<Hotel> showHotels(){
        ArrayList<String> hotelOnly = new ArrayList<String>();
        hotelOnly.add("Hotel");

        ArrayList<String> hotelRest = new ArrayList<String>();
        hotelRest.add("Hotel");
        hotelRest.add("Restaurant");

        List<Hotel> hotels = new ArrayList<Hotel>();
        hotels.add(new Hotel("Pension Kastel", R.drawable.kastel,
                "Die Pension Kastel begrüßt Sie hoch " +
                        "in den Schweizer Alpen in Zeneggen mit Zimmer im Landhausstil " +
                        "und mit Panoramablick über Vispertal." +
                        " WLAN und Privatparkplätze sind kostenfrei verfügbar." +
                        " Alle Zimmer in der Pension Kastel besitzen einen Balkon und ein eigenes Bad mit kostenfreien Pflegeprodukten." +
                        " Die Minibar ist mit Getränken bestückt.\n" +"\n"+
                        "Die Pension Kastel verfügt über einen Speisesaal, wo das Abendessen nach vorheriger Reservierung serviert wird.",
                "+41 27 946 22 64",
                "Zeneggen",
                hotelOnly,"Dorfstrasse 112","3924","http://www.pension.ch/","kastel@gmx.ch"));
        hotels.add(new Hotel("Hotel Alpenblick", R.drawable.alpenblick,
                "Das Hotel Alpenblick liegt ruhig in Zeneggen und bietet ein Restaurant und Zimmer mit beeindruckendem Bergblick. Kostenfreies WLAN ist verfügbar.\n" +
                        "Die Zimmer bieten alle Sat-TV, ein eigenes Badezimmer mit einem Haartrockner sowie einen Schreibtisch, einen Safe und Bettwäsche.\n" +"\n"+
                        "Am Hotel Alpenblick steht Ihnen ein Garten mit einer Sonnenterrasse und Aussicht auf die umliegenden Berge zur Verfügung." +
                        " Konferenzeinrichtungen, eine gemeinschaftlich genutzte Lounge und eine Gepäckaufbewahrung gehören zu den weiteren Einrichtungen." +
                        " An der Unterkunft und in der Umgebung können Sie zahlreichen Aktivitäten wie Wandern nachgehen.",
                "+41 27 948 09 90",
                "Zeneggen",
                hotelOnly,"Bielweg 4","3924","http://www.alpenblickzeneggen.ch/de/willkommen/","info@alpenblickzeneggen.ch"));
        hotels.add(new Hotel("B&B Waldesruh", R.drawable.waldesruh,
                "Unser Haus besitzt im Erdgeschoss eine 3 ½ Zimmer-Wohnung und im 2. Stockwerk eine 5 Zimmer-Wohnung." +
                        " Die südliche Terrasse mit Kamin, der Aufenthaltsraum und" +
                        " die östliche Terrasse sowie ein gedeckter Sitz- und Aufenthaltsplatz auf der" +
                        " Westseite können von den Gästen mit genutzt werden."
                ,"+41 27 946 36 08","Bürchen",hotelOnly));
        hotels.add(new Hotel("Bistro", R.drawable.bistro,"Das kleine Bistro in der Dorfmitte bietet Dorfbewohnern und Gästen ein gemütlicher Treffpunkt." +
                "Das Bistro ist ausgestattet mit einer Terasse und Wifi Zugang. ","+41 27 946 8949","Zeneggen",hotelRest));
        return hotels;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Hotel> CREATOR  = new Parcelable.Creator<Hotel>() {
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    private Hotel(Parcel in) {
        setName(in.readString());
        setDrawableResource(in.readInt());
        setDescription(in.readString());
        setPhonenumber(in.readString());
        setLocation(in.readString());
        in.readList(getType(),String.class.getClassLoader());
        setPlz(in.readString());
        setAddress(in.readString());
        setEmail(in.readString());
        setLink(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.getDrawableResource());
        dest.writeString(this.description);
        dest.writeString(this.phonenumber);
        dest.writeString(this.location);
        dest.writeList(this.type);
        dest.writeString(this.plz);
        dest.writeString(this.address);
        dest.writeString(this.email);
        dest.writeString(this.link);
    }
}
