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
 * File Name   : LaunchWebParsing.java
 *
 * Created     : 18/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 */

package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



public class LaunchWebParsing {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		//getting the config file URL
		//String configFilePath = "pathToConfigFile";
		//String configFilePath = "C:/Users/AissataJo/Desktop/ProjetMineur/Parambethsoft.xml";

		String configFilePath = "C:/Users/AissataJo/Desktop/ProjetMineur/Resultats/ATARI/ConfigFileATARI.xml";
		if( !(new File(configFilePath).exists()) ){
			Logger.getLogger(LaunchWebParsing.class).debug("the path to the configuration file is not correct. Please set a corret one.");
			return;
		}

		ReadConfigFile readConfigFile = new ReadConfigFile(configFilePath);


		/* *********** read config file to get Paramters******** */	
		String postsParamsInput=null;
		boolean useParametersFile = false;
		String useParametersFileStringValue = (String) readConfigFile.readParamInXmlFile("useParametersFile");
		if(useParametersFileStringValue!=null){
			if(useParametersFileStringValue.equalsIgnoreCase("true")||useParametersFileStringValue.equalsIgnoreCase("false")){
				useParametersFile =  Boolean.parseBoolean(useParametersFileStringValue);
				if(useParametersFile){
					postsParamsInput = (String) readConfigFile.readParamInXmlFile("postsParamsInput");
					if(postsParamsInput==null) {
						Logger.getLogger(LaunchWebParsing.class).debug("the param postsParamsInput is not set");
					} else {
						Logger.getLogger(LaunchWebParsing.class).debug("the param postsParamsInput :"+ postsParamsInput);
						if( !(new File(postsParamsInput).exists()) ){
							Logger.getLogger(LaunchWebParsing.class).debug("the path to the  posts Params File is not correct. Please set a corret one.");
							postsParamsInput=null;
						}
					}
				}
			}
		}


		String urlsFileName=null;
		boolean useUrlsInFile = false;
		String useUrlsInFileStringValue = (String) readConfigFile.readParamInXmlFile("useUrlsInFile");
		if(useUrlsInFileStringValue!=null){
			if(useUrlsInFileStringValue.equalsIgnoreCase("true")||useUrlsInFileStringValue.equalsIgnoreCase("false")){
				useUrlsInFile =  Boolean.parseBoolean(useUrlsInFileStringValue);
				if(useUrlsInFile){
					urlsFileName = (String) readConfigFile.readParamInXmlFile("urlsFileName");
					if(urlsFileName==null) {
						Logger.getLogger(LaunchWebParsing.class).debug("the param urlsFileName is not set");
					} else {
						if( !(new File(urlsFileName).exists()) ){
							Logger.getLogger(LaunchWebParsing.class).debug("the path to the Urls file is not correct. Please set a corret one.");
							urlsFileName=null;
						}
					}
				}
			}
		}


		String pageUrlForExample=(String) readConfigFile.readParamInXmlFile("samplePageUrl");
		if(pageUrlForExample==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param samplePageUrl is not set");
		}

		String StartpageUrl=(String) readConfigFile.readParamInXmlFile("forumToParseURL");
		if(StartpageUrl==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param forumToparseURL is not set");
		}



		@SuppressWarnings("unchecked")
		ArrayList<String> keyWordsInURL = (ArrayList<String>) readConfigFile.readParamInXmlFile("keyWordsInURL");
		@SuppressWarnings("unchecked")
		ArrayList<String> exclusionKeyWords = (ArrayList<String>) readConfigFile.readParamInXmlFile("exclusionKeyWords");


		//post sample, the parser will use it to find the tags of the post
		String date =(String) readConfigFile.readParamInXmlFile("postDate");
		if(date==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param postDate is not set");
		}

		String author = (String) readConfigFile.readParamInXmlFile("postAurhor");
		if(author==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param postAurhor is not set");
		}

		String message = (String) readConfigFile.readParamInXmlFile("postMessage");
		if(message==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param postMessage is not set");
		}



		// Delimiter of the CSV file in which the program saves all posts
		String delimiterOfPostsFile =(String) readConfigFile.readParamInXmlFile("DelimiterOfPostsFile");
		if(delimiterOfPostsFile==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param delimiterOfPostsFile is not set");
		}

		// File where post will be store
		String postsFileName =(String) readConfigFile.readParamInXmlFile("postsFileName");
		if(postsFileName==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param pathToPostsFile is not set");
		} else {
			if( !(new File(postsFileName).getParentFile().exists()) ){
				Logger.getLogger(LaunchWebParsing.class).debug("the path to the posts file is not correct. Please set a corret one.");
				postsFileName=null;
			}
		}

		// File where the program saves the posts parameters
		String postsParamsFileName =(String) readConfigFile.readParamInXmlFile("postsParamsFileName");
		if(postsParamsFileName==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param paramsFileName is not set");
		}else {
			if( !(new File(postsParamsFileName).getParentFile().exists()) ){
				Logger.getLogger(LaunchWebParsing.class).debug("the path to the posts parameters file is not correct. Please set a corret one.");
				postsParamsFileName=null;
			}
		}		

