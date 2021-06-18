package com.xworkz.enquiryAndCallManagement.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString   
@Entity
@Table(name = "enquiry_details")
  @NamedQueries({ 
	  @NamedQuery(name = "fetchEnquiryByEmail", query ="from EnquiryCallEntity e WHERE e.emailId =:email"),
	  @NamedQuery(name = "fetchEnquiryById", query ="from EnquiryCallEntity e WHERE e.enquiryId =:id"),
	  @NamedQuery(name = "fetchEnquiryByFullName", query ="from EnquiryCallEntity e WHERE e.fullName =:name"),
	  @NamedQuery(name = "fetchEnquiryByMobileNo", query ="from EnquiryCallEntity e WHERE e.mobileNo =:mobileNo"),
	  @NamedQuery(name = "updateOTPById", query ="update EnquiryCallEntity e set e.otp =:otp, e.dateTime =:dt WHERE e.enquiryId =:id"),
	  @NamedQuery(name = "fetchCallByStatus", query ="from EnquiryCallEntity e WHERE e.status =:status")})

public class EnquiryCallEntity {
     
	@Id
	@GenericGenerator(name = "enquiry", strategy = "increment")
	@GeneratedValue(generator = "enquiry")
	@Column(name = "Enquiry_ID")
	private int enquiryId;
	
	@Column(name = "TIME_STAMP")
	private Timestamp dateTime;
		
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
	@Column(name = "ALTERNATE_MOBILE")
	private String alternateMobileNo;
	
	@Column(name = "EMAIL_ID")
	private String emailId;
	
	@Column(name = "COURSE")
	private String course;
	
	@Column(name = "BATCH_TYPE")
	private String batchType;
	
	@Column(name = "SOURCE")
	private String source;
	
	@Column(name = "BRANCH")
	private String branch;
	
	@Column(name = "REFRENCE")
	private String refrenceName;
	
	@Column(name = "REFRENCE_MOBILE")
	private String refrenceMobileNo;
	
	@Column(name = "COUNSELOR_NAME")
	private String counselor;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "SUB_STATUS")
	private String subStatus;
	
	@Column(name = "INTRESTED_DATE")
	private String intrestedDate;
	
	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "COMMENTS")
	private String comments;
}
