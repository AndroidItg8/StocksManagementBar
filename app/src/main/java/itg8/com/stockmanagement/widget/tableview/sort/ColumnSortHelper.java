/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package itg8.com.stockmanagement.widget.tableview.sort;

import androidx.annotation.NonNull;



import java.util.ArrayList;
import java.util.List;

import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import itg8.com.stockmanagement.widget.tableview.layoutmanager.ColumnHeaderLayoutManager;

/**
 * Created by evrencoskun on 15.12.2017.
 */

public class ColumnSortHelper {
    @NonNull
    private List<Directive> mSortingColumns = new ArrayList<>();
    @NonNull
    private ColumnHeaderLayoutManager mColumnHeaderLayoutManager;

    public ColumnSortHelper(@NonNull ColumnHeaderLayoutManager columnHeaderLayoutManager) {
        this.mColumnHeaderLayoutManager = columnHeaderLayoutManager;
    }

    private void sortingStatusChanged(int column, @NonNull SortState sortState) {
        AbstractViewHolder holder = mColumnHeaderLayoutManager.getViewHolder(column);

        if (holder != null) {
            if (holder instanceof AbstractSorterViewHolder) {
                ((AbstractSorterViewHolder) holder).onSortingStatusChanged(sortState);

            } else {
                throw new IllegalArgumentException("Column Header ViewHolder must extend " +
                        "AbstractSorterViewHolder");
            }
        }
    }

    public void setSortingStatus(int column, @NonNull SortState status) {
        Directive directive = getDirective(column);
        if (directive != EMPTY_DIRECTIVE) {
            mSortingColumns.remove(directive);
        }
        if (status != SortState.UNSORTED) {
            mSortingColumns.add(new Directive(column, status));
        }

        sortingStatusChanged(column, status);
    }

    public void clearSortingStatus() {
        mSortingColumns.clear();
    }

    public boolean isSorting() {
        return mSortingColumns.size() != 0;
    }

    @NonNull
    public SortState getSortingStatus(int column) {
        return getDirective(column).direction;
    }

    @NonNull
    private Directive getDirective(int column) {
        for (int i = 0; i < mSortingColumns.size(); i++) {
            Directive directive = mSortingColumns.get(i);
            if (directive.column == column) {
                return directive;
            }
        }
        return EMPTY_DIRECTIVE;
    }

    private static class Directive {
        private int column;
        @NonNull
        private SortState direction;

        public Directive(int column, @NonNull SortState direction) {
            this.column = column;
            this.direction = direction;
        }
    }

    @NonNull
    private static Directive EMPTY_DIRECTIVE = new Directive(-1, SortState.UNSORTED);
}
