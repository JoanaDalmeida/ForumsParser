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
 * File Name   : ReadXMLFile.java
 *
 * Created     : 18/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 */
package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadConfigFile {

	private String fileName;

	public ReadConfigFile(String fileName) {
		super();
		this.fileName = fileName;
	}


	public  Object readParamInXmlFile(String paramName){
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
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if (!paramName.equalsIgnoreCase("keyWordsInURL")){
			if(params.size()>0){
				return params.get(0);
			} else {
				return null;
			}
		}
		return params;

	}

}
