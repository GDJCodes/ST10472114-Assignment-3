/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.login;
import javax.swing.JOptionPane;

/**
 *
 * @author garet
 */

public class Login {
static String firstName;
    static String lastName;
    static String username;
    static String password;
    static String cellPhone;
    
     public static boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity() {
        return password.length() > 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()_+=<>?].*");
    }

    public static boolean checkCellPhoneNumber() {
        return cellPhone.matches("\\+27\\d{9}");
    }

    public static String registerUser() {
        firstName = JOptionPane.showInputDialog("Please enter your first name: ");
        lastName = JOptionPane.showInputDialog("Please enter your last name: ");
        username = JOptionPane.showInputDialog("Please enter your username: \n- Contains an underscore. \n- No more than 5 characters long.");
        if(checkUserName()){
            JOptionPane.showMessageDialog(null, "Welcome " + firstName + ", " + lastName + " it is great to see you.");
        }else{
            JOptionPane.showMessageDialog(null, "Username is incorrectly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
        }
        password = JOptionPane.showInputDialog("Please enter a password: \n- At least eight characters long. \n- Contain a capital letter. \n- Contain a number. \n- Contain a special character.");
        if(checkPasswordComplexity()){
            JOptionPane.showMessageDialog(null, "Password successfully captured.");
        }else{
        JOptionPane.showMessageDialog(null, "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
        cellPhone = JOptionPane.showInputDialog("Please enter a valid cell phone number starting with the international country code: ");
        if(checkCellPhoneNumber()){
            JOptionPane.showMessageDialog(null, "Cell number successfully captured.");
        }else{
            JOptionPane.showMessageDialog(null, "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
        } 
        return "User has been registered successfully!";
        }
            
        
    
public static void main(String[] args) {

      
        String registrationMessage = registerUser();
        JOptionPane.showMessageDialog(null, registrationMessage);

        if (registrationMessage.contains("successfully")) {
            boolean loginSuccess = false;
            while (!loginSuccess) {
            String loginUsername = JOptionPane.showInputDialog("Enter username to log in: ");
            String loginPassword = JOptionPane.showInputDialog("Enter password: ");
            
            loginSuccess = loginUser(loginUsername, loginPassword);
            JOptionPane.showMessageDialog(null, returnLoginStatus(loginSuccess));
            
            if(!loginSuccess){
                int retry = JOptionPane.showConfirmDialog(null, "Would you like to try again?", "login failed.", JOptionPane.YES_NO_OPTION);
                if (retry != JOptionPane.YES_OPTION)
                    break;
            }
            }
        }
}

    public static boolean loginUser(String loginUsername, String loginPassword){
        return loginUsername.equals(username) && loginPassword.equals(password);
    }

    public static String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "A successful login.";
        } else {
            return "A failed login.";
        }
    }
}
