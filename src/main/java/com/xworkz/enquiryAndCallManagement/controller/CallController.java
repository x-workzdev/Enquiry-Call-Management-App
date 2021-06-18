package com.xworkz.enquiryAndCallManagement.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;
import com.xworkz.enquiryAndCallManagement.service.CallService;
import com.xworkz.enquiryAndCallManagement.service.EnquiryService;

@RestController
@RequestMapping("/")
public class CallController {

	@Autowired
	private CallService callService;

	@Autowired
	private EnquiryService enquiryService;

	private Logger logger = LoggerFactory.getLogger(CallController.class);

	@RequestMapping(value = "/getNewCalls.do", method = RequestMethod.GET)
	public ModelAndView getNewCalls() {
		logger.debug("invoked CallController() in controller");
		ModelAndView modelAndView = new ModelAndView("NewCalls");
		try {
			List<EnquiryCallEntity> callList = callService.getNewCalls();
			if (Objects.nonNull(callList)) {
				logger.info("Fetched New Calls Successfully");
				modelAndView.addObject("callList", callList);
				return modelAndView;
			} else {
				logger.info("No Calls found at the time, Please try after sometime");
				modelAndView.addObject("msg", "No Calls found at the time, Please try after sometime");
				return modelAndView;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			modelAndView.addObject("msg", "No Calls found at the time, Please try after sometime");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/getUnsolvedCalls.do", method = RequestMethod.GET)
	public ModelAndView getUnsolvedCalls() {
		logger.debug("invoked getUnsolvedCalls() in controller");
		ModelAndView modelAndView = new ModelAndView("UnsolvedCalls");
		try {
			List<EnquiryCallEntity> callList = callService.getUnsolvedCalls();
			if (Objects.nonNull(callList)) {
				logger.info("Fetched New Calls Successfully");
				modelAndView.addObject("callList", callList);
				return modelAndView;
			} else {
				logger.info("No Unsloved Calls found at the time, Please try after sometime");
				modelAndView.addObject("msg", "No Unsloved Calls found at the time, Please try after sometime");
				return modelAndView;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			modelAndView.addObject("msg", "No Unsloved Calls found at the time, Please try after sometime");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/getAllCalls.do", method = RequestMethod.GET)
	public ModelAndView getAllCalls() {
		logger.debug("invoked getAllCalls() in controller");
		ModelAndView modelAndView = new ModelAndView("AllCalls");
		try {
			List<EnquiryCallEntity> callList = callService.getAllCalls();
			if (Objects.nonNull(callList)) {
				logger.info("Fetched New Calls Successfully");
				modelAndView.addObject("callList", callList);
				return modelAndView;
			} else {
				logger.info("No Calls found at the time, Please try after sometime");
				modelAndView.addObject("msg", "No Calls found at the time, Please try after sometime");
				return modelAndView;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			modelAndView.addObject("msg", "No Calls found at the time, Please try after sometime");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/sendOTP.do", method = RequestMethod.POST)
	public String generateAndSendOTP(@RequestParam("enquiryId") int enquiryId) {
		logger.debug("invoked generateAndSendOTP() in controller");
		try {
			String onetimepass = callService.genarateOTP();

			if (Objects.nonNull(onetimepass) && enquiryId > 0) {
				boolean sentMail = callService.sendOTPMail(enquiryId, onetimepass);
				boolean sentSMS = callService.sendOTPSMS(enquiryId, onetimepass);

				if (sentMail || sentSMS) {
					callService.updateOTPById(enquiryId, onetimepass);
					logger.info("OTP Sent Successfully TO Registered Number and Email ID");
					return "OTP has been sent to Registered Number and Email id, Valid for 30min.";
				} else {
					logger.info("OTP Sent Failed ,Check The Mial Id or Mobile Number!");
					return "Failed to send OTP, Please try again!";
				}
			}

			else {
				logger.debug("MailId is not availbale for enquiryID:" + enquiryId);
				return "Failed to send OTP, Mailid is not valid!";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "Failed to send OTP, at this time. Please try again later!";
		}

	}

	@RequestMapping(value = "/validateNewCallOTP.do", method = RequestMethod.POST)
	public ModelAndView validateNewCallOTP(@RequestParam("enquiryId") int enquiryId,
			@RequestParam("otp") String enentredOTP, Model model) {
		logger.debug("invoked validateNewCallOTP() in controller");

		try {
			EnquiryCallDTO callEnquiry = callService.validateOTP(enquiryId, enentredOTP);

			if (Objects.nonNull(callEnquiry)) {
				model.addAttribute("callEnquiry", callEnquiry);
				model.addAttribute("msg", "OTP Matched For The Enquiry");
				return new ModelAndView("UpdateCall");
			} else {
				logger.debug("OTP Not Matched for enquiryId:" + enquiryId);
				ModelAndView modelV = getNewCalls();
				return modelV.addObject("msg", "OTP Not Valid Or Time Over, Please Try Again!");
			}

		} catch (Exception e) {
			logger.debug("OTP Not available for enquiryId:" + enquiryId);
			ModelAndView modelV = getNewCalls();
			return modelV.addObject("msg", "OTP Not Valid Or Time Over, Please Resend Again!");
		}
	}

	@RequestMapping(value = "/validateUnsolvedCallOTP.do", method = RequestMethod.POST)
	public ModelAndView validateUnsolvedCallOTP(@RequestParam("enquiryId") int enquiryId,
			@RequestParam("otp") String enentredOTP, Model model) {
		logger.debug("invoked validateUnsolvedCallOTP() in controller");

		try {
			EnquiryCallDTO callEnquiry = callService.validateOTP(enquiryId, enentredOTP);

			if (Objects.nonNull(callEnquiry)) {
				model.addAttribute("callEnquiry", callEnquiry);
				model.addAttribute("msg", "OTP Matched For The Enquiry");
				return new ModelAndView("UpdateUnsolvedCall");
			} else {
				logger.debug("OTP Not Matched for enquiryId:" + enquiryId);
				ModelAndView modelV = getUnsolvedCalls();
				return modelV.addObject("msg", "OTP Not Valid Or Time Over, Please Try Again!");
			}

		} catch (Exception e) {
			logger.debug("OTP Not available for enquiryId:" + enquiryId);
			ModelAndView modelV = getUnsolvedCalls();
			return modelV.addObject("msg", "OTP Not Valid Or Time Over, Please Try Again!");
		}
	}

	@RequestMapping(value = "/updateNewCall.do", method = RequestMethod.POST)
	public ModelAndView updateNewCall(EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked updateNewCall() in controller");
		ModelAndView modelAndView = new ModelAndView("UpdateCall");
		try {
			if (Objects.nonNull(enquiryDTO)) {
				boolean validate = enquiryService.updateEnquiryById(enquiryDTO);
				if (Objects.nonNull(validate)) {
					logger.info("Enquiry Updated Successfully");
					ModelAndView modelV = getNewCalls();
					return modelV.addObject("msg", "Call Enquiry Updated Successfully");
				} else {
					logger.info("Enquiry Not Updated");
					modelAndView.addObject("msg", "Not able to Update the Call Enquiry, Please try again!");
					return modelAndView;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/updateUnsolvedCall.do", method = RequestMethod.POST)
	public ModelAndView updateunsolvedCall(EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked updateunsolvedCall() in controller");
		ModelAndView modelAndView = new ModelAndView("UpdateUnsolvedCall");
		try {
			if (Objects.nonNull(enquiryDTO)) {
				boolean validate = enquiryService.updateEnquiryById(enquiryDTO);
				if (Objects.nonNull(validate)) {
					logger.info("Enquiry Updated Successfully");
					ModelAndView modelV = getUnsolvedCalls();
					return modelV.addObject("msg", "Call Enquiry Updated Successfully");
				} else {
					logger.info("Enquiry Not Updated");
					modelAndView.addObject("msg", "Not able to Update the Call Enquiry, Please try again!");
					return modelAndView;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/getEnquiryByStatus.do", method = RequestMethod.POST)
	public List<EnquiryCallEntity> getEnquiryByStatus(@RequestBody EnquiryCallDTO enquiryDTO) {
		logger.debug("invoked getEnquiryByStatus() in controller");
		List<EnquiryCallEntity> callList = null;
		try {
			callList = callService.getEnquiryByStatus(enquiryDTO.getStatus());
			if (Objects.nonNull(callList)) {
				logger.info("Fetched New Calls Successfully");
				return callList;
			} else {
				logger.info("No Calls found at the time, Please try after sometime");
				return callList;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return callList;
		}
	}

}
