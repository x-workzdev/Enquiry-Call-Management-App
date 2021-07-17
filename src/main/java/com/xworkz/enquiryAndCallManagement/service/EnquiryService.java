package com.xworkz.enquiryAndCallManagement.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

  public interface EnquiryService {

	  boolean validateAndSaveEnquiry(EnquiryCallDTO dto);

	  List<EnquiryCallDTO> getEnquiryListFromXls(MultipartFile file);

	  List<EnquiryCallDTO> downloadEnquiry() throws URISyntaxException, IOException;

	  List<EnquiryCallEntity> getLatestEnquiries();

	  List<EnquiryCallEntity> getCustomEnquiries(String fromDate, String toDate);

	  EnquiryCallDTO getEnquiryByEmail(String emailId);

	  boolean updateEnquiryById(EnquiryCallDTO enquiryDTO);

	  EnquiryCallDTO getEnquiryById(int enquiryId);

	  EnquiryCallDTO getEnquiryByFullName(String fullName);

	  EnquiryCallDTO getEnquiryByMobileNo(String mobileNo);

	  boolean validateExcelFile(); 

}
