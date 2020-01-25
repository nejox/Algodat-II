package Klausurvorbereitung.ub8;

public class Point {
	private int key;
	private double lon; // longitude Längengrad
	private double lat; // Latitude Breitengrad

	public Point(int key, double lon, double lat) {
		super();
		this.key = key;
		this.lon = lon;
		this.lat = lat;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
