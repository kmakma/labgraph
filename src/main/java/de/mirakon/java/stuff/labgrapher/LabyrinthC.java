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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * erweitertest spezielles Labyrinth mit Jarní-Prim-Algorithmus
 * <p>
 * ACHTUNG: DIESE IMPLEMENTIERUNG IST SOWAS VON ÜBERHAUPT NICHT LAUFZEIT EFFIZIENT!!!
 */
public class LabyrinthC {

    Node[][] labyrinth;
    private Set<Node> addedNodes = new HashSet<>();
    // lines
    private int sizeX;
    // rows
    private int sizeY;

    LabyrinthC(int x, int y) {
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
        if (addedNodes.add(labyrinth[currentX][currentY])) {
            labyrinth[currentX][currentY].setToPath();
        }

        ArrayList<Node> possibleConnectionNodes = findPossibleConnections();
        while (!possibleConnectionNodes.isEmpty()) {
            Node newConnection = possibleConnectionNodes.get(ThreadLocalRandom.current().nextInt
                    (possibleConnectionNodes.size()));
            int conPosX = newConnection.getPosX();
            int conPosY = newConnection.getPosY();
            if (conPosX > 0 && labyrinth[conPosX - 1][conPosY].isPath()) {
                openToNorthFrom(conPosX, conPosY);
            } else if (conPosX < labyrinth.length - 1 && labyrinth[conPosX + 1][conPosY].isPath()) {
                openToSouthFrom(conPosX, conPosY);
            } else if (conPosY > 0 && labyrinth[conPosX][conPosY - 1].isPath()) {
                openToWestFrom(conPosX, conPosY);
            } else if (conPosY < labyrinth[conPosX].length - 1 && labyrinth[conPosX][conPosY + 1].isPath()) {
                openToEastFrom(conPosX, conPosY);
            } else {
                throw new IllegalStateException("Verbindungsknoten findet keinen begehbaren Knoten");
            }
            possibleConnectionNodes = findPossibleConnections();
        }
    }

    private void openToNorthFrom(int conPosX, int conPosY) {
        labyrinth[conPosX - 1][conPosY].openSouth();
        labyrinth[conPosX - 1][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX - 1][conPosY]);
        labyrinth[conPosX][conPosY].openNorth();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);

    }

    private void openToSouthFrom(int conPosX, int conPosY) {
        labyrinth[conPosX][conPosY].openSouth();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);
        labyrinth[conPosX + 1][conPosY].openNorth();
        labyrinth[conPosX + 1][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX + 1][conPosY]);
    }

    private void openToWestFrom(int conPosX, int conPosY) {
        labyrinth[conPosX][conPosY].openWest();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);
        labyrinth[conPosX][conPosY - 1].openEast();
        labyrinth[conPosX][conPosY - 1].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY - 1]);
    }

    private void openToEastFrom(int conPosX, int conPosY) {
        labyrinth[conPosX][conPosY + 1].openWest();
        labyrinth[conPosX][conPosY + 1].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY + 1]);
        labyrinth[conPosX][conPosY].openEast();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);
    }

    private ArrayList<Node> findPossibleConnections() {
        ArrayList<Node> possibleConnectionNodes = new ArrayList<>();
        for (Node node : addedNodes) {
            if (node.isWallNorth() && node.getPosX() >= 1) {
                if (isNodePossibleConnection(labyrinth[node.getPosX() - 1][node.getPosY()])) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX() - 1][node.getPosY()]);
                }
            }
            if (node.isWallEast() && node.getPosY() < labyrinth[node.getPosX()].length - 1) {
                if (isNodePossibleConnection(labyrinth[node.getPosX()][node.getPosY() + 1])) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX()][node.getPosY() + 1]);
                }
            }
            if (node.isWallSouth() && node.getPosX() < labyrinth.length - 1) {
                if (isNodePossibleConnection(labyrinth[node.getPosX() + 1][node.getPosY()])) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX() + 1][node.getPosY()]);
                }
            }
            if (node.isWallWest() && node.getPosY() >= 1) {
                if (isNodePossibleConnection(labyrinth[node.getPosX()][node.getPosY() - 1])) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX()][node.getPosY() - 1]);
                }
            }
        }
        return possibleConnectionNodes;
    }

    protected boolean isNodePossibleConnection(Node node) {
        if (node.isPath()) return false;

        int directNeighbours = 0;
        int indirectNeighbours = 0;
        boolean hasNorth = node.getPosX() > 0;
        boolean hasSouth = node.getPosX() < labyrinth.length - 1;
        boolean hasWest = node.getPosY() > 0;
        boolean hasEast = node.getPosY() < labyrinth[0].length - 1;

        if (hasNorth && labyrinth[node.getPosX() - 1][node.getPosY()].isPath()) {
            directNeighbours++;
        }
        if (hasSouth && labyrinth[node.getPosX() + 1][node.getPosY()].isPath()) {
            directNeighbours++;
        }
        if (hasWest && labyrinth[node.getPosX()][node.getPosY() - 1].isPath()) {
            directNeighbours++;
        }
        if (hasEast && labyrinth[node.getPosX()][node.getPosY() + 1].isPath()) {
            directNeighbours++;
        }

        if (hasNorth && hasWest) {
            if (labyrinth[node.getPosX() - 1][node.getPosY() - 1].isPath()) {
                indirectNeighbours++;
            }
        }
        if (hasNorth && hasEast) {
            if (labyrinth[node.getPosX() - 1][node.getPosY() + 1].isPath()) {
                indirectNeighbours++;
            }
        }
        if (hasSouth && hasWest) {
            if (labyrinth[node.getPosX() + 1][node.getPosY() - 1].isPath()) {
                indirectNeighbours++;
            }
        }
        if (hasSouth && hasEast) {
            if (labyrinth[node.getPosX() + 1][node.getPosY() + 1].isPath()) {
                indirectNeighbours++;
            }
        }


        return (directNeighbours == 1 && indirectNeighbours < 2);
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
