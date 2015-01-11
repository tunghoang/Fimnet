/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.fimnet.omi;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author tunghx
 */
public class EntitlementInfo {
	private String deviceId;
	private String packageId;
	private String entTimeStr;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the packageId
	 */
	public String getPackageId() {
		return packageId;
	}

	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	/**
	 * @return the entTimeStr
	 */
	public String getEntTimeStr() {
		return entTimeStr;
	}

	/**
	 * @param entTimeStr the entTimeStr to set
	 */
	public void setEntTimeStr(String entTimeStr) {
		this.entTimeStr = entTimeStr;
	}
}
