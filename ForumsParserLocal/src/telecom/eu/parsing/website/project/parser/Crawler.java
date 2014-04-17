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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	@SuppressWarnings("rawtypes")
	private static HashSet listURLs = new HashSet<>();
	@SuppressWarnings("unchecked")
	public static void processPage(String Url, ArrayList<String> racines, ParsingPostsOnWebPage parsingPostsOnWebPage,WriteToCSV writeToCSV ) {
		Document doc;
		if(verifyUrl(Url)!=null){
			if (listURLs.add(Url) && listURLs.size()<500) { 
				try {
					doc = Jsoup.connect(Url).userAgent("Mozilla").get();
					if(doc!=null){
						Element elt = doc.body();
						if(elt!=null){
							Elements links =elt.select("a[href]");
							for(Element link: links){
								for(int i=0; i<racines.size(); i++){
									if(link.attr("abs:href").contains(racines.get(i))) {
										Logger.getLogger(Crawler.class).debug(Url);
										parsingPostsOnWebPage.processData(Url,writeToCSV);
										processPage(link.attr("abs:href"), racines, parsingPostsOnWebPage, writeToCSV);
										break;
									}
								}

							}
						}
					}
				} catch (IOException e) {
					listURLs.remove(Url);
					Logger.getLogger(Crawler.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());

				}
			}
		}

	}


	private static String verifyUrl(String url) {

		if (!url.toLowerCase().startsWith("http")) {
			return null;
		}
		try {
			new URL(url);
		} 
		catch (MalformedURLException e) {
			return null;
		}

		return url;
	}

	public static ArrayList<String> getURLsLists (){
		@SuppressWarnings("rawtypes")
		Iterator it = listURLs.iterator();
		ArrayList<String> linksList = new ArrayList<String>();
		while (it.hasNext()){
			linksList.add((String) it.next());
		}
		return linksList;

	}
}
