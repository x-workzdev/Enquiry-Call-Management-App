package com.xworkz.enquiryAndCallManagement.service;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import com.xworkz.enquiryAndCallManagement.dao.CallDAO;
import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;
import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;
import com.xworkz.enquiryAndCallManagement.util.EncryptionHelper;

import lombok.Setter;

@Setter
@Service
public class CallServiceImpl implements CallService {

	private static final Logger logger = LoggerFactory.getLogger(CallServiceImpl.class);

	@Autowired
	private CallDAO callDAO;
	
	@Autowired
	private EncryptionHelper encryptionHelper;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private SpringMailService mailService;
	
	@Autowired
	private EnquiryService enquiryService;

	@Value("${OTPMailSubject}")
	private String otpMailSubject;
	@Value("${MailReminderSubject}")
	private String toMailReminderSubject;
	@Value("${toMailID}")
	private String toMailID;
	@Value("${ccmailID}")
	private String[] ccmailID;
	
	@Value("${apiKey}")
	private String apiKey;
	@Value("${SMSusername}")
	private String SMSusername;
	@Value("${sender}")
	private String sender;
	@Value("${typeOfSMS}")
	private String typeOfSMS;
	@Value("${route}")
	private String route;
	@Value("${templateId}")
	private String templateId;
	@Value("${smsSuccess}")
	private String smsSuccess;
	
		
	public CallServiceImpl() {
		logger.debug("created " + this.getClass().getSimpleName());
	}
	
