package ru.mihozhereb.collection.model;

import java.util.Objects;

/**
 * Coordinates class
 */
public class Coordinates implements Comparable<Coordinates> {
    /**
     * X coordinate
     *
     * @restriction The field value must be greater than -823
     * @restriction The field can't be null
     */
    private Double x;

    /**
     * Y coordinate
     *
     * @restriction Maximum field value: 752
     * @restriction The field can't be null
     */
    private Float y;


    /**
     * get X coordinate
     *
     * @return X coordinate
     */
    public Double getX() {
        return x;
    }

    /**
     * set X coordinate
     *
     * @param x X coordinate
     * @throws IllegalArgumentException X can't be null and must be greater than -823
     */
    public void setX(Double x) {
        if (x == null) {
            throw new IllegalArgumentException("X can't be null");
        } else if (x <= -823) {
            throw new IllegalArgumentException("X must be greater than -823");
        }
        this.x = x;
    }

    /**
     * get Y coordinate
     *
     * @return Y coordinate
     */
    public Float getY() {
        return y;
    }

    /**
     * set Y coordinate
     * @param y Y coordinate
     * @throws IllegalArgumentException X can't be null and must be less than 752
     */
    public void setY(Float y) {
        if (y == null) {
            throw new IllegalArgumentException("Y can't be null");
        } else if (y > 752) {
            throw new IllegalArgumentException("Y must be less than 752");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the executes the command with arguments from the request and returns the
     * result in the response object.
     * <p>
     * The string includes the {@code x} and {@code y} coordinates, formatted in a compact style.
     * </p>
     *
     * @return a string representation of the {@code Coordinates} object
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Coordinates o) {
        int result = this.getX().compareTo(o.getX());
        if (result != 0) return result;
        result = this.getY().compareTo(o.getY());
        return result;
    }
}
