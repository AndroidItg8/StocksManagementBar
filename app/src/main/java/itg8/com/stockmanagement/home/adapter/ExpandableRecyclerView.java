package itg8.com.stockmanagement.home.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import itg8.com.stockmanagement.home.model.ReportModel;

import static itg8.com.stockmanagement.home.adapter.ProductAdapter.TYPE_PERSON;

public abstract  class ExpandableRecyclerView <T extends ExpandableRecyclerView> extends RecyclerView.Adapter<ExpandableRecyclerView.ViewHolder> {
    protected Context mContext;
    protected HashMap<String,List<ReportModel>> allItems = new HashMap<>();
    protected List<T> visibleItems = new ArrayList<>();
    private List<Integer> indexList = new ArrayList<>();
    private SparseIntArray expandMap = new SparseIntArray();
    private int mode;

    protected static final int TYPE_HEADER = 1000;

    private static final int ARROW_ROTATION_DURATION = 150;

    public static final int MODE_NORMAL = 0;
    public static final int MODE_ACCORDION = 1;

    public ExpandableRecyclerView(Context context) {
        mContext = context;
    }

//    public static class ListItem {
//        public int ItemType;
//
//        public ListItem(int itemType) {
//            ItemType = itemType;
//        }
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return visibleItems == null ? 0 : visibleItems.size();
//        return allItems.size();
//        return 10;
    }

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(mContext).inflate(resourceID, viewGroup, false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public class HeaderViewHolder extends ViewHolder {
        ImageView arrow;

        public HeaderViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleExpandedItems(getAdapterPosition(), true);


                /*if(isExpanded(getLayoutPosition())){
                    collapseItems(getLayoutPosition(),false);
                }else {
                    expandItems(getLayoutPosition(),true);
                }*/
                }
            });
        }

        public HeaderViewHolder(View view, final ImageView arrow) {
            super(view);

            this.arrow = arrow;

            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleClick();
                }
            });
        }

        protected void handleClick() {
            if (toggleExpandedItems(getLayoutPosition(), false)) {
                openArrow(arrow);
            } else {
                closeArrow(arrow);
            }
        }

        public void bind(int position) {
            arrow.setRotation(isExpanded(position) ? 90 : 0);
        }
    }

    public boolean toggleExpandedItems(int position, boolean notify) {
        if (isExpanded(position)) {
            collapseItems(position, notify);
            return false;
        } else {
            expandItems(position, notify);

            if (mode == MODE_ACCORDION) {
                collapseAllExcept(position);
            }

            return true;
        }
    }

    public void expandItems(int position, boolean notify) {
        int count = 0;
        int index = indexList.get(position);
        int insert = position;

        Iterator it = allItems.entrySet().iterator();

        while (it.hasNext()) {
            insert++;
            count++;
            HashMap.Entry pair = (HashMap.Entry)it.next();
            visibleItems.add(insert, (T) pair.getValue());
            indexList.add(insert, Integer.parseInt(String.valueOf(pair.getKey())));
        }

//        for (int i = index + 1; i < allItems.size() && allItems.get(i) != TYPE_HEADER; i++) {
//            insert++;
//            count++;
//            visibleItems.add(insert, allItems.get(i));
//            indexList.add(insert, i);
//        }

        notifyItemRangeInserted(position + 1, count);
        int allItemsPosition = indexList.get(position);
        expandMap.put(allItemsPosition, 1);

        if (notify) {
            notifyItemChanged(position);
        }
    }

    public void collapseItems(int position, boolean notify) {
        int count = 0;
        int index = indexList.get(position);
        Iterator it = allItems.entrySet().iterator();
        while (it.hasNext()) {
            count++;
            visibleItems.remove(position + 1);
            indexList.remove(position + 1);
        }




//        for (int i = index + 1; i < allItems.size() && allItems.get(i).ItemType != TYPE_HEADER; i++) {
//            count++;
//            visibleItems.remove(position + 1);
//            indexList.remove(position + 1);
//        }



        notifyItemRangeRemoved(position + 1, count);
        int allItemsPosition = indexList.get(position);
        expandMap.delete(allItemsPosition);

        if (notify) {
            notifyItemChanged(position);
        }
    }


    protected boolean isExpanded(int position) {
        int allItemsPosition = indexList.get(position);
        return expandMap.get(allItemsPosition, -1) >= 0;



    }

    @Override
    public int getItemViewType(int position) {
        Iterator it = allItems.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
        if(pair.getKey()!=null){
            return TYPE_HEADER;
        }else{
            return TYPE_PERSON;
        }
        }
        return -1;
    }

    public void setItems(HashMap<String , List<ReportModel>> items) {
        allItems = items;
        List<T> visibleItems = new ArrayList<>();
        expandMap.clear();
        indexList.clear();

//        for (int i = 0; i < items.size(); i++) {
//            if (items.get(i).ItemType == TYPE_HEADER) {
//                indexList.add(i);
//                visibleItems.add(items.get(i).);
//            }
//        }


        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            indexList.add(Integer.parseInt(String.valueOf(pair.getKey())));
            visibleItems.addAll((Collection<? extends T>) pair.getValue());
//            visibleItems.addAll((T) pair.getValue());
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }

        this.visibleItems = visibleItems;
        notifyDataSetChanged();


    }


    protected void removeItemAt(int visiblePosition) {
        int allItemsPosition = indexList.get(visiblePosition);

        allItems.remove(allItemsPosition);
        visibleItems.remove(visiblePosition);
        incrementIndexList(allItemsPosition, visiblePosition, -1);
        incrementExpandMapAfter(allItemsPosition, -1);

        notifyItemRemoved(visiblePosition);


    }

    private void incrementExpandMapAfter(int position, int direction) {
        SparseIntArray newExpandMap = new SparseIntArray();

        for (int i = 0; i < expandMap.size(); i++) {
            int index = expandMap.keyAt(i);
            newExpandMap.put(index < position ? index : index + direction, 1);
        }

        expandMap = newExpandMap;
    }

    private void incrementIndexList(int allItemsPosition, int visiblePosition, int direction) {
        List<Integer> newIndexList = new ArrayList<>();

        for (int i = 0; i < indexList.size(); i++) {
            if (i == visiblePosition) {
                if (direction > 0) {
                    newIndexList.add(allItemsPosition);
                }
            }

            int val = indexList.get(i);
            newIndexList.add(val < allItemsPosition ? val : val + direction);
        }

        indexList = newIndexList;
    }

    public void collapseAll() {
        collapseAllExcept(-1);
    }

    public void collapseAllExcept(int position) {
        for (int i = visibleItems.size() - 1; i >= 0; i--) {
            if (i != position && getItemViewType(i) == TYPE_HEADER) {
                if (isExpanded(i)) {
                    collapseItems(i, true);
                }
            }
        }
    }

    public void expandAll() {
        for (int i = visibleItems.size() - 1; i >= 0; i--) {
            if (getItemViewType(i) == TYPE_HEADER) {
                if (!isExpanded(i)) {
                    expandItems(i, true);
                }
            }
        }
    }

    public static void openArrow(View view) {
        view.animate().setDuration(ARROW_ROTATION_DURATION).rotation(180);

    }

    public static void closeArrow(View view) {
        view.animate().setDuration(ARROW_ROTATION_DURATION).rotation(0);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
