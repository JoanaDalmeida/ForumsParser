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
 * File Name   : ReadParametersFile.java
 *
 * Created     : 18/05/2014
 * 
 * @author     : Joana D'ALMEIDA
 * 
 */
package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadParametersFile {

	public static boolean setParametes( String fileName){

		String allPostsContainerTag = readParamInXmlFile("allPostsContainerTag", fileName);
		if(allPostsContainerTag!=null){
			PostsParameters.setAllPostsContainerTag(allPostsContainerTag);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		String allPostsContainerClass = readParamInXmlFile("allPostsContainerClass", fileName);
		if(allPostsContainerClass!=null){
			PostsParameters.setAllPostsContainerClass(allPostsContainerClass);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		
		String postContainerTag = readParamInXmlFile("postContainerTag", fileName);
		if(postContainerTag!=null){
			PostsParameters.setPostContainerTag(postContainerTag);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		String postContainerClass = readParamInXmlFile("postContainerClass", fileName);
		if(postContainerClass!=null){
			PostsParameters.setPostContainerClass(postContainerClass);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		
		String dateContainerTag = readParamInXmlFile("dateContainerTag", fileName);
		if(dateContainerTag!=null){
			PostsParameters.setDateContainerTag(dateContainerTag);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		String dateContainerClass = readParamInXmlFile("dateContainerClass", fileName);
		if(dateContainerClass!=null){
			PostsParameters.setDateContainerClass(dateContainerClass);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
				
		String authorContainerTag = readParamInXmlFile("authorContainerTag", fileName);
		if(authorContainerTag!=null){
			PostsParameters.setAuthorContainerTag(authorContainerTag);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		String authorContainerClass = readParamInXmlFile("authorContainerClass", fileName);
		if(authorContainerClass!=null){
			PostsParameters.setAuthorContainerClass(authorContainerClass);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		
		String messageContainerTag = readParamInXmlFile("messageContainerTag", fileName);
		if(messageContainerTag!=null){
			PostsParameters.setMessageContainerTag(messageContainerTag);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		String messageContainerClass = readParamInXmlFile("messageContainerClass", fileName);
		if(messageContainerClass!=null){
			PostsParameters.setMessageContainerClass(messageContainerClass);
		} else {
			Logger.getLogger(ReadParametersFile.class).debug("At least one of the parameters is not set. the programm can't continue");
			return false;
		}
		
		return true;
	}

	public static  String readParamInXmlFile(String paramName, String fileName){
		ArrayList<String> params = new ArrayList<String>();
		try{ 
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc;
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(paramName);
			for (int i = 0; i < nList.getLength(); i++){
				params.add(nList.item(i).getTextContent());
			}
		} catch (ParserConfigurationException e) {
			Logger.getLogger(ReadParametersFile.class).debug("EXCEPTION while reading parameters : "+ e.getMessage()+
					" Description : "+ e.toString());
		} catch (SAXException | IOException e) {
			Logger.getLogger(ReadParametersFile.class).debug("EXCEPTION while reading parameters : "+ e.getMessage()+
					" Description : "+ e.toString());
		}
		if(params.size()>0){
			return params.get(0);
		} else {
			return null;
		}
	}


}
