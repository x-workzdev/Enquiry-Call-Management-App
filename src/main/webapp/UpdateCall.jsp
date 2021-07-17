<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="./img/Logo.png">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
	
<link rel="stylesheet" type="text/css" href="./css/index.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

	<div class="backgroundImage">
		<div class="header">
			<a href="" class="logo"><img src="./img/Logo.png" height="90px"></a>
			<div class="header-right">
				<h1>Om's Development Center</h1>
			</div>
			<div class="container">
				<div class="row justify-content-end">
					<div class="col-3 "
						style="align-content: flex-basis;  margin-bottom: 0.5%;">
						<button onclick="location.href='Home.jsp'" type="button" class="btn btn-success btn-block">
							<i class="fa fa-home" aria-hidden="true"></i> Home</button>
					</div>
					<div class="col-3 " style="align-items: flex-end; ">
						<button onclick="location.href='EnquiryLogin.jsp'" type="button" class="btn btn-danger btn-block">
							<i class="fa fa-sign-out" aria-hidden="true"></i> Log Out</button>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="container" align="center">
				<div>
					<nav class="navbar navbar navbar-dark bg-dark">
					<h3
						style="color: white; font-weight: 900; width: 100%; align-items: center;">
						<b>Update Call Enquiry</b>
					</h3>
					</nav>
				</div>
				<div class="row"
					style="color: white; text-align: center; margin-top: 2%">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<h3 style="color: red;">
							<b>${msg}</b>
						</h3>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
			<div class="container container_border ">
			<div></div>
                 <form  method="POST" action="updateNewCall.do" style="margin-top: 8%;">
					<table class="col-md-6 table table-bordered table-dark" border="1"
						border-color="white" align="center" style="color: white">
						<tr>
							<td colspan="2" align="center"><h3>Update Call callEnquiry</h3></td>
							<td><input name="enquiryId" type="hidden" value="${callEnquiry.enquiryId}"/></td>
						</tr>
                         
						<tr>
							<td><h5>
									Full Name<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" name="fullName"
								required placeholder="Enter full name" value="${callEnquiry.fullName}" readonly="readonly"/><b><span
									id="isName"></span></b></td>
						</tr>

						<tr>
							<td><h5>
									MOBILE NO<sup>*</sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="mobileNo" required placeholder="Enter valid mobile number" value="${callEnquiry.mobileNo}" readonly="readonly"/></td>
						</tr>

						<tr>
							<td><h5>
									ALTERNATE MOBILE<sup></sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="alternateMobileNo"
								placeholder="Enter alternate mobile number" value="${callEnquiry.alternateMobileNo}" readonly="readonly"/></td>
						</tr>

						<tr>
							<td><h5>
									EMAIL ID<sup>*</sup>:
								</h5></td>
							<td><input type="email" class="form-control" name="emailId"
								required placeholder="Enter valid email id"
								onblur="checkEmailExist()" value="${callEnquiry.emailId}" readonly="readonly"/><b><span id="isE"></span></b></td>
						</tr>

						<tr>
							<td><h5>
									COURSE<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" name="course"
								required placeholder="Enter course name" value="${callEnquiry.course}"/></td>
						</tr>

						<tr>
							<td><h5>
									BATCH TYPE<sup>*</sup>:
								</h5></td>
							<td><select name="batchType"
								class="custom-select custom-select-lg sm-3" value="${callEnquiry.batchType}">
									<option value="Weekend" label="Weekend" />
									<option value="WeekDay" label="WeekDay" />
							</select></td>
						</tr>

						<tr>
							<td><h5>
									SOURCE<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" required
								name="source" placeholder="Enter source of information" value="${callEnquiry.source}"/></td>
						</tr>

						<tr>
							<td><h5>
									REFRENCE<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control"
								name="refrenceName" placeholder="Enter refrence name" value="${callEnquiry.refrenceName}"/></td>
						</tr>

						<tr>
							<td><h5>
									REFRENCE MOBILE NO<sup></sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="refrenceMobileNo"
								placeholder="Enter refrence mobile number" value="${callEnquiry.refrenceMobileNo}"/></td>
						</tr>

						<tr>
							<td><h5>
									BRANCH TYPE<sup>*</sup>:
								</h5></td>
							<td><select name="branch" required
								class="custom-select custom-select-lg sm-3" value="${callEnquiry.branch}">
									<option value="Rajajinagar" label="Rajajinagar" />
									<option value="BTM" label="BTM" />
							</select></td>
						</tr>
						
						<tr>
							<td><h5>
								  STATUS<sup>*</sup>:
								</h5></td>
							<td>						
							  <select name="status" class="custom-select custom-select-lg sm-3" value="${callEnquiry.status}" required>
								    <option value="Pending" label="Pending" />
									<option value="Intrested" label="Intrested" />
									<option value="Intrested for Next Batch" label="Intrested for Next Batch" />
									<option value="Intrested in future Batches" label="Intrested in future Batches"/>
									<option value="Looking for Free Classes" label="Looking for Free Classes"/>
									<option value="Looking for perticuler course" label="Looking for perticuler course"/>
									<option value="Intrested only for weekend batch" label="Intrested only for weekend batch" />
									<option value="Intrested only for weekday batch" label="Intrested only for weekday batch" />
									<option value="Not Intrested" label="Not Intrested"/>
									<option value="Joined" label="Joined"/>
									<option value="Not Joined" label="Not Joined"/>
									<option value="Joined And Left" label="Joined And Left"/>
							  </select> 
							</td>
						</tr>
						
						<tr>
							<td><h5>
									INTRESTED DATE<sup></sup>:
								</h5></td>
							<td><input type="date" class="form-control"
								name="intrestedDate"
								placeholder="Enter refrence mobile number" value="${callEnquiry.intrestedDate}" required/></td>
						</tr>
						
						<tr>
							<td><h5>
								 SUB STATUS<sup>*</sup>:
								</h5></td>
							<td>						
							  <select name="subStatus" class="custom-select custom-select-lg sm-3">
								     <option value="Completed Call" label="Call Completed" selected/>
							  </select> 
							</td>
						</tr>

						<tr>
							<td><h5>
									COUNSELOR<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" name="counselor"
								required placeholder="Enter refrence name" value="${callEnquiry.counselor}"/></td>
						</tr>

						<tr>
							<td><h5>
									COMMENTS<sup></sup>:
								</h5></td>
							<td><textarea  rows="10" cols="10"
									style="overflow: auto; resize: none" maxlength="300"
									class="form-control" name="comments"
									placeholder="Enter comments" required>${callEnquiry.comments}</textarea></td>
						</tr>

						<tr>
							<td colspan="2" align="center"><input type="submit"
								class="col-sm-5 btn btn-secondary" value="Update"></td>
						</tr>

					</table>
				</form>
			</div>
		</div>
		<div>
			<nav class="navbar navbar-expand-md navbar-dark bg-dark"></nav>
		</div>
	</div>
	<!-- Latest compiled JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="./js/index.js"></script>
</body>
</html>