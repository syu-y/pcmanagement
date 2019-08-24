package com.example.pcmanagement.domain.model;

import java.sql.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name="PC")
public class PC {

	@Id
	private String pcId;

	private String type;
	private Date buyDate;
	private Date recycleDate;
	private String purpose;
	private String userId;
	private String userName;
	private String state;
	private String maker;
	private String serial;
	private String macAddress;
	private String cpu;
	private int score;
	private int memory;
	private String resolution; //解像度
	private String graphics;
	private String os;

	public PC() {}

	public PC(String pcId,String type, Date buyDate, Date recycleDate, String purpose,
		String userId,String userName, String state, String maker, String serial,
		String macAddress, String cpu, int score, int memory, String resolution,
		String graphics, String os) {
		this.pcId = pcId;
		this.type = type;
		this.buyDate = buyDate;
		this.recycleDate = recycleDate;
		this.purpose = purpose;
		this.userId = userId;
		this.userName = userName;
		this.state = state;
		this.maker = maker;
		this.serial = serial;
		this.macAddress = macAddress;
		this.cpu = cpu;
		this.score = score;
		this.memory = memory;
		this.resolution = resolution;
		this.graphics = graphics;
		this.os = os;
    }

	public String getPcId() {
		return pcId;
	}
	public void setPcId(String pcId) {
		this.pcId = pcId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public Date getRecycleDate() {
		return recycleDate;
	}
	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getGraphics() {
		return graphics;
	}
	public void setGraphics(String graphics) {
		this.graphics = graphics;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
}
