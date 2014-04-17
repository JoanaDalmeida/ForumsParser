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
 * File Name   : InitParser.java
 *
 * Created     : 18/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 * 
 */
package telecom.eu.parsing.website.project.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InitParser {

	public static void startParser(String StartPageUrl,String HomeURL, String date, String author,  String message, 
			String fileName, ArrayList<String> racines, boolean isPostIdExists ){
		Document doc;
		try {
			String pageUrl=StartPageUrl;
			doc = Jsoup.connect(pageUrl).userAgent("Mozilla").get();

			//init params
			InitAllParam.initPostParam(doc, date, author, message, isPostIdExists);	

			/*the csv file that will contain all posts*/
			WriteToCSV writeToCSV = new WriteToCSV(fileName);

			////start parsing 
			Logger.getLogger(InitParser.class).debug("****************Start parsing***********");
			ParsingPostsOnWebPage parsingPostsOnWebPage =  new ParsingPostsOnWebPage();
			Crawler.processPage(HomeURL, racines,parsingPostsOnWebPage,writeToCSV);
			 
		} catch (IOException e) {
			Logger.getLogger(InitParser.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
		}

	}

}
