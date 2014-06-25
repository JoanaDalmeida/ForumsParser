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
			boolean useParametersFile, String postsParamsInput, boolean useUrlsInFile, String urlsFileName,
			String delimiterOfPostsFile, String postsFileName, String discussionsFileName, String paramsfileName,String failedUrlsFileName,String succedUrlsFileName,
			ArrayList<String> keyWordsInURL, ArrayList<String> exclusionKeyWords,
			boolean usePagination, ArrayList<String> keyWordsPagination){

		boolean initParamsOk=false;
		if(useParametersFile) {
			initParamsOk=ReadParametersFile.setParametes(postsParamsInput);
		} else {
			Document doc;
			try {
				String pageUrl=StartPageUrl;
				doc = Jsoup.connect(pageUrl).userAgent("Mozilla").get();

				//init params
				InitAllParam.initPostParam(doc, date, author, message);	
				//prompt a confirmation
				String[] parameters = {PostsParameters.getAllPostsContainerTag(), PostsParameters.getAllPostsContainerClass(),
						PostsParameters.getPostContainerTag(), PostsParameters.getPostContainerClass(),
						PostsParameters.getDateContainerTag(), PostsParameters.getDateContainerClass(),
						PostsParameters.getAuthorContainerTag(), PostsParameters.getAuthorContainerClass(),
						PostsParameters.getMessageContainerTag(), PostsParameters.getMessageContainerClass() };

				initParamsOk = ConfirmationDialog.confirmParams(parameters);
				if (initParamsOk){
					PostsParameters.saveParamsToFile(paramsfileName);
				}
			} catch (IOException e) {
				Logger.getLogger(InitParser.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
			}
		}


		////start parsing 
		if (initParamsOk){
			/*the csv file that will contain all posts*/
			WriteToCsvPostsFile writeToPostCSVFile = new WriteToCsvPostsFile(postsFileName, delimiterOfPostsFile);
			
			// Csv file that contains discussions list
			WriteToCsvDiscussionsFile writeToCsvDiscussionsFile = new WriteToCsvDiscussionsFile(discussionsFileName, delimiterOfPostsFile);
			
			OutputParams.setOutputParams(failedUrlsFileName, succedUrlsFileName, postsFileName, paramsfileName, writeToCsvDiscussionsFile);

			Logger.getLogger(InitParser.class).debug("****************Start parsing***********");
			ParsingPostsOnWebPage parsingPostsOnWebPage =  new ParsingPostsOnWebPage();
			if(usePagination){
				//set pagination parameters
				PaginationParameters.setParameters(keyWordsPagination);
				if(!useUrlsInFile){
					Crawler.processPageByPagination(HomeURL,"",false, exclusionKeyWords, parsingPostsOnWebPage, 
							writeToPostCSVFile);
				} else {
					Crawler.processPage(urlsFileName, parsingPostsOnWebPage, writeToPostCSVFile);
				}
			} else {
				if(!useUrlsInFile){
					Crawler.processPage(HomeURL, "", keyWordsInURL, exclusionKeyWords, parsingPostsOnWebPage, writeToPostCSVFile);
				} else {
					Crawler.processPage(urlsFileName, parsingPostsOnWebPage, writeToPostCSVFile);
				}
			}
		} else {
			Logger.getLogger(InitParser.class).debug("***The Step : Parameters Initialisation Failed, Can't continue!***");
		}

	}

}
