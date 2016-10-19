package main;

import java.util.ArrayList;
import java.util.List;

import lejos.robotics.Color;

public class Colors {
	private List<String> colors;
	
	public Colors(){
		colors = new ArrayList<>();
	}

	public boolean colorExists(int colorId){
		
		switch(colorId){
		case Color.BLUE:
			return colors.contains("B");
		case Color.YELLOW:
			return colors.contains("Y");
		case Color.RED:
			return colors.contains("R");
		}
		
		return false;
	}
	
	public boolean isDone(){
		return colors.size() == 3;
	}
	
	public void addColor(String c){
		System.out.println("Color to add " + c);
		if (! colors.contains(c) && colors.size() < 3){
			colors.add(c);
			System.out.println("Color added ");
			printColors();
		}else{
			System.out.println("Don't add it");
		}
	}
	
	public void printColors(){
		for(int i = 0 ; i < colors.size(); i++){
			System.out.println("Kleur " + i + " " + colors.get(i));
		}
	}
}
