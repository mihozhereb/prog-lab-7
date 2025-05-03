package ru.mihozhereb.collection.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Person class
 */
public class Person {
    /**
     * Person's name
     *
     * @restriction The field can't be null
     * @restriction The string can't be empty
     */
    private String name;

    /**
     * Person's birthday
     *
     * @restriction The field can't be null
     */
    private java.time.LocalDate birthday;

    /**
     * Person's height
     *
     * @restriction The field can be null
     * @restriction The field value must be greater than 0
     */
    private Double height;

    /**
     * Person's weight
     *
     * @restriction The field value must be greater than 0
     */
    private int weight;

    /**
     * Person's hairColor
     *
     * @restriction The field can be null
     */
    private Color hairColor;

    /**
     * Get Person's name
     *
     * @return Person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Person's name
     *
     * @param name Person's name
     * @throws IllegalArgumentException Person's name can't be null and blank
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Person's name can't be null");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Person's name can't be blank");
        }
        this.name = name;
    }

    /**
     * Get Person's birthday
     *
     * @return Person's birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Set Person's birthday
     *
     * @param birthday Person's birthday
     * @throws IllegalArgumentException Person's birthday can't be null
     */
    public void setBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("Person's birthday can't be null");
        }
        this.birthday = birthday;
    }

    /**
     * Get Person's height
     *
     * @return Person's height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Set Person's height
     *
     * @param height Person's height
     * @throws IllegalArgumentException Person's height must be greater than 0
     */
    public void setHeight(Double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Person's height must be greater than 0");
        }
        this.height = height;
    }

    /**
     * Get Person's weight
     *
     * @return Person's weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set Person's weight
     *
     * @param weight Person's weight
     * @throws IllegalArgumentException Person's weight must be greater than 0
     */
    public void setWeight(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Person's weight must be greater than 0");
        }
        this.weight = weight;
    }

    /**
     * Get Person's hair color
     *
     * @return Person's hair color
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Set Person's hair color
     *
     * @param hairColor Person's hair color
     */
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return weight == person.weight && Objects.equals(name, person.name) &&
                Objects.equals(birthday, person.birthday) &&
                Objects.equals(height, person.height) && hairColor == person.hairColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, height, weight, hairColor);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", hairColor=" + hairColor +
                '}';
    }
}
