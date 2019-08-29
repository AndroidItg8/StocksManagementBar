package itg8.com.stockmanagement.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itg8.com.stockmanagement.R;
import itg8.com.stockmanagement.home.model.ReportModel;


public class ProductAdapter  extends ExpandableRecyclerView<ProductAdapter> {
    public static final int TYPE_PERSON = 1001;

    public ProductAdapter(Context context) {
        super(context);
        setItems(getSampleItems());
    }

//    public static class PeopleListItem extends ExpandableRecyclerView.ListItem {
//        public String Text;
//
//        public PeopleListItem(String group) {
//            super(TYPE_HEADER);
//
//            Text = group;
//        }
//
//        public PeopleListItem(List<ReportModel> list) {
//            super(TYPE_PERSON);
//        }
//    }

    public class HeaderViewHolder extends ExpandableRecyclerView.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.imgArrow));
//
//            name = (TextView) view.findViewById(R.id.txt_header_name);
        }

        public void bind(int position) {
            super.bind(position);

        //    name.setText(visibleItems.get(position).Text);
        }
    }

    public class PersonViewHolder extends ExpandableRecyclerView.ViewHolder {
        EditText name;

        public PersonViewHolder(View view) {
            super(view);

//            name = (EditText) view.findViewById(R.id.edt_description);
        }

        public void bind(int position) {
       //     name.setText(name.getText());
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_header, parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.item_rv_content, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }

    private HashMap<String, List<ReportModel>> getSampleItems() {

        HashMap<String, List<ReportModel>> hashMap = new HashMap<>();
        List<ReportModel> models= new ArrayList<>();
        for(int i=0; i<=10; i++){
            ReportModel model = new ReportModel();
            model.setTemp(String.valueOf(i)+"model");
            model.setId(String.valueOf(i));
            models.add(model);
        }
        for(int i=0; i<=10; i++){

            hashMap.put(String.valueOf(i),models);
        }


//
//        List<PeopleListItem> items = new ArrayList<>();
//        items.add(new PeopleListItem("Friends"));
//        items.add(new PeopleListItem("", ""));
//        items.add(new PeopleListItem("Friends"));
//        items.add(new PeopleListItem("", ""));

//        for (int i=0; i<=10; i++){
//            PeopleListItem item = new PeopleListItem("NIP","RedRum");
//            items.add(item);
//        }
        return hashMap;
    }
}
