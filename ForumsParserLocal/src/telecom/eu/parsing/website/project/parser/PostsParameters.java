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

}
