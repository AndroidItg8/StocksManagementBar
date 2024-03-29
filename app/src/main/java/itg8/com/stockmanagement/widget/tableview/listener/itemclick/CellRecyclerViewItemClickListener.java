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

package itg8.com.stockmanagement.widget.tableview.listener.itemclick;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import itg8.com.stockmanagement.widget.tableview.ITableView;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.CellRecyclerView;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.CellRowRecyclerViewAdapter;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.holder.AbstractViewHolder;

/**
 * Created by evrencoskun on 26/09/2017.
 */

public class CellRecyclerViewItemClickListener extends AbstractItemClickListener {
    @NonNull
    private CellRecyclerView mCellRecyclerView;

    public CellRecyclerViewItemClickListener(@NonNull CellRecyclerView recyclerView, @NonNull ITableView tableView) {
        super(recyclerView, tableView);
        this.mCellRecyclerView = tableView.getCellRecyclerView();
    }

    @Override
    protected boolean clickAction(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        // Get interacted view from x,y coordinate.
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            // Find the view holder
            AbstractViewHolder holder = (AbstractViewHolder) mRecyclerView.getChildViewHolder
                    (childView);

            // Get y position from adapter
            CellRowRecyclerViewAdapter adapter = (CellRowRecyclerViewAdapter) mRecyclerView
                    .getAdapter();

            int column = holder.getAdapterPosition();
            int row = adapter.getYPosition();

            // Control to ignore selection color
            if (!mTableView.isIgnoreSelectionColors()) {
                mSelectionHandler.setSelectedCellPositions(holder, column, row);
            }

            // Call ITableView listener for item click
            getTableViewListener().onCellClicked(holder, column, row);

            return true;
        }
        return false;
    }

    protected void longPressAction(@NonNull MotionEvent e) {
        // Consume the action for the time when either the cell row recyclerView or
        // the cell recyclerView is scrolling.
        if ((mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) ||
                (mCellRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE)) {
            return;
        }

        // Get interacted view from x,y coordinate.
        View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());

        if (child != null) {
            getTableViewListener();
            // Find the view holder
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(child);

            // Get y position from adapter
            CellRowRecyclerViewAdapter adapter = (CellRowRecyclerViewAdapter) mRecyclerView
                    .getAdapter();

            // Call ITableView listener for long click
            getTableViewListener().onCellLongPressed(holder, holder.getAdapterPosition(), adapter
                    .getYPosition());
        }
    }
}
