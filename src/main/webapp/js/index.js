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
			//console.log("Checking for existing email ID:"+EnquiryDTO.emailId);
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

function loadSelectedCourses(courseSelected) {
	$.ajax({  
    	type: "GET",  
		url: contextPath + "/getAllCourses.do",  
		success: function (data) {  
			var course = '<option value="'+courseSelected+'">'+courseSelected+'</option>';  
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

function getCurrentEnquiriesForReview() {	
	 $.ajax( {
		type : "GET",
		url :contextPath + "/getLatestEnquiriesForReview.do",
		contentType: false,
		dataType : 'Json',
		success : function(data) {
			
			var dataitems=[];
			$.each(data,function(i,item){
			  var data=[];
			  data.push(item.course);
			  data.push(item.status);
			  dataitems.push(data);
			});
			
			
			var table = $('#homeTable').DataTable({
				searching: true,
				responsive : true,
				searchPanes: {
		        	columns: [0,1],
		            viewTotal: true
		        },
		        data: dataitems,
		        columns: [
		            { title: "course" },
		            { title: "status" }
		        ],
		        paging:false,
		        info:false,
		        ordering: false,
		        searching: false,
		        dom: 'Plfrtip'
			});
		  },
		 error : function(e) {
              console.log("ERROR: ", e);
       }
	});
}

function getCustomEnquiriesForReview() {
       var fromDate = $('#givendate').val();
       var toDate = $('#today').val();
       
       var table = $('#homeTable').DataTable();
	    table.clear().draw();
       table.destroy();
	
	 $.ajax( {
		type : "POST",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		url :contextPath + "/getCustomEnquiriesForReview.do",
		data: {
			fromDate:fromDate,
			toDate:toDate,
		},
		dataType : 'json',
		success : function(data) {
			
			var dataitems=[];
			$.each(data,function(i,item){
			  var data=[];
			  data.push(item.course);
			  data.push(item.status);
			  dataitems.push(data);
			});
			
			
			var table = $('#homeTable').DataTable({
				searching: true,
				responsive : true,
				searchPanes: {
		        	columns: [0,1],
		            viewTotal: true
		        },
		        data: dataitems,
		        columns: [
		            { title: "course" },
		            { title: "status" }
		        ],
		        paging:false,
		        info:false,
		        ordering: false,
		        searching: false,
		        dom: 'Plfrtip'
			});
		  },
		 error : function(e) {
             console.log("ERROR: ", e);
      }
	});
}


function getEnquiryByStatus(fillteredStatus) {
	var EnquiryDTO = {};
	var statusGiven = fillteredStatus;	
	var table = $('#filterTable').DataTable();
    table.clear().draw();
    table.destroy();
       
	$.ajax({
		type : "POST",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		url : contextPath + "/getEnquiryByStatus.do",
		data : {
			status:statusGiven,
		},
		dataType : 'json',
		success : function(callList) {
			
			 $("document").ready(function () {

			        $("#filterTable").dataTable({
			           searching: true,
			           responsive : true,
			           order: [[6, 'asc']],
			           rowGroup: {
			               dataSrc: 6,
			            }
			        });
			   });
			
			if (callList != null) {
                 				 
				  var tableFilltredData;
				    
				  for (var i = 0; i < callList.length; i++) {
					    var timeStamp = fullDateTime(callList[i].dateTime);
						tableFilltredData += '<tr><td>'+timeStamp+'</td><td>'+callList[i].fullName+'</td><td>'+callList[i].mobileNo+'</td><td>'+callList[i].alternateMobileNo+'</td><td>'+callList[i].emailId+'</td><td>'+callList[i].course+'</td><td>'+callList[i].status+'</td><td>'+callList[i].interestedDate+'</td><td>'+callList[i].subStatus+'</td><td>'+callList[i].comments+'</td><td><input id="currentEnquiry" type="hidden" type="number" value="'+callList[i].enquiryId+'" name="enquiryId"><button onclick="sendOTP('+callList[i].enquiryId+')"class="btn btn-primary">Send OTP</button> <span class="sendingOTP" style="display: none;"><i class="fa fa-spinner fa-spin"></i> Sending...</span> <span class="sentOTP" style="display: none;"><i class="fa fa-check"></i> Sent</span> </td><td><form method="post" action="validateUnsolvedCallOTP.do"><input type="hidden" type="number" value="'+callList[i].enquiryId+'" name="enquiryId"> <input id="partitioned" type="text" maxlength="6" name="otp" required="required" /> <button class="btn btn-success">Submit</button></form></td></tr>'					
				  }
				  
				  function fullDateTime(dateTime) {
			            var d = new Date(dateTime);          
			            var fullDateTime = d.toLocaleString([], { hour12: true});
			            return fullDateTime;
			        }
			
				 $("#tabledata").html(tableFilltredData);
				 
			  }
			
	     },
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}

function loadData(){
	validateExcelFields() ;
	loadCourses();
	getCurrentEnquiriesForReview();
}



