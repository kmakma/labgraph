/*
 * Copyright 2017 Michael
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.mirakon.java.mazegrapher.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Strings");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/mazegrapher.fxml"), bundle);
        Parent mainMazeParent = loader.load();
        MazegrapherController controller = loader.getController();
        controller.setStageListeners(stage);

        stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("MazeGrapher");
        stage.setScene(new Scene(mainMazeParent));
        // TODO: 17.03.2017 setMinHeight/~Width
        // stage.setMinHeight();
        // stage.setMinWidth();
        stage.show();
    }

}
