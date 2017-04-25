package com.mycompany.extractor.logic;

import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.select.Elements;
import com.mycompany.extractor.model.Record;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Xavier on 4/24/2017.
 */
public interface ExtractorInterface {

    /**
     *Retrieve raw data from the address url="https://news.ycombinator.com/";
     * @return a Pair which first contains the data from the class athing
     * and the second from the class subtext
     */
    Pair<Elements,Elements> getRawData();

    /**
     * Transform a tuple jsoup elements into a record list, and retrieve the selected number ot items
     * @param rawInput tuple jsoup
     * @param number number of items selected
     * @return
     */
    List<Record>  processInput(Pair<Elements,Elements> rawInput, int number);

    /**
     * Filter and sort a list by a given parameters
     * @param entries
     * @param filterCriteria
     * @param sortCriteria
     * @return
     */
    List<Record>  filterSort(List<Record> entries, Predicate<Record> filterCriteria, Comparator<Record> sortCriteria);

}
