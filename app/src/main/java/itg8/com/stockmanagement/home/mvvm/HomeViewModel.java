package itg8.com.stockmanagement.home.mvvm;

import android.content.Context;
import android.widget.Spinner;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;

import itg8.com.stockmanagement.common.GenericSpinnerAdapter;
import itg8.com.stockmanagement.common.SpinnerGenericModel;
import itg8.com.stockmanagement.common.SpinnerItemSelect;

public class HomeViewModel  extends BaseObservable {
    public ObservableList<SpinnerGenericModel> users;
    private Context context;
    public ObservableBoolean isfab;
    public ObservableBoolean isBottomView;
    public SpinnerItemSelect.OnItemSelectListener userItemListener = new SpinnerItemSelect.OnItemSelectListener() {
        @Override
        public void onItemSelect(SpinnerGenericModel id) {

        }
    };


    public HomeViewModel(Context context) {
        this.context = context;
        users = new ObservableArrayList<>();
        isfab= new ObservableBoolean(false);
        isBottomView= new ObservableBoolean(false);
        createData();
    }

    private void createData() {
        for (int i=0; i<9; i++){
            SpinnerGenericModel model = new SpinnerGenericModel(String.valueOf(i), "user "+String.valueOf(i));
            users.add(model);
        }
    }

    @BindingAdapter(value = {"customEntriesUser", "customUserListener"}, requireAll = false)
    public static void bindSpinnerAdapter(Spinner spinner, ObservableList<SpinnerGenericModel> allContriesObs, SpinnerItemSelect.OnItemSelectListener listener) {
        spinner.setAdapter(new GenericSpinnerAdapter(spinner.getContext(), allContriesObs));
//        if (model != null) {
            SpinnerItemSelect itemDate = new SpinnerItemSelect("AppType");
            spinner.setOnItemSelectedListener(itemDate);
            itemDate.setOnItemAvailListener(listener);
//        }

//        listener.onItemSelect(model.setState(Integer.parseInt(id)));
    }

}
