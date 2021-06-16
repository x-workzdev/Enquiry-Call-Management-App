package com.xworkz.enquiryAndCallManagement.dao;

import java.sql.Timestamp;
import java.util.List;

import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

public interface CallDAO {

	public List<EnquiryCallEntity> getNewCalls();
	
	public String sendSMS(String apiKey, String username, String sender, String phone, String message,
			String smsType,String route,String templateId);

	public boolean updateOTPById(int enquiryId, String otp);
	
	public Timestamp getTimeStamp();

	public List<EnquiryCallEntity> getUnsolvedCalls();

	public List<EnquiryCallEntity> getAllEnquiryCalls();

}
