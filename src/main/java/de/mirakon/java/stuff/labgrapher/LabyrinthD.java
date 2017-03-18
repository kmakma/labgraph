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

@SuppressWarnings("Duplicates")
public class LabyrinthD extends LabyrinthA {

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    // TODO: 18.03.2017 macht manchmal kreise !!!

    LabyrinthD(int x, int y) {
        super(x, y);
    }

    @Override
    void openHorizontalAt(int conPosX, int conPosY) {
        super.openHorizontalAt(conPosX, conPosY);
        addedNodes.add(labyrinth[conPosX][conPosY]);
    }

    @Override
    void openVerticalAt(int conPosX, int conPosY) {
        super.openVerticalAt(conPosX, conPosY);
        addedNodes.add(labyrinth[conPosX][conPosY]);
    }

    @SuppressWarnings("Duplicates")
    @Override
    ArrayList<Node> findPossibleConnections() {
        ArrayList<Node> possibleConnectionNodes = new ArrayList<>();
        for (Node node : addedNodes) {
            if (node.isWallNorth() && node.getPosX() >= 2) {
                if (isPossibleConnection(node, NORTH)) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX() - 1][node.getPosY()]);
                }
            }
            if (node.isWallEast() && node.getPosY() < labyrinth[node.getPosX()].length - 2) {
                if (isPossibleConnection(node, EAST)) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX()][node.getPosY() + 1]);
                }
            }
            if (node.isWallSouth() && node.getPosX() < labyrinth.length - 2) {
                if (isPossibleConnection(node, SOUTH)) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX() + 1][node.getPosY()]);
                }
            }
            if (node.isWallWest() && node.getPosY() >= 2) {
                if (isPossibleConnection(node, WEST)) {
                    possibleConnectionNodes.add(labyrinth[node.getPosX()][node.getPosY() - 1]);
                }
            }
        }
        return possibleConnectionNodes;
    }

    private ArrayList<Node> findShortPossibleConnections() {
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

    @Override
    void createLab() {
        super.createLab();

        ArrayList<Node> possibleConnectionNodes = findShortPossibleConnections();
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
            possibleConnectionNodes = findShortPossibleConnections();
        }
    }
    void openToNorthFrom(int conPosX, int conPosY) {
        labyrinth[conPosX - 1][conPosY].openSouth();
        labyrinth[conPosX - 1][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX - 1][conPosY]);
        labyrinth[conPosX][conPosY].openNorth();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);

    }

    void openToSouthFrom(int conPosX, int conPosY) {
        labyrinth[conPosX][conPosY].openSouth();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);
        labyrinth[conPosX + 1][conPosY].openNorth();
        labyrinth[conPosX + 1][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX + 1][conPosY]);
    }

    void openToWestFrom(int conPosX, int conPosY) {
        labyrinth[conPosX][conPosY].openWest();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);
        labyrinth[conPosX][conPosY - 1].openEast();
        labyrinth[conPosX][conPosY - 1].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY - 1]);
    }

    void openToEastFrom(int conPosX, int conPosY) {
        labyrinth[conPosX][conPosY + 1].openWest();
        labyrinth[conPosX][conPosY + 1].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY + 1]);
        labyrinth[conPosX][conPosY].openEast();
        labyrinth[conPosX][conPosY].setToPath();
        addedNodes.add(labyrinth[conPosX][conPosY]);
    }

    protected boolean isNodePossibleConnection(Node node) {
        if (node.isPath()) return false;

        int directNeighbour = -1;
        boolean neNeighbour = false;
        boolean seNeighbour = false;
        boolean swNeighbour = false;
        boolean nwNeighbour = false;
        boolean hasNorth = false;
        boolean hasSouth = false;
        boolean hasWest = false;
        boolean hasEast = false;
        if (node.getPosX() > 0) {
            hasNorth = true;
        }
        if (node.getPosX() < labyrinth.length - 1) {
            hasSouth = true;
        }
        if (node.getPosY() > 0) {
            hasWest = true;
        }
        if (node.getPosY() < labyrinth[0].length - 1) {
            hasEast = true;
        }

        if (hasNorth) {
            if (labyrinth[node.getPosX() - 1][node.getPosY()].isPath()) {
                directNeighbour = NORTH;
            }
            neNeighbour = (hasEast && labyrinth[node.getPosX() - 1][node.getPosY() + 1].isPath());
            nwNeighbour = (hasWest && labyrinth[node.getPosX() - 1][node.getPosY() - 1].isPath());
        }
        if (hasWest && labyrinth[node.getPosX()][node.getPosY() - 1].isPath()) {
            if (directNeighbour > -1) {
                return false;
            }
            directNeighbour = WEST;

        }
        if (hasSouth) {
            if (labyrinth[node.getPosX() + 1][node.getPosY()].isPath()) {
                if (directNeighbour > -1) {
                    return false;
                }
                directNeighbour = SOUTH;
            }
            seNeighbour = (hasEast && labyrinth[node.getPosX() + 1][node.getPosY() + 1].isPath());
            swNeighbour = (hasWest && labyrinth[node.getPosX() + 1][node.getPosY() - 1].isPath());
        }
        if (hasEast && labyrinth[node.getPosX()][node.getPosY() + 1].isPath()) {
            if (directNeighbour > -1) {
                return false;
            }
            directNeighbour = EAST;
        }

        switch (directNeighbour) {
            case NORTH:
                return !(seNeighbour || swNeighbour);
            case EAST:
                return !(swNeighbour || nwNeighbour);
            case SOUTH:
                return !(neNeighbour || nwNeighbour);
            case WEST:
                return !(neNeighbour || seNeighbour);
            default:
                throw new IllegalStateException("Knoten hat keinen Nachbarn, wurde aber vom Nachbarn aufgerufen");
        }
    }

    private boolean isPossibleConnection(Node node, int direction) {
        boolean hasNorth = node.getPosX() > 0;
        boolean hasSouth = node.getPosX() < labyrinth.length - 1;
        boolean hasWest = node.getPosY() > 0;
        boolean hasEast = node.getPosY() < labyrinth[0].length - 1;
        int xNorthCheck = -1;
        int xSouthCheck = -1;
        int yWestCheck = -1;
        int yEastCheck = -1;
        switch (direction) {
            case NORTH:
                if (node.getPosX() > 2) {
                    xNorthCheck = 3;
                } else {
                    xNorthCheck = 2;
                }
                if (hasEast) yEastCheck = 1;
                if (hasWest) yWestCheck = 1;
                break;
            case SOUTH:
                if (node.getPosX() < labyrinth.length - 3) {
                    xSouthCheck = 3;
                } else {
                    xSouthCheck = 2;
                }
                if (hasEast) yEastCheck = 1;
                if (hasWest) yWestCheck = 1;
                break;
            case WEST:
                if (node.getPosY() > 2) {
                    yWestCheck = 3;
                } else {
                    yWestCheck = 2;
                }
                if (hasNorth) xNorthCheck = 1;
                if (hasSouth) xSouthCheck = 1;
                break;
            case EAST:
                if (node.getPosY() < labyrinth[0].length - 3) {
                    yEastCheck = 3;
                } else {
                    yEastCheck = 2;
                }
                if (hasNorth) xNorthCheck = 1;
                if (hasSouth) xSouthCheck = 1;
                break;
        }
        return checkRegionIfNoPath(node, xNorthCheck, xSouthCheck, yWestCheck, yEastCheck);
    }

    private boolean checkRegionIfNoPath(Node node, int xNorthCheck, int xSouthCheck, int yWestCheck, int yEastCheck) {
        int startX = node.getPosX();
        int startY = node.getPosY();
        for (int xIt = startX - xNorthCheck; xIt <= startX + xSouthCheck; xIt++) {
            for (int yIt = startY - yWestCheck; yIt <= startY + yEastCheck; yIt++) {
                if (labyrinth[xIt][yIt].isPath()) return false;
            }
        }
        return true;
    }
}
