package itg8.com.stockmanagement.common.genericRv;

public interface ViewModel<T> {
    int layoutId();
    void setModel(T t);
}
