package com.example.wallet.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private int id;
    private String username;
    public String userfio;
    private String country;
    private String city;
    private String avatarlink;
    private String password;
    private int place;
    private String frendslistid;
    private int capital;
    private String balansinsber;
    private String balansintinkoff;
    private String balansinvtb;
    private String balansinalfa;
    private String balansinotkritie;
    private int placeincity;
    private int placeincountry;



    // Конструктор по умолчанию нужен для Spring
    public Person() {
    }
    public String getUserfio() {
        return userfio;
    }

    public void setUserfio(String userfio) {
        this.userfio = userfio;
    }
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPlaceincity() {
        return placeincity;
    }

    public void setPlaceincity(int placeincity) {
        this.placeincity = placeincity;
    }

    public int getPlaceincountry() {
        return placeincountry;
    }

    public void setPlaceincountry(int placeincountry) {
        this.placeincountry = placeincountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getAvatarlink() {
        return avatarlink;
    }

    public void setAvatarlink(String avatalink) {
        this.avatarlink = avatalink;
    }

    public String getFrendslistid() {
        return frendslistid;
    }

    public void setFrendslistid(String frendslistid) {
        this.frendslistid = frendslistid;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public String getBalansinsber() {
        return balansinsber;
    }

    public void setBalansinsber(String balansinsber) {
        this.balansinsber = balansinsber;
    }

    public String getBalansintinkoff() {
        return balansintinkoff;
    }

    public void setBalansintinkoff(String balansintinkoff) {
        this.balansintinkoff = balansintinkoff;
    }

    public String getBalansinvtb() {
        return balansinvtb;
    }

    public void setBalansinvtb(String balansinvtb) {
        this.balansinvtb = balansinvtb;
    }

    public String getBalansinalfa() {
        return balansinalfa;
    }

    public void setBalansinalfa(String balansinalfa) {
        this.balansinalfa = balansinalfa;
    }

    public String getBalansinotkritie() {
        return balansinotkritie;
    }

    public void setBalansinotkritie(String balansinotkritie) {
        this.balansinotkritie = balansinotkritie;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public Person(String username, String userfio, String country, String city, String password) {
        this.username = username;
        this.userfio = userfio;
        this.country = country;
        this.city = city;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(userfio);
        dest.writeString(avatarlink);
        dest.writeString(frendslistid);
        dest.writeInt(capital);
        dest.writeString(balansinsber);
        dest.writeString(balansintinkoff);
        dest.writeString(balansinvtb);
        dest.writeString(balansinalfa);
        dest.writeString(balansinotkritie);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(password);
        dest.writeInt(place);
        dest.writeInt(placeincity);
        dest.writeInt(placeincountry);
    }
    protected Person(Parcel in) {
        id = in.readInt();
        username = in.readString();
        userfio = in.readString();
        avatarlink = in.readString();
        frendslistid = in.readString();
        capital = in.readInt();
        balansinsber = in.readString();
        balansintinkoff = in.readString();
        balansinvtb = in.readString();
        balansinalfa = in.readString();
        balansinotkritie = in.readString();
        country = in.readString();
        city = in.readString();
        password = in.readString();
        place = in.readInt();
        placeincity = in.readInt();
        placeincountry = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };


}