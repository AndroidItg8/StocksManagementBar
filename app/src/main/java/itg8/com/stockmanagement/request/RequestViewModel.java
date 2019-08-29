package itg8.com.stockmanagement.request;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.common.genericRv.ViewModel;

public class RequestViewModel implements ViewModel {

    public RequestViewModel() {
    }

    @Override
    public int layoutId() {
        return R.layout.item_rv_request;
    }

    @Override
    public void setModel(Object o) {

    }
}
