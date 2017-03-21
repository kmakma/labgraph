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
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

import java.util.Map;
import java.util.TreeMap;

public class MazegrapherController {

    public SplitPane splitPaneMazeVars;
    @FXML
    private Accordion accordionMazeVariations;
    private TreeMap<String, TreeMap<String, Maze>> mazeVariations;


    public void initialize() {
        // TODO: 19.03.2017 create maze depending on what was choosen
        populateAccordion();
    }

    private void populateAccordion() {
        ObservableList<TitledPane> accMVTitledPanes = accordionMazeVariations.getPanes();
        accMVTitledPanes.clear();

        // FIXME: 21.03.2017 zeug umbenennen, größen (breiten, höhen) anpassen aaand stuff

        // FIXME: 21.03.2017 variablen deklarationen (in allen klassen) in die schleifen reinpacken wenn nicht außerhalb nötig

        mazeVariations = MazeCoordinator.getDefaultMazeVariations();

        System.out.println(mazeVariations.size());

        TitledPane paneToAdd;
        for (Map.Entry<String, TreeMap<String, Maze>> mazeVarEntry : mazeVariations.entrySet()) {
            // TODO: 20.03.2017 erstelle zuerst die listview
            ListView<MyItem> listView = new ListView<>();

            TreeMap<String, Maze> mazeCategory = mazeVarEntry.getValue();

            // TODO: 21.03.2017 nur set nehmen nicht map entries?
            for (Map.Entry<String, Maze> mazeCatEntry : mazeCategory.entrySet()) {
                MyItem myItem = new MyItem(mazeCatEntry.getKey(), false);

                myItem.inUseProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(myItem.getMazeVariant() + " changed on state from " + wasOn + " to " + isNowOn);
                });

                listView.getItems().add(myItem);
            }


            listView.setCellFactory(CheckBoxListCell.forListView(new Callback<MyItem, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(MyItem myItem) {
                    return myItem.inUseProperty();
                }
            }));

            paneToAdd = new TitledPane(mazeVarEntry.getKey(), listView);
            accMVTitledPanes.add(paneToAdd);
        }


        System.out.println(accordionMazeVariations.getPanes().size());
        // TODO: 20.03.2017 erstelle für jede kategorie eine TitledPane, mit einer ListView mit check boxen (vorgehen erstmal eine erstellen und schauen wo was (listener etc) gebraucht wird
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
