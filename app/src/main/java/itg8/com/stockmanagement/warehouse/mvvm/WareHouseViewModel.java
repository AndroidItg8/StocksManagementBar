package itg8.com.stockmanagement.warehouse.mvvm;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import itg8.com.stockmanagement.MainActivity;
import itg8.com.stockmanagement.common.FragmentSupportBaseObservable;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.home.model.ReportModel;
import itg8.com.stockmanagement.warehouse.WarehouseDetailActivity;

public class WareHouseViewModel extends FragmentSupportBaseObservable {

    public ObservableBoolean isProgress;
    public ObservableList<ReportModel> list;
    public GenericAdapter<ReportModel, CategoryViewModel> genericCategoryAdapter;
    public GenericAdapter<ReportModel, ProductItemViewModel> genericProductAdapter;

    public GenericAdapter.OnItemClickListner listner = new GenericAdapter.OnItemClickListner() {
        @Override
        public void onItemClicked(Object o) {

            if((MainActivity)getFragment().getContext()!=null)
                ((MainActivity)getFragment().getContext()).startCustomActivity(o);

//            genericProductAdapter.notifyDataSetChanged();
        }
    };

    public GenericAdapter.OnItemClickListner listenerProduct = new GenericAdapter.OnItemClickListner() {
        @Override
        public void onItemClicked(Object o) {

            if((MainActivity)getFragment().getContext()!=null)
                ((MainActivity)getFragment().getContext()).startCustomActivity(o);


        }
    };

    public WareHouseViewModel(Fragment fragment) {
        super(fragment);
        list = new ObservableArrayList<>();
        setTempItem();
        isProgress = new ObservableBoolean(false);
        generateRvCategoryContent();
        generateRvProductContent();


    }

    private void generateRvCategoryContent() {
        CategoryViewModel itemModel = new CategoryViewModel();
        itemModel.setListener(listner);
        genericCategoryAdapter = new GenericAdapter<>(list, itemModel);
    }

    private void generateRvProductContent() {
        ProductItemViewModel itemModel = new ProductItemViewModel();
        itemModel.setListener(listenerProduct);
        genericProductAdapter = new GenericAdapter<>(list, itemModel);
    }


    @BindingAdapter(value = {"customCategoryAdapter"}, requireAll = false)
    public static void categoryRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }


    @BindingAdapter(value = {"customProAdapter"}, requireAll = false)
    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }


    public void setTempItem() {
        for (int i = 0; i < 10; i++) {
            ReportModel model = new ReportModel();
            model.setTemp(String.valueOf(i));
            list.add(model);

        }
    }
}
