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
 * File Name   : WriteToCsvDiscussionsFile.java
 *
 * Created     : 24/05/2014
 * 
 * @author     : Joana D'ALMEIDA
 */

package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class WriteToCsvDiscussionsFile {

	private static String fileName;
	private static String delimiter;
	public WriteToCsvDiscussionsFile(String fileName,String delimiterOfPostsFile){
		File input =new File(fileName);
		if (!input.exists()) {
			try {
				input.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(this.getClass() ).debug("Execption while creating CSV file : "+ e.getMessage());
			}
		}
		WriteToCsvDiscussionsFile.fileName=fileName;
		WriteToCsvDiscussionsFile.delimiter=delimiterOfPostsFile;
		writeDiscussionsToCSVFile("URL", "Title");
	}

	public void writeDiscussionsToCSVFile(String URL, String title) {
		if(!searchTextInDiscussionsFile(URL, title)) {
			try {
				FileWriter writer = new FileWriter(fileName,true);
				writer.append( "\"" +URL +  "\"" );
				writer.append(WriteToCsvDiscussionsFile.delimiter);
				writer.append("\""+title+"\"");
				writer.append("\n");
				writer.flush();
				writer.close();
				Logger.getLogger(this.getClass() ).debug("\n Write to csv  : "+ URL +","+ title +"\n");
			} catch(IOException e) {
				Logger.getLogger(this.getClass() ).debug("Execption while writing in CSV file : "+ e.getMessage());
			}
		} 
	}


	public  void findAndReplaceDiscussionsUrlAndTitle (String url, String title) {
		String content="";
		try {
			@SuppressWarnings("resource")
			final Scanner scanner = new Scanner(new File(WriteToCsvDiscussionsFile.fileName));
			while (scanner.hasNextLine()) {
				String lineFromFile = scanner.nextLine();
				if(lineFromFile.contains(url)) { 
					String newText="\"" +url +  "\""+ WriteToCsvDiscussionsFile.delimiter + "\""+title+"\"";
					lineFromFile=newText;
				}
				content +=lineFromFile+"\n";
			}
			FileWriter writer = new FileWriter(fileName);
			writer.append(content);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			Logger.getLogger(OutputParams.class).debug("Execption while searching  Url to Succed URLs  file : "
					+ e.getMessage());
		}
	}


	public  boolean searchTextInDiscussionsFile(String url, String title){
		try {
			@SuppressWarnings("resource")
			final Scanner scanner = new Scanner(new File(WriteToCsvDiscussionsFile.fileName));
			while (scanner.hasNextLine()) {
				final String lineFromFile = scanner.nextLine();
				if(lineFromFile.contains("\"" +url +  "\""+WriteToCsvDiscussionsFile.delimiter+"\""+ title+"\"")) { 
					return true;  
				}
			}
		} catch (IOException e) {
			Logger.getLogger(OutputParams.class).debug("Execption while searching  Url to Succed URLs  file : "
					+ e.getMessage());
		}
		return false;
	}


	public  String searchPostDiscussion(String url){
		String discussionUrl = "";
		if(url.contains(PaginationParameters.getPostsUrlPattern())){
			String urlWithoutPattern = url.substring(0, url.indexOf(PaginationParameters.getPostsUrlPattern()));
			try {
				@SuppressWarnings("resource")
				final Scanner scanner = new Scanner(new File(WriteToCsvDiscussionsFile.fileName));
				while (scanner.hasNextLine()) {
					final String lineFromFile = scanner.nextLine();
					if(lineFromFile.contains(urlWithoutPattern)) { 
						discussionUrl = lineFromFile.substring(1, (lineFromFile.indexOf(
								WriteToCsvDiscussionsFile.delimiter)-1));
						return discussionUrl;  
					}
				}
			} catch (IOException e) {
				Logger.getLogger(OutputParams.class).debug("Execption while searching  Url to Succed URLs  file : "
						+ e.getMessage());
			}
		} else {
			return url;
		}
		return discussionUrl;
	}



}
