$(document).ready(function () {
    allHide();
	$('.newEnquiry').hide();
	$('.getCloudEnquiries').hide();
	$('.getlatestEnquiries').hide();
	$('.uploadEnquiry').hide();
	$('.viewEnquiries').hide();
	$('.enquiryManagement').hide();
	$('.callManagement').hide(); 
});

function allHide() {
	$('.newEnquiry').hide();
	$('.getCloudEnquiries').hide();
	$('.getlatestEnquiries').hide();
	$('.uploadEnquiry').hide();
	$('.startics').hide();
}

function hideMainTab(){	
	$('.getlatestEnquiries').hide();
	$('.getCloudEnquiries').hide();
	$('.enquiryManagement').hide();
	$('.callManagement').hide();
	$('.validateExcelfile').hide();   
	$('.overview').hide();
	$('.startics').hide();	
}

function selectpage(className){
	console.log("called clickFunc="+className);
	hideMainTab();
	allHide();
	$('.'+className).show();
}

function openkFunc(mainclass,subclass){
	console.log("called clickFunc="+mainclass+" sub:"+subclass);
	hideMainTab();
	allHide();
	$('.'+mainclass).show();
	$('.'+subclass).show();
}

function handleSelect(page) {
	window.location = page.value + ".jsp";
}

var contextPath = $("meta[name='contextPath']").attr("content");

function checkEmailExist() {
	var EnquiryDTO = {};
	EnquiryDTO["emailId"] = document.forms["newenq"]["emailId"].value;
		
	 $.ajax( {
		type : "POST",
		contentType : "application/json",
		url :contextPath + "/getEnquiryByEmail.do",
		data : JSON.stringify(EnquiryDTO),
		dataType : 'json',
		success : function(data) {
			if (data.emailId != null) {
				$('#isE').text('Email Id Already Exist!');
			 }
			else {
				$('#isE').text('');
			}
		  },
		 error : function(e) {
                console.log("ERROR: ", e);
         }
	});
}

function checkNameExist() {
	var EnquiryDTO = {};
	EnquiryDTO["fullName"] = document.forms["newenq"]["fullName"].value; 
		
	 $.ajax( {
		type : "POST",
		contentType : "application/json",
		url :contextPath + "/getEnquiryByName.do",
		data : JSON.stringify(EnquiryDTO), 
		dataType : 'json',
		success : function(data) {
			if (data.fullName != null) {
				$('#isName').text('FullName Exist!,Check Once');
			 }
			else {
				$('#isName').text('');
			}
		  },
		 error : function(e) {
                console.log("ERROR: ", e);
         }
	});
}

function validateMobileNo() {
	var EnquiryDTO = {};
	var number = document.forms["newenq"]["mobileNo"].value;
	EnquiryDTO["mobileNo"] =number;  
	if (number.length == 10){
		$.ajax( {
			type : "POST",
			contentType : "application/json",
			url :contextPath + "/getEnquiryByMobile.do",
			data : JSON.stringify(EnquiryDTO), 
			dataType : 'json',
			success : function(data) {
				if (data.mobileNo != null) {
					$('#isMobile').text('Mobile Number Exist!,Check Once');
				 }
				else {
					$('#isMobile').text('');
				}
			},
		 	error : function(e) {
                console.log("ERROR: ", e);
        	}
		});
	}else if (number.length != 10){
		$('#isMobile').text('Enter only 10 digit mobile number');
	}
}

function sendOTP(enquiryId){
	console.log(enquiryId);	
	
	$(function(){	
    $('.sendingOTP').css("display", "inline");
	$.ajax({
		   type:'POST',
		   url :contextPath + "/sendOTP.do",
		   data :  {
			   enquiryId: enquiryId,
           },
           dataType: 'text',
           async: false,
           beforeSend: function(){
        	   console.log('beforeSend')
			   $('.sendingOTP').css("display", "inline");
			},
		   success: function(data) {
			    console.log(data);
		        $("#responseMsg").html(data);		       
		        $('.sentOTP').css("display", "inline");
		   },
		   complete: function(){
			   $('.sendingOTP').css("display", "none");
		      },
		   error:function(exception){
			   alert('Exeption:'+exception);
			   }
	    });
	});
	
	document.querySelector("#today").valueAsDate = new Date();
	
}


function getCurrentEnquiries() {	
	 $.ajax( {
		type : "GET",
		contentType : "application/json",
		url :contextPath + "/getLatestEnquiriesForReview.do",
		dataType : 'json',
		success : function(data) {
		  },
		 error : function(e) {
                console.log("ERROR: ", e);
         }
	});
}


function loadCourses() {
	$.ajax({  
    	type: "GET",  
		url: contextPath + "/getAllCourses.do",  
		success: function (data) {  
			var course = '<option value="-1">Please Select a Course</option>';  
			for (var i = 0; i < data.length; i++) {  
				course += '<option value="' + data[i] + '">' + data[i] + '</option>';  
			}  
			$("#courses").html(course);  
		}  
	});  
}

function validateExcelFields() {
	console.log("calling validateExcelFields()");
	$.ajax({  
    	type: "GET",  
		url: contextPath + "/validateExcelFields.do",  
		success: function (data) {
			console.log("msg " + data);
			$("#validateFieldmsg").html(data);  
		}  
	});  
}

$(document).ready(function() {
	var table = $('#example').DataTable({
		searching: true,
		responsive : true,
	});

});


function loadData(){
	validateExcelFields() ;
	loadCourses();
	getCurrentEnquiries();
}

