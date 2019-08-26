package itg8.com.stockmanagement.warehouse.mvvm;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Queue;

import itg8.com.stockmanagement.common.FragmentSupportBaseObservable;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.home.model.ReportModel;

public class PurchaseViewModel  extends FragmentSupportBaseObservable {

    public ObservableBoolean isProgress;
    private ObservableList<ReportModel> list;
    public GenericAdapter<ReportModel, PurchaseItemViewModel> genericPurchaseAdapter;

    public PurchaseViewModel(Fragment fragment) {
        super(fragment);
        isProgress = new ObservableBoolean(false);
        list= new ObservableArrayList<>();
        genericRv();
        generateTempItem();
    }

    private void generateTempItem() {
        for(int i=0; i<=10; i++){
            ReportModel model  = new ReportModel();
            model.setTemp(String.valueOf(i));
            list.add(model);
            genericPurchaseAdapter.notifyDataSetChanged();
        }
    }



    private void genericRv() {
        PurchaseItemViewModel itemModel = new PurchaseItemViewModel();
        genericPurchaseAdapter = new GenericAdapter<>(list, itemModel);
    }





    @BindingAdapter(value = {"customPurchaseAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

}
