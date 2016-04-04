package info.androidhive.loginandregistration.activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Inštancie tejto triedy reprezentujú konkrétne úlohy. Prerobená verzia z KucharkaFragment a DatabazaTest2
 */
public class IdeaItem implements Parcelable {


    private int _id;
    private String short_name;
    private String actual_state;
    private String improved_state;
    private String advantages;
    private String costs;
    private  String time;
    private  String creator;
    private  String file_b;
    private  String file_a;
    private String priority;
    private String receiver;

    /**
     * prazdny konstruktor
     */
    public IdeaItem() {}

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
    public IdeaItem(Parcel in) {
        short_name = in.readString();
        actual_state = in.readString();
        improved_state = in.readString();
        advantages = in.readString();
        costs = in.readString();
        file_a = in.readString();
        file_b = in.readString();
        priority = in.readString();
        receiver = in.readString();
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
        return short_name;
    }

    /**
     * nastavi nazov ulohy
     * @param eve
     */
    public void setName(String eve) {
        this.short_name = eve;
    }

    /**
     * @return vrati kategoriu
     */
    public String getActState() {
        return actual_state;
    }

    /**
     * nastavi kategoriu
     * @param cath
     */
    public void setActState(String cath) {
        this.actual_state = cath;
    }

    /**
     * @return vrati datum
     */
    public String getImpState() {
        return improved_state;
    }

    /**
     * nastavi datum
     * @param dat
     */
    public void setImpState(String dat) {
        this.improved_state = dat;
    }

    /**
     * @return vrati prioritu
     */
    public String getAdv() {
        return advantages;
    }

    /**
     * nastavi prioritu
     * @param pri
     */
    public void setAdv(String pri) {
        this.advantages = pri;
    }

    /**
     * @return vrati popis
     */
    public String getCosts() {
        return costs;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setCosts(String desc) {
        this.costs = desc;
    }


    public String getTime() {
        return time;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setTime(String t) {
        this.time = t;
    }


    public String getCreator() {
        return creator;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setFileBefore(String fb) {
        this.file_b = fb;
    }


    public String getfileBefore() {
        return file_b;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setCreator(String c) {
        this.creator = c;
    }


    public String getPriority() {
        return priority;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setPriority(String fa) {
        this.priority = fa;
    }

    public String getReceiver() {
        return receiver;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setReceiver(String fa) {
        this.receiver = fa;
    }

    public String getFileAfter() {
        return file_a;
    }

    /**
     * nastavi popis
     * @param desc
     */
    public void setFileAfter(String fa) {
        this.file_a = fa;
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
        dest.writeString(short_name);
        dest.writeString(actual_state);
        dest.writeString(improved_state);
        dest.writeString(advantages);
        dest.writeString(costs);
        dest.writeString(file_a);
        dest.writeString(file_b);
        dest.writeString(priority);
        dest.writeString(receiver);
    }

    public static final Parcelable.Creator<IdeaItem> CREATOR
            = new Parcelable.Creator<IdeaItem>() {
        public IdeaItem createFromParcel(Parcel in) {
            return new IdeaItem(in);
        }

        public IdeaItem[] newArray(int size) {
            return new IdeaItem[size];
        }
    };
}
