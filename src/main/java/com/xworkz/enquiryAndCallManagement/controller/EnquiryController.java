package com.xworkz.enquiryAndCallManagement.controller;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;
import com.xworkz.enquiryAndCallManagement.service.CoursesService;
import com.xworkz.enquiryAndCallManagement.service.EnquiryService;

@RestController
@RequestMapping("/")
public class EnquiryController {

	@Autowired
	private EnquiryService enquiryService;
	
	@Autowired
	private CoursesService coursesService;

	private Logger logger = LoggerFactory.getLogger(EnquiryController.class);

	@RequestMapping(value = "/newEnquiry.do", method = RequestMethod.POST)
	public ModelAndView newEnquiry(EnquiryCallDTO enqiryDTO) {
		ModelAndView modelAndView = new ModelAndView("Home");
		logger.debug("invoked newEnquiry");

		try {
			logger.debug("Enquiry Reday to save...");
			boolean validate = enquiryService.validateAndSaveEnquiry(enqiryDTO);
			if (validate) {
				logger.info("Enquiry Saved Successfuly");
				modelAndView.addObject("success", "Enquiry Saved Successfuly");
			} else {
				logger.info("Enquiry Not saved...Please Enter the valid Details");
				modelAndView.addObject("faild", "Enquiry Not saved...Please Enter the valid Details");
			}
		}

		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return modelAndView;
   
	}

	@RequestMapping(value = "/uploadEnquiry.do", method = RequestMethod.POST)
	public ModelAndView uploadBulkEnqiry(@RequestParam("uploadFile") MultipartFile file) {
		logger.debug("uploadBulkEnqiry() is called....");

		ModelAndView modelAndView = new ModelAndView("Home");
		if (!file.isEmpty()) {
			logger.debug("contant type {}", file.getContentType());
			List<EnquiryCallDTO> enquiryList = enquiryService.getEnquiryListFromXls(file);
			if (Objects.nonNull(enquiryList)) {
				for (EnquiryCallDTO enquiryDTO : enquiryList) {
					if (Objects.nonNull(enquiryDTO.getFullName())) {
						enquiryService.validateAndSaveEnquiry(enquiryDTO);
					}
				}
				return modelAndView.addObject("msg", "Bulk Enquiry Upload Successful");
			}
			logger.info("Is file is Empty :{}", enquiryList.isEmpty());
		} else {
			modelAndView.addObject("msg", "File is not found.........");
		}
		return modelAndView.addObject("msg", "Bulk Enquiry Upload Incomplete! Check and Try Again.");

	}

