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

import androidx.annotation.NonNull;

import itg8.com.stockmanagement.widget.tableview.TableView;
import itg8.com.stockmanagement.widget.tableview.preference.Preferences;

/**
 * Created by evrencoskun on 3.03.2018.
 */

public class PreferencesHandler {
    @NonNull
    private ScrollHandler scrollHandler;
    @NonNull
    private SelectionHandler selectionHandler;

    public PreferencesHandler(@NonNull TableView tableView) {
        this.scrollHandler = tableView.getScrollHandler();
        this.selectionHandler = tableView.getSelectionHandler();
    }

    @NonNull
    public Preferences savePreferences() {
        Preferences preferences = new Preferences();
        preferences.columnPosition = scrollHandler.getColumnPosition();
        preferences.columnPositionOffset = scrollHandler.getColumnPositionOffset();
        preferences.rowPosition = scrollHandler.getRowPosition();
        preferences.rowPositionOffset = scrollHandler.getRowPositionOffset();
        preferences.selectedColumnPosition = selectionHandler.getSelectedColumnPosition();
        preferences.selectedRowPosition = selectionHandler.getSelectedRowPosition();
        return preferences;
    }

    public void loadPreferences(@NonNull Preferences preferences) {
        scrollHandler.scrollToColumnPosition(preferences.columnPosition, preferences.columnPositionOffset);
        scrollHandler.scrollToRowPosition(preferences.rowPosition, preferences.rowPositionOffset);
        selectionHandler.setSelectedColumnPosition(preferences.selectedColumnPosition);
        selectionHandler.setSelectedRowPosition(preferences.selectedRowPosition);
    }
}
