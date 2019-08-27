package itg8.com.stockmanagement.restaurants.mvvm;

import android.view.View;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.genericRv.ViewModel;
import itg8.com.stockmanagement.home.model.ReportModel;

public class RestaurantItemViewModel  implements ViewModel {


    public RestaurantItemViewModel() {
    }

    @Override
    public int layoutId() {
        return R.layout.item_rv_restaurant;
    }

    @Override
    public void setModel(Object o) {

    }
    public void itemClicked(View view, ReportModel model){

    }


}
