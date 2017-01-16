package eu.ceitgroup.AndroidKaizen.activity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Palo on 4/5/2016.
 */
public class ButtonHelper implements Parcelable{
    private String button;

    public ButtonHelper() {}

    public void set_button(String _id) {
        this.button = _id;
    }

    public ButtonHelper(Parcel in) {
        button = in.readString();
    }

    public String get_button(){ return button; }


    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Ulozenie dat do Parcel objektu
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(button);

    }

    public static final Parcelable.Creator<ButtonHelper> CREATOR
            = new Parcelable.Creator<ButtonHelper>() {
        public ButtonHelper createFromParcel(Parcel in) {
            return new ButtonHelper(in);
        }

        public ButtonHelper[] newArray(int size) {
            return new ButtonHelper[size];
        }
    };
}
