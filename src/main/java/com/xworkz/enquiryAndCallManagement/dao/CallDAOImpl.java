package com.xworkz.enquiryAndCallManagement.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xworkz.enquiryAndCallManagement.entity.EnquiryCallEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository
public class CallDAOImpl implements CallDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(CallDAOImpl.class);

	@Autowired
	private SessionFactory factory;

	public CallDAOImpl() {
		logger.debug("created " + this.getClass().getSimpleName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnquiryCallEntity> getNewCalls() {
		logger.debug("invoked getLatestMonthEnquiries()...");
		Session session = factory.openSession();
		Transaction transaction = null;
		List<EnquiryCallEntity> callList = null;
		
		String hqlQuery = "FROM EnquiryCallEntity EC WHERE EC.status = 'Pending'";
		
		try {
			if (Objects.nonNull(session)) {
				transaction = session.beginTransaction();
				logger.debug("transcation stared..");
				Query  query  = session.createQuery(hqlQuery);
				callList = query.list();
				transaction.commit();
				logger.debug("Transaction commited");
				logger.debug("Calls are fetched");
				return callList;
			}
			else {
				logger.debug("not able to create Session");
				return callList;
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage(), e);
		}

		finally {
			if (Objects.nonNull(session)) {
				session.close();
				logger.debug("session closed");
			}
		}
		return callList;
	}
	
	
	public String sendSMS(String apiKey, String username, String sender, String phone, String message, String smsType,
			String route,String templateId) {
		StringBuilder content = new StringBuilder();
		String line;
		try {
			String requestUrl = "http://www.k3digitalmedia.co.in/websms/api/http/index.php?" + "&username="
					+ URLEncoder.encode(username, "UTF-8") + "&apikey=" + URLEncoder.encode(apiKey, "UTF-8")
					+ "&apirequest=" + URLEncoder.encode(smsType, "UTF-8") 
					+ "&route=" + URLEncoder.encode(route, "UTF-8") 
					+ "&sender=" + URLEncoder.encode(sender, "UTF-8") 
					+ "&mobile=" + URLEncoder.encode(phone, "UTF-8") 
					+ "&message=" + URLEncoder.encode(message, "UTF-8")
					+ "&TemplateID=" + URLEncoder.encode(templateId, "UTF-8");
			URL url = new URL(requestUrl);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.setDoOutput(true);
			uc.setRequestMethod("POST");

			DataOutputStream wr = new DataOutputStream(uc.getOutputStream());
			wr.write(requestUrl.toString().getBytes());
			BufferedReader bufferedReader = null;
			if (uc.getResponseCode() == 200) {
				logger.debug("Response code is {}", uc.getResponseCode());
				bufferedReader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				logger.debug("Success URL is {}", uc);
				logger.info("SMS Sent Succesfuly to +" + phone);
				while ((line = bufferedReader.readLine()) != null) {
					content.append(line).append("\n");
				}
				bufferedReader.close();
				logger.debug("Content is {}", content);
				return content.toString();
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(uc.getErrorStream()));
				logger.debug("Fail contact number is {}", requestUrl.length());
				logger.info("SMS Sent Faild to +" + phone);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;

	}

	@Override
	public boolean updateOTPById(int enquiryId, String otp) {
		logger.debug("invoked updateOTPById()...");
		Session session = factory.openSession();
		Transaction transaction = null;
		boolean update = false;
		
		try {
			if (Objects.nonNull(session)) {
				transaction = session.beginTransaction();
				logger.debug("transcation stared..");
				Query query = session.getNamedQuery("updateOTPById");
				query.setParameter("id", enquiryId);
				query.setParameter("otp", otp);
				query.setParameter("dt", getTimeStamp());
				query.executeUpdate();
				transaction.commit();
				logger.debug("Transaction commited");
				logger.info("Enquiry updated");
				update = true;
			} else {
				logger.debug("not able to create Session");
			}
		 } 
		catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage(), e);
		}

		finally {
			if (Objects.nonNull(session)) {
				session.close();
				logger.debug("session closed");
			}
		}
		return update;
		
	}
	
	public Timestamp getTimeStamp() {
		Date date = new Date();
		Timestamp currentTimestamp = new Timestamp(date.getTime());
		logger.debug("Timestamp:{}", currentTimestamp);
		return currentTimestamp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnquiryCallEntity> getUnsolvedCalls() {
		logger.debug("invoked getLatestMonthEnquiries()...");
		Session session = factory.openSession();
		Transaction transaction = null;
		List<EnquiryCallEntity> callList = null;
		
		String hqlQuery = "FROM EnquiryCallEntity EC WHERE EC.status != 'Joined' and status !='Not Intrested' and status !='Joined And Left'";
		
		try {
			if (Objects.nonNull(session)) {
				transaction = session.beginTransaction();
				logger.debug("transcation stared..");
				Query  query  = session.createQuery(hqlQuery);
				callList = query.list();
				transaction.commit();
				logger.debug("Transaction commited");
				logger.debug("Calls are fetched");
				return callList;
			}
			else {
				logger.debug("not able to create Session");
				return callList;
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage(), e);
		}

		finally {
			if (Objects.nonNull(session)) {
				session.close();
				logger.debug("session closed");
			}
		}
		return callList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnquiryCallEntity> getAllEnquiryCalls() {
		logger.debug("invoked getLatestMonthEnquiries()...");
		Session session = factory.openSession();
		Transaction transaction = null;
		List<EnquiryCallEntity> callList = null;
		
		String hqlQuery = "FROM EnquiryCallEntity";
		
		try {
			if (Objects.nonNull(session)) {
				transaction = session.beginTransaction();
				logger.debug("transcation stared..");
				Query  query  = session.createQuery(hqlQuery);
				callList = query.list();
				transaction.commit();
				logger.debug("Transaction commited");
				logger.debug("Calls are fetched");
				return callList;
			}
			else {
				logger.debug("not able to create Session");
				return callList;
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage(), e);
		}

		finally {
			if (Objects.nonNull(session)) {
				session.close();
				logger.debug("session closed");
			}
		}
		return callList;
	}


}
