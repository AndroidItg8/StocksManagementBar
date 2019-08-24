package itg8.com.stockmanagement.warehouse.mvvm;

import android.view.View;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.common.genericRv.ViewModel;
import itg8.com.stockmanagement.home.model.ReportModel;

public class CategoryViewModel implements ViewModel {
    private GenericAdapter.OnItemClickListner listner;

    @Override
    public int layoutId() {
        return R.layout.item_rv_category;
    }

    @Override
    public void setModel(Object o) {

    }

    public void setListener(GenericAdapter.OnItemClickListner listner) {

        this.listner = listner;
    }

    public void onClickedCategory(View view, ReportModel model){
        if(listner!=null){
            listner.onItemClicked(model);
        }
    }
}
