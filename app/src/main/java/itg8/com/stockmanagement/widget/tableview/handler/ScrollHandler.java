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

package itg8.com.stockmanagement.widget.tableview.handler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import itg8.com.stockmanagement.widget.tableview.ITableView;
import itg8.com.stockmanagement.widget.tableview.layoutmanager.CellLayoutManager;
import itg8.com.stockmanagement.widget.tableview.layoutmanager.ColumnHeaderLayoutManager;
import itg8.com.stockmanagement.widget.tableview.layoutmanager.ColumnLayoutManager;

/**
 * Created by evrencoskun on 13.01.2018.
 */

public class ScrollHandler {
    @NonNull
    private ITableView mTableView;
    @NonNull
    private CellLayoutManager mCellLayoutManager;
    @NonNull
    private LinearLayoutManager mRowHeaderLayoutManager;
    @NonNull
    private ColumnHeaderLayoutManager mColumnHeaderLayoutManager;

    public ScrollHandler(@NonNull ITableView tableView) {
        this.mTableView = tableView;
        this.mCellLayoutManager = tableView.getCellLayoutManager();
        this.mRowHeaderLayoutManager = tableView.getRowHeaderLayoutManager();
        this.mColumnHeaderLayoutManager = tableView.getColumnHeaderLayoutManager();
    }

    public void scrollToColumnPosition(int columnPosition) {
        // TableView is not on screen yet.
        if (!((View) mTableView).isShown()) {
            // Change default value of the listener
            mTableView.getHorizontalRecyclerViewListener().setScrollPosition(columnPosition);
        }

        // Column Header should be scrolled firstly because of fitting column width process.
        scrollColumnHeader(columnPosition, 0);
        scrollCellHorizontally(columnPosition, 0);
    }

    public void scrollToColumnPosition(int columnPosition, int offset) {
        // TableView is not on screen yet.
        if (!((View) mTableView).isShown()) {
            // Change default value of the listener
            mTableView.getHorizontalRecyclerViewListener().setScrollPosition(columnPosition);
            mTableView.getHorizontalRecyclerViewListener().setScrollPositionOffset(offset);
        }

        // Column Header should be scrolled firstly because of fitting column width process.
        scrollColumnHeader(columnPosition, offset);
        scrollCellHorizontally(columnPosition, offset);
    }

    public void scrollToRowPosition(int rowPosition) {
        mRowHeaderLayoutManager.scrollToPosition(rowPosition);
        mCellLayoutManager.scrollToPosition(rowPosition);
    }

    public void scrollToRowPosition(int rowPosition, int offset) {
        mRowHeaderLayoutManager.scrollToPositionWithOffset(rowPosition, offset);
        mCellLayoutManager.scrollToPositionWithOffset(rowPosition, offset);
    }

    private void scrollCellHorizontally(int columnPosition, int offset) {
        CellLayoutManager cellLayoutManager = mTableView.getCellLayoutManager();

        for (int i = cellLayoutManager.findFirstVisibleItemPosition(); i < cellLayoutManager
                .findLastVisibleItemPosition() + 1; i++) {

            RecyclerView cellRowRecyclerView = (RecyclerView) cellLayoutManager
                    .findViewByPosition(i);

            if (cellRowRecyclerView != null) {
                ColumnLayoutManager columnLayoutManager = (ColumnLayoutManager)
                        cellRowRecyclerView.getLayoutManager();

                columnLayoutManager.scrollToPositionWithOffset(columnPosition, offset);
            }

        }
    }

    private void scrollColumnHeader(int columnPosition, int offset) {
        mTableView.getColumnHeaderLayoutManager().scrollToPositionWithOffset(columnPosition,
                offset);
    }

    public int getColumnPosition() {
        return mColumnHeaderLayoutManager.findFirstVisibleItemPosition();
    }

    public int getColumnPositionOffset() {
        View child = mColumnHeaderLayoutManager.findViewByPosition(mColumnHeaderLayoutManager
                .findFirstVisibleItemPosition());
        if (child != null) {
            return child.getLeft();
        }
        return 0;
    }

    public int getRowPosition() {
        return mRowHeaderLayoutManager.findFirstVisibleItemPosition();
    }

    public int getRowPositionOffset() {
        View child = mRowHeaderLayoutManager.findViewByPosition(mRowHeaderLayoutManager
                .findFirstVisibleItemPosition());
        if (child != null) {
            return child.getLeft();
        }
        return 0;
    }
}
