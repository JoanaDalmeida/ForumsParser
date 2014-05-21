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
 * File Name   : ConfirmationDialog.java
 *
 * Created     : 15/05/2014
 * 
 * @author     : Joana D'ALMEIDA
 * 
 * this class crawls website and get all URLs 
 */
package telecom.eu.parsing.website.project.parser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfirmationDialog {
	private static boolean confirm= false;
	private static String [] paramsAfterConfirmDialog;
	private static JPanel labelsAndFields;
	private static JTextField[] fields;
	
	public static boolean confirmParams(String [] params){
		confirmationDialog(params);
		if (confirm){
			setPostsParametersFinalValues(paramsAfterConfirmDialog);
		}
		return confirm;
	}
	
	public static void setPostsParametersFinalValues(
			String[] paramsFinalsValues) {
		if(paramsFinalsValues==null || paramsFinalsValues.length!=10 ){
			return;
		}
		PostsParameters.setAllPostsContainerTag(paramsAfterConfirmDialog[0]);
		PostsParameters.setAllPostsContainerClass(paramsAfterConfirmDialog[1]);
		
		PostsParameters.setPostContainerTag(paramsAfterConfirmDialog[2]);
		PostsParameters.setPostContainerClass(paramsAfterConfirmDialog[3]);
		
		PostsParameters.setDateContainerTag(paramsAfterConfirmDialog[4]);
		PostsParameters.setDateContainerClass(paramsAfterConfirmDialog[5]);
		
		PostsParameters.setAuthorContainerTag(paramsAfterConfirmDialog[6]);
		PostsParameters.setAuthorContainerClass(paramsAfterConfirmDialog[7]);
		
		PostsParameters.setMessageContainerTag(paramsAfterConfirmDialog[8]);
		PostsParameters.setMessageContainerClass(paramsAfterConfirmDialog[9]);
	
	}
	
	
	
	public static boolean confirmationDialog(String [] parameters){
		
		final JDialog confirmDialog = new JDialog();

		labelsAndFields = new JPanel();
		labelsAndFields.setLayout(new BorderLayout());

		String [] labels = { "allPostsContainerTag", "allPostsContainerClass", 
				"postContainerTag", "postContainerClass", "dateContainerTag", "dateContainerClass",
				"authorContainerTag", "authorContainerClass", "messageContainerTag", "messageContainerClass"};

		JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
		JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
		JPanel questionPanel = new JPanel();
		questionPanel.add(new JLabel("Do you want to continue?"));
				
		labelsAndFields.add(labelPanel, BorderLayout.WEST);
		labelsAndFields.add(fieldPanel, BorderLayout.CENTER);
		labelsAndFields.add(questionPanel, BorderLayout.SOUTH);

		fields = new JTextField[labels.length];

		for (int i = 0; i < labels.length; i += 1) {
			fields[i] = new JTextField();
			fields[i].setColumns(20);
			fields[i].setText(parameters[i]);
			JLabel lab = new JLabel(labels[i], JLabel.RIGHT);
			lab.setLabelFor(fields[i]);
			labelPanel.add(lab);
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(fields[i]);
			fieldPanel.add(panel);
		}

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm=true;
				
				System.out.println("suis icici  "+ confirm);
				
				paramsAfterConfirmDialog =  new String[fields.length];
				for (int i=0; i < fields.length; i++){
					paramsAfterConfirmDialog[i]=fields[i].getText();
				}
				confirmDialog.dispose();
			}
		});

		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm =false; 
				confirmDialog.dispose();
			}
		});

		confirmDialog.getRootPane().setDefaultButton(okButton);
		confirmDialog.setTitle("Confirm Posts's Parameters");
		okButton.requestFocusInWindow();

		confirmDialog.getContentPane().add(labelsAndFields, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(noButton);
		confirmDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		confirmDialog.pack();
		confirmDialog.setModal(true);
		confirmDialog.setVisible(true);
		return confirm;
	}
	
}
