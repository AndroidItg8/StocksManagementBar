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

package itg8.com.stockmanagement.widget.tableview.pagination;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import itg8.com.stockmanagement.widget.tableview.ITableView;
import itg8.com.stockmanagement.widget.tableview.adapter.AdapterDataSetChangedListener;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.CellRecyclerViewAdapter;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.RowHeaderRecyclerViewAdapter;
import itg8.com.stockmanagement.widget.tableview.filter.FilterChangedListener;
import itg8.com.stockmanagement.widget.tableview.sort.ColumnForRowHeaderSortComparator;
import itg8.com.stockmanagement.widget.tableview.sort.ColumnSortComparator;
import itg8.com.stockmanagement.widget.tableview.sort.ColumnSortStateChangedListener;
import itg8.com.stockmanagement.widget.tableview.sort.ISortableModel;
import itg8.com.stockmanagement.widget.tableview.sort.RowHeaderForCellSortComparator;
import itg8.com.stockmanagement.widget.tableview.sort.RowHeaderSortComparator;
import itg8.com.stockmanagement.widget.tableview.sort.SortState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pagination implements IPagination {

    private static final int DEFAULT_ITEMS_PER_PAGE = 10;
    private int itemsPerPage;
    private int currentPage;
    private int pageCount;
    @NonNull
    private List<List<ISortableModel>> originalCellData;
    @NonNull
    private List<ISortableModel> originalRowData;
    @Nullable
    private RowHeaderRecyclerViewAdapter<ISortableModel> mRowHeaderRecyclerViewAdapter;
    @Nullable
    private CellRecyclerViewAdapter<List<ISortableModel>> mCellRecyclerViewAdapter;
    @Nullable
    private OnTableViewPageTurnedListener onTableViewPageTurnedListener;

    /**
     * Basic constructor, TableView instance is required.
     *
     * @param tableView The TableView to be paginated.
     */
    public Pagination(@NonNull ITableView tableView) {
        this(tableView, DEFAULT_ITEMS_PER_PAGE, null);
    }

    /**
     * Applies pagination to the supplied TableView with number of items per page.
     *
     * @param tableView    The TableView to be paginated.
     * @param itemsPerPage The number of items per page.
     */
    public Pagination(@NonNull ITableView tableView, int itemsPerPage) {
        this(tableView, itemsPerPage, null);
    }

    /**
     * Applies pagination to the supplied TableView with number of items per page and an
     * OnTableViewPageTurnedListener for handling changes in the TableView pagination.
     *
     * @param tableView    The TableView to be paginated.
     * @param itemsPerPage The number of items per page.
     * @param listener     The OnTableViewPageTurnedListener for the TableView.
     */
    public Pagination(@NonNull ITableView tableView, int itemsPerPage, @Nullable OnTableViewPageTurnedListener listener) {
        initialize(tableView, itemsPerPage, listener);
    }

    @SuppressWarnings("unchecked")
    private void initialize(@NonNull ITableView tableView, int itemsPerPage, @Nullable OnTableViewPageTurnedListener listener) {
        this.onTableViewPageTurnedListener = listener;
        this.itemsPerPage = itemsPerPage;
        this.mRowHeaderRecyclerViewAdapter = (RowHeaderRecyclerViewAdapter) tableView
                .getRowHeaderRecyclerView().getAdapter();
        this.mCellRecyclerViewAdapter = (CellRecyclerViewAdapter) tableView.getCellRecyclerView()
                .getAdapter();
        tableView.getColumnSortHandler().addColumnSortStateChangedListener(columnSortStateChangedListener);
        tableView.getAdapter().addAdapterDataSetChangedListener(adapterDataSetChangedListener);
        tableView.getFilterHandler().addFilterChangedListener(filterChangedListener);
        this.originalCellData = tableView.getAdapter().getCellRecyclerViewAdapter().getItems();
        this.originalRowData = tableView.getAdapter().getRowHeaderRecyclerViewAdapter().getItems();
        this.currentPage = 1;
        reloadPages();
    }

    private void reloadPages() {
        paginateData();
        goToPage(currentPage);
    }

    private void paginateData() {
        int start, end;
        List<List<ISortableModel>> currentPageCellData = new ArrayList<>();
        List<ISortableModel> currentPageRowData = new ArrayList<>();
        // No pagination if itemsPerPage is 0, all data will be loaded into the TableView.
        if (itemsPerPage == 0) {
            currentPageCellData.addAll(originalCellData);
            currentPageRowData.addAll(originalRowData);
            pageCount = 1;
            start = 0;
            end = currentPageCellData.size();
        } else {
            start = (currentPage * itemsPerPage) - itemsPerPage;
            end = (currentPage * itemsPerPage) > originalCellData.size() ?
                    originalCellData.size() : (currentPage * itemsPerPage);

            for (int x = start; x < end; x++) {
                currentPageCellData.add(originalCellData.get(x));
                currentPageRowData.add(originalRowData.get(x));
            }

            // Using ceiling to calculate number of pages, e.g. 103 items of 10 items per page
            // will result to 11 pages.
            pageCount = (int) Math.ceil((double) originalCellData.size() / itemsPerPage);
        }

        // Sets the paginated data to the TableView.
        mRowHeaderRecyclerViewAdapter.setItems(currentPageRowData, true);
        mCellRecyclerViewAdapter.setItems(currentPageCellData, true);

        // Dispatches TableView changes to Listener interface
        if (onTableViewPageTurnedListener != null) {
            onTableViewPageTurnedListener.onPageTurned(currentPageCellData.size(), start, end - 1);
        }
    }

    @Override
    public void nextPage() {
        currentPage = currentPage + 1 > pageCount ? currentPage : ++currentPage;
        paginateData();
    }

    @Override
    public void previousPage() {
        currentPage = currentPage - 1 == 0 ? currentPage : --currentPage;
        paginateData();
    }

    @Override
    public void goToPage(int page) {
        currentPage = (page > pageCount || page < 1) ? (page > pageCount && pageCount > 0 ? pageCount : currentPage) : page;
        paginateData();
    }

    @Override
    public void setItemsPerPage(int numItems) {
        itemsPerPage = numItems;
        currentPage = 1;
        paginateData();
    }

    @Override
    public void setOnTableViewPageTurnedListener(@Nullable OnTableViewPageTurnedListener onTableViewPageTurnedListener) {
        this.onTableViewPageTurnedListener = onTableViewPageTurnedListener;
    }

    @Override
    public void removeOnTableViewPageTurnedListener() {
        this.onTableViewPageTurnedListener = null;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public boolean isPaginated() {
        return itemsPerPage > 0;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private AdapterDataSetChangedListener adapterDataSetChangedListener =
            new AdapterDataSetChangedListener() {
                @Override
                public void onRowHeaderItemsChanged(@NonNull List rowHeaderItems) {
                    originalRowData = new ArrayList<>(rowHeaderItems);
                    reloadPages();
                }

                @Override
                public void onCellItemsChanged(@NonNull List cellItems) {
                    originalCellData = new ArrayList<>(cellItems);
                    reloadPages();
                }
            };

    @NonNull
    private FilterChangedListener<ISortableModel> filterChangedListener =
            new FilterChangedListener<ISortableModel>() {
                @Override
                public void onFilterChanged(@NonNull List<List<ISortableModel>> filteredCellItems, @NonNull List<ISortableModel> filteredRowHeaderItems) {
                    originalCellData = new ArrayList<>(filteredCellItems);
                    originalRowData = new ArrayList<>(filteredRowHeaderItems);
                    reloadPages();
                }

                @Override
                public void onFilterCleared(@NonNull List<List<ISortableModel>> originalCellItems, @NonNull List<ISortableModel> originalRowHeaderItems) {
                    originalCellData = new ArrayList<>(originalCellItems);
                    originalRowData = new ArrayList<>(originalRowHeaderItems);
                    reloadPages();
                }
            };

    @NonNull
    private ColumnSortStateChangedListener columnSortStateChangedListener =
            new ColumnSortStateChangedListener() {
                @Override
                public void onColumnSortStatusChanged(int column, @NonNull SortState sortState) {
                    paginateOnColumnSort(column, sortState);
                }

                @Override
                public void onRowHeaderSortStatusChanged(@NonNull SortState sortState) {
                    paginateOnColumnSort(-1, sortState);
                }
            };

    private void paginateOnColumnSort(int column, @NonNull SortState sortState) {
        List<ISortableModel> sortedRowHeaderList = new ArrayList<>(originalRowData);
        List<List<ISortableModel>> sortedList = new ArrayList<>(originalCellData);
        if (sortState != SortState.UNSORTED) {
            if (column == -1) {
                Collections.sort(sortedRowHeaderList, new RowHeaderSortComparator(sortState));
                RowHeaderForCellSortComparator rowHeaderForCellSortComparator =
                        new RowHeaderForCellSortComparator(
                                originalRowData,
                                originalCellData,
                                sortState
                        );

                Collections.sort(sortedList, rowHeaderForCellSortComparator);
            } else {
                Collections.sort(sortedList, new ColumnSortComparator(column, sortState));
                ColumnForRowHeaderSortComparator columnForRowHeaderSortComparator =
                        new ColumnForRowHeaderSortComparator(
                                originalRowData,
                                originalCellData,
                                column,
                                sortState
                        );

                Collections.sort(sortedRowHeaderList, columnForRowHeaderSortComparator);
            }
        }

        originalRowData = new ArrayList<>(sortedRowHeaderList);
        originalCellData = new ArrayList<>(sortedList);
        reloadPages();
    }

    /**
     * Listener interface for changing of TableView page.
     */
    public interface OnTableViewPageTurnedListener {

        /**
         * Called when the page is changed in the TableView.
         *
         * @param numItems   The number of items currently being displayed in the TableView.
         * @param itemsStart The starting item currently being displayed in the TableView.
         * @param itemsEnd   The ending item currently being displayed in the TableView.
         */
        void onPageTurned(int numItems, int itemsStart, int itemsEnd);
    }
}
