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

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;



import itg8.com.stockmanagement.widget.tableview.listener.ITableViewListener;
import itg8.com.stockmanagement.widget.tableview.ITableView;
import itg8.com.stockmanagement.widget.tableview.handler.SelectionHandler;
import itg8.com.stockmanagement.widget.tableview.adapter.recyclerview.CellRecyclerView;

/**
 * Created by evrencoskun on 22.11.2017.
 */

public abstract class AbstractItemClickListener implements RecyclerView.OnItemTouchListener {
    private ITableViewListener mListener;
    @NonNull
    protected GestureDetector mGestureDetector;
    @NonNull
    protected CellRecyclerView mRecyclerView;
    @NonNull
    protected SelectionHandler mSelectionHandler;
    @NonNull
    protected ITableView mTableView;

    public AbstractItemClickListener(@NonNull CellRecyclerView recyclerView, @NonNull ITableView tableView) {
        this.mRecyclerView = recyclerView;
        this.mTableView = tableView;
        this.mSelectionHandler = tableView.getSelectionHandler();

        mGestureDetector = new GestureDetector(mRecyclerView.getContext(), new GestureDetector
                .SimpleOnGestureListener() {

            @Nullable
            MotionEvent start;

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return clickAction(mRecyclerView, e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                start = e;
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // Check distance to prevent scroll to trigger the event
                if (start != null && Math.abs(start.getRawX() - e.getRawX()) < 20 && Math.abs
                        (start.getRawY() - e.getRawY()) < 20) {
                    longPressAction(e);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @NonNull
    protected ITableViewListener getTableViewListener() {
        if (mListener == null) {
            mListener = mTableView.getTableViewListener();
        }
        return mListener;
    }

    abstract protected boolean clickAction(@NonNull RecyclerView view, @NonNull MotionEvent e);

    abstract protected void longPressAction(@NonNull MotionEvent e);
}
