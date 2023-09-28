package application;

import javafx.beans.property.SimpleStringProperty;

public class User {
	private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    
    User(String username, String password){
    	this.username = new SimpleStringProperty(username);
    	this.password = new SimpleStringProperty(password);
    }
    
    public String getUsername(){
    	return username.get();
    }
    public String getPassword(){
    	return password.get();
    }
}