	@Override
	public List<EnquiryCallEntity> getNewCalls() {
		List<EnquiryCallEntity> enquiryList = null;
		try {
			enquiryList = callDAO.getNewCalls();
			logger.debug("fetched calls");
			return enquiryList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiryList;
	}
	
	public String genarateOTP() {
		String newRandomPassword = RandomStringUtils.randomNumeric(6);
		return newRandomPassword;
	}
	
	
	public boolean sendOTPMail(int enquiryId, String onetimepass) {
		logger.info("invoked sendOTPMail in service...");
		try {
			
			EnquiryCallDTO callEnquiry = enquiryService.getEnquiryById(enquiryId);

			  if(Objects.nonNull(onetimepass) && Objects.nonNull(callEnquiry.getEmailId())) {
				  Context context = new Context();
				  context.setVariable("onetimepass", onetimepass);

				String content = templateEngine.process("otpMailTemplate", context);
				MimeMessagePreparator messagePreparator = mimeMessage -> {

					MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
					messageHelper.setTo(callEnquiry.getEmailId());
					messageHelper.setSubject(otpMailSubject);
					messageHelper.setText(content, true);
				};

				boolean mailvalidation = mailService.validateAndSendMailByMailId(messagePreparator);

				if (mailvalidation) {
					logger.info("success", "OTP sent to registred mailID");
					return true;
				} else {
					logger.info("faild", "not able send OTP to registred mailID!");
					return false;
				}

			}

		} catch (Exception e) {
			logger.info("faild", "not able send OTP to registred mailID!");
			logger.error(e.getMessage(), e);
		}
	 return false;
	}
	
	

	@Override
	public boolean sendOTPSMS(int enquiryId,String onetimepass) {
		String response = null;
		String  status  = null;
		try {
			EnquiryCallDTO callEnquiry = enquiryService.getEnquiryById(enquiryId);
			String mobileNumber = callEnquiry.getMobileNo();
			
			if(Objects.nonNull(mobileNumber)){
			String smsMessage = "Dear "+callEnquiry.getFullName()+"," +'\n'+ "Your one-time password (OTP) for X-workz Enquiry is "+onetimepass+'\n'+'\n'+"See you soon,"+'\n'+"X-workz";	
	
			logger.debug("smsType is :{} mobileNumber is :{} message is: {}",typeOfSMS,mobileNumber,smsMessage);
			
			response = callDAO.sendSMS(encryptionHelper.decrypt(apiKey), encryptionHelper.decrypt(SMSusername),encryptionHelper.decrypt(sender),
					mobileNumber, smsMessage,typeOfSMS, route,templateId);
			
			logger.info("SingleSMS Result is {}", response);
			JSONObject json = new JSONObject(response);
			status = json.getString("message");
			if (status.equals(smsSuccess)) {
				return true;
			}
			else {
				logger.info("SingleSMS Result is {}", response);
				return false;
			}
			
			}
			else {
				logger.info("SingleSMS Result is {}", response);
				return false;
			}
			
		} catch (Exception e) {
			logger.error("\n\nMessage is {} and exception is {}\n\n\n\n\n", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean updateOTPById(int enquiryId, String otp) {
		boolean result = false;
		try {
			//entity.getTimeStamp(entity);
			result = callDAO.updateOTPById(enquiryId,otp);
			if (result) {
				logger.debug("Updated OTP");
				return result;
			} else {
				logger.debug("not able to Updated OTP");
				return result;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<EnquiryCallEntity> getUnsolvedCalls() {
		List<EnquiryCallEntity> enquiryList = null;
		try {
			enquiryList = callDAO.getUnsolvedCalls();
			logger.debug("fetched calls");
			return enquiryList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiryList;
	}
	
	
	public EnquiryCallDTO validateOTP(int enquiryId, String enentredOTP) {
		logger.debug("invoked validateOTP() in service");
		EnquiryCallDTO callEnquiry = enquiryService.getEnquiryById(enquiryId);
		
		if(Objects.nonNull(callEnquiry.getOtp())) {
			 long diffMinutes = calculateTimeDiff(callEnquiry.getDateTime(), callDAO.getTimeStamp());
			 logger.debug("diffMinutes:"+diffMinutes);
			 
		   if (callEnquiry.getOtp().equals(enentredOTP) && diffMinutes > -30) {
			   logger.debug("OTP Matched for enquiryId:"+enquiryId);
		       return callEnquiry;
		   }
		   else {
			   logger.debug("OTP Not Matched for enquiryId:"+enquiryId);
		      }
		  }
		 else {
			   logger.debug("OTP Not available for enquiryId:"+enquiryId);
		    }
		return null;
	}
	
	
	private long calculateTimeDiff(Timestamp dbTimeStamp, Timestamp currenrTimeStamp) {
		long diff = dbTimeStamp.getTime() - currenrTimeStamp.getTime();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		return minutes;
	}
	
	private long calculateDaysDiff(String intrestedDate) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormatter.format(new Date());		
		long diffDays = 0;
	    try {
	    	Date givenDate = dateFormatter.parse(intrestedDate);
			Date today = dateFormatter.parse(currentDate);
			diffDays = (givenDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);
			logger.info("diffDays="+diffDays);
			return diffDays;
			
		}    
	    catch (ParseException e) {
	    	logger.error(e.getMessage(), e);
		}
	  return diffDays;
	}	

	@Override
	public List<EnquiryCallEntity> getAllCalls() {
		List<EnquiryCallEntity> enquiryList = null;
		try {
			enquiryList = callDAO.getAllEnquiryCalls();
			return enquiryList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiryList;
	}

	@Override
	public List<EnquiryCallEntity> getEnquiryByStatus(String status) {
		List<EnquiryCallEntity> enquiryList = null;
		try {
			enquiryList = callDAO.getEnquiryByStatus(status);
			logger.debug("fetched all callenquiries");
			return enquiryList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return enquiryList;
	}

	@Override
	public boolean sendMailReminder(List<EnquiryCallEntity> callList) {
		logger.info("invoked sendMailReminder in service...");
		
     try {
		
		for (EnquiryCallEntity callEnquiry : callList) {
			 String intrestedDate = callEnquiry.getIntrestedDate();
			 if (Objects.nonNull(intrestedDate)) {
				long diffDays = calculateDaysDiff(intrestedDate);
			       if (diffDays==2 || diffDays==1) {
			    	    Context context = new Context();
						context.setVariable("name", callEnquiry.getFullName());
						context.setVariable("intrestedDate", callEnquiry.getIntrestedDate());

						String content = templateEngine.process("callReminderMailTemplate", context);
						MimeMessagePreparator messagePreparator = mimeMessage -> {

					  		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
							messageHelper.setTo(toMailID);
							messageHelper.setCc(ccmailID);
							messageHelper.setSubject(toMailReminderSubject);
							messageHelper.setText(content, true);
						};

						boolean mailvalidation = mailService.validateAndSendMailByMailId(messagePreparator);

						if (mailvalidation) {
							logger.info("success", "Reminder mail sent Succesfuly");
							return true;
						} else {
							logger.info("faild", "not able send the Reminder mail!");
							return false;
						}
					}	
			 else {
				 logger.info("Intrested Date is not available for the call enquiry "+callEnquiry.getFullName());
			}
	    }	       
		}
		
	  }
       
	  catch (Exception e) {
			
	   }	 
		return false;
		
	}	

}
