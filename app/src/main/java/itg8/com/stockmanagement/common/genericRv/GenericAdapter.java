package itg8.com.stockmanagement.common.genericRv;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import itg8.com.stockmanagement.BR;


public  class GenericAdapter<T extends BaseObservable,V extends ViewModel> extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {

    private static final String TAG = "GenericAdapter";

    private List<? extends T> list;
    private V v;

    public GenericAdapter(List<? extends T> list,V  v) {
        this.list = list;
        this.v=v;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ViewDataBinding bind = DataBindingUtil.bind(LayoutInflater.from(parent.getContext()).inflate(i, parent, false));
        return new ViewHolder<>(bind);
    }

    @Override
    public void onBindViewHolder( @NonNull  ViewHolder viewHolder, int i) {
        final T model=list.get(i);
        v.setModel(model);
        viewHolder.getBinding().setVariable(BR.model,v);
        viewHolder.getBinding().setVariable(BR.position,i);
        viewHolder.getBinding().setVariable(BR.rawmodel,model);
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return v.layoutId();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+list.size());
        return list.size();
    }

    public class ViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

        V v;

        public ViewHolder(@NonNull V v) {
            super(v.getRoot());
            this.v=v;
        }

        public V getBinding(){
            return v;
        }

    }

    public interface OnItemClickListner extends BaseClickListner{

    }



}