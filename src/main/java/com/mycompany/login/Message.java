/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;

import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author garet
 */
public class Message {
    private ArrayList<String> chatHistory = new ArrayList<>();
    private ArrayList<String> draftMessages = new ArrayList<>();
    
    
public boolean checkMessageID(String id) {
        return id != null && id.matches("\\d{10}") ;
    }
    public static int checkRecipientCell(String number){
        if (number == null) 
            return 0;
        if (number.startsWith("+27") && number.length() == 12 && number.substring(3).matches("\\d{9}")){
            return 1;
        }return 0;
    }
    public void run(Login login) {
        
        JOptionPane.showMessageDialog(null, "Login Successful. Welcome, " + login.username + "!");
        boolean running = true;
        while (running){
            String[] options = {
                "1 - Send Message",
                "2 - Show recently sent messages",
                "3 - Quit"
            };
            String choiceString = (String) JOptionPane.showInputDialog(null,
                    "Choose an option:",
                    "Main menu",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
                    );
            if (choiceString == null){
                System.exit(0);
                continue;
            }
            
            int choice = Integer.parseInt(choiceString.substring(0, 1));
            switch (choice){
                case 1:
                    sentMessage(login.username);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Coming soon.");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Session ended.");
                    running = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option");
            }
                    
             
        }
    }
public void startChat(){
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat");
        
    }

public void sentMessage(String username){
    
    String recipient = JOptionPane.showInputDialog(null, "Enter recipients number: ");
    if (recipient == null || recipient.isEmpty()){
        System.exit(0);
        JOptionPane.showMessageDialog(null,"Recipient cannot be empty.");
        return;
        
    }
    int messageCount = 0;
    try{
        String countInput = JOptionPane.showInputDialog("How many messages would you like to send?");
        if (countInput == null)
            System.exit(0);
           
        messageCount = Integer.parseInt(countInput);
        
        if (messageCount <= 0) {
            JOptionPane.showMessageDialog(null, "Enter number.");
            return;
        }
    }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid number");
                return;
                }
    
    for (int i = 1; i <= messageCount; i++){
    String messageContent = JOptionPane.showInputDialog(null, "Enter the message: ");
    if (messageContent == null || messageContent.isEmpty()){
        System.exit(0);
        JOptionPane.showMessageDialog(null, "Message cannot be empty");
        return;
    }
    String messageId = checkMessageID();
    String messageHash = createMessageHash(messageId, i, messageContent);
    String fullMessage = "[Message ID: " + messageId + "}\nHash: " + messageHash + 
            "\nFrom: " + username + " to " + recipient + ": " + messageContent;
    String[] options = {"Send", "Disregard", "Store for later" };
    int decision = JOptionPane.showOptionDialog(
            null,
            "What would you like to do?" + fullMessage,
            "Confirm Message",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
            );
            switch (decision){
    case 0:
        chatHistory.add(fullMessage);
        JOptionPane.showMessageDialog(null, "Message sent.");
        break;
    case 1:
        JOptionPane.showMessageDialog(null, "Message discarded");
        break;
    case 2:
        draftMessages.add(fullMessage);
        storeMessage(messageId, messageHash, username, recipient, messageContent);
        JOptionPane.showMessageDialog(null, "Message stored.");
        break;
    default:
        JOptionPane.showMessageDialog(null, "No action taken");
            }
    }
}
private String checkMessageID(){
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++){
            id.append(rand.nextInt(10));
        }
        return id.toString();
}
private String createMessageHash(String messageId, int messageNumber, String messageContent){
    String firstTwoDigits = messageId.length() >= 2 ? messageId.substring(0, 2) : messageId;
    
    String[] words = messageContent.trim().split("\\s+");
    String firstWord = words.length > 0 ? words[0] : "";
    String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
    
    return firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
}
private void printMessage(){
    if (chatHistory.isEmpty()){
        JOptionPane.showMessageDialog(null, "No messages sent");
        return;
    }
    StringBuilder messageList = new StringBuilder("Sent messages:\n\n");
    
    for (String message : chatHistory){
        messageList.append(message).append("\n\n");
    }
    
    JTextArea textArea = new JTextArea(messageList.toString());
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(500, 400));
    
    JOptionPane.showMessageDialog(null, scrollPane, "Chat history", JOptionPane.INFORMATION_MESSAGE);
}
private void storeMessage(String messageId, String messageHash, String username, String recipient, String messageContent){
    JSONObject messageObj = new JSONObject();
    messageObj.put("id", messageId);
    messageObj.put("hash", messageHash);
    messageObj.put("from", username);
    messageObj.put("to", recipient);
    messageObj.put("content", messageContent);
    
    JSONArray messageArray = new JSONArray();
    
    messageArray.put(messageObj);
    
    try(FileWriter file = new FileWriter("stored_messages.json", true)){
        file.write(messageObj.toString() + "\n");
        JOptionPane.showMessageDialog(null, "Message stored in JSOn file.");
    }catch (IOException e){
        JOptionPane.showMessageDialog(null, "Failed to store message: " + e.getMessage());
    }
}
}
