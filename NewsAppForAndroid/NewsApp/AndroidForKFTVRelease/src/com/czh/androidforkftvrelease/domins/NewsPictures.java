package com.czh.androidforkftvrelease.domins;

public class NewsPictures {
 
  private String newspicture;
  private String newspicturetitle;
  
public NewsPictures() {
	super();
}
public NewsPictures( String newspicture,
		String newspicturetitle) {
	super();
	
	this.newspicture = newspicture;
	this.newspicturetitle = newspicturetitle;
}

public String getNewspicture() {
	return newspicture;
}
public void setNewspicture(String newspicture) {
	this.newspicture = newspicture;
}
public String getNewspicturetitle() {
	return newspicturetitle;
}
public void setNewspicturetitle(String newspicturetitle) {
	this.newspicturetitle = newspicturetitle;
}
  
}
