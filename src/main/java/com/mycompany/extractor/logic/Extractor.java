package com.mycompany.extractor.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import com.mycompany.extractor.model.Record;


/**
 * Created by Xavier on 4/23/2017.
 */
public class Extractor implements ExtractorInterface {


        static String url="https://news.ycombinator.com/";
        //filtering
        static Predicate<Record> greatFiveWords= e ->e.getTitle().split("\\W").length>5;
        static Predicate<Record> lessFiveWords= e ->e.getTitle().split("\\W").length<=5;

        //sorting
        static Comparator<Record> compAmPoints =  Comparator.comparing(Record::getAmoPoints);
        static Comparator<Record> compAmComments = Comparator.comparing(Record::getAmoComments);

        //labels
        static String lblSeparator="=================================================";
        static String lblGt5SortPoi=lblSeparator+"\nTitle greater than 5 words and sorted by number of comments\n";
        static String lblLt5SortCom=lblSeparator+"\nTitle less or equal than 5 and sorted by number of points\n";

        static Consumer<List<Record>> printOut = Extractor::showResult;

    public static void main(String[] args) {
        Extractor ext= new Extractor();
        int numRec=30;
        Pair<Elements,Elements> rawInput;
        List<Record> extractedData;
        List<Record> pro1;
        List<Record> pro2;

        rawInput=ext.getRawData();
        extractedData=ext.processInput(rawInput,numRec);

        pro1=ext.filterSort(extractedData, greatFiveWords, compAmComments);
        pro2=ext.filterSort(extractedData, lessFiveWords, compAmPoints);

        System.out.println(lblGt5SortPoi);
        printOut.accept(pro1);
        System.out.println(lblLt5SortCom);
        printOut.accept(pro2);
      }

      @Override
      public List<Record> filterSort(List<Record> entries, Predicate<Record> filterCriteria, Comparator<Record> sortCriteria) {
        return entries.stream().filter(filterCriteria).
                sorted(sortCriteria).collect(Collectors.toList());
    }

    @Override
    public  List<Record> processInput(Pair<Elements,Elements> input, int number) {
        Iterator<Element> itAth;
        Iterator<Element> itSub;
        List<Record> outputList= new ArrayList<>();

        itAth = input.getLeft().iterator();
        itSub = input.getRight().iterator();

        for (int i = 1; i <=number ; i++) {
            outputList.add(parseInputEntry(Pair.of(itAth.next(), itSub.next())));
        }
        return outputList;
    }

    @Override
    public Pair<Elements,Elements> getRawData() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements els = doc.getElementsByClass("athing");
        Elements sub = doc.getElementsByClass("subtext");
        return Pair.of(els,sub);
    }

    private static void showResult(List<Record> proData) {
        proData.stream().forEach(System.out::println);
        System.out.println("Number of Records: "+proData.size());
    }

    private Integer format(Element element) {
        if (element.text().contains("points") || element.text().contains("comments"))
            return Integer.valueOf(element.text().replaceAll("\\D+",""));
        else
            return 0;
    }

    private Record parseInputEntry(Pair<Element,Element> input){
        Record entry= new Record();
        Element tmp1 =input.getLeft();
        Elements rankElem=tmp1.getElementsByClass("rank");
        Elements titleElem=tmp1.getElementsByClass("storylink");

        Element tmp2 = input.getRight();
        Element points=tmp2.children().first();
        Element comments=tmp2.children().last();

        entry.setTitle(titleElem.text());

        int rank=Integer.valueOf(rankElem.text().replace(".",""));
        int amPoint=format(points);
        int amComments= format(comments);

        entry.setNumOrder(rank);
        entry.setAmoPoints(amPoint);
        entry.setAmoComments(amComments);
        return entry;
    }

}
