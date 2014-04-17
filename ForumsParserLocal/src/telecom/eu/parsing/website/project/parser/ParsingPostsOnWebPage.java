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
 * File Name   : ParsingPostsOnWebPage.java
 *
 * Created     : 18/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 */

package telecom.eu.parsing.website.project.parser;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class ParsingPostsOnWebPage {
	ArrayList<String> ids = new ArrayList<String>();
	ArrayList<String> authors = new ArrayList<String>();
	ArrayList<String> dates = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();


	/**
	 * this method from the message, date and author gets the direct container
	 * @param n it is node on the webpage for example the body
	 * @param date : date the post was published
	 * @param message : the post message 
	 * @param author : the author
	 * @return : the node that contains the post
	 */
	public Node getPostContainer(Node n, String date, String message, String author){
		List <Node> nodes = n.childNodes();
		for (int i=0; i< nodes.size(); i++){
			if (nodes.get(i).childNodes().size()>0) {
				// remove the html tag, to get only text and compare it with tje message, date, author
				String content = nodes.get(i).toString().replaceAll("\\<.*?>","");
				// parse the content to get text in UTF-8 without html accent 
				String contentParsed = Jsoup.parse(content).text();
				if( contentParsed.toLowerCase().contains(author.toLowerCase()) &&
						contentParsed.toLowerCase().contains(message.toLowerCase()) &&
						contentParsed.toLowerCase().contains(date.toLowerCase())) {
					return getPostContainer( nodes.get(i), date, message, author);
				} 
			}
		}
		return n;
	}


	/**
	 * this method  gets the direct container of the text in param
	 * @param text the text to search
	 * @return : the node that contains the text
	 */
	public Node getElementContainer(Node n, String text){
		List <Node> nodes = n.childNodes();
		for (int i=0; i< nodes.size(); i++){
			if (nodes.get(i).childNodes().size()>0) {
				// remove the html tag, to get only text and compare it with  message, date, author
				String content = nodes.get(i).toString().replaceAll("\\<.*?>","");
				// parse the content to get text in UTF-8 without html accent 
				String contentParsed = Jsoup.parse(content).text();
				if( contentParsed.toLowerCase().contains(text.toLowerCase())) {
					return getElementContainer( nodes.get(i),  text);
				} 
			}
		}
		return n;
	}



	/**
	 * this method  gets the  container of all posts
	 * @param text the text to search
	 * @return : the node that contains the text
	 */
	public Node getAllPostsContainer(Node n, String tagName, String className){
		if(n!=null){
			Node parent  =  n.parent();
			if (((Element) parent).select(tagName+"."+className).size()>1){
				return parent;
			} else {
				return getAllPostsContainer(parent,tagName, className);
			}
		} 
		return null;

	}


	/**
	 * this method  gets the  container of all posts
	 * @param text the text to search
	 * @return : the node that contains the text
	 */
	public Node getPostContainerWithAttributeIdAndClass(Node n){
		if(n!=null){
			Node parent  =  n.parent();
			if (n.hasAttr("id")){
				return n;
			} else {
				return getPostContainerWithAttributeIdAndClass(parent);
			}
		} 
		return null;

	}


	/**
	 * this method  gets the parent node of the node in param 
	 * with attribute Class not null
	 * @param n
	 * @return
	 */
	public Node searchDirectParentWithAttribute(Node n){
		if (n!=null) {
			Attributes attributes =n.attributes();
			List <Attribute> list_attributes= attributes.asList();
			if (list_attributes.size()>0){
				for (int i=0; i<list_attributes.size(); i++){
					String attributeHtml =list_attributes.get(i).html();
					if(attributeHtml.toLowerCase().contains("class=")) {
						return n;
					} 
				}
				return searchDirectParentWithAttribute( n.parent());
			} else {
				return searchDirectParentWithAttribute( n.parent());
			}
		} else {
			return n;
		}
	}

	
	/**
	 * this method sets the param of the post container 
	 * @param body the body of  the page
	 * @param date the example of date to search
	 * @param message the example of message to search
	 * @param author the example of author to search
	 */
	public void setPostsParameters(Element body, String date, String message, String author, boolean isPostIdExists) {
		Logger.getLogger(this.getClass() ).debug("method setPostsParameters is call ");
		String postContainerClass=null;
		String messageContainerClass=null;
		String dateContainerClass=null;
		String authorContainerClass=null;
		String allPostsContainerClass=null;
		String postContainerTag=null;
		String messageContainerTag=null;
		String dateContainerTag=null;
		String authorContainerTag=null;
		String allPostsContainerTag=null;

		Node postNode;
	    if (isPostIdExists) {
	    	postNode=getPostContainerWithAttributeIdAndClass(getPostContainer(body, date, message, author));
	    }else{
	    	postNode=getPostContainerWithAttributeIdAndClass(getPostContainer(body, date, message, author));
	    }
		if( postNode != null ) {
			postContainerTag= postNode.nodeName();
			Attributes attributes =postNode.attributes();
			List <Attribute> list_attributes= attributes.asList();
			for (int j=0; j<list_attributes.size(); j++) {
				String attributeHtml =list_attributes.get(j).html();
				if(attributeHtml.toLowerCase().contains("class=")) {
					postContainerClass=removeSpaceInClassName(list_attributes.get(j).getValue());
				}

			}
			Node parent = getAllPostsContainer(postNode,postContainerTag,postContainerClass);
			if (parent!=null){
				allPostsContainerTag = parent.nodeName();
				Attributes attributesParent =postNode.parent().attributes();
				List <Attribute> list_attributesParent= attributesParent.asList();
				for (int j=0; j<list_attributesParent.size(); j++) {
					String attributeHtml =list_attributesParent.get(j).html();
					if(attributeHtml.toLowerCase().contains("class=")) {
						allPostsContainerClass=removeSpaceInClassName(list_attributesParent.get(j).getValue());
						break;
					}

				}
			}

		}

		Node messageNode =searchDirectParentWithAttribute(getElementContainer(postNode, message));
		if( messageNode != null ) {
			messageContainerTag= messageNode.nodeName();
			Attributes attributes =messageNode.attributes();
			List <Attribute> list_attributes= attributes.asList();
			for (int j=0; j<list_attributes.size(); j++) {
				String attributeHtml =list_attributes.get(j).html();
				if(attributeHtml.toLowerCase().contains("class=")) {
					messageContainerClass=removeSpaceInClassName(list_attributes.get(j).getValue());
					break;
				}

			}		
		} 

		Node authorNode = searchDirectParentWithAttribute(getElementContainer(postNode, author));
		if( authorNode != null ) {
			authorContainerTag = authorNode.nodeName();
			Attributes attributes =authorNode.attributes();
			List <Attribute> list_attributes= attributes.asList();
			for (int j=0; j<list_attributes.size(); j++) {
				String attributeHtml =list_attributes.get(j).html();
				if(attributeHtml.toLowerCase().contains("class=")) {
					authorContainerClass=removeSpaceInClassName(list_attributes.get(j).getValue());
					break;
				}

			}		
		} 

		Node dateNode =searchDirectParentWithAttribute(getElementContainer(postNode, date));
		if( dateNode != null ) {
			dateContainerTag = dateNode.nodeName();
			Attributes attributes =dateNode.attributes();
			List <Attribute> list_attributes= attributes.asList();
			for (int j=0; j<list_attributes.size(); j++) {
				String attributeHtml =list_attributes.get(j).html();
				if(attributeHtml.toLowerCase().contains("class=")) {
					dateContainerClass=removeSpaceInClassName(list_attributes.get(j).getValue());
					break;
				}

			}		
		} 

		PostsParameters.setPostContainerClass(postContainerClass);
		PostsParameters.setAuthorContainerClass(authorContainerClass);
		PostsParameters.setDateContainerClass(dateContainerClass);
		PostsParameters.setMessageContainerClass(messageContainerClass);
		PostsParameters.setAllPostsContainerClass(allPostsContainerClass);
		PostsParameters.setPostContainerTag(postContainerTag);
		PostsParameters.setAuthorContainerTag(authorContainerTag);
		PostsParameters.setDateContainerTag(dateContainerTag);
		PostsParameters.setMessageContainerTag(messageContainerTag);
		PostsParameters.setAllPostsContainerTag(allPostsContainerTag);

		Logger.getLogger(this.getClass() ).debug("Post container Tag.Class : "+ postContainerTag+"."+postContainerClass);
		Logger.getLogger(this.getClass() ).debug("Author container Tag : "+ authorContainerTag+"."+authorContainerClass);
		Logger.getLogger(this.getClass() ).debug("Date  container Tag : "+ dateContainerTag+"."+dateContainerClass);
		Logger.getLogger(this.getClass() ).debug("Message container Tag : "+messageContainerTag+"."+messageContainerClass);
		Logger.getLogger(this.getClass() ).debug("All Posts container Tag : "+allPostsContainerTag+"."+allPostsContainerClass);
	}
	
	
	/**
	 * this method search all posts on given page and write them to a csv file
	 * @param body
	 * @param writeToCSV
	 */

	public void findAllPostsAndWriteToCSV(Element body, WriteToCSV writeToCSV){
		Logger.getLogger(this.getClass() ).debug("Start parsing data on page");
		ids = new ArrayList<String>();
		authors = new ArrayList<String>();
		dates = new ArrayList<String>();
		messages = new ArrayList<String>();
		if (body!=null) {
			Logger.getLogger(this.getClass() ).debug("Node name "+ body.nodeName());
			Elements elementsAllPostContainer = body.select(PostsParameters.getAllPostsContainerTag()+"."
					+PostsParameters.getAllPostsContainerClass());
			for (int i=0; i< elementsAllPostContainer.size(); i++){
				Element element = elementsAllPostContainer.get(i);
				Elements elementsPosts = element.select(PostsParameters.getPostContainerTag()+"."+PostsParameters.getPostContainerClass());
				for (int j=0; j<elementsPosts.size(); j++ ){
					Element elementPost = elementsPosts.get(j);
					String textMessage = null;
					String textAuthor = null;
					String textDate = null;
					String post_id = elementPost.id();

					Element elementMessage = null;
					Element elementAuthor = null;
					Element elementDate = null;

					if (elementPost.select(PostsParameters.getMessageContainerTag()+"."+PostsParameters.getMessageContainerClass()).size()>0) {
						elementMessage = elementPost.select(PostsParameters.getMessageContainerTag()+"."
								+PostsParameters.getMessageContainerClass()).first();
					}

					if(elementPost.select(PostsParameters.getAuthorContainerTag()+"."+PostsParameters.getAuthorContainerClass()).size()>0) {
						elementAuthor = elementPost.select(PostsParameters.getAuthorContainerTag()+"."
								+PostsParameters.getAuthorContainerClass()).first();
					}

					if (elementPost.select(PostsParameters.getDateContainerTag()+"."+PostsParameters.getDateContainerClass()).size()>0) {
						elementDate = elementPost.select(PostsParameters.getDateContainerTag()+"."
								+PostsParameters.getDateContainerClass()).first();
					}

					if (elementMessage!=null){
						textMessage = Jsoup.parse(elementMessage.toString().replaceAll("\\<.*?>","")).text();
					}

					if (elementAuthor!=null){
						textAuthor = Jsoup.parse(elementAuthor.toString().replaceAll("\\<.*?>","")).text();
					}
					if (elementDate!=null){
						textDate = Jsoup.parse(elementDate.toString().replaceAll("\\<.*?>","")).text();
					}
					ids.add(post_id);
					authors.add(textAuthor);
					dates.add(textDate);
					messages.add(textMessage);
				}
				//writeToCSV.WritePostToCSVFile(post_id, textAuthor, textDate, textMessage);
			}
		} else {
			Logger.getLogger(this.getClass() ).debug("Error cannot parse the webpage");
		}
		Logger.getLogger(this.getClass() ).debug("Finish parsing data on page");
	}


	
	public void processData(String link, WriteToCSV writeToCSV){
			Document doc;
			try {
				doc = Jsoup.connect(link).userAgent("Mozilla").get();
				Element body = doc.body();
				if(body!=null) {
					findAllPostsAndWriteToCSV(body, writeToCSV);
				}
			} catch (SocketTimeoutException a){
				processData(link, writeToCSV);
				return;
			} catch (IOException e) {
				Logger.getLogger(InitParser.class).debug("Exception :"+e.getMessage() + " Description :"+ e.toString());
				return;
			} 		
			writeToCSV.WritePostToCSVFile(ids, authors, dates, messages);
	}
	
	
	/**
	 * it removes spaces between class names and replaces them by a point
	 * @param className
	 * @return
	 */
	public String removeSpaceInClassName (String className){
		StringBuilder classNameWithoutSpace = new StringBuilder();
		boolean lastCharIsSpace = false;
		for (int i=0; i< className.length(); i++){
			if (className.charAt(i)==' '){
				lastCharIsSpace = true;
			} else {
				if (lastCharIsSpace) {
					classNameWithoutSpace.append('.');
					lastCharIsSpace=false;
				}
				classNameWithoutSpace.append(className.charAt(i));
			}
		}
		return classNameWithoutSpace.toString();
	}
}
