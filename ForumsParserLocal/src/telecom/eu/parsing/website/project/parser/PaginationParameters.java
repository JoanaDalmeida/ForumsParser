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
 * File Name   : PaginationParameters.java
 *
 * Created     : 18/05/2014
 * 
 * @author     : Joana D'ALMEIDA
 * 
 */

package telecom.eu.parsing.website.project.parser;

import java.util.ArrayList;

public class PaginationParameters {
	private static String discussionsUrlRegex;
	
	private static String discussionsUrlStart;
	private static String discussionsUrlEnd;
	private static String postsUrlStart;
	private static String postsUrlPattern;

	public static void setParameters (ArrayList<String> keyWordsPagination){
		setDiscussionsUrlRegex(getUrlRegex(keyWordsPagination.get(0), keyWordsPagination.get(1)));
		setPostsUrlStart(keyWordsPagination.get(2));
		setPostsUrlPattern(keyWordsPagination.get(3));
	}

	public static String getUrlRegex(String url1, String url2){
		StringBuilder firstPart = new StringBuilder();
		StringBuilder secondPart = new StringBuilder();
		int i=0;
		if (url1.length()<url2.length()){
			for (; i<url1.length(); i++){
				if(url1.charAt(i)==url2.charAt(i)){
					firstPart.append(url1.charAt(i));
				} else {
					break;
				}
			}
			if(i<url1.length() && url2.contains(url1.substring(i))){
				secondPart.append(url1.substring(i));
			}

		} else {
			for (; i<url2.length(); i++){
				if(url1.charAt(i)==url2.charAt(i)){
					firstPart.append(url2.charAt(i));
				} else {
					break;
				}
			}
			if(i<url2.length() && url1.contains(url2.substring(i))){
				secondPart.append(url1.substring(i));
			}

		}
		String regex = "^"+firstPart.toString()+".*"+secondPart.toString()+"$";
		setDiscussionsUrlStart(firstPart.toString());
		setDiscussionsUrlEnd(secondPart.toString());
		return regex;
		
	}

	public static String getDiscussionsUrlRegex() {
		return discussionsUrlRegex;
	}

	public static void setDiscussionsUrlRegex(String discussionsUrlRegex) {
		PaginationParameters.discussionsUrlRegex = discussionsUrlRegex;
	}

	
	public static String getDiscussionsUrlStart() {
		return discussionsUrlStart;
	}

	public static void setDiscussionsUrlStart(String discussionsUrlStart) {
		PaginationParameters.discussionsUrlStart = discussionsUrlStart;
	}

	public static String getDiscussionsUrlEnd() {
		return discussionsUrlEnd;
	}

	public static void setDiscussionsUrlEnd(String discussionsUrlEndh) {
		PaginationParameters.discussionsUrlEnd = discussionsUrlEndh;
	}

	public static String getPostsUrlStart() {
		return postsUrlStart;
	}

	public static void setPostsUrlStart(String postsUrlStartWith) {
		PaginationParameters.postsUrlStart = postsUrlStartWith;
	}

	public static String getPostsUrlPattern() {
		return postsUrlPattern;
	}

	public static void setPostsUrlPattern(String postsUrlEnd) {
		PaginationParameters.postsUrlPattern = postsUrlEnd;
	}


}
