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

        labsizeX = 31;
        labsizeY = 31;
        gridPaneSquareSize = 10.0;

        buildAndDrawLabs();
    }

    @FXML
    private void buildAndDrawLabs() {
        // perfektes Labyrinth mit Jarní-Prim-Algorithmus
        boolean[][] boolLabA = new LabyrinthA(labsizeX, labsizeY).getBooleanLab();
//        perfektes Labyrinth erweitert
//        LabyrinthA labB = new LabyrinthA(30, 30);
//        LabyrinthA labC = new LabyrinthA(30, 30);

        fillGridPane(labA, boolLabA);
    }

    private void fillGridPane(GridPane labGridPane, boolean[][] boolLab) {
        for (int line = 0; line <= boolLab[0].length; line++) {
            labGridPane.add(black(), line, 0);
        }
        for (int row = 0; row <= boolLab.length; row++) {
            labGridPane.add(black(), 0, row);
        }

        if(boolLab.length % 2 == 1)        {
            for (int line = 0; line <= boolLab[0].length+1; line++) {
                labGridPane.add(black(), line, boolLab.length+1);
            }
        }
        if(boolLab[0].length % 2 == 1) {
            for (int row = 0; row <= boolLab.length+1; row++) {
                labGridPane.add(black(), boolLab[0].length+1, row);
            }
        }
        for (int i = 0; i < boolLab.length; i++) {
            for (int j = 0; j < boolLab[i].length; j++) {
                if (boolLab[i][j]) {
                    labGridPane.add(white(), j + 1, i + 1);
                } else {
                    labGridPane.add(black(), j + 1, i + 1);
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
