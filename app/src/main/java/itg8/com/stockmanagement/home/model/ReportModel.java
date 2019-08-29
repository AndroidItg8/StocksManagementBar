package itg8.com.stockmanagement.home.model;


import androidx.databinding.BaseObservable;

public class ReportModel  extends BaseObservable {

    public ReportModel() {
    }
    private String temp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