	@Scheduled(cron = "${cron.expression}")
	@RequestMapping(value = "/downloadEnquirySchedule.do", method = RequestMethod.GET)
	public ModelAndView downloadEnquiryScheduler() {
		logger.debug("invoked downloadEnquiryScheduler() in controller");
		ModelAndView modelAndView = new ModelAndView("Home");
		try {
			List<EnquiryCallDTO> enquiryList = enquiryService.downloadEnquiry();
			logger.debug("Downalod Enquiry Task Exccution Done");
			boolean valid=enquiryService.validateExcelFile();
			if (valid) {
				if (Objects.nonNull(enquiryList)) {
					for (EnquiryCallDTO enquiryDTO : enquiryList) {
						if (Objects.nonNull(enquiryDTO)) {
							enquiryService.validateAndSaveEnquiry(enquiryDTO);
							logger.info("Enquiry Read from cloud doc and Saved Successfully");
						} else {
							logger.info("The Enquiry has empty values");

						}
					}
					return modelAndView.addObject("msg", "Bulk Enquiry Read and Saved Successfully");
				} else {
					logger.info("Is file is Empty :{}", enquiryList.isEmpty());
				}
			}else {
				return modelAndView.addObject("msg", "Bulk Enquiry Reading Incomplete! Because of Excel file fiels are not match ");
			}
			
		} catch (URISyntaxException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView.addObject("msg", "Bulk Enquiry Reading Incomplete! Check and Try Again.");
	}
	
	
	@RequestMapping(value = "/getLatestEnquiries.do", method = RequestMethod.GET)
	public ModelAndView getRecentEnquiries() {
		logger.debug("invoked getRecentEnquiries() in controller");
		ModelAndView modelAndView = new ModelAndView("ViewEnquiry");
		try {
			List<EnquiryCallEntity> enquiryList = enquiryService.getLatestEnquiries();
			if (Objects.nonNull(enquiryList)) {
				logger.info("Latest 30 days Enquiry Fetched Successfully");
				modelAndView.addObject("enquiryList", enquiryList);
				return modelAndView;
			} else {
				logger.info("No enquiries found at the time, Please try after sometime");
				modelAndView.addObject("msg", "No enquiries found at the time, Please try after sometime");
				return new ModelAndView("Home");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}
	

	@RequestMapping(value = "/getCustomEnquiries.do", method = RequestMethod.POST)
	public ModelAndView getCustomEnquiries(@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate) {
		logger.debug("invoked getCustomEnquiries() in controller");
		ModelAndView modelAndView = new ModelAndView("ViewEnquiry");
		try {
			if (Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
				List<EnquiryCallEntity> enquiryList = enquiryService.getCustomEnquiries(fromDate, toDate);
				if (Objects.nonNull(enquiryList)) {
					logger.debug("Custom Enquiries Fetched Successfully");
					modelAndView.addObject("msg", "Custom Enquiries Fetched Successfully");
					modelAndView.addObject("enquiryList", enquiryList);
					return modelAndView;
				} else {
					logger.debug("No enquiries found for givin dates");
					modelAndView.addObject("msg", "No enquiries found for givin dates, Please try after sometime");
					return modelAndView;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/getLatestEnquiriesForReview.do", method = RequestMethod.GET)
	public ModelAndView getLatestEnquiriesForReview() {
		logger.debug("invoked getCurrentEnquiriesForFiltter() in controller");
		ModelAndView modelAndView = new ModelAndView("Home");
		try {
			List<EnquiryCallEntity> enquiryList = enquiryService.getLatestEnquiries();
			if (Objects.nonNull(enquiryList)) {
				logger.info("Latest 30 days Enquiries Fetched Successfully");
				modelAndView.addObject("enquiryList", enquiryList);
				return modelAndView;
			} else {
				logger.info("No enquiries found at the time, Please try after sometime");
				modelAndView.addObject("msg", "No enquiries found at the time");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/getCustomEnquiriesForReview.do", method = RequestMethod.POST)
	public ModelAndView getCustomEnquiriesForFillter(@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate) {
		logger.debug("invoked getCustomEnquiriesForFillter() in controller");
		ModelAndView modelAndView = new ModelAndView("Home");
		try {
			if (Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
				List<EnquiryCallEntity> enquiryList = enquiryService.getCustomEnquiries(fromDate, toDate);
				if (Objects.nonNull(enquiryList)) {
					logger.debug("Custom Enquiries Fetched Successfully");
					modelAndView.addObject("msg", "Overview of Custom Enquiries Fetched");
					modelAndView.addObject("enquiryList", enquiryList);
					return modelAndView;
				} else {
					logger.debug("No enquiries found for givin dates");
					modelAndView.addObject("msg", "No enquiries found for givin dates");
					return modelAndView;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}	

	@RequestMapping(value = "getEnquiryByEmail.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public EnquiryCallDTO getEnquiryByEmail(@RequestBody EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked getEnquiryByEmail() in controller");
		EnquiryCallDTO enquiry = null;
		try {
			if (Objects.nonNull(enquiryDTO.getEmailId())) {
				enquiry = enquiryService.getEnquiryByEmail(enquiryDTO.getEmailId());
				if (Objects.nonNull(enquiry)) {
					logger.debug("Enquiry Is available for Email:{}",enquiry.getEmailId());
					return enquiry;
				 }
				else {
					logger.debug("No enquiries found for the Email:{}",enquiry.getEmailId());
					return enquiry;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiry;
	}
	
	@RequestMapping(value = "getEnquiryByName.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public EnquiryCallDTO getEnquiryByName(@RequestBody EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked getEnquiryByName() in controller");
		EnquiryCallDTO enquiry = null;
		try {
			if (Objects.nonNull(enquiryDTO.getFullName())) {
				enquiry = enquiryService.getEnquiryByFullName(enquiryDTO.getFullName());
				if (Objects.nonNull(enquiry)) {
					logger.debug("Enquiry Is available for Name:{}",enquiry.getFullName());
					return enquiry;
				 }
				else {
					logger.debug("No enquiries found for the Name:{}",enquiry.getFullName());
					return enquiry;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiry;
	}
	
	@RequestMapping(value = "getEnquiryByMobile.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public EnquiryCallDTO getEnquiryByMobileNo(@RequestBody EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked getEnquiryByMobileNo() in controller");
		logger.debug("mobile no:{}",enquiryDTO.getMobileNo());
		EnquiryCallDTO enquiry = null;
		try {
			if (Objects.nonNull(enquiryDTO.getMobileNo())) {
				enquiry = enquiryService.getEnquiryByMobileNo(enquiryDTO.getMobileNo());
				if (Objects.nonNull(enquiry)) {
					logger.debug("Enquiry Is available for Mobile No:{}",enquiry.getMobileNo());
					return enquiry;
				 }
				else {
					logger.debug("No enquiries found for the Mobile No:{}",enquiry.getMobileNo());
					return enquiry;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiry;
	}
	
	@RequestMapping(value = "/getEnquiryById.do", method = RequestMethod.POST)
	public ModelAndView getEnquiryById(@RequestParam("enquiryId") int enquiryId) {
		logger.debug("invoked getEnquiryById() in controller");
		ModelAndView modelAndView = new ModelAndView("UpdateEnquiry");
		EnquiryCallDTO enquiry = null;
		try {
			if (Objects.nonNull(enquiryId)) {
				enquiry = enquiryService.getEnquiryById(enquiryId);
				if (Objects.nonNull(enquiry)) {
					logger.info("Enquiry available for Id:{}",enquiry.getEnquiryId());
					modelAndView.addObject("enquiry", enquiry);
					return modelAndView;
				 }
				else {
					logger.info("No enquiries found for the Id:{}",enquiryId);
					modelAndView.addObject("enquiry", enquiry);
					return modelAndView;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateEnquiryById.do", method = RequestMethod.POST)
	public ModelAndView updateEnquiryById(EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked updateEnquiryById() in controller");
		ModelAndView modelAndView = new ModelAndView("UpdateEnquiry");
		try {
			if (Objects.nonNull(enquiryDTO)) {
				boolean validate = enquiryService.updateEnquiryById(enquiryDTO);
				if (Objects.nonNull(validate)) {
		   			logger.info("Enquiry Updated Successfully");
		   			ModelAndView modelAndV = getRecentEnquiries();
		   			return modelAndV.addObject("msg", "Enquiry Updated Successfully");				 
				} else {
					logger.info("Enquiry Not Updated");
					modelAndView.addObject("msg", "Not able to Update the Enquiry!, Please try again!");
					return modelAndView;
				}
			}
			else {
				modelAndView.addObject("msg", "Not able to Find the Enquiry!, Please try again!");
				return modelAndView;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "getAllCourses.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<String> getAllCourses() {
		logger.info("invoked getAllCourses() in controller");
		List<String> list = null;
		try {
			list = coursesService.getAllCourses();
			if (Objects.nonNull(list)) {
				logger.debug("Courses are available");
			}else {
				logger.debug("Course not found");
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return list;
	}
	
	@RequestMapping(value = "/validateExcelFields.do",method = RequestMethod.GET)
	public String validateExcelFileFields() {
		logger.debug("invoked validateExcelFileFields() in controller");
		boolean valid = false;
		String msg = null;
		try{
			valid=enquiryService.validateExcelFile();
			if (valid) {
				msg = "Excel file fields are matched";
			} else {
				msg = "Excel file fiels are not matched";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return msg;
	}

}
