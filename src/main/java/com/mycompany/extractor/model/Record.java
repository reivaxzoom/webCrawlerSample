package com.mycompany.extractor.model;

/**
 * Created by Xavier on 4/23/2017.
 */
public class Record {
    String title;
    int numOrder;
    int amoComments;
    int amoPoints;

    /**
     * Data model for extracted data
     */
    public Record() {
    }

    //for testing purposes

    /**
     * Constructor for testing purposes
     * @param numOrder number of the order
     * @param amoComments amount of comments
     * @param amoPoints amount of points
     * @param title
     */
    public Record(int numOrder, int amoComments, int amoPoints, String title) {
        this.title = title;
        this.numOrder = numOrder;
        this.amoComments = amoComments;
        this.amoPoints = amoPoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(int numOrder) {
        this.numOrder = numOrder;
    }

    public int getAmoComments() {
        return amoComments;
    }

    public void setAmoComments(int amoComments) {
        this.amoComments = amoComments;
    }

    public int getAmoPoints() {
        return amoPoints;
    }

    public void setAmoPoints(int amoPoints) {
        this.amoPoints = amoPoints;
    }


    @Override
    public String toString() {
        return "Record{" +
                " numOrder=" + numOrder +
                ", amoComments=" + amoComments +
                ", amoPoints=" + amoPoints +
                ", title='" + title + '\'' +
                '}';
    }
}
