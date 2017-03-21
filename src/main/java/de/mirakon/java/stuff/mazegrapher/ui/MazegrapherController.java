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

import java.util.Map;
import java.util.TreeMap;

public class MazegrapherController {

    @FXML
    private Accordion accordionMazes;

    private TreeMap<String, TreeMap<String, Maze>> mazes;


    public void initialize() {
        // TODO: 19.03.2017 create maze depending on what was choosen
        populateAccordion();
    }

    // FIXME: 21.03.2017 zeug umbenennen, größen (breiten, höhen) anpassen aaand stuff

    // FIXME: 21.03.2017 variablen deklarationen (in allen klassen) in die schleifen reinpacken wenn nicht außerhalb nötig

    private void populateAccordion() {
        // TODO: 21.03.2017 größenanpassung des Accordion, TitledPanes und ListViews
        // Update which mazes shall be used
        mazes = MazeCoordinator.getDefaultMazeMap();
        // Clear current accordion, to repopulate it
        ObservableList<TitledPane> accMazeTitledPanes = accordionMazes.getPanes();
        accMazeTitledPanes.clear();
        // Add for each maze category a TitledPane with a ListView containing mazes of corresponding category
        for (Map.Entry<String, TreeMap<String, Maze>> entryMazeCategory : mazes.entrySet()) {
            // Create a ListView and populate it with mazes of one Category
            ListView<MyItem> listView = new ListView<>();
            for (String mazeName : entryMazeCategory.getValue().keySet()) {
                MyItem mazeItem = new MyItem(mazeName, false);
                mazeItem.inUseProperty().addListener((observable, wasInUse, isNowInUse) -> {
                    // TODO: 21.03.2017 was ist sinnvoller, von hier eine liste updaten welche mazes inUse sind ODER bei zugriff die listViews durchiterieren und status prüfen
                    // diese info wird benötigt wenn der (zufällige) maze ausgewählt wird
                    System.out.println(mazeItem.getMazeVariant() + " changed on state from " + wasInUse + " to " + isNowInUse);
                });
                listView.getItems().add(mazeItem);
            }
            // Add check boxes to the list items
            listView.setCellFactory(CheckBoxListCell.forListView(MyItem::inUseProperty));
            // Add the ListView to a TitledPane and add latter one to the Accordion
            accMazeTitledPanes.add(new TitledPane(entryMazeCategory.getKey(), listView));
        }

        // TODO: 21.03.2017 look up from preferences or stuff, and set at least one to true / selected
    }

    private static class MyItem {
        // TODO: 21.03.2017 zeug umbenennen
        private final StringProperty mazeVariant = new SimpleStringProperty();
        private final BooleanProperty inUse = new SimpleBooleanProperty();

        public MyItem(String mazeVariantName, boolean inUse) {
            setMazeVariant(mazeVariantName);
            setInUse(inUse);
        }

        public final StringProperty mazeVariantProperty() {
            return this.mazeVariant;
        }

        public String getMazeVariant() {
            return this.mazeVariantProperty().get();
        }

        public final void setMazeVariant(final String mazeVariantName) {
            this.mazeVariantProperty().set(mazeVariantName);
        }

        public final BooleanProperty inUseProperty() {
            return this.inUse;
        }

        public final boolean getInUse() {
            return this.inUseProperty().get();
        }

        public final void setInUse(final boolean inUse) {
            this.inUseProperty().set(inUse);
        }

        @Override
        public String toString() {
            return getMazeVariant();
        }
    }

    // TODO: 19.03.2017 finde alle maze generator klassen, eigene (pfad/referenz in ner xml oder ner klasse?) und dann fremde (i-wie per plugin? ^^) und füge sie korrekt in die comboBox ein
}
