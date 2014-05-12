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

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



public class LaunchWebParsing {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		//getting the config file URL
		String configFilePath = "pathToConfigFile";
		ReadConfigFile readConfigFile = new ReadConfigFile(configFilePath);

		/* *********** read config file to get Paramters******** */	
		String pageUrlForExample=(String) readConfigFile.readParamInXmlFile("samplePageurl");;
		if(pageUrlForExample==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param samplePageUrl is not set");
		}

		String StartpageUrl=(String) readConfigFile.readParamInXmlFile("forumToparseURL");
		if(StartpageUrl==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param forumToparseURL is not set");
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<String> racines = (ArrayList<String>) readConfigFile.readParamInXmlFile("keyWordsInURL");
		
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
		
		// File where post will be store
		String postFileName =(String) readConfigFile.readParamInXmlFile("pathToPostsFile");
		if(postFileName==null) {
			Logger.getLogger(LaunchWebParsing.class).debug("the param pathToPostsFile is not set");
		}

		boolean isPostIdExists = true;

		/* *****************end paramters *******************************/
		
		if(pageUrlForExample==null||StartpageUrl==null||racines.size()>0||date==null||author==null||message==null||postFileName==null){
			Logger.getLogger(LaunchWebParsing.class).debug("Error : At least one of the params is not set");
			return;
		}
		InitParser.startParser(pageUrlForExample,StartpageUrl, date, author, message, postFileName, racines, isPostIdExists);

	}
}