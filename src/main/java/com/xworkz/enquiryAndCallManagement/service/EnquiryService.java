package com.xworkz.enquiryAndCallManagement.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

public interface EnquiryService {

	public boolean validateAndSaveEnquiry(EnquiryCallDTO dto);

	public List<EnquiryCallDTO> getEnquiryListFromXls(MultipartFile file);

	public List<EnquiryCallDTO> downloadEnquiry() throws URISyntaxException, IOException;

	public List<EnquiryCallEntity> getLatestEnquiries();

	public List<EnquiryCallEntity> getCustomEnquiries(String fromDate, String toDate);

	public EnquiryCallDTO getEnquiryByEmail(String emailId);

	public boolean updateEnquiryById(EnquiryCallDTO enquiryDTO);

	public EnquiryCallDTO getEnquiryById(int enquiryId);

	public EnquiryCallDTO getEnquiryByFullName(String fullName);

	public EnquiryCallDTO getEnquiryByMobileNo(String mobileNo); 

}
