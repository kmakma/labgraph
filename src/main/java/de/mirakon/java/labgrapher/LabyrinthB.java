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

package de.mirakon.java.labgrapher;

/**
 * erweitertest Labyrinth mit Jarní-Prim-Algorithmus
 * <p>
 * ACHTUNG: DIESE IMPLEMENTIERUNG IST SOWAS VON ÜBERHAUPT NICHT LAUFZEIT EFFIZIENT!!!
 */
@Deprecated
public class LabyrinthB extends LabyrinthC {

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    LabyrinthB(int x, int y) {
        super(x, y);
    }

    @Override
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
}
