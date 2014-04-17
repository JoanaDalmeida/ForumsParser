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
 * File Name   : InitAllParam.java
 *
 * Created     : 18/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 */

package telecom.eu.parsing.website.project.parser;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class InitAllParam {


	public static void initPostParam(Document doc,String date, String author, String message, boolean isPostIdExists){
		ParsingPostsOnWebPage webParser = new ParsingPostsOnWebPage();
		Element body = doc.body();
		if(body!=null) {
			webParser.setPostsParameters(body, date, message, author, isPostIdExists);
		} else {
			Logger.getLogger(InitAllParam.class).debug("*****************Connexion Error ******************\n");
			return;
		}
	}


}
