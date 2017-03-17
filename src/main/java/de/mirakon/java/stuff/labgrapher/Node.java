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

class Node {

    private int posX;
    private int posY;
    private boolean wallNorth = true;
    private boolean wallEast = true;
    private boolean wallSouth = true;
    private boolean wallWest = true;

    Node(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    boolean isWallEast() {
        return wallEast;
    }

    boolean isWallSouth() {
        return wallSouth;
    }

    boolean isWallWest() {
        return wallWest;
    }

    boolean isWallNorth() {
        return wallNorth;
    }

    public void openNorth() {
        wallNorth = false;
    }

    public void openEast() {
        wallEast = false;
    }

    public void openSouth() {
        wallSouth = false;
    }

    public void openWest() {
        wallWest = false;
    }

    public void openHorizontal() {
        wallEast = false;
        wallWest = false;
    }

    public void openVertical() {
        wallNorth = false;
        wallSouth = false;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    boolean isAnywhereOpen() {
        return !(wallNorth && wallEast && wallSouth && wallWest);
    }
}
