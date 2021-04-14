package util;

import java.util.ArrayList;

public class Pixel {
	
	double r, g, b;
	int x, y;
	
	ArrayList<Pixel> viz3 = new ArrayList<>();
	ArrayList<Pixel> vizX = new ArrayList<>();
	ArrayList<Pixel> vizC = new ArrayList<>();
	
	public Pixel(double red, double green, double blue, int i, int j) {
		red = this.r;
		green = this.g;
		blue = this.b;
		i = this.x;
		j = this.y;
	}

	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	public double getG() { 
		return g;
	}
	public void setG(double g) {
		this.g = g;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
}
