/*
 * Copyright 2017 Michael
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.mirakon.java.stuff.mazegrapher.ui;

import de.mirakon.java.stuff.mazegrapher.mazes.Maze;
import de.mirakon.java.stuff.mazegrapher.mazes.MazeCoordinator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxListCell;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MazegrapherController {

    @FXML
    private Accordion accordionMazes;

    private TreeMap<String, TreeMap<String, Maze>> mazes;
    private Set<String> checkedMazes = Collections.synchronizedSet(new HashSet<>());

    public void initialize() {
        populateAccordion();
        // TODO: 19.03.2017 create maze depending on what's choosen
    }

    private void populateAccordion() {
        // Update which mazes shall be used
        mazes = MazeCoordinator.getDefaultMazeMap();
        // Clear current accordion, to repopulate it
        ObservableList<TitledPane> accMazeTitledPanes = accordionMazes.getPanes();
        accMazeTitledPanes.clear();
        // Add for each maze category a TitledPane with a ListView containing mazes of corresponding category
        for (Map.Entry<String, TreeMap<String, Maze>> entryMazeCategory : mazes.entrySet()) {
            // Create a ListView and populate it with mazes of one Category
            ListView<MazeListItem> mazesListView = new ListView<>();
            for (String mazeName : entryMazeCategory.getValue().keySet()) {
                MazeListItem mazeItem = new MazeListItem(mazeName, false);
                mazeItem.checkedProperty().addListener((observable, wasChecked, isNowChecked) -> {
                    if (isNowChecked) {
                        checkedMazes.add(mazeItem.getMazeName());
                    } else {
                        checkedMazes.remove(mazeItem.getMazeName());
                    }
                });
                mazesListView.getItems().add(mazeItem);
            }
            // Add check boxes to the list items
            mazesListView.setCellFactory(CheckBoxListCell.forListView(MazeListItem::checkedProperty));
            // Add the ListView to a TitledPane and add latter one to the Accordion
            accMazeTitledPanes.add(new TitledPane(entryMazeCategory.getKey(), mazesListView));
        }

        // TODO: 21.03.2017 look up from preferences or stuff, and set at least one to true / selected
    }

    private static class MazeListItem {
        private final StringProperty mazeName = new SimpleStringProperty();
        private final BooleanProperty checked = new SimpleBooleanProperty();

        MazeListItem(String mazeName, boolean checked) {
            setMazeName(mazeName);
            setChecked(checked);
        }

        final StringProperty getMazeNameProperty() {
            return this.mazeName;
        }

        String getMazeName() {
            return this.getMazeNameProperty().get();
        }

        final void setMazeName(final String mazeVariantName) {
            this.getMazeNameProperty().set(mazeVariantName);
        }

        final BooleanProperty checkedProperty() {
            return this.checked;
        }

        final boolean getChecked() {
            return this.checkedProperty().get();
        }

        final void setChecked(final boolean checked) {
            this.checkedProperty().set(checked);
        }

        @Override
        public String toString() {
            return getMazeName();
        }
    }
}
