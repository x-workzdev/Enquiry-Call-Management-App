package com.xworkz.enquiryAndCallManagement.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.xworkz.enquiryAndCallManagement.dto.EnquiryCallDTO;

@Component
public class ExcelHelper {

	static Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

	public ExcelHelper() {
		logger.info("{} is created", this.getClass().getSimpleName());
	}

	@SuppressWarnings("deprecation")
	public List<String> getContactListFromInputStream(InputStream inputStream) {
		List<String> mobileNumList = new ArrayList<String>();
		try {
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet excelSheet = workbook.getSheetAt(0);
			logger.info("Last Row Number Is: " + excelSheet.getLastRowNum());
			logger.info("Excel file Is opened");
			// logger.info("Size of Excel is {}", excelSheet.getLastRowNum());
			Row row;
			for (int i = 0; i <= excelSheet.getLastRowNum(); i++) {
				row = (Row) excelSheet.getRow(i);
				logger.debug("Data type is {}", row.getCell(0).getCellType());
				if (row.getCell(0).getCellType() == 0) {
					Long long1 = (long) row.getCell(0).getNumericCellValue();
					logger.info("Data: {} is read and stored.", long1);
					mobileNumList.add(String.valueOf(long1));
				}
			}
		} catch (Exception e) {
			logger.error("error is {} and message is {}", e, e.getMessage());
		}
		return mobileNumList;
	}

	public List<EnquiryCallDTO> getEnquiryListFromInputStream(InputStream inputStream) {
		List<EnquiryCallDTO> enquiryList = new ArrayList<EnquiryCallDTO>();
		try {
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet excelSheet = workbook.getSheetAt(0);
			logger.info("Last Row Number Is: " + excelSheet.getLastRowNum());
			logger.info("Excel file Is opened");
			Date today = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String todayDate = dateFormat.format(today);
			if (Objects.nonNull(excelSheet)) {
				DataFormatter dataFormatter = new DataFormatter();
				for (Row row : excelSheet) {
					EnquiryCallDTO enquiryDTO = new EnquiryCallDTO();
					String date = dataFormatter.formatCellValue(
							row.getCell(EnquiryExcelFileColumn.TIMESTAMP, MissingCellPolicy.RETURN_BLANK_AS_NULL));
					Date eDate = null;
					String enquiryDate = null;
					try {
						eDate = dateTimeFormat.parse(date);
						enquiryDate=dateFormat.format(eDate);
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}

					if (Objects.nonNull(enquiryDate)) { 
						if (enquiryDate.equals(todayDate)) {
							enquiryDTO.setFullName(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.NAME, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setMobileNo(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.MOBILE, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setAlternateMobileNo(dataFormatter.formatCellValue(row.getCell(
									EnquiryExcelFileColumn.ALTERNATE_MOBILE, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setEmailId(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.EMAIL_ID, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setCourse(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.COURSE, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setBatchType(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.BATCH, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setSource(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.SOURCE, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setRefrenceName(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.REFRENCE, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setRefrenceMobileNo(dataFormatter.formatCellValue(row.getCell(
									EnquiryExcelFileColumn.REFRENCE_MOBILE, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setBranch(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.BRANCH, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setComments(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.COMMENTS, MissingCellPolicy.RETURN_BLANK_AS_NULL)));
							enquiryDTO.setCounsellor(dataFormatter.formatCellValue(
									row.getCell(EnquiryExcelFileColumn.COUNSELOR, MissingCellPolicy.RETURN_BLANK_AS_NULL)));

							if (Objects.nonNull(enquiryDTO.getFullName()) && Objects.nonNull(enquiryDTO.getEmailId())
									&& Objects.nonNull(enquiryDTO.getMobileNo())) {
								enquiryList.add(enquiryDTO);
							} else {
								logger.info("Fields Has empty values in Excel at Row:" + row.getRowNum());
							}
						}
					}
				}				
			} else {
				logger.error("ExcelSheet is Empty!");
			}
		} catch (Exception e) {
			logger.error("error is {} and message is {}", e, e.getMessage());
		}
		return enquiryList;
	}

	
	public ByteArrayInputStream readExcelFile(URI url){
		ByteArrayInputStream inputStream = null;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
		if (Objects.nonNull(responseEntity)) {
			byte[] result = responseEntity.getBody();

			logger.debug("Staring..........");
			inputStream = new ByteArrayInputStream(result);
		}
		return inputStream;
	}
	
	public List<String> getColumnHeading(InputStream inputStream) {
		List<String> columnHeadingList = new ArrayList<String>();
		try {
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet excelSheet = workbook.getSheetAt(0);
			logger.info("Excel file Is opened");
			if (Objects.nonNull(excelSheet)) {
				DataFormatter dataFormatter = new DataFormatter();
				Row row = excelSheet.getRow(0);
				int columns = row.getLastCellNum();
				for (int i = 0; i < columns; i++) {
					columnHeadingList.add(dataFormatter.formatCellValue(row.getCell(i,MissingCellPolicy.RETURN_BLANK_AS_NULL)));
				}
			} else {
				logger.error("ExcelSheet is Empty!");
			}
			
		}catch (Exception e) {
			logger.error("error is {} and message is {}", e, e.getMessage());
		}
		return columnHeadingList;
	}
}
