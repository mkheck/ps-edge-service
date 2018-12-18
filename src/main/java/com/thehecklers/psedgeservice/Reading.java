package com.thehecklers.psedgeservice;

public class Reading {
	private int id;
	private int node;
	private double hum;
	private double temp;
	private double volts;
	private double amps;
	private int status;
	
	protected Reading() {}
	
	public Reading(int id, int node, double hum, double temp, double volts, double amps, int status) {
		super();
		this.id = id;
		this.node = node;
		this.hum = hum;
		this.temp = temp;
		this.volts = volts;
		this.amps = amps;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public double getHum() {
		return hum;
	}

	public void setHum(double hum) {
		this.hum = hum;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getVolts() {
		return volts;
	}

	public void setVolts(double volts) {
		this.volts = volts;
	}

	public double getAmps() {
		return amps;
	}

	public void setAmps(double amps) {
		this.amps = amps;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setReading(Reading reading) {
        this.id = reading.getId();
        this.node = reading.getNode();
        this.hum = reading.getHum();
        this.temp = reading.getTemp();
        this.volts = reading.getVolts();
        this.amps = reading.getAmps();
        //this.pressure = reading.getPressure();
        this.status = reading.getStatus();
    }

	@Override
	public String toString() {
		return "Reading [id=" + id + ", node=" + node + ", hum=" + hum + ", temp=" + temp + ", volts=" + volts
				+ ", amps=" + amps //+ ", pressure=" + pressure
				+ ", status=" + status + "]";
	}
}
