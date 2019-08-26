package itg8.com.stockmanagement.warehouse.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.home.model.ReportModel;
import itg8.com.stockmanagement.warehouse.WarehouseDetailActivity;

public class WarehouseDetailViewModel  extends BaseObservable {
public ObservableBoolean isProgress;
private ObservableArrayList<ReportModel> list;
    public GenericAdapter<ReportModel, ProductItemViewModel> dispatchedGenericAdapter;

    public WarehouseDetailViewModel(WarehouseDetailActivity fragment) {
        isProgress= new ObservableBoolean(false);
        list = new ObservableArrayList<>();
        genericRv();
        generateTempItem();
    }

    private void generateTempItem() {
        for(int i=0; i<=10; i++){
            ReportModel model  = new ReportModel();
            model.setTemp(String.valueOf(i));
            list.add(model);
            dispatchedGenericAdapter.notifyDataSetChanged();
        }
    }



    private void genericRv() {
        ProductItemViewModel itemModel = new ProductItemViewModel();
        dispatchedGenericAdapter = new GenericAdapter<>(list, itemModel);
    }





    @BindingAdapter(value = {"dispatchedAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

}
