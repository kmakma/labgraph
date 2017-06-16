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

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

    // TODO: 22.05.2017 performance test /z.b. für mazes, mit 300x300 mazes

    public static final String CHECKEDMAZES_PREF_NODE = "checkedmazes";
    private final Set<String> checkedMazes = Collections.synchronizedSet(new HashSet<>());
    @FXML
    private Accordion accordionMazes;
    @FXML
    private TextArea mazeDescription;
    private ResourceBundle strings;
    private Preferences settings = Preferences.userNodeForPackage(this.getClass());
    private Preferences checkedMazesPrefs = Preferences.userRoot().node(Constants.generateExtraPreferencesNodePath
            (this.getClass(), CHECKEDMAZES_PREF_NODE));
    private TreeMap<String, Maze> mazes;
    private Maze currentMaze;

    void setStageListeners(@NotNull Stage stage) {
        stage.setOnCloseRequest(event -> saveCheckedMazes());
    }

    private void saveCheckedMazes() {
        try {
            checkedMazesPrefs.clear();
        } catch (BackingStoreException e) {
            getAlert(AlertType.WARNING, strings.getString("warning"), null, strings.getString
                    ("warningPrefClearCheckedMazesContent"), e).show();
        }
        synchronized (checkedMazes) {
            for (String mazeName : checkedMazes) {
                Maze checkedMaze = mazes.get(mazeName);
                if (checkedMaze != null) {
                    checkedMazesPrefs.put(mazeName, checkedMaze.getMazeCategory());
                }
            }
        }
    }

    public void initialize() {
        try {
            loadStringResources();
            checkSettings();
            fetchMazes();
            populateAccordion();

//            currentMaze = getRandomMaze();
//            getRandomXMazeSize();
//            currentMaze.generate(getRandomXMazeSize(), getRandomMazeWidth());
            // TODO: 18.05.2017 generate in einem thread
        } catch (MissingMazeArgumentException | IllegalStateException e) {
            showErrorAlert(strings.getString("error"), strings.getString("errorInitialization"), e.getMessage(), e);
        }

    }

    /**
     * Loads the Strings.properties (or Strings_xx.properties) ResourceBundle containing all location dependent
     * strings, with current default Locale
     */
    private void loadStringResources() {
        strings = ResourceBundle.getBundle("Strings");
    }

    private void checkSettings() {
        // TODO: 20.05.2017 when pulling some setting and it doesn't exit set default
        if (!settings.getBoolean(SettingsController.ALL_SETTINGS_SET, false)) {
            SettingsController.setDefaultSettings();
        }
    }

    private void fetchMazes() throws MissingMazeArgumentException, IllegalStateException {
        // TODO: 21.03.2017 with plugin, do more?
        mazes = MazeManager.getDefaultMazePluginMap();
    }

    private void populateAccordion() throws IllegalStateException {
        // Sort mazes by category
        TreeMap<String, ArrayList<String>> mazesByCategories = getMazeNamesByCategory();
        if (mazesByCategories.size() == 0) {
            throw new IllegalStateException(strings.getString("errorNoMazeFound"));
        }
        // Clear current accordion, to repopulate it
        ObservableList<TitledPane> accMazeTitledPanes = accordionMazes.getPanes();
        accMazeTitledPanes.clear();
        // Add for each maze category a TitledPane with a ListView containing mazes of corresponding category
        for (Map.Entry<String, ArrayList<String>> mazeCategory : mazesByCategories.entrySet()) {
            // Create a ListView and populate it with mazes of one Category
            ListView<MazeItem> mazeItemListView = new ListView<>();
            ObservableList<MazeItem> mazesOfCategory = mazeItemListView.getItems();
            for (String mazeName : mazeCategory.getValue()) {
                MazeItem mazeItem = new MazeItem(mazeName, false);
                mazeItem.checkedProperty().addListener((obs, wasChecked, isNowChecked) -> {
                    synchronized (checkedMazes) {
                        if (isNowChecked) {
                            checkedMazes.add(mazeItem.getMazeName());
                        } else {
                            checkedMazes.remove(mazeItem.getMazeName());
                            if (checkedMazes.size() == 0) {
                                getAlert(AlertType.WARNING, strings.getString("warning"), null, strings.getString
                                        ("warningNoCheckedMazesContent"), null).showAndWait();
                            }
                        }
                    }
                });
                mazesOfCategory.add(mazeItem);
            }
            // Add listener to show description of selected mazeItem
            // TODO: 20.05.2017 deselect from every other list
            mazeItemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                    -> {
                if (newValue != null) {
                    String description = getDescription(newValue.getMazeName());
                    showDescription(description);
                } else {
                    showDescription(null);
                }
            });
            // Add check boxes to the list items
            mazeItemListView.setCellFactory(CheckBoxListCell.forListView(MazeItem::checkedProperty));
            // Add the ListView to a TitledPane and add latter one to the Accordion
            accMazeTitledPanes.add(new TitledPane(mazeCategory.getKey(), mazeItemListView));
        }
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

    private String getDescription(String mazeName) {
        return mazes.get(mazeName).getDescription();
    }

    private void showDescription(@Nullable String description) {
        if (description == null) {
            description = strings.getString("descriptionPlaceholder");
        }
        mazeDescription.setText(description);
    }

    @SuppressWarnings("unchecked")
    private void checkMazes() {
        String[] checkedMazes;
        try {
            checkedMazes = checkedMazesPrefs.keys();
        } catch (BackingStoreException e) {
            getAlert(AlertType.WARNING, strings.getString("warning"), strings.getString
                    ("warningPrefLoadCheckedMazesHeader"), strings.getString
                    ("warningPrefLoadCheckedMazesContent"), e).show();
            checkedMazes = new String[0];
        }

        if (checkedMazes.length > 0) {
            // Check mazes from preferences (from last time)
            for (String checkedMaze : checkedMazes) {
                accordionMazes.getPanes().stream()
                        // get all TitledPanes with names equal to the category of current checkedMaze,
                        // checkedMazesPreference (key = maze name, value = category) and limit it to length of one,
                        // since there should be only one TitledPane per category
                        .filter(titledPane -> titledPane.getText().equals(checkedMazesPrefs.get(checkedMaze, null)))
                        .limit(1)
                        // from all mazes (MazeItems) of the TitledPane
                        .flatMap(titledPane -> ((ListView<MazeItem>) titledPane.getContent()).getItems().stream())
                        // get the one with equal name to current checkedMaze
                        .filter(mazeItem -> mazeItem.getMazeName().equals(checkedMaze))
                        .limit(1)
                        // and set it as checked
                        .forEach(mazeItem -> mazeItem.setChecked(true));
            }
        } else {
            // Check first maze
            checkFirstMaze();
        }
    }

    @SuppressWarnings("unchecked")
    private void checkFirstMaze() {
        TitledPane firstTitledPane = accordionMazes.getPanes().get(0);
        ListView<MazeItem> listView = (ListView<MazeItem>) firstTitledPane.getContent();
        MazeItem firstMazeItem = listView.getItems().get(0);
        firstMazeItem.setChecked(true);
    }

    @NotNull
    private Maze getRandomMaze() {
        String randomMazeName;
        synchronized (checkedMazes) {
            if (checkedMazes.size() == 0) {
                checkFirstMaze();
            }
            randomMazeName = GenericMethods.getElementXFromSet(checkedMazes, ThreadLocalRandom.current().nextInt
                    (checkedMazes.size()));
        }
        Maze randomMaze = mazes.get(randomMazeName);
        return randomMaze.newInstance();
    }

    private int getRandomMazeHeight() throws IllegalStateException {
        int maxMazeHeight = settings.getInt(SettingsController.MAX_MAZE_HEIGHT, -1);
        int minMazeHeight = settings.getInt(SettingsController.MIN_MAZE_HEIGHT, -1);
        if (maxMazeHeight < 1 || minMazeHeight < 1) {
            throw new IllegalStateException(strings.getString("errorReadingSettings"));
        }
        return ThreadLocalRandom.current().nextInt(minMazeHeight, maxMazeHeight + 1);
    }

    private int getRandomMazeWidth() throws IllegalStateException {
        int maxMazeWidth = settings.getInt(SettingsController.MAX_MAZE_WIDTH, -1);
        int minMazeWidth = settings.getInt(SettingsController.MIN_MAZE_WIDTH, -1);
        if (maxMazeWidth < 1 || minMazeWidth < 1) {
            throw new IllegalStateException(strings.getString("errorReadingSettings"));
        }
        return ThreadLocalRandom.current().nextInt(minMazeWidth, maxMazeWidth + 1);
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
            // TODO: 01.05.2017 open settings in new but blocking window, beim beenden von settings soll dies hier
            // neu geladen werden
            Platform.exit();
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

    @FXML
    private void generateActionMViewer(ActionEvent actionEvent) {
        // TODO: 16.06.2017 some loading animation
        // TODO: 22.05.2017 create a thead, and stuff happens
        // TODO: 16.06.2017 get random sizes
        // TODO: 16.06.2017 get random maze
        // TODO: 16.06.2017 generate maze
        // TODO: 16.06.2017 get result
        // TODO: 16.06.2017 display result
        // TODO: 16.06.2017 wenn diese methode aufgerufen wird während gearbeitet frag ob abgebrochen werden soll
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
