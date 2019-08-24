package itg8.com.stockmanagement.common;

import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.Field;

public class SpinnerItemSelect implements AdapterView.OnItemSelectedListener {
    private Field field;
    private Object object;
    private String fieldText;
    private SpinnerGenericModel spinnerGenericModel;
    private OnItemSelectListener listener;

    public SpinnerItemSelect(String field) {
//        field.setAccessible(true);
    }

    public SpinnerItemSelect(Object object, String fieldName) {
        this.object = object;
        Class<?> clazz = object.getClass();
        if (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view==null)
            return;
        SpinnerGenericModel model= (SpinnerGenericModel) view.getTag();
        if(listener!=null)
            listener.onItemSelect(model);
        try {
            if(field!=null)
                if(object instanceof Integer)
                    field.set(object, Integer.parseInt(model.getId()));
                else if(object instanceof String){
                    field.set(object,model.getId());
                }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        if(view instanceof TextView) {
//            TextView t = (TextView) view;
//            field = t.getText().toString();
//            }
//            if(view instanceof AdapterView) {
//                spinnerGenericModel = (SpinnerGenericModel) parent.getItemAtPosition(position);
//                setSpinnerGenericModel(spinnerGenericModel);
//            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        field=null;
    }

    public void setOnItemAvailListener(OnItemSelectListener listener) {
        this.listener = listener;
    }


    public interface OnItemSelectListener{
        void onItemSelect(SpinnerGenericModel id);
    }

}
