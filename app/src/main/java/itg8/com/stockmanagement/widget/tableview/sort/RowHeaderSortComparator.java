package itg8.com.stockmanagement.widget.tableview.sort;

import androidx.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by cedricferry on 6/2/18.
 */

public class RowHeaderSortComparator extends AbstractSortComparator implements Comparator<ISortableModel> {

    public RowHeaderSortComparator(@NonNull SortState sortState) {
        this.mSortState = sortState;
    }

    @Override
    public int compare(ISortableModel o1, ISortableModel o2) {
        if (mSortState == SortState.DESCENDING) {
            return compareContent(o2.getContent(), o1.getContent());
        } else {
            return compareContent(o1.getContent(), o2.getContent());
        }
    }
}
