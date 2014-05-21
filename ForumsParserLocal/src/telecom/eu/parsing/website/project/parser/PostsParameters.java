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
 * File Name   : PostsParameters.java
 *
 * Created     : 24/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 */
package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class PostsParameters {
	private static String postContainerClass;
	private static String messageContainerClass;
	private static String dateContainerClass;
	private static String authorContainerClass;
	private static String allPostsContainerClass;
	private static String postContainerTag;
	private static String messageContainerTag;
	private static String dateContainerTag;
	private static String authorContainerTag;
	private static String allPostsContainerTag;
	
	


	public PostsParameters () {
		
	}
	
	public static String getPostContainerClass() {
		return postContainerClass;
	}


	public static void setPostContainerClass(String postContainerClass) {
		PostsParameters.postContainerClass = postContainerClass;
	}


	public static String getMessageContainerClass() {
		return messageContainerClass;
	}


	public static void setMessageContainerClass(String messageContainerClass) {
		PostsParameters.messageContainerClass = messageContainerClass;
	}


	public static String getDateContainerClass() {
		return dateContainerClass;
	}


	public static void setDateContainerClass(String dateContainerClass) {
		PostsParameters.dateContainerClass = dateContainerClass;
	}


	public static String getAuthorContainerClass() {
		return authorContainerClass;
	}


	public static void setAuthorContainerClass(String authorContainerClass) {
		PostsParameters.authorContainerClass = authorContainerClass;
	}


	public static String getAllPostsContainerClass() {
		return allPostsContainerClass;
	}


	public static void setAllPostsContainerClass(String allPostsContainerClass) {
		PostsParameters.allPostsContainerClass = allPostsContainerClass;
	}	
	
	public static String getPostContainerTag() {
		return postContainerTag;
	}
	
	
	public static void setPostContainerTag(String postContainerTag) {
		PostsParameters.postContainerTag = postContainerTag;
	}
	
	
	public static String getMessageContainerTag() {
		return messageContainerTag;
	}
	
	
	public static void setMessageContainerTag(String messageContainerTag) {
		PostsParameters.messageContainerTag = messageContainerTag;
	}
	
	
	public static String getDateContainerTag() {
		return dateContainerTag;
	}
	
	
	public static void setDateContainerTag(String dateContainerTag) {
		PostsParameters.dateContainerTag = dateContainerTag;
	}
	
	
	public static String getAuthorContainerTag() {
		return authorContainerTag;
	}
	
	
	public static void setAuthorContainerTag(String authorContainerTag) {
		PostsParameters.authorContainerTag = authorContainerTag;
	}
	
	
	public static String getAllPostsContainerTag() {
		return allPostsContainerTag;
	}
	
	
	public static void setAllPostsContainerTag(String allPostsContainerTag) {
		PostsParameters.allPostsContainerTag = allPostsContainerTag;
	}

	public static void saveParamsToFile(String fileName){
		File input =new File(fileName);
		if( !fileName.substring(fileName.indexOf(".")+1).equalsIgnoreCase("xml")){
			String newFilName = fileName.substring(0, fileName.indexOf(".")+1)+"xml";
			input= new File(newFilName);
		}
		if (!input.exists()) {
			try {
				input.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(PostsParameters.class ).debug("Execption while creating Params file : "+ e.getMessage());
				return;
			}
		}
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.append("<?xml version="+ "\""+"1.0"+"\""+ " encoding="+ "\""+"ISO-8859-1"+ "\""+"?>");
			writer.append("\n");
			
			writer.append("<postsParameters>");
			writer.append("\n");
			
			writer.append("<allPostsContainerTag>");
			writer.append(PostsParameters.getAllPostsContainerTag());
			writer.append("</allPostsContainerTag>");
			writer.append("\n");
			
			writer.append("<allPostsContainerClass>");
			writer.append(PostsParameters.getAllPostsContainerClass());
			writer.append("</allPostsContainerClass>");
			writer.append("\n");
			
			writer.append("<postContainerTag>");
			writer.append(PostsParameters.getPostContainerTag());
			writer.append("</postContainerTag>");
			writer.append("\n");
			
			writer.append("<postContainerClass>");
			writer.append(PostsParameters.getPostContainerClass());
			writer.append("</postContainerClass>");
			writer.append("\n");
			
			writer.append("<dateContainerTag>");
			writer.append(PostsParameters.getDateContainerTag());
			writer.append("</dateContainerTag>");
			writer.append("\n");
			
			writer.append("<dateContainerClass>");
			writer.append(PostsParameters.getDateContainerClass());
			writer.append("</dateContainerClass>");
			writer.append("\n");	
			
			writer.append("<authorContainerTag>");
			writer.append(PostsParameters.getAuthorContainerTag());
			writer.append("</authorContainerTag>");
			writer.append("\n");
			
			writer.append("<authorContainerClass>");
			writer.append(PostsParameters.getAuthorContainerClass());
			writer.append("</authorContainerClass>");
			writer.append("\n");
			
			writer.append("<messageContainerTag>");
			writer.append(PostsParameters.getMessageContainerTag());
			writer.append("</messageContainerTag>");
			writer.append("\n");
			
			writer.append("<messageContainerClass>");
			writer.append(PostsParameters.getMessageContainerClass());
			writer.append("</messageContainerClass>");
			writer.append("\n");
			
			writer.append("</postsParameters>");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Logger.getLogger(PostsParameters.class ).debug("Execption while writing in params file : "+ e.getMessage());
		}
	}
}
