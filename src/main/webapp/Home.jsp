<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="contextPath" content="${pageContext.request.contextPath}" />
<link rel="shortcut icon" href="./img/Logo.png">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css" rel="stylesheet">
<link href="https://cdn.datatables.net/1.10.23/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/searchpanes/1.3.0/css/searchPanes.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.3/css/select.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="./css/index.css">
<style type="text/css">
ul {
  list-style-type: none;
  }
  li {
  float: left;
}

li button {
  display: block;
  padding: 16px;
  margin: 5px;
}
}
</style>
</head>

<body onload="loadData()">  <!-- loadCourses() --> 

	<div class="backgroundImage">
		<div class="header">
			<a href="" class="logo"><img src="./img/Logo.png" height="90px"></a>
			<div class="header-right">
				<h1>Om's Development Center</h1>
			</div>
			<div style="margin-left: auto; margin-right: 5%; width: 10%;">
				<button onclick="location.href='EnquiryLogin.jsp'" type="button"
					class="btn btn-danger btn-block">
					<i class="fa fa-sign-out" aria-hidden="true"></i> Log Out
				</button>
			</div>
		</div>
		
		<div class="container">
				<div>
					<nav class="navbar navbar-expand-md navbar-dark bg-dark">
						
						<div class="collapse navbar-collapse" id="navbarsExample04">
					      <ul class="navbar-nav mr-auto">
					       <li class="nav-item"><a class="nav-link" href="#"
							onclick="selectpage('overview')">Overview</a></li>
					      
						    <li class="nav-item"><a class="nav-link" href="#"
							onclick="selectpage('enquiryManagement')">Enquiry Management</a></li>
						
						    <li class="nav-item"><a class="nav-link" href="#"
							onclick="selectpage('callManagement')">Call Management</a></li>
					      </ul>
				        </div>
					</nav>
				</div>
				
				<div class="container" align="center">
				   <div class="row"
					style="color: white; text-align: center; margin-top: 2%">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<h3 style="color: red;">
							<b>${LoginMsg}${success}${faild}${msg}</b>
						</h3>
					</div>
					<div class="col-md-3"></div>
				    </div>
			     </div>
			     
			 <div class="container container_border ">
				<div class="enquiryManagement" style="margin-bottom: 10%;">
					<h2 style="text-align: center;margin-top: 5%; color: white; padding-top: 3%">Enquiry Management</h2>
					<div class="panel panel-default">
						<div class="panel-body" align="center" style="margin-left: 20%;">						
						    <ul>
						      <li><button type="button" class="btn btn-primary" onclick="openkFunc('enquiryManagement','newEnquiry')"><i class="fa fa-file-text-o" aria-hidden="true"></i> New Enquiry</button></li>
						      <li><button type="button" class="btn btn-success" onclick="openkFunc('enquiryManagement','uploadEnquiry')"><i class="fa fa-cloud-upload" aria-hidden="true"></i> Upload Enquires</button></li>
						      <li><form action="getLatestEnquiries.do" method="GET"><button type="submit" class="btn btn-info"><i class="fa fa-eye" aria-hidden="true"></i>View Enquiries</button></form></li>
						      <li><button type="button" class="btn btn-light" onclick="openkFunc('enquiryManagement','getCloudEnquiries')"><i class="fa fa-refresh" aria-hidden="true"></i> Sync Enquires</button></li>   
						    </ul>						
						</div>
					</div>
				</div>
				
				<div class = "validateExcelfile">
						<div class ="row justify-content-center" style="color :red; margin-top: 2%; ">
							<h3 id="validateFieldmsg"></h3>
						</div>
				</div>
	
				<div class="callManagement">
					<h2 style="text-align: center;margin-top: 5%; color: white; padding-top: 3%">Call Management</h2>
					<div class="panel panel-default">
						<div class="panel-body" align="center" style="margin-top: 2%;margin-left: 28%;">
							<ul> 
						      <li><form action="getNewCalls.do" method="get"><button type="submit" class="btn btn-success"><i class="fa fa-file-text-o" aria-hidden="true"></i> New Calls</button></form></li>
						      <li><form action="getUnsolvedCalls.do" method="get"><button type="submit" class="btn btn-primary"><i class="fa fa-clock-o" aria-hidden="true"></i> Unsolved Calls</button></form></li>
						      <li><form action="getAllCalls.do" method="get"><button type="submit" class="btn btn-info" ><i class="fa fa-eye" aria-hidden="true"></i>All Calls</button></form></li>
						    </ul>	
						</div>
					</div>
				</div>
				
				<div class="overview">
				<h2 style="text-align: center;margin-top: 5%; color: white; padding-top: 3%">Overview</h2>
				<form action="getCustomEnquiriesForReview.do" class="getCustomEnquiries" method="post">
						<label> Select From Date: <input type="date" name="fromDate" value="20-9-2018"></label>
						<label> Select To Date: <input type="date" name="toDate" id="today">
						</label> <span><button class="btn btn-primary">Search</button></span>
				</form>	
				</div>
								
					
                  <div class="startics">
					<table id="homeTable" class="table table-striped table-bordered"
						style="width: 100%;display: none;">
						<thead style="color: black; background-color: brown;">
							<tr>
								<th>COURSE</th>
								<th>STATUS</th>
							</tr>
						</thead>

						<tbody style="color: white;">
							<c:forEach var="enquiryList" items="${enquiryList}">
								<tr>
									<td>${enquiryList.course}</td>
									<td>${enquiryList.status}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>				
				
				<form name="newenq" class="newEnquiry" method="POST" action="newEnquiry.do" style="margin-top:8%;">
					<table class="col-md-6 table table-bordered table-dark" border="1" border-color="white" align="center" style="color: white">
						<tr>
							<td colspan="2" align="center"><h3>New Enquiry</h3></td>
						</tr>

						<tr>
							<td><h5>
									Full Name<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" name="fullName"
								required placeholder="Enter full name" onblur="checkNameExist()" /><b><span
									id="isName" style="color: red;"></span></b></td>
						</tr>

						<tr>
							<td><h5>
									MOBILE NO<sup>*</sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="mobileNo" required placeholder="Enter valid mobile number" onblur="validateMobileNo()" /><b><span
			  						id="isMobile" style="color: red;"></span></b></td>
						</tr>

						<tr>
							<td><h5>
									ALTERNATE MOBILE<sup></sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="alternateMobileNo"
								placeholder="Enter alternate mobile number" /></td>
						</tr>

						<tr>
							<td><h5>
									EMAIL ID<sup>*</sup>:
								</h5></td>
							<td><input type="email" class="form-control" name="emailId"
								required placeholder="Enter valid email id"
								onblur="checkEmailExist()" /><b><span id="isE" style="color: red;"></span></b></td>
						</tr>

						<tr>
							<td><h5>
									COURSE<sup>*</sup>:
								</h5></td>
							<td><select class="form-control" id="courses" name="course"></select></td>
						</tr>

						<tr>
							<td><h5>
									BATCH TYPE<sup>*</sup>:
								</h5></td>
							<td><select name="batchType"
								class="form-control">
									<option value="OnlineWeekend" label="OnlineWeekend" />
									<option value="OnlineWeekDay" label="OnlineWeekDay" />
									<option value="OfflineWeekend" label="OfflineWeekend" />
									<option value="OfflineWeekDay" label="OfflineWeekDay" />
							</select></td>
						</tr>

						<tr>
							<td><h5>
									SOURCE<sup>*</sup>:
								</h5></td>
							<td><select name="source"
								class="form-control">
									<option value="Facebook" label="Facebook" />
									<option value="LinkedIn" label="LinkedIn" />
									<option value="Instagram" label="Instagram" />
									<option value="X-workZ Website" label="X-workZ Website" />
									<option value="Friends Refrence" label="Friends Refrence" />
							</select></td>
						</tr>

						<tr>
							<td><h5>
									REFRENCE<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control"
								name="refrenceName" placeholder="Enter refrence name" /></td>
						</tr>

						<tr>
							<td><h5>
									REFRENCE MOBILE NO<sup></sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="refrenceMobileNo"
								placeholder="Enter refrence mobile number" /></td>
						</tr>

						<tr>
							<td><h5>
									BRANCH<sup>*</sup>:
								</h5></td>
							<td><select name="branch" required
								class="form-control">
									<option value="Rajajinagar" label="Rajajinagar" />
									<option value="BTM" label="BTM" />
							</select></td>
						</tr>
						
						<tr>
							<td><h5>
									COUNSELORE<sup>*</sup>:
								</h5></td>
							<td><select name="counselor"
								class="form-control">
									<option value="Vinay" label="Vinay" />
									<option value="Akshar" label="Akshar"/>
									<option value="Omkar" label="Omkar"/>
									<option value="Receptionist" label="Receptionist"/>
							</select></td>
						</tr>						
						
						<tr>
							<td><h5>
								  STATUS<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" 
								name="status" value="Pending" readonly="readonly"/>							
							</td>
						</tr>
						
						<tr>
							<td><h5>
								 SUB STATUS<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" 
								name="subStatus" value="Need to Call" readonly="readonly"/>							
							</td>
						</tr>

						<tr>
							<td><h5>
									COMMENTS<sup>*</sup>:
								</h5></td>
							<td><textarea rows="10" cols="10"
									style="overflow: auto; resize: none" maxlength="300"
									class="form-control" name="comments"
									placeholder="Enter comments" required></textarea></td>
						</tr>

						<tr>
							<td colspan="2" align="center"><input type="submit"
								class="col-sm-5 btn btn-secondary" value="Submit"></td>
						</tr>

					</table>
				</form>

				<form action="uploadEnquiry.do" class="uploadEnquiry" method="post"
					enctype="multipart/form-data">
					<table class="col-md-6 table table-bordered table-dark" border="1"
						border-color="white" align="center" style="color: white">
						<tr>
							<td colspan="2" align="center"><h3>
									<b>Upload Enquires</b>
								</h3></td>
						</tr>
						<tr>
							<td>
								<h5>Upload Excel File:</h5>
							</td>
							<td><input type="file" class="form-control"
								name="uploadFile"></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input
								class="col-sm-5 btn btn-secondary" type="submit" value="Upload"></td>
						</tr>

					</table>
				</form>

				<form class="getlatestEnquiries" action="getLatestEnquiries.do"
					method="post">
					<table class="col-md-6 table table-bordered table-dark" border="1"
						border-color="white" align="center" style="color: white">
						<tr>
							<td colspan="2" align="center"><h3>
									<b>View Enquiries</b>
								</h3></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input
								class="col-sm-5 btn btn-secondary" type="submit"
								value="View Enquiries"></td>
						</tr>
					</table>
				</form>

				<form class="getCloudEnquiries" action="downloadEnquirySchedule.do" method="get">
					<table class="col-md-6 table table-bordered table-dark" border="1"
						border-color="white" align="center" style="color: white">
						<tr>
							<td colspan="2" align="center"><h3>
									<b>Sync Enquiries</b>
								</h3></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input
								class="col-sm-5 btn btn-secondary" type="submit" value="Sync"></td>
						</tr>
					</table>
				</form>
	        </div>
            </div>
         <div>
			<nav class="navbar navbar-expand-md navbar-dark bg-dark"></nav>
		</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/searchpanes/1.3.0/js/dataTables.searchPanes.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"></script>	
	
<script type="text/javascript">		
$(document).ready(function() {
	var table = $('#homeTable').DataTable({
		searching: true,
		responsive : true,
		searchPanes: {
        	columns: [0,1],
            viewTotal: true
        },
        paging:false,
        info:false,
        ordering: false,
        searching: false,
        dom: 'Plfrtip'
	});
});		
</script>
			
	<!-- Latest compiled JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
     <script type="text/javascript" src="./js/index.js"></script>
</body>
</html>