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
 * File Name   : OutputParams.java
 *
 * Created     : 18/05/2014
 * 
 * @author     : Joana D'ALMEIDA
 * 
 * this class crawls website and get all URLs 
 */
package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class OutputParams {
	
	private static String failedUrlsFileName;
	private static String succedUrlsFileName;
	private static  String postsFileName;
	private static String postsParamsFileName;
	private static WriteToCsvDiscussionsFile writeToCsvDiscussionsFile;
	
	
	public static void  setOutputParams(String failedUrlsFileName, String succedUrlsFileName,
			String postsFileName, String postsParamsFileName, WriteToCsvDiscussionsFile writeToCsvDiscussionsFile) {
	
		OutputParams.failedUrlsFileName = failedUrlsFileName;
		OutputParams.succedUrlsFileName = succedUrlsFileName;
		OutputParams.writeToCsvDiscussionsFile = writeToCsvDiscussionsFile;
		
		File failedUrlsFile =new File(failedUrlsFileName);
		if (!failedUrlsFile.exists()) {
			try {
				failedUrlsFile.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(OutputParams.class).debug("Execption while creating failed Urls  file : "+ e.getMessage());
			}
		}
		
		File succedUrlsFile =new File(succedUrlsFileName);
		if (!succedUrlsFile.exists()) {
			try {
				succedUrlsFile.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(OutputParams.class).debug("Execption while creating failed Urls  file : "+ e.getMessage());
			}
		}
		
	}
		

	public static void appendToFailedUrlsFile(String url){	
		try {
			FileWriter writer = new FileWriter(OutputParams.failedUrlsFileName,true);
			writer.append(url);
			writer.append("\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Logger.getLogger(OutputParams.class).debug("Execption while appending  Url to Failed URLs  file : "+ e.getMessage());
		}
	}
	
	
	public static void appendToSuccedUrlsFile(String url){
		try {
			FileWriter writer = new FileWriter(OutputParams.succedUrlsFileName,true);
			writer.append(url);
			writer.append("\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Logger.getLogger(OutputParams.class).debug("Execption while appending  Url to Succed URLs  file : "+ e.getMessage());
		}
	}
	
	
	public static boolean searchUrlInSuccedUrlsFile(String url){
		try {
			@SuppressWarnings("resource")
			final Scanner scanner = new Scanner(new File(OutputParams.succedUrlsFileName));
			while (scanner.hasNextLine()) {
			   final String lineFromFile = scanner.nextLine();
			   if(lineFromFile.contains(url)) { 
			       return true;  
			   }
			}
		} catch (IOException e) {
			Logger.getLogger(OutputParams.class).debug("Execption while searching  Url to Succed URLs  file : "+ e.getMessage());
		}
		return false;
	}
	
	
	public static void writeToCsvDiscussionsFile(String URL, String title){
		writeToCsvDiscussionsFile.writeDiscussionsToCSVFile(URL, title);
		
	}
	
	
	
	public static String getFailedUrlsFileName() {
		return failedUrlsFileName;
	}
	public static void setFailedUrlsFileName(String failedUrlsFileName) {
		OutputParams.failedUrlsFileName = failedUrlsFileName;
	}
	public static String getSuccedUrlsFileName() {
		return succedUrlsFileName;
	}
	public static void setSuccedUrlsFileName(String succedUrlsFileName) {
		OutputParams.succedUrlsFileName = succedUrlsFileName;
	}
	public String getPostsFileName() {
		return postsFileName;
	}
	public static void setPostsFileName(String postsFileName) {
		OutputParams.postsFileName = postsFileName;
	}
	public static String getPostsParamsFileName() {
		return postsParamsFileName;
	}
	public static void setPostsParamsFileName(String postsParamsFileName) {
		OutputParams.postsParamsFileName = postsParamsFileName;
	}
	public static WriteToCsvDiscussionsFile getWriteToCsvDiscussionsFile() {
		return writeToCsvDiscussionsFile;
	}
	public static void setWriteToCsvDiscussionsFile(
			WriteToCsvDiscussionsFile writeToCsvDiscussionsFile) {
		OutputParams.writeToCsvDiscussionsFile = writeToCsvDiscussionsFile;
	}
	

}
