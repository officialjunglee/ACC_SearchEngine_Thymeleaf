package com.acc.searchngine.processor;

import com.google.gson.Gson;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.acc.searchngine.scraper.WebScraper;
import com.acc.searchngine.sorting.Sort;
import com.acc.searchngine.stropr.StringOperations;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class algo {

    public static void main(String[] args) {
    }
    public static ArrayList search(String query) throws IOException
    {
        // Fetch Data from Google
        WebScraper scraper = new WebScraper();
        Document webData=scraper.get_google_Data(query);
        Elements links = webData.select("a[href]");
        StringOperations stoper = new StringOperations();
        String[] domain_list=new String[15];
        String[] temp_list=new String[15];
        ArrayList result_list = new ArrayList<TreeMap>();
        TreeMap<String,ArrayList> final_data = new TreeMap<String,ArrayList>();
        int i =0;
        FileWriter f1 = new FileWriter("./Results.txt", false);
        for (Element link : links)
            f1.write(link.attr("href")+"\n");
        f1.close();
        for (Element link : links) {
            String temp = link.attr("href");
            if(temp.startsWith("/url?")){
                if(i==15)
                {
                    break;
                }
                // Use Regex to obtain Domain Name
                String domain=stoper.dnsExtractor(temp,true);
                String url = temp.substring(0,temp.indexOf("&ved"));
                if(domain!=null&&url!=null)
                {
                    domain_list[i]=domain;
                    temp_list[i]=url;
                    i++;
                }

            }
        }
        String finalDomainList []= new String[i];
        i=0;
        for(String temp : finalDomainList)
            if(domain_list[i]!=null)
            {
                finalDomainList[i]=domain_list[i];
                i++;
            }
        // Sort the Results in Alphabetical Order using Merge Sort
        Sort msort = new Sort();
        //msort.shellsort(temp_list);
        msort.mergeSort(temp_list);
        for(String temp : temp_list) {
            if(temp != null && temp.length() > 0) {
                int d = temp.indexOf("http");
                temp = temp.substring(d);
                TreeMap<String,String> data = new TreeMap<String,String>();
                // Count Keyword Occurences
                data=stoper.keywordCounter(temp,query);
                result_list.add(data);
            }
        }
        //System.out.println(domain_list);
        final_data.put("results",result_list);
        //Remove Null Values from Array
        List<String> list = new ArrayList<String>();
        for(String s : domain_list) {
            if(s != null && s.length() > 0) {
                list.add(s);
            }
        }
        domain_list = list.toArray(new String[list.size()]);
        // Remove Duplicates from Array
        int end = domain_list.length;
        Set<String> set = new HashSet<String>();
        for(int j = 0; j < end; j++){
            set.add(domain_list[j]);
        }
        ArrayList analysis_list = new ArrayList<TreeMap>();
        // Run Frequency Analysis on the Domains Returned
        analysis_list.add(stoper.frequency(set,webData.html()));
        final_data.put("analysis",analysis_list);
        String json=new Gson().toJson(final_data);
        // Return Data to GUI in Json Format
        return result_list;
    }
}