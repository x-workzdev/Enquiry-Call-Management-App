package com.xworkz.enquiryAndCallManagement.service;

import java.util.List;

import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

public interface CallService {

	public List<EnquiryCallEntity> getNewCalls();
	
	public boolean sendOTPSMS(int enquiryId, String onetimepass);
	
	public String genarateOTP();
	
	public boolean sendOTPMail(int enquiryId,String toMailId);

	public boolean updateOTPById(int enquiryId,String otp);
	
	public EnquiryCallDTO validateOTP(int enquiryId, String enentredOTP);

	public List<EnquiryCallEntity> getUnsolvedCalls();

	public List<EnquiryCallEntity> getAllCalls();
	
}
