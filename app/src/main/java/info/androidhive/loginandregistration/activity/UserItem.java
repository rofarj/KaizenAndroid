package info.androidhive.loginandregistration.activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Inštancie tejto triedy reprezentujú konkrétne úlohy. Prerobená verzia z KucharkaFragment a DatabazaTest2
 */
public class UserItem implements Parcelable {


    private int _id;
    private String name;
    private String surname;
    private String user_email;
    private String company;
    private String date;
    private String phone;

    /**
     * prazdny konstruktor
     */
    public UserItem() {}

/*
    public EventItem(int id, String eve, String cath, String dat, String pri, String des){
        this._id = id;
        this.uloha = eve;
        this.kategoria = cath;
        this.datum = dat;
        this.priorita = pri;
        this.popis = des;
    }
*/

    /**
     * Nacitanie dat z parcelable objektu.
     */
    public UserItem(Parcel in) {
        name = in.readString();
        surname = in.readString();
        user_email = in.readString();
        company = in.readString();
        phone = in.readString();
        date = in.readString();
    }

    /**
     * nastavi id
     * @param _id
     */
    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id(){ return _id; }

    /**
     * @return vrati nazov ulohy
     */
    public String getName() {
        return name;
    }

    /**
     * nastavi nazov ulohy
     * @param eve
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * @return vrati kategoriu
     */
    public String getSurname() {
        return surname;
    }

    /**
     * nastavi kategoriu
     * @param cath
     */
    public void setSurname(String cath) {
        this.surname = cath;
    }

    /**
     * @return vrati datum
     */
    public String getUserName() {
        return user_email;
    }

    /**
     * nastavi datum
     * @param dat
     */
    public void setUserName(String dat) {
        this.user_email = dat;
    }

    /**
     * @return vrati prioritu
     */
    public String getCompany() {
        return company;
    }

    /**
     * nastavi prioritu
     * @param pri
     */
    public void setCompany(String pri) {
        this.company = pri;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * nastavi prioritu
     * @param pri
     */
    public void setPhone(String pri) {
        this.phone = pri;
    }

    public String getDate() {
        return date;
    }

    /**
     * nastavi prioritu
     * @param pri
     */
    public void setDate(String pri) {
        this.date = pri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Ulozenie dat do Parcel objektu
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(user_email);
        dest.writeString(company);
        dest.writeString(phone);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<UserItem> CREATOR = new Parcelable.Creator<UserItem>() {
        public UserItem createFromParcel(Parcel in) {
            return new UserItem(in);
        }

        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };
}
