package com.mycompany.extractor;

import com.mycompany.extractor.logic.Extractor;
import com.mycompany.extractor.model.Record;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import org.jsoup.nodes.Document;
import static org.junit.Assert.*;

/**
 * Created by Xavier on 4/25/2017.
 */
public class ExtractorTest {

    public static final String TEST_OUTPUT_PATH = "src/test/resources/testOutput.txt";
    public static final String INPUT_PAGE_PATH = "src/test/resources/HackerNews.html";
    static List preList = new ArrayList();
    static Pair<Elements, Elements> rawInput;
    static List<Record> validOutputData;
    //filtering
    Predicate<Record> greatFiveWords = e -> e.getTitle().split("\\W").length > 5;
    //sorting
    Comparator<Record> compAmPoints = Comparator.comparing(Record::getAmoPoints);

    /**
     * load predefined list for filterSort
     */
    @Before
    public void initList() {
        loadPredefinedList();
    }

    /**
     * Load data for offline web side
     * @throws IOException
     */
 @Before
    public void loadFromFile() throws IOException {
        //Loading offline data for test
        File input = new File(INPUT_PAGE_PATH);
        Document doc = Jsoup.parse(input, "UTF-8", "https://news.ycombinator.com/news");
        Elements els = doc.getElementsByClass("athing");
        Elements sub = doc.getElementsByClass("subtext");
        rawInput = Pair.of(els, sub);
        //loading expected result from file for validation
        validOutputData = readOutputFile();
    }

    /**Test all process from the offline web page
    to a expected result saved in a file */
    @Test
    public void wholeOfflineProcess() {
        Extractor ext = new Extractor();
        List step1 = ext.processInput(rawInput, 30);
        List step2 = ext.filterSort(step1, greatFiveWords, compAmPoints);
        assertTrue(validOutputData.retainAll(step2));
    }

    /**
     * Test FilterSort from com.mycompany.extractor.logic.Extractor
     * @throws Exception
     */
    @Test
    public void filterSort() throws Exception {
        Extractor ext = new Extractor();
        List<Record> proList = ext.filterSort(preList, greatFiveWords, compAmPoints);
        assertTrue(proList.size() == 29 && isSortedByAmoPoints(proList));
    }


    /**
     * Verify if the list is sorted by ammount of points
     * @param data
     * @return
     */
    private static boolean isSortedByAmoPoints(List<Record> data) {
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i - 1).getAmoPoints() > data.get(i).getAmoPoints()) {
                return false;
            }
        }
        return true;
    }

    public List<Record> readOutputFile() {
        BufferedReader br = null;
        List<Record> listRecord = new ArrayList<>();
        try {
            String line;
            br = new BufferedReader(new FileReader(TEST_OUTPUT_PATH));
            while ((line = br.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                while (stringTokenizer.hasMoreElements()) {
                    Record rec = new Record();
                    rec.setNumOrder(Integer.parseInt(stringTokenizer.nextElement().toString().trim()));
                    rec.setAmoPoints(Integer.parseInt(stringTokenizer.nextElement().toString().trim()));
                    rec.setAmoComments(Integer.parseInt(stringTokenizer.nextElement().toString().trim()));
                    rec.setTitle(stringTokenizer.nextElement().toString().trim());
                    listRecord.add(rec);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return listRecord;
    }


    private void loadPredefinedList() {
        preList.add(new Record(1, 24, 91, "Uber gets sued over alleged ‘Hell’ program to track Lyft drivers"));
        preList.add(new Record(2, 31, 88, "Going Multi-Cloud with Google Cloud Endpoints and AWS Lambda"));
        preList.add(new Record(3, 113, 136, "Square Said to Acquire Team from Struggling Social App Yik Yak"));
        preList.add(new Record(4, 65, 231, "Glob Matching Can Be Simple and Fast Too"));
        preList.add(new Record(5, 97, 192, "Apple cuts App Store affiliate commission from 7% to 2.5%"));
        preList.add(new Record(6, 10, 59, "Alan Kay: Is it really Complex? Or did we make it Complicated?"));
        preList.add(new Record(7, 79, 172, "Its time to end the cult of the CEO"));
        preList.add(new Record(8, 55, 88, "China may lead the electric car revolution"));
        preList.add(new Record(9, 236, 391, "Caterpillar found to eat shopping bags, suggesting solution to plastic pollution"));
        preList.add(new Record(10, 3, 63, "Minimal examples of data structures and algorithms in Python"));
        preList.add(new Record(11, 6, 54, "To keep EpiPen sales up, Mylan threatened states, sued making bogus claims"));
        preList.add(new Record(12, 45, 189, "High-performance .NET by example: Filtering bot traffic"));
        preList.add(new Record(13, 27, 56, "The Intel Optane Memory (SSD) Preview: 32GB of Kaby Lake Caching"));
        preList.add(new Record(14, 268, 1146, "Lyrebird – An API to copy the voice of anyone"));
        preList.add(new Record(15, 20, 31, "BitTorrent Inventor Bram Cohen Will Start His Own Cryptocurrency"));
        preList.add(new Record(16, 63, 163, "Show HN: Play multiplayer and singleplayer NES games online"));
        preList.add(new Record(17, 53, 108, "On Chinese Writing: Evolution"));
        preList.add(new Record(18, 28, 57, "Happy hiring: The firm that recruits Mr Men characters"));
        preList.add(new Record(19, 0, 7, "Show HN: Language Learning with Music and Lyrics"));
        preList.add(new Record(20, 10, 67, "Founder Stories: Kate Heddleston of Opsolutely (YC S16)"));
        preList.add(new Record(21, 16, 86, "How I learned React Native in a weekend and shipped an app"));
        preList.add(new Record(22, 133, 149, "Software Developers after 40, 50 and 60 Whore Still Coding"));
        preList.add(new Record(23, 24, 82, "HPTT: A High-Performance Tensor Transposition C++ Library"));
        preList.add(new Record(24, 0, 0, "Next Caller is hiring second Senior Architect"));
        preList.add(new Record(25, 153, 185, "Why Juicero’s Press Is So Expensive"));
        preList.add(new Record(26, 29, 57, "A black hole in a low mass X-ray binary star"));
        preList.add(new Record(27, 28, 57, "Universal USB-C charging: How the dream is coming true"));
        preList.add(new Record(28, 26, 70, "Amazon Launches Self-Service Marketplace for Subscription Providers"));
        preList.add(new Record(29, 137, 244, "LSD microdoses make people feel sharper, and scientists want to know how"));
        preList.add(new Record(30, 0, 8, "How to Secure Your Web App with HTTP Headers"));
    }

}