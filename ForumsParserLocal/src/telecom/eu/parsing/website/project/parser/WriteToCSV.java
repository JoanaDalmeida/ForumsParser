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
 * File Name   : WriteToCSV.java
 *
 * Created     : 24/03/2014
 * 
 * @author     : Joana D'ALMEIDA
 */
package telecom.eu.parsing.website.project.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class WriteToCSV {
	private static String fileName;
	public WriteToCSV(String fileName){
		File input =new File(fileName);
		if (!input.exists()) {
			try {
				input.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(this.getClass() ).debug("Execption while creating CSV file : "+ e.getMessage());
			}
		}
		WriteToCSV.fileName=fileName;
		WritePostToCSVFile("id", "author", "date","message");
	}

	public void WritePostToCSVFile(String id, String author, String date, String message) {
		try {
			FileWriter writer = new FileWriter(fileName,true);
			writer.append( "\"" +id +  "\"" );
			writer.append(',');
			writer.append("\""+ author+"\"");
			writer.append(",");
			writer.append("\""+ date+ "\"");
			writer.append(",");
			writer.append("\""+ message+"\"");
			writer.append('\n');
			writer.flush();
			writer.close();
			Logger.getLogger(this.getClass() ).debug("\n Write to csv  : "+ id +","+ author+","+date+","+message+"\n");
		} catch(IOException e) {
			Logger.getLogger(this.getClass() ).debug("Execption while writing in CSV file : "+ e.getMessage());
		} 

	}
	
	public void WritePostToCSVFile(ArrayList<String> ids, ArrayList<String> authors, ArrayList<String> dates, ArrayList<String> messages) {
		if(ids.size()==authors.size() && ids.size()==dates.size() && ids.size()==messages.size()){
			for(int i = 0; i<ids.size(); i++){
				WritePostToCSVFile(ids.get(i), authors.get(i), dates.get(i), messages.get(i));
			}
		}

	}

}
