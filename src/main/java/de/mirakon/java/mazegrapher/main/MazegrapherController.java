/*
 * Copyright 2017 Michael
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package de.mirakon.java.mazegrapher.main;

import de.mirakon.java.mazegrapher.plugins.DummyMaze;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.ButtonBar.ButtonData;


public class MazegrapherController {

    public static final String CHECKEDMAZES_PREF_NODE_PATH = generateExtraPreferencesNodePath("checkedmazes");

    @FXML
    private Accordion accordionMazes;

    private ResourceBundle strings;

    private Preferences checkedMazesPrefs;
    private TreeMap<String, Maze> mazes;

    private Set<String> checkedMazes = Collections.synchronizedSet(new HashSet<>());
    private Maze currentMaze;


    private static String generateExtraPreferencesNodePath(@NotNull String extra) {
        String className = MazegrapherController.class.getName();
        int endIndex = className.lastIndexOf('.');
        String packageName = className.substring(0, endIndex).replace('.', '/');
        return String.format("/%s/%s", packageName, extra);
    }

    void setStageListeners(@NotNull Stage stage) {
        stage.setOnCloseRequest(event -> {
            saveCheckedMazes();
        });
    }

    private void saveCheckedMazes() {
        try {
            checkedMazesPrefs.clear();
        } catch (BackingStoreException e) {
            getAlert(AlertType.WARNING, strings.getString("warning"), null, strings.getString
                    ("warningPrefContentClearCheckedMazes"), e).show();
        }
        for (String mazeName : checkedMazes) {
            Maze checkedMaze = mazes.get(mazeName);
            if (checkedMaze != null) {
                checkedMazesPrefs.put(mazeName, checkedMaze.getMazeCategory());
            }
        }
    }

    public void initialize() {
        try {
            loadStringResources();
            fetchPreferences();
            fetchMazes();
            populateAccordion();
            currentMaze = createRandomMaze();
        } catch (MissingMazeArgumentException | IllegalStateException e) {
            showErrorAlert(strings.getString("error"), strings.getString("errorInitialization"), e.getMessage(), e);
        }
        // TODO: 19.03.2017 create maze depending on what's choosen, null maze abfangen

    }

    /**
     * Loads the Strings.properties (or Strings_xx.properties) ResourceBundle containing all location dependent
     * strings, with current default Locale
     */
    private void loadStringResources() {
        strings = ResourceBundle.getBundle("Strings");
    }

    private void fetchPreferences() {
        checkedMazesPrefs = Preferences.userRoot().node(CHECKEDMAZES_PREF_NODE_PATH);
    }

    private void fetchMazes() throws MissingMazeArgumentException, IllegalStateException {
        // TODO: 21.03.2017 with plugin, do more :P
        mazes = MazeManager.getDefaultMazePluginMap();
    }

    private void populateAccordion() {
        // Sort mazes by category
        TreeMap<String, ArrayList<String>> mazesByCategories = getMazeNamesByCategory();
        // Clear current accordion, to repopulate it
        ObservableList<TitledPane> accMazeTitledPanes = accordionMazes.getPanes();
        accMazeTitledPanes.clear();
        // Add for each maze category a TitledPane with a ListView containing mazes of corresponding category
        for (Map.Entry<String, ArrayList<String>> mazeCategory : mazesByCategories.entrySet()) {
            // Create a ListView and populate it with mazes of one Category
            ListView<MazeItem> mazeItemListView = new ListView<>();
            for (String mazeName : mazeCategory.getValue()) {
                MazeItem mazeItem = new MazeItem(mazeName, false);
                mazeItem.checkedProperty().addListener((obs, wasChecked, isNowChecked) -> {
                    if (isNowChecked) {
                        checkedMazes.add(mazeItem.getMazeName());
                    } else {
                        checkedMazes.remove(mazeItem.getMazeName());
                    }
                });
                mazeItemListView.getItems().add(mazeItem);
            }
            // Add check boxes to the list items
            mazeItemListView.setCellFactory(CheckBoxListCell.forListView(MazeItem::checkedProperty));
            // Add the ListView to a TitledPane and add latter one to the Accordion
            accMazeTitledPanes.add(new TitledPane(mazeCategory.getKey(), mazeItemListView));
        }
        // TODO: 08.04.2017 sicherstellen dass accordion mindestens ein kind hat? (emergency maze?)
        // TODO: 02.05.2017 besschreibung des angeklickten (nicht ge-check-ten) anzeigen
        checkMazes();
    }

    @NotNull
    private TreeMap<String, ArrayList<String>> getMazeNamesByCategory() {
        TreeMap<String, ArrayList<String>> mazesByCategories = new TreeMap<>();
        for (Map.Entry<String, Maze> mazeEntry : mazes.entrySet()) {
            String mCategory = mazeEntry.getValue().getMazeCategory();
            mazesByCategories.computeIfAbsent(mCategory, k -> new ArrayList<>()).add(mazeEntry.getKey());
        }
        return mazesByCategories;
    }

    @SuppressWarnings("unchecked")
    private void checkMazes() {
        String[] checkedMazes;
        try {
            checkedMazes = checkedMazesPrefs.keys();
        } catch (BackingStoreException e) {
            getAlert(AlertType.WARNING, strings.getString("warning"), strings.getString
                            ("warningPrefHeaderLoadCheckedMazes"), strings.getString
                            ("warningPrefContentLoadCheckedMazes"),
                    e).show();
            checkedMazes = new String[0];
        }

        if (checkedMazes.length > 0) {
            // Check mazes from preferences (from last time)
            for (String checkedMaze : checkedMazes) {
                accordionMazes.getPanes().stream()
                        // TODO: 05.05.2017 kommentare hinzufügen
                        .filter(titledPane -> titledPane.getText().equals(checkedMazesPrefs.get(checkedMaze, null)))
                        .limit(1)
                        .flatMap(titledPane -> ((ListView<MazeItem>) titledPane.getContent()).getItems().stream())
                        .filter(mazeItem -> mazeItem.getMazeName().equals(checkedMaze))
                        .limit(1)
                        .forEach(mazeItem -> mazeItem.setChecked(true));
            }

            // Check mazes from preferences (from last time)
            /*
            for (String checkedMaze : checkedMazes) {
                ObservableList<TitledPane> titledPanes = accordionMazes.getPanes();
                for (TitledPane titledPane : titledPanes) {
                    if (titledPane.getText().equals(checkedMazesPrefs.get(checkedMaze, null))) {
                        ObservableList<MazeItem> mazeItems = ((ListView<MazeItem>) titledPane.getContent()).getItems();
                        for (MazeItem mazeItem : mazeItems) {
                            if (mazeItem.getMazeName().equals(checkedMaze)) {
                                mazeItem.setChecked(true);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
             */
        } else {
            // Check first maze
            TitledPane firstTitledPane = accordionMazes.getPanes().get(0);
            ListView<MazeItem> listView = (ListView<MazeItem>) firstTitledPane.getContent();
            MazeItem firstMazeItem = listView.getItems().get(0);
            firstMazeItem.setChecked(true);
        }
    }

    @NotNull
    private Maze createRandomMaze() {
        Maze maze = getRandomMazeInstance();
        // TODO: 22.03.2017 throw / meldung
        if (maze == null) {
        }
        int[] size = getRandomMazeSize();
        // TODO: 21.03.2017 maze größen erstellen
        // TODO: 21.03.2017 maze instanz besorgen und prüfen ob erster maze frei (dann entweder direkt als array oder
        // maze abspeichern)

        return maze.newInstance();
    }

    @NotNull
    private Maze getRandomMazeInstance() {
        String[] checkedMazes = this.checkedMazes.toArray(new String[this.checkedMazes.size()]);
        String mazeName;
        if (checkedMazes.length > 0) {
            mazeName = checkedMazes[ThreadLocalRandom.current().nextInt(checkedMazes.length)];
        } else {
            // TODO: 21.03.2017 warnmeldung das keine mazes ausgewählt sind und das erste (mit namen) verwendet wird
            // TODO: 21.03.2017 get & check first (wirft fehlermeldung falls kein maze gefunden)
            mazeName = null;
        }
        if (mazeName != null) {
            Maze maze = mazes.get(mazeName);
            if (maze != null) {
                // TODO: 21.03.2017 call maze.newInstance stuff
            } else {
                // TODO: 21.03.2017 error: konnte maze mit namen xy nicht finden
            }
        }
//        return null;
        return new DummyMaze();
    }

    @NotNull
    private int[] getRandomMazeSize() {
        // TODO: 22.03.2017 präferenz holen, wenn nicht gefunden standardwerte von irgendwo holen
        return new int[]{20, 20};
    }

    @NotNull
    private Alert getAlert(@NotNull AlertType alertType, @Nullable String title, @Nullable String headerText,
                           @Nullable String contentText, @Nullable Exception exception) {
        Alert alert = new Alert(alertType);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        if (exception != null) {
            alert.getDialogPane().setExpandableContent(createExpandableContent(exception));
        }
        return alert;
    }

    private void showErrorAlert(@Nullable String title, @Nullable String headerText, @Nullable String contentText,
                                @Nullable Exception exception) {
        Alert alert = getAlert(AlertType.ERROR, title, headerText, contentText, exception);

        ButtonType settingsButton = new ButtonType(strings.getString("buttonSettings"), ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType(strings.getString("buttonExit"), ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(settingsButton, exitButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == settingsButton)) {
            // TODO: 01.05.2017 open settings
        } else {
            Platform.exit();
        }
    }

    @NotNull
    private Node createExpandableContent(@NotNull Exception exception) {
        // Stacktrace String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        // Expandable Content
        Label label = new Label("Exception stacktrace:");
        TextArea textArea = new TextArea(stackTrace);

        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        VBox expContent = new VBox();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.getChildren().addAll(label, textArea);

        return expContent;
    }

    private static class MazeItem {
        private final StringProperty mazeName = new SimpleStringProperty();
        private final BooleanProperty isChecked = new SimpleBooleanProperty();

        MazeItem(String mazeName, boolean checked) {
            setMazeName(mazeName);
            setChecked(checked);
        }

        @Contract(pure = true)
        final StringProperty getMazeNameProperty() {
            return this.mazeName;
        }

        String getMazeName() {
            return this.getMazeNameProperty().get();
        }

        final void setMazeName(final String mazeVariantName) {
            this.getMazeNameProperty().set(mazeVariantName);
        }

        @Contract(pure = true)
        final BooleanProperty checkedProperty() {
            return this.isChecked;
        }

        final boolean getChecked() {
            return this.checkedProperty().get();
        }

        final void setChecked(final boolean isChecked) {
            this.checkedProperty().set(isChecked);
        }

        @Override
        public String toString() {
            return getMazeName();
        }
    }
}
