package com.example.spotsaver;

public class UserLocation {
	String placeName = "";
	double longitude = 0;
	double latidude = 0;
	
	public UserLocation(String placeName, double lo, double la){
		this.placeName = placeName;
		longitude = lo;
		latidude = la;
	}
	
	public String toString(){
		return  "- - " + placeName.toUpperCase() + " - -  \nLon: " + longitude + " Lat: "+ latidude;
		
	}
	
	public String getLabel(){
		return placeName;
	}
	public double getLong(){
		return longitude;
	}
	public double getLat(){
		return latidude;
	}
}
