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

package de.mirakon.java.stuff.labgrapher;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LabyrinthController {
    @FXML
    private GridPane labA;
    @FXML
    private GridPane labB;

    private double gridPaneSquareSize;
    private int labsizeX;
    private int labsizeY;

    // TODO: 17.03.2017 make 3 different labs

    public void initialize() {

        labsizeX = 30;
        labsizeY = 30;
        gridPaneSquareSize = 10.0;

        buildAndDrawLabs();
    }

    @FXML
    private void buildAndDrawLabs() {
        System.out.print("Erstelle Labyrinthe...");
        // perfektes Labyrinth mit Jarní-Prim-Algorithmus
        boolean[][] boolLabA = new LabyrinthA(labsizeX, labsizeY).getBooleanLab();
//        perfektes Labyrinth erweitert
//        LabyrinthA labB = new LabyrinthA(30, 30);
//        LabyrinthA labC = new LabyrinthA(30, 30);
        System.out.println("\tfertig");

        System.out.print("Fülle GridPanes...");
        fillGridPane(labA, boolLabA);
        System.out.println("\tfertig");
        System.out.println("");
    }

    private void fillGridPane(GridPane labGridPane, boolean[][] boolLabA) {
        for (int i = 0; i < boolLabA.length; i++) {
            for (int j = 0; j < boolLabA[i].length; j++) {
                if (boolLabA[i][j]) {
                    labGridPane.add(white(), i, j);
                } else {
                    labGridPane.add(black(), i, j);
                }
            }
        }
    }

    private Node white() {
        return new Rectangle(gridPaneSquareSize, gridPaneSquareSize, Color.WHITE);
    }

    private Node black() {
        return new Rectangle(gridPaneSquareSize, gridPaneSquareSize, Color.BLACK);
    }


}
