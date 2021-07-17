<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="contextPath" content="${pageContext.request.contextPath}" />

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css"
	rel="stylesheet">
<link
	href="https://cdn.datatables.net/1.10.23/css/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<!-- <link rel="stylesheet" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css" /> -->
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" /> -->

<link
	href="https://cdn.datatables.net/responsive/2.2.7/css/responsive.dataTables.min.css"
	rel="stylesheet">
<link
	href="https://cdn.datatables.net/rowgroup/1.1.3/css/rowGroup.bootstrap4.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="./css/index.css">
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
							<b>All Calls</b>
						</h3>
					</nav>
				</div>
				<div class="row"
					style="color: white; text-align: center; margin-top: 2%">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<h3 style="color: red;">
							<b id="responseMsg">${msg}${Successmsg}${Failmsg}</b>
						</h3>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>

			<div class="container container_border ">
				<div class="container" style="align-content: flex-start;">

					<div>
						<select id="statusFilter">
							<option value="All" label="All Calls" />
							<option value="Pending" label="Pending" />
							<option value="Intrested" label="Intrested" />
							<option value="Intrested for Next Batch"
								label="Intrested for Next Batch" />
							<option value="Intrested in future Batches"
								label="Intrested in future Batches" />
							<option value="Looking for Free Classes"
								label="Looking for Free Classes" />
							<option value="Looking for perticuler course"
								label="Looking for perticuler course" />
							<option value="Not Intrested" label="Not Intrested"/>
							<option value="Intrested only for weekend batch"
								label="Intrested only for weekend batch" />
							<option value="Intrested only for weekday batch"
								label="Intrested only for weekday batch" />
							<option value="Joined" label="Joined" />
							<option value="Not Joined" label="Not Joined" />
							<option value="Joined And Left" label="Joined And Left" />
						</select>
					</div>

					<table id="filterTable" class="table table-striped table-bordered"
						style="width: 100%">
						<thead style="color: black; background-color: brown;">
							<tr>
								<th>TIME STAMP</th>
								<th>FULL NAME</th>
								<th>MOBILE</th>
								<th>ALTERNATE MOBILE</th>
								<th>EMAIL ID</th>
								<th>COURSE</th>
								<th>STATUS</th>
								<th>INTRESTED DATE</th>
								<th>SUB STATUS</th>
								<th>COMMENTS</th>
								<th>SEND OTP</th>
								<th>SUBMIT OTP</th>

							</tr>
						</thead>

						<tbody style="color: white;">
							<c:forEach var="callList" items="${callList}">
								<tr>
									<td><fmt:formatDate type="both" dateStyle="short"
											timeStyle="short" value="${callList.dateTime}" /></td>
									<td>${callList.fullName}</td>
									<td>${callList.mobileNo}</td>
									<td>${callList.alternateMobileNo}</td>
									<td>${callList.emailId}</td>
									<td>${callList.course}</td>
									<td>${callList.status}</td>
									<td>${callList.intrestedDate}</td>
									<td>${callList.subStatus}</td>
									<td>${callList.comments}</td>
									<td><input id="currentEnquiry" type="hidden" type="number"
										value="${callList.enquiryId}" name="enquiryId">
										<button onclick="sendOTP(${callList.enquiryId})"
											class="btn btn-primary">Send OTP</button> <span
										class="sendingOTP" style="display: none;"><i
											class="fa fa-spinner fa-spin"></i> Sending...</span> <span
										class="sentOTP" style="display: none;"><i
											class="fa fa-check"></i> Sent</span> <!-- </form> --></td>
									<td><form method="post"
											action="validateUnsolvedCallOTP.do">
											<input type="hidden" type="number"
												value="${callList.enquiryId}" name="enquiryId"> <input
												id="partitioned" type="text" maxlength="6" name="otp"
												required="required" />
											<button class="btn btn-success">Submit</button>
										</form></td>
								</tr>
							</c:forEach>
						<tfoot style="color: black; background-color: brown;">
							<tr>
								<th>TIME STAMP</th>
								<th>FULL NAME</th>
								<th>MOBILE</th>
								<th>ALTERNATE MOBILE</th>
								<th>EMAIL ID</th>
								<th>COURSE</th>
								<th>STATUS</th>
								<th>INTRESTED DATE</th>
								<th>SUB STATUS</th>
								<th>COMMENTS</th>
								<th>SEND OTP</th>
								<th>SUBMIT OTP</th>
							</tr>
						</tfoot>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div>
			<nav class="navbar navbar-expand-md navbar-dark bg-dark"></nav>
		</div>

	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
		crossorigin="anonymous"></script>

	<script
		src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
	<script
		src="https://cdn.datatables.net/responsive/2.2.7/js/dataTables.responsive.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/rowgroup/1.1.3/js/dataTables.rowGroup.min.js"></script>

	<script>
    $("document").ready(function () {

      $("#filterTable").dataTable({
         searching: true,
         responsive : true,
         order: [[6, 'asc']],
         rowGroup: {
             dataSrc: 6,
          }
      });

      var table = $('#filterTable').DataTable();

      $("#filterTable_filter.dataTables_filter").append($("#statusFilter"));
      
      var statusIndex = 0;
      $("#filterTable th").each(function (i) {
        if ($($(this)).html() == "STATUS") {
          statusIndex = i; return false;
        }
      });

      $.fn.dataTable.ext.search.push(
        function (settings, data, dataIndex) {
          var selectedItem = $('#statusFilter').val()
          var category = data[statusIndex];
          if (selectedItem === "All" || category.includes(selectedItem)) {
            return true;
          }
            return false;
        }
      );

      $("#statusFilter").change(function (e) {
        table.draw();
      });

      table.draw();
    });
  </script>
  
  <script type="text/javascript" src="./js/index.js"></script>

</body>

</html>