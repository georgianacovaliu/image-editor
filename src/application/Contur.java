package application;

import javafx.beans.property.SimpleStringProperty;

public class Contur {
	private final SimpleStringProperty name;
	private final SimpleStringProperty department;
	
	Contur(String name, String department){
		this.name = new SimpleStringProperty(name);
		this.department = new SimpleStringProperty(department);
	}
	
	public String getName(){
		return name.get();
	}
	
	public String getDepartment(){
		return department.get();
	}
}
