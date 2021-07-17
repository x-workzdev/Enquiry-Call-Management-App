package com.xworkz.enquiryAndCallManagement.service;

import java.util.List;

import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

 interface CallService {

	 List<EnquiryCallEntity> getNewCalls();
	
	 boolean sendOTPSMS(int enquiryId, String onetimepass);
	
	 String genarateOTP();
	
	 boolean sendOTPMail(int enquiryId,String toMailId);

	 boolean updateOTPById(int enquiryId,String otp);
	
	 EnquiryCallDTO validateOTP(int enquiryId, String enentredOTP);

	 List<EnquiryCallEntity> getUnsolvedCalls();

	 List<EnquiryCallEntity> getAllCalls();

	 List<EnquiryCallEntity> getEnquiryByStatus(String status);

	 boolean sendMailReminder(List<EnquiryCallEntity> callList);
	
}
