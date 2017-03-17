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

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * perfektes Labyrinth mit Jarní-Prim-Algorithmus
 * <p>
 * ACHTUNG: DIESE IMPLEMENTIERUNG IST SOWAS VON ÜBERHAUPT NICHT LAUFZEIT EFFIZIENT!!!
 */
public class LabyrinthA {

    private Node[][] labyrinth;
    private ArrayList<Node> addedNodes = new ArrayList<>();
    // lines
    private int sizeX;
    // rows
    private int sizeY;

    LabyrinthA(int x, int y) {
        sizeX = x;
        sizeY = y;
        labyrinth = new Node[x][y];

        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                labyrinth[i][j] = new Node(i, j);
            }
        }

        createLab();
    }


    private void createLab() {
        int currentX = ThreadLocalRandom.current().nextInt(sizeX / 2) * 2;
        int currentY = ThreadLocalRandom.current().nextInt(sizeY / 2) * 2;
        addedNodes.add(labyrinth[currentX][currentY]);

        ArrayList<Node> possibleConnectionNodes = findPossibleConnections();
        if (!possibleConnectionNodes.isEmpty()) {
            // TODO: 17.03.2017 wähle zufällig eine Verbindung 
            // TODO: 17.03.2017 öffne angrenzede knoten und diesen horizontal oder vertikal  
        }
    }

    private ArrayList<Node> findPossibleConnections() {
        ArrayList<Node> possibleConnectionNodes = new ArrayList<>();
        for (Node node : addedNodes) {
            if (node.isWallNorth() && node.getPosX() >= 2) {
                if (!labyrinth[node.getPosX() - 2][node.getPosY()].isAnywhereOpen()) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX() - 1][node.getPosY()]);
                }
            }
            if (node.isWallEast() && node.getPosY() < labyrinth[node.getPosX()].length - 2) {
                if (!labyrinth[node.getPosX()][node.getPosY() + 2].isAnywhereOpen()) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX()][node.getPosY() + 1]);
                }
            }
            if (node.isWallSouth() && node.getPosX() < labyrinth.length - 2) {
                if (!labyrinth[node.getPosX() + 2][node.getPosY()].isAnywhereOpen()) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX() + 1][node.getPosY()]);
                }
            }
            if (node.isWallWest() && node.getPosY() >= 2) {
                if (!labyrinth[node.getPosX()][node.getPosY() - 2].isAnywhereOpen()) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX()][node.getPosY() - 1]);
                }
            }
        }
        return possibleConnectionNodes;
    }

//    if(node.isWallNorth()&&node.getPosX()>=2)
//    {
//        node.openNorth();
//        labyrinth[node.getPosX() - 1][node.getPosY()].openVertical();
//        labyrinth[node.getPosX() - 2][node.getPosY()].openSouth();
//    }
//    if(node.isWallEast()&&node.getPosY() <labyrinth[node.getPosX()].length -2)
//    {
//        node.openEast();
//        labyrinth[node.getPosX()][node.getPosY()].openHorizontal();
//        labyrinth[node.getPosX() - 2][node.getPosY()].openWest();
//    }
//    if(node.isWallSouth()&&node.getPosX() <labyrinth.length -2)
//    {
//        node.openSouth();
//        labyrinth[node.getPosX() + 1][node.getPosY()].openVertical();
//        labyrinth[node.getPosX() + 2][node.getPosY()].openNorth();
//    }
//    if(node.isWallWest()&&node.getPosY()>=2)
//    {
//        node.openWest();
//        labyrinth[node.getPosX()][node.getPosY() - 1].openHorizontal();
//        labyrinth[node.getPosX()][node.getPosY() - 2].openEast();
//    }

    /**
     * gibt boolean matrix die labyrinth repräsentiert zurück
     *
     * @return labyrinth als matrix, true=weg, false=wand
     */
    boolean[][] getBooleanLab() {
        boolean[][] booleanLab = new boolean[labyrinth.length][labyrinth[0].length];
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                booleanLab[i][j] = labyrinth[i][j].isAnywhereOpen();
            }
        }
        return booleanLab;
    }
}
