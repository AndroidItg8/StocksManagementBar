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

package itg8.com.stockmanagement.widget.tableview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import itg8.com.stockmanagement.widget.tableview.adapter.AbstractTableAdapter;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.CellRecyclerView;
import itg8.com.stockmanagement.widget.tableview.filter.Filter;
import itg8.com.stockmanagement.widget.tableview.handler.ColumnSortHandler;
import itg8.com.stockmanagement.widget.tableview.handler.FilterHandler;
import itg8.com.stockmanagement.widget.tableview.handler.ScrollHandler;
import itg8.com.stockmanagement.widget.tableview.handler.SelectionHandler;
import itg8.com.stockmanagement.widget.tableview.layoutmanager.CellLayoutManager;
import itg8.com.stockmanagement.widget.tableview.layoutmanager.ColumnHeaderLayoutManager;
import itg8.com.stockmanagement.widget.tableview.listener.ITableViewListener;
import itg8.com.stockmanagement.widget.tableview.listener.scroll.HorizontalRecyclerViewListener;
import itg8.com.stockmanagement.widget.tableview.listener.scroll.VerticalRecyclerViewListener;
import itg8.com.stockmanagement.widget.tableview.sort.SortState;


/**
 * Created by evrencoskun on 19/06/2017.
 */

public interface ITableView {

    void addView(View child, ViewGroup.LayoutParams params);

    boolean hasFixedWidth();

    boolean isIgnoreSelectionColors();

    boolean isShowHorizontalSeparators();

    boolean isShowVerticalSeparators();

    boolean isSortable();

    @NonNull
    CellRecyclerView getCellRecyclerView();

    @NonNull
    CellRecyclerView getColumnHeaderRecyclerView();

    @NonNull
    CellRecyclerView getRowHeaderRecyclerView();

    @NonNull
    ColumnHeaderLayoutManager getColumnHeaderLayoutManager();

    @NonNull
    CellLayoutManager getCellLayoutManager();

    @NonNull
    LinearLayoutManager getRowHeaderLayoutManager();

    @NonNull
    HorizontalRecyclerViewListener getHorizontalRecyclerViewListener();

    @NonNull
    VerticalRecyclerViewListener getVerticalRecyclerViewListener();

    @Nullable
    ITableViewListener getTableViewListener();

    @NonNull
    SelectionHandler getSelectionHandler();

    @Nullable
    ColumnSortHandler getColumnSortHandler();

    @NonNull
    DividerItemDecoration getHorizontalItemDecoration();

    @NonNull
    DividerItemDecoration getVerticalItemDecoration();

    @NonNull
    SortState getSortingStatus(int column);

    @Nullable
    SortState getRowHeaderSortingStatus();

    void scrollToColumnPosition(int column);

    void scrollToColumnPosition(int column, int offset);

    void scrollToRowPosition(int row);

    void scrollToRowPosition(int row, int offset);

    void showRow(int row);

    void hideRow(int row);

    boolean isRowVisible(int row);

    void showAllHiddenRows();

    void clearHiddenRowList();

    void showColumn(int column);

    void hideColumn(int column);

    boolean isColumnVisible(int column);

    void showAllHiddenColumns();

    void clearHiddenColumnList();

    int getShadowColor();

    int getSelectedColor();

    int getUnSelectedColor();

    int getSeparatorColor();

    void sortColumn(int columnPosition, @NonNull SortState sortState);

    void sortRowHeader(@NonNull SortState sortState);

    void remeasureColumnWidth(int column);

    int getRowHeaderWidth();

    void setRowHeaderWidth(int rowHeaderWidth);

    @Nullable
    AbstractTableAdapter getAdapter();

    /**
     * Filters the whole table using the provided Filter object which supports multiple filters.
     *
     * @param filter The filter object.
     */
    void filter(@NonNull Filter filter);

    /**
     * Retrieves the FilterHandler of the TableView.
     *
     * @return The FilterHandler of the TableView.
     */
    @Nullable
    FilterHandler getFilterHandler();

    /**
     * Retrieves the ScrollHandler of the TableView.
     *
     * @return The ScrollHandler of the TableView.
     */
    @NonNull
    ScrollHandler getScrollHandler();
}
