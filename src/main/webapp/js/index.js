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


function getEnquiryType() {
	
    if ($('#enquiryOperations').val() == '0') {
        allHide();
    }
    if ($('#enquiryOperations').val() == '1') {
        allHide();
        $('.newEnquiry').show();
    }
    if ($('#enquiryOperations').val() == '2') {
        allHide();
        $('.uploadEnquiry').show();
    }
    if ($('#enquiryOperations').val() == '3') {
        allHide();
    	$('.getCloudEnquiries').show();
    }
    if ($('#enquiryOperations').val() == '4') {
        allHide(); 
        $('.getlatestEnquiries').show(); 
    }
}

function allHide() {
	$('.newEnquiry').hide();
	$('.getCloudEnquiries').hide();
	$('.getlatestEnquiries').hide();
	$('.uploadEnquiry').hide(); 
}


function hideMainTab(){	
	$('.bulkMail').hide();
	$('.sendSMS').hide();
	$('.sendBulkSMS').hide();
	$('.getlatestEnquiries').hide();
	$('.getCloudEnquiries').hide();
	$('.enquiryManagement').hide();
	$('.callManagement').hide();
}

function clickFunc(className){
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

function checkEmailExist() {
	var contextPath = $("meta[name='contextPath']").attr("content");
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
	var contextPath = $("meta[name='contextPath']").attr("content");
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

function checkMobileNoExist() {
    var contextPath = $("meta[name='contextPath']").attr("content");
	var EnquiryDTO = {};
	EnquiryDTO["mobileNo"] = document.forms["newenq"]["mobileNo"].value; 
		
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
}

var contextPath = $("meta[name='contextPath']").attr("content");

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


$(document).ready(function() {
	var table = $('#example').DataTable({
		searching: true,
		responsive : true,
	});

	new $.fn.dataTable.FixedHeader(table);
});

/*function getEnquiryByStatus() {
    var contextPath = $("meta[name='contextPath']").attr("content");
	var EnquiryDTO = {};
	fillteredStatus = document.querySelector('#statusFilter').value;
	EnquiryDTO["status"] = fillteredStatus;
	console.log(fillteredStatus);
		
	 $.ajax( { 
		type : "POST",
		contentType : "application/json",
		url :contextPath + "/getEnquiryByStatus.do",
		data : JSON.stringify(EnquiryDTO), 
		dataType : 'json',
		success : function(callList) {
			if (callList != null) {
				console.log(callList);
				var myTable = $('#example').DataTable();
		           myTable.clear();
		           myTable.rows.add(callList).draw();
			 }
			else {
				$('#isMobile').text('');
			}
		  },
		 error : function(e) {
                console.log("ERROR: ", e);
         }
	});
}
*/

