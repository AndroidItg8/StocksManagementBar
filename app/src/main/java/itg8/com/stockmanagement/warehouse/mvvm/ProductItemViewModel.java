package itg8.com.stockmanagement.warehouse.mvvm;

import android.util.Log;
import android.view.View;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.common.genericRv.ViewModel;
import itg8.com.stockmanagement.home.model.ReportModel;

public class ProductItemViewModel implements ViewModel {
    private GenericAdapter.OnItemClickListner listenerProduct;
    private static final String TAG = "ProductItemViewModel";
    public ProductItemViewModel() {
    }

    @Override
    public int layoutId() {
        return R.layout.item_rv_report_detail;
    }

    @Override
    public void setModel(Object o) {

    }

    public void setListener(GenericAdapter.OnItemClickListner listenerProduct) {
        this.listenerProduct = listenerProduct;
    }

    public void itemClicked(View view, ReportModel model){
        Log.d(TAG, "itemClicked: ");
    }
}
