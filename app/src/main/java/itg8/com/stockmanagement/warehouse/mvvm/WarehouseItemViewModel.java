package itg8.com.stockmanagement.warehouse.mvvm;


import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.genericRv.ViewModel;

public class WarehouseItemViewModel  implements ViewModel {
    @Override
    public int layoutId() {
        return R.layout.item_rv_product;
    }

    @Override
    public void setModel(Object o) {

    }
}
