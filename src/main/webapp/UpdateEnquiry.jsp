<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="contextPath" content="${pageContext.request.contextPath}"/>
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
						<b>X-WorkZ Enquiry And Call Management</b>
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
                 <form  method="POST" action="updateEnquiryById.do" style="margin-top: 8%;">
					<table class="col-md-6 table table-bordered table-dark" border="1"
						border-color="white" align="center" style="color: white">
						<tr>
							<td colspan="2" align="center"><h3>Update Enquiry</h3></td>
							<td><input name="enquiryId" type="hidden" value="${enquiry.enquiryId}"/></td>
						</tr>
                         
						<tr>
							<td><h5>
									Full Name<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" name="fullName"
								required placeholder="Enter full name" value="${enquiry.fullName}"/><b><span
									id="isName"></span></b></td>
						</tr>

						<tr>
							<td><h5>
									MOBILE NO<sup>*</sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="mobileNo" required placeholder="Enter valid mobile number" value="${enquiry.mobileNo}"/></td>
						</tr>

						<tr>
							<td><h5>
									ALTERNATE MOBILE<sup></sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="alternateMobileNo"
								placeholder="Enter alternate mobile number" value="${enquiry.alternateMobileNo}"/></td>
						</tr>

						<tr>
							<td><h5>
									EMAIL ID<sup>*</sup>:
								</h5></td>
							<td><input type="email" class="form-control" name="emailId"
								required placeholder="Enter valid email id"
								onblur="checkEmailExist()" value="${enquiry.emailId}"/><b><span id="isE"></span></b></td>
						</tr>
						
						<tr>
							<td><h5>
									EDUCATION<sup>*</sup>:
								</h5></td>
							<td>
							<select class="form-control" id="education" name="education">
							 <option value="${enquiry.education}" selected="selected">${enquiry.education}</option>
							 <option value="BE">BE</option>   
							 <option value="B.Tech">B.Tech</option>
							 <option value="M.Tech">M.Tech</option>
							 <option value="BCA">BCA</option>
							 <option value="MCA">MCA</option>
							 <option value="BSC">BSC</option>
							 <option value="MSC">MSC</option>   
							</select>
							</td>
						</tr>
						
						<tr>
							<td><h5>
									STREAM<sup></sup>:
								</h5></td>
							<td>
							<select class="form-control" id="Stream" name="stream">
							 <option value="${enquiry.stream}" selected="selected">${enquiry.stream}</option>
							 <option value="CSE">CSE</option>   
							 <option value="ECE">ECE</option>
							 <option value="CVE">CVE</option>
							 <option value="EEE">EEE</option>
							 <option value="ME">ME</option>
							 <option value="ISE">ISE</option>
							</select>
							</td>
						</tr>
						
						<tr>
							<td><h5>
									YEAR_OF_PASS<sup>*</sup>:
								</h5></td>
							<td>
							<select class="form-control" id="yop" name="yop" required="required">
							 <option value="${enquiry.yop}" selected="selected">${enquiry.yop}</option>
							 <option value="2016">2016</option>   
							 <option value="2017">2017</option>
							 <option value="2018">2018</option>
							 <option value="2019">2019</option>
							 <option value="2020">2020</option>
							 <option value="2021">2021</option>
							 <option value="2022">2022</option>
							</select>
							</td>
						</tr>

						<tr>
							<td><h5>
									COURSE<sup>*</sup>:
								</h5></td>
							<td><select class="form-control" id="courses" name="course"></select>
							</td>
						</tr>

						<tr>
							<td><h5>
									BATCH TYPE<sup>*</sup>:
								</h5></td>
							<td><select name="batchType"
								class="form-control">
									<option value="${enquiry.batchType}" selected="selected">${enquiry.batchType}</option>
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
									<option value="${enquiry.source}" selected="selected">${enquiry.source}</option>
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
								name="refrenceName" placeholder="Enter refrence name" value="${enquiry.refrenceName}"/></td>
						</tr>

						<tr>
							<td><h5>
									REFRENCE MOBILE NO<sup></sup>:
								</h5></td>
							<td><input type="number" class="form-control"
								name="refrenceMobileNo"
								placeholder="Enter refrence mobile number" value="${enquiry.refrenceMobileNo}"/></td>
						</tr>

						<tr>
							<td><h5>
									BRANCH TYPE<sup>*</sup>:
								</h5></td>
							<td><select name="branch" required class="custom-select custom-select-lg sm-3">
									<option value="${enquiry.branch}" selected="selected">${enquiry.branch}</option>
									<option value="Rajajinagar" label="Rajajinagar" />
									<option value="BTM" label="BTM" />
							</select></td>
						</tr>
						
						<tr>
							<td><h5>
								 SUB STATUS<sup>*</sup>:
								</h5></td>
							<td>						
							  <select name="subStatus" class="custom-select custom-select-lg sm-3" required>
								     <option value="Need to Call" label="Need to Call" selected/>
								     <option value="RNR" label="RNR"/>
								     <option value="Multiple calls RNR" label="Multiple calls RNR" />
								     <option value="Line Busy" label="Line Busy"/>
								     <option value="Not Reachable" label="Not Reachable"/>
								     <option value="Switch Off" label="Switch Off"/>
							  </select> 
							</td>
						</tr>
						
						<tr>
							<td><input name="status" type="hidden" value="${enquiry.status}"/></td>
						</tr>

						<tr>
							<td><h5>
									COUNSELLOR<sup>*</sup>:
								</h5></td>
							<td><input type="text" class="form-control" name="counsellor"
								required placeholder="Enter refrence name" value="${enquiry.counsellor}" readonly="readonly"/></td>
						</tr>

						<tr>
							<td><h5>
									COMMENTS<sup>*</sup>:
								</h5></td>
							<td><textarea  rows="10" cols="10"
									style="overflow: auto; resize: none" maxlength="300"
									class="form-control" name="comments"
									placeholder="Enter comments" required readonly="readonly">${enquiry.comments}</textarea></td>
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