		// File where the program saves all Urls that it can't parse
		String failedUrlsFileName =(String) readConfigFile.readParamInXmlFile("failedUrlsFileName");
		if(failedUrlsFileName==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param failedUrlsLFileName is not set");
		} else {
			if( !(new File(failedUrlsFileName).getParentFile().exists()) ){
				Logger.getLogger(LaunchWebParsing.class).debug("the path to the failed Urls  file is not correct. Please set a corret one.");
				failedUrlsFileName=null;
			}
		}

		// File where post the program saves All urls that it parses
		String succedUrlsFileName =(String) readConfigFile.readParamInXmlFile("succedUrlsFileName");
		if(succedUrlsFileName==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param succedUrlsFileName is not set");
		} else {
			if( !(new File(succedUrlsFileName).getParentFile().exists()) ){
				Logger.getLogger(LaunchWebParsing.class).debug("the path to the succed Urls  file is not correct. Please set a corret one.");
				succedUrlsFileName=null;
			}
		}


		boolean usePagination = false;
		ArrayList<String> keyWordsPagination = new ArrayList<String>();
		String usePaginationStringValue = (String) readConfigFile.readParamInXmlFile("usePagination");
		if(usePaginationStringValue!=null){
			if(usePaginationStringValue.equalsIgnoreCase("true")||usePaginationStringValue.equalsIgnoreCase("false")){
				usePagination =  Boolean.parseBoolean(usePaginationStringValue);
				if(usePagination){
					String dicussionsListPageURLStartWith = (String) readConfigFile.readParamInXmlFile("dicussionsListPageURLStartWith");
					if(dicussionsListPageURLStartWith==null) {
						Logger.getLogger(LaunchWebParsing.class).debug("the param dicussionsListPageURLStartWith is not set. You must specify it, Initialisation Failed.");
						return;
					}

					String dicussionListPagination = (String) readConfigFile.readParamInXmlFile("dicussionListPagination");
					if(dicussionListPagination==null) {
						Logger.getLogger(LaunchWebParsing.class).debug("the param dicussionListPagination is not set. You must specify it, Initialisation Failed.");
						return;
					}

					String postsListPageStartWith = (String) readConfigFile.readParamInXmlFile("postsListPageStartWith");
					if(postsListPageStartWith==null) {
						Logger.getLogger(LaunchWebParsing.class).debug("the param postsListPageStartWith is not set. You must specify it, Initialisation Failed.");
						return;
					}

					String postsListPagination = (String) readConfigFile.readParamInXmlFile("postsListPagination");
					if(postsListPagination==null) {
						Logger.getLogger(LaunchWebParsing.class).debug("the param postsListPagination is not set. You must specify it, Initialisation Failed.");
						return;
					}
					keyWordsPagination.add(dicussionsListPageURLStartWith);
					keyWordsPagination.add(dicussionListPagination);
					keyWordsPagination.add(postsListPageStartWith);
					keyWordsPagination.add(postsListPagination);
				}
			}
		}



		/* *****************end paramters *******************************/
		// checking If all parameters are set correctly
		if(useParametersFile) {
			if(useUrlsInFile){
				if((StartpageUrl == null) || (postsParamsInput==null) || (urlsFileName == null) || (postsFileName==null) ||
						(postsParamsFileName==null) || (failedUrlsFileName==null) || (succedUrlsFileName==null) || (delimiterOfPostsFile==null)){
					Logger.getLogger(LaunchWebParsing.class).debug("Error : At least one of the params is not set or is not correct.");
					return;
				} 
			} else {
				if((StartpageUrl==null) || (postsParamsInput==null) || (postsFileName==null) || (postsParamsFileName==null) 
						|| (failedUrlsFileName==null) || (succedUrlsFileName==null) || (delimiterOfPostsFile==null)){
					Logger.getLogger(LaunchWebParsing.class).debug("Error : At least one of the params is not set or is not correct.");
					return;
				}
			}
		} else {
			if(useUrlsInFile){
				if((urlsFileName==null) || (pageUrlForExample==null) || (StartpageUrl==null) || (date==null) ||
						(author==null) || (message==null) || (postsFileName==null) || (postsParamsFileName==null)
						|| (failedUrlsFileName==null) || (succedUrlsFileName==null) || (delimiterOfPostsFile==null)){
					Logger.getLogger(LaunchWebParsing.class).debug("Error : At least one of the params is not set or is not correct.");
					return;
				}
			} else {
				if((pageUrlForExample==null) || (StartpageUrl==null) || (date==null) || (author==null) ||
						(message==null) || (postsFileName==null) || (postsParamsFileName==null)
						|| (failedUrlsFileName==null) || (succedUrlsFileName==null) || (delimiterOfPostsFile==null) ){
					Logger.getLogger(LaunchWebParsing.class).debug("Error : At least one of the params is not set or is not correct.");
					return;
				}
			}
		}


		InitParser.startParser(pageUrlForExample,StartpageUrl, date, author, message, 
				useParametersFile, postsParamsInput, useUrlsInFile, urlsFileName,
				delimiterOfPostsFile,postsFileName, postsParamsFileName, failedUrlsFileName, succedUrlsFileName,
				keyWordsInURL, exclusionKeyWords, usePagination, keyWordsPagination );

	}
}