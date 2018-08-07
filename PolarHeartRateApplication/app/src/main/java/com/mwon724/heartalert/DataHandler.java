package com.mwon724.heartalert;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.androidplot.xy.SimpleXYSeries;

/**
 * This handler decodes data from the belt
 * From docs:
 *   Hdr Len Chk Seq Status HeartRate RRInterval_16-bits
 *    FE  08  F7  06   F1      48          03 64
 *   where; 
 *      Hdr always = 254 (0xFE), 
 *      Chk = 255 - Len
 *      Seq range 0 to 15
 *      Status = Upper nibble may be battery voltage,
 *               bit 0 is Beat Detection flag.
 *               
 *   src:http://ww.telent.net/2012/5/3/listening_to_a_polar_bluetooth_hrm_in_linux
 *   
 *   
 *
 *
 */
public class DataHandler extends Observable{
	private static DataHandler dd = new DataHandler();

	boolean newValue = true;
	SimpleXYSeries series1;
	ConnectThread reader;
	H7ConnectThread H7;
	
	int pos = 0;
	int rriVal = 0; //incoming HRM value
	int min = 0;
	int max = 0;
	int totalValuesReceived = 0; // counts how many values have been sent by belt

    List rriValuesList = new ArrayList<>();
	
	//for averaging
	int data = 0;
	int total = 0;

	int id;
	
	private DataHandler(){
		
	}
	
	public static DataHandler getInstance(){
		return dd;
	}

	public void acqui(int i){
		if (i == 2000){
			pos = 0;
		}
		else if (pos == 5){
			cleanInput(i);
		}
		pos++;
	}
	// assigns incoming HRM value if not garbage + converts to RRI
	public void cleanInput(int bpmVal){
		rriVal = 60*1000/bpmVal;
		totalValuesReceived++;
		rriValuesList.add(rriVal);
        Log.i("DataHandler", "bpmVal converted  to RRI: " + rriVal);
		if(rriVal != 0){
			data += rriVal;
			total++;
		}
		if(rriVal < min || min == 0)
			min = rriVal;
		else if(rriVal > max)
			max = rriVal;


		setChanged(); // must call this before notifying observers, marks hasChanged() to true
		notifyObservers(); // notify all observers, causing them to be notified by calling their update()
	}

    public String getLastValue() { return rriVal + " ms";}
    public int getLastIntValue() { return rriVal;}
	public String getMin() { return "Min " + min + " ms";}
	public String getMax(){ return "Max " + max + " ms";}
	
	public String getAvg(){
		if(total==0)
            return "Avg " + 0 + " ms";
		return "Avg " + data/total + " ms";
	}
    public void setNewValue(boolean newValue) {this.newValue = newValue; }
	public SimpleXYSeries getSeries1() {
		return series1;
	}
	public void setSeries1(SimpleXYSeries series1) {
		this.series1 = series1;
	}
	public ConnectThread getReader() {
		return reader;
	}
	public void setReader(ConnectThread reader) {
		this.reader = reader;
	}
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id=id;
	}
	public void setH7(H7ConnectThread H7){
		this.H7=H7;
	}
	public H7ConnectThread getH7(){
		return H7;
	}

	// RRI Value List + Occurrence Tracking Getters/Setters
    public int getTotalValuesReceived() { return totalValuesReceived;}
    public List getRRIValuesList() { return rriValuesList;}
    public void clearTotalValuesReceived() {totalValuesReceived = 0;}
    public void clearRRIValuesList() {rriValuesList.clear();}
	
}
