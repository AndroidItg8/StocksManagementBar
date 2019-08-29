package itg8.com.stockmanagement.home.mvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;

import com.google.android.material.navigation.NavigationView;

import itg8.com.stockmanagement.common.GenericSpinnerAdapter;
import itg8.com.stockmanagement.common.SpinnerGenericModel;
import itg8.com.stockmanagement.common.SpinnerItemSelect;
import itg8.com.stockmanagement.databinding.ActivityMainBinding;

public class HomeViewModel  extends BaseObservable {

    private Context context;
    public ObservableBoolean isfab;
    public ObservableBoolean isBottomView;


    public HomeViewModel(Context context) {
        this.context = context;
        isfab= new ObservableBoolean(false);
        isBottomView= new ObservableBoolean(false);
    }





}
