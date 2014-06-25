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
	public static void processPage(String Url, String UrlText, ArrayList<String> keyWordsInURL,
			ArrayList<String> exclusionKeyWords, ParsingPostsOnWebPage parsingPostsOnWebPage, 
			WriteToCsvPostsFile writeToCSV ) {
		Logger.getLogger(Crawler.class).debug("Process page "+ Url+ "\n");
		if(verifyUrl(Url, keyWordsInURL,exclusionKeyWords )!=null){
			if (!OutputParams.searchUrlInSuccedUrlsFile(Url)) { 
				Logger.getLogger(Crawler.class).debug(Url);
				parsingPostsOnWebPage.processData(Url, UrlText, writeToCSV,0);
				Elements links =getUrlsOnPage(Url,keyWordsInURL, 0);
				if(links!=null){
					for(Element link: links){	
						processPage(link.attr("abs:href"), link.text(), keyWordsInURL,exclusionKeyWords, parsingPostsOnWebPage, 
								writeToCSV);
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
	public static void processPage(String urlsFileName, ParsingPostsOnWebPage parsingPostsOnWebPage, 
			WriteToCsvPostsFile writeToCSV ) {
		try {
			FileInputStream fis = new FileInputStream(new File(urlsFileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while ((line = br.readLine()) != null) {
				Logger.getLogger(Crawler.class).debug(line);
				parsingPostsOnWebPage.processData(line,"", writeToCSV,0);
			}
			br.close();
		} catch (IOException e) {
			Logger.getLogger(Crawler.class).debug("Exception :" + e.getMessage());	

		}	
	}


	private  static Elements getUrlsOnPage(String url, ArrayList<String> keyWordsInURL, int howTimes){
		Elements links =null;
		try {
			Document doc;
			doc = Jsoup.connect(url).userAgent("Mozilla").get();
			if(doc!=null){
				Element elt = doc.body();
				if(elt!=null){	
					links = new Elements();
					for(int i=0; i<keyWordsInURL.size();i++){
						Elements elements = elt.select("a[abs:href*="+keyWordsInURL.get(2)+"]");
						links = new Elements();
						for(int j=0; j<elements.size(); i++){
							links.add(elements.get(j));
						}
					}
					///links =elt.select("a[href]");
				}
			}
		} catch (SocketTimeoutException a) {
			//try three times the same URL if timeoutexecption
			if (howTimes+1==3){
				OutputParams.appendToFailedUrlsFile(url);
				Logger.getLogger(InitParser.class).debug("Can't process this URL :" + url +" Content, cause time out exception");	
			} else {
				getUrlsOnPage(url, keyWordsInURL, howTimes+1);
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
			if(url.toLowerCase().contains(exclusionKeyWords.get(i).toLowerCase())){
				return null;
			}
		}
		return url;
	}



	public static void processPageByPagination(String url, String UrlText, 
			boolean ParentUrlContainDiscussionLits, ArrayList<String> exclusionKeyWords, 
			ParsingPostsOnWebPage parsingPostsOnWebPage, WriteToCsvPostsFile writeToPostCSVFile) {
		Logger.getLogger(Crawler.class).debug("Process page by pagination "+ url+ "\n");
		boolean doesParentUrlContainDiscussionLits=false;
		
		if(verifyUrlPagination(url, exclusionKeyWords )!=null){
			if(ParentUrlContainDiscussionLits){
				if(!url.contains(PaginationParameters.getDiscussionsUrlStart()))
				OutputParams.getWriteToCsvDiscussionsFile().findAndReplaceDiscussionsUrlAndTitle(url, UrlText);
			}
			if (!OutputParams.searchUrlInSuccedUrlsFile(url)) { 
				Logger.getLogger(Crawler.class).debug(url);
				parsingPostsOnWebPage.processData(url, UrlText, writeToPostCSVFile,0);
				if(url.contains(PaginationParameters.getDiscussionsUrlStart()) &&
						url.contains(PaginationParameters.getDiscussionsUrlEnd())){
					doesParentUrlContainDiscussionLits=true;
				}
				Elements links =getUrlsOnPagePagination(url, 0);
				if(links!=null){
					for(Element link: links){	
						processPageByPagination(link.attr("abs:href"), link.text(), doesParentUrlContainDiscussionLits,
								exclusionKeyWords,  parsingPostsOnWebPage, 
								writeToPostCSVFile);
					}
				}

			}
		}

	}


	private static Elements getUrlsOnPagePagination(String url,int howTimes) {
		Elements links =null;
		Elements links1 = new Elements();
		Elements links2 = new Elements();
		try {
			Document doc;
			doc = Jsoup.connect(url).userAgent("Mozilla").get();
			if(doc!=null){
				Element elt = doc.body();
				if(elt!=null){	
					links1 =elt.select("a[abs:href~="+PaginationParameters.getDiscussionsUrlRegex()+"]");
					links2 =elt.select("a[abs:href*="+PaginationParameters.getPostsUrlStart()+"]");

					links = new Elements();
					for(int i=0; i<links1.size(); i++){
						if(!isUrlInPosts(elt, links1.get(i).attr("href"))) {
							links.add(links1.get(i));
						}
					}

					for(int i=0; i<links2.size(); i++){
						if(!isUrlInPosts(elt, links2.get(i).attr("href"))) {
							int j=0;
							for(; j<links.size(); j++){
								if((links.get(j).attr("href")).equalsIgnoreCase(links2.get(i).attr("href"))){
									break;
								}
							} 
							if(j==links.size()){
								links.add(links2.get(i));
							}
						}
					}
				}
			}
		} catch (SocketTimeoutException a) {
			//try three times the same URL if timeoutexecption
			if (howTimes+1==3){
				OutputParams.appendToFailedUrlsFile(url);
				Logger.getLogger(Crawler.class).debug("Can't process this URL :" + url +" Content, cause time out exception");	
			} else {
				getUrlsOnPagePagination(url, howTimes+1);
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

	private static boolean isUrlInPosts(Element elt, String url){
		Elements elementsPosts = elt.select(PostsParameters.getPostContainerTag()+"."+PostsParameters.getPostContainerClass());
		for (int j=0; j<elementsPosts.size(); j++ ){
			Element elementPost = elementsPosts.get(j);
			Element elementMessage = null;
			if (elementPost.select(PostsParameters.getMessageContainerTag()+"."+PostsParameters.getMessageContainerClass()).size()>0) {
				elementMessage = elementPost.select(PostsParameters.getMessageContainerTag()+"."
						+PostsParameters.getMessageContainerClass()).first();
			}
			if (elementMessage!=null){
				if (elementMessage.toString().contains(url)) {
					return true;
				}
			}
		}
		return false;
	}

}
