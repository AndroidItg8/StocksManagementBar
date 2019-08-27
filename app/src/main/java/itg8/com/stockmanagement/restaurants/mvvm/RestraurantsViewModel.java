package itg8.com.stockmanagement.restaurants.mvvm;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;


import itg8.com.stockmanagement.common.FragmentSupportBaseObservable;
import itg8.com.stockmanagement.common.genericRv.GenericAdapter;
import itg8.com.stockmanagement.home.model.ReportModel;
import itg8.com.stockmanagement.restaurants.tableview.TableViewAdapter;
import itg8.com.stockmanagement.restaurants.tableview.TableViewListener;
import itg8.com.stockmanagement.restaurants.tableview.TableViewModel;
import itg8.com.stockmanagement.widget.tableview.TableView;

public class RestraurantsViewModel extends FragmentSupportBaseObservable {

    private static final String TAG = "RestraurantsViewModel";
    private  TableView tableLayout;
    private  Context context;
    public ObservableBoolean isProgress;
    private ObservableList<ReportModel> list;
    public GenericAdapter<ReportModel, RestaurantItemViewModel> genericPurchaseAdapter;

    public RestraurantsViewModel(Fragment fragment, TableView tableLayout) {
        super(fragment);
        this.context = fragment.getContext();
        this.tableLayout = tableLayout;
        isProgress = new ObservableBoolean(false);
        generateTableLayout();


        //list= new ObservableArrayList<>();

     //   genericRv();
     //   generateTempItem();
    }

    private void generateTableLayout() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(context, tableViewModel);

        tableLayout.setAdapter(tableViewAdapter);
        tableLayout.setTableViewListener(new TableViewListener(tableLayout));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(tableViewModel.getColumnHeaderList(), tableViewModel
                .getRowHeaderList(), tableViewModel.getCellList());

        Log.d(TAG, "generateTableLayout: "+tableViewModel.getColumnHeaderList().size()+" Row Header"+ tableViewModel.getRowHeaderList().size()+" Cell List"+ tableViewModel.getCellList().size());


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
        RestaurantItemViewModel itemModel = new RestaurantItemViewModel();
        genericPurchaseAdapter = new GenericAdapter<>(list, itemModel);
    }





//    @BindingAdapter(value = {"customRestraurantsAdapter"}, requireAll = false)
//    public static void productRecyclerview(RecyclerView recyclerView, GenericAdapter adapter) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setAdapter(adapter);
//    }
}
