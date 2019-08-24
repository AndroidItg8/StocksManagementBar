package itg8.com.stockmanagement.common.genericRv;


import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(ViewDataBinding itemView) {
        super(itemView.getRoot());
        setBindable(itemView);
    }

    public abstract void setBindable(ViewDataBinding bindable);

    public abstract void bind(T object);
}
