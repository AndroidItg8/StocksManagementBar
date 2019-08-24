package itg8.com.stockmanagement.common;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

public class SpinnerGenericModel extends BaseObservable implements Parcelable {

    private String id;

    public SpinnerGenericModel() {
    }

    public SpinnerGenericModel(String text, String value) {

        this.id = text;
        this.value = value;
    }

    protected SpinnerGenericModel(Parcel in) {
        id = in.readString();
        value = in.readString();
    }

    public static final Creator<SpinnerGenericModel> CREATOR = new Creator<SpinnerGenericModel>() {
        @Override
        public SpinnerGenericModel createFromParcel(Parcel in) {
            return new SpinnerGenericModel(in);
        }

        @Override
        public SpinnerGenericModel[] newArray(int size) {
            return new SpinnerGenericModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String text) {
        this.id = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(value);
    }
}