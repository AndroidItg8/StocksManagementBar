package itg8.com.stockmanagement.home.mvvm;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.common.genericRv.ViewModel;
import itg8.com.stockmanagement.home.model.ReportModel;

public class ReportItemViewModel implements ViewModel {

    public ObservableArrayList<ReportModel> Observablelist;
    public GenericAdapter<ReportModel, ReportDetailItemViewModel> genericAdapter;

    public ReportItemViewModel() {
        Observablelist= new ObservableArrayList<>();
        genericRv();
        generateTempData();
    }

    private void generateTempData() {
        for (int i=0; i<10; i++){
            ReportModel model = new ReportModel();
            model.setTemp(String.valueOf(i));
            Observablelist.add(model);
            genericAdapter.notifyDataSetChanged();
        }
    }

    private void genericRv() {
        ReportDetailItemViewModel itemModel = new ReportDetailItemViewModel();
        genericAdapter = new GenericAdapter<>(Observablelist, itemModel);
    }

    @Override
    public int layoutId() {
        return R.layout.item_rv_report;
    }

    @Override
    public void setModel(Object o) {

    }

    @BindingAdapter(value = {"genericReport"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }
}
