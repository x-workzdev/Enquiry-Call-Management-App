package com.xworkz.enquiryAndCallManagement.dao;

import java.util.List;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

public interface EnquiryDAO {

	public boolean saveEnquiry(EnquiryCallEntity enquiryEntity);

	public List<EnquiryCallEntity> getLatestMonthEnquiries();

	public List<EnquiryCallEntity> getCustomDatesEnquiries(String fromDate, String toDate);

	public EnquiryCallEntity checkEnquiryByEmail(String emailId);

	public EnquiryCallEntity getEnquiryById(int enquiryId);

	public boolean updateEnquiryById(EnquiryCallEntity entity);

	public EnquiryCallEntity getEnquiryByFullName(String fullName);

	public EnquiryCallEntity getEnquiryByMobileNo(String mobileNo);
}
