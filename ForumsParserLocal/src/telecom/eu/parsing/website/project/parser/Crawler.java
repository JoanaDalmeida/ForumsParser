/**
 * Software Name : Website Parser
 *
 * Copyright (C) 2014  Telecom Bretagne
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ------------------------------------------------------------------
 * File Name   : Crawler.java
 *
 * Created     : 18/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 * 
 * this class crawls website and get all URLs 
 */

package telecom.eu.parsing.website.project.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	public static void processPage(String Url, ArrayList<String> keyWordsInURL,ArrayList<String> exclusionKeyWords,
			ParsingPostsOnWebPage parsingPostsOnWebPage,WriteToCSV writeToCSV ) {
		Logger.getLogger(Crawler.class).debug("Process page "+ Url+ "\n");
		if(verifyUrl(Url, keyWordsInURL,exclusionKeyWords )!=null){
			if (!OutputParams.searchUrlInSuccedUrlsFile(Url)) { 
				Logger.getLogger(Crawler.class).debug(Url);
				parsingPostsOnWebPage.processData(Url,writeToCSV,0);
				Elements links =getUrlsOnPage(Url,0);
				if(links!=null){
					for(Element link: links){	
						processPage(link.attr("abs:href"), keyWordsInURL,exclusionKeyWords, parsingPostsOnWebPage, writeToCSV);
					}
				}

			}
		}

	}


	/**
	 * Parse URLS in WebPages.
	 * @param parsingPostsOnWebPage
	 * @param writeToCSV
	 */
	public static void processPage(String urlsFileName, ParsingPostsOnWebPage parsingPostsOnWebPage,WriteToCSV writeToCSV ) {

		try {
			FileInputStream fis = new FileInputStream(new File(urlsFileName));

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				Logger.getLogger(Crawler.class).debug(line);
				parsingPostsOnWebPage.processData(line,writeToCSV,0);
			}
			br.close();

		} catch (IOException e) {
			Logger.getLogger(Crawler.class).debug("Exception :" + e.getMessage());	

		}	
	}


	private  static Elements getUrlsOnPage(String url, int howTimes){
		Elements links =null;
		try {
			Document doc;
			doc = Jsoup.connect(url).userAgent("Mozilla").get();
			if(doc!=null){
				Element elt = doc.body();
				if(elt!=null){		
					links =elt.select("a[href]");
				}
			}
		} catch (SocketTimeoutException a) {
			//try three times the same URL if timeoutexecption
			if (howTimes+1==3){
				OutputParams.appendToFailedUrlsFile(url);
				Logger.getLogger(InitParser.class).debug("Can't process this URL :" + url +" Content, cause time out exception");	
			} else {
				getUrlsOnPage(url, howTimes+1);
			}
		} catch (IOException e) {
			Logger.getLogger(Crawler.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
		}
		return links;
	}





	private static String verifyUrl(String url, ArrayList<String> keyWordsInURL,ArrayList<String> exclusionKeyWords) {

		if (!url.toLowerCase().startsWith("http")) {
			return null;
		}
		try {
			new URL(url);
		} 
		catch (MalformedURLException e) {
			Logger.getLogger(Crawler.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
			return null;
		}

		for(int i=0; i<keyWordsInURL.size(); i++){
			if(!url.toLowerCase().contains(keyWordsInURL.get(i).toLowerCase())){
				return null;
			}
		}

		for(int i=0; i<exclusionKeyWords.size(); i++){
			if(!url.toLowerCase().contains(exclusionKeyWords.get(i).toLowerCase())){
				return null;
			}
		}
		return url;
	}





	public static void processPageByPagination(String url, ArrayList<String> keyWordsPagination,
			ArrayList<String> exclusionKeyWords, ParsingPostsOnWebPage parsingPostsOnWebPage, WriteToCSV writeToCSV) {
		Logger.getLogger(Crawler.class).debug("Process page by pagination "+ url+ "\n");
		if(verifyUrlPagination(url, exclusionKeyWords )!=null){
			if (!OutputParams.searchUrlInSuccedUrlsFile(url)) { 
				Logger.getLogger(Crawler.class).debug(url);
				parsingPostsOnWebPage.processData(url,writeToCSV,0);
				Elements links =getUrlsOnPagePagination(url,keyWordsPagination,0);
				if(links!=null){
					for(Element link: links){	
						processPageByPagination(link.attr("abs:href"),keyWordsPagination ,exclusionKeyWords,  parsingPostsOnWebPage, writeToCSV);
					}
				}

			}
		}

	}


	private static Elements getUrlsOnPagePagination(String url,
			ArrayList<String> keyWordsPagination, int howTimes) {
		Elements links =null;
		Elements links1 = new Elements();
		Elements links2 = new Elements();
		try {
			Document doc;
			doc = Jsoup.connect(url).userAgent("Mozilla").get();
			if(doc!=null){
				Element elt = doc.body();
				if(elt!=null){	
					if(!url.contains(keyWordsPagination.get(2))){
						links1 =elt.select("a[abs:href*="+keyWordsPagination.get(0)+"]");
					} 
					links2 =elt.select("a[abs:href*="+keyWordsPagination.get(2)+"]");
					links = new Elements();
					for(int i=0; i<links1.size(); i++){
						links.add(links1.get(i));
					}
					for(int i=0; i<links2.size(); i++){
						links.add(links2.get(i));
					}
				}
			}
		} catch (SocketTimeoutException a) {
			//try three times the same URL if timeoutexecption
			if (howTimes+1==3){
				OutputParams.appendToFailedUrlsFile(url);
				Logger.getLogger(Crawler.class).debug("Can't process this URL :" + url +" Content, cause time out exception");	
			} else {
				getUrlsOnPagePagination(url, keyWordsPagination, howTimes+1);
			}
		} catch (IOException e) {
			Logger.getLogger(Crawler.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
		}
		return links;
	}


	private static String  verifyUrlPagination(String url,ArrayList<String> exclusionKeyWords) {

		if (!url.toLowerCase().startsWith("http")) {
			return null;
		}
		try {
			new URL(url);
		} 
		catch (MalformedURLException e) {
			Logger.getLogger(Crawler.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
			return null;
		}
		for(int i=0; i<exclusionKeyWords.size(); i++) {
			if(url.contains(exclusionKeyWords.get(i))){
				return null;
			}
		}
		return url;
	}

}
