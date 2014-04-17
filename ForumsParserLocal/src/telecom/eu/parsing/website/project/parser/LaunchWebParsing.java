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



public class LaunchWebParsing {

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		/* ****************************************Paramters********************************************* */	
		String pageUrlForExample="http://forums.bethsoft.com/topic/1397803-brink-sucks/";
		String StartpageUrl="http://forums.bethsoft.com/";
				
		ArrayList<String> racines = new ArrayList<String>();
		racines.add("http://forums.bethsoft.com/");
		
		//post sample, the parser will use it to find the tags of the post
		String date = "22 July 2012 - 04:17 PM";
		String author = "BioHazzard831";
		String message = "Exactly, everyone does have different opinions, but I don't think it's right for the cashier to just say that the game is awful and that it's just a waste of money, they should just let you to but the game without trying to bring you down on your purchase. ";

		// File where post will be store
		String postFileName ="C:/Users/AissataJo/Desktop/ProjetMineur/Resultats/test1.csv";
		
		boolean isPostIdExists = true;

		/* *****************end paramters *******************************/
		
		InitParser.startParser(pageUrlForExample,StartpageUrl, date, author, message, postFileName, racines, isPostIdExists);

	}
}