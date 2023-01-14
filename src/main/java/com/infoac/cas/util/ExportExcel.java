package com.infoac.cas.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.infoac.cas.dto.AllSubjectDTO;
import com.infoac.cas.dto.CountPicDTO;
import com.infoac.cas.dto.PageDTO;
import com.infoac.cas.dto.SubjectDTO;



public class ExportExcel {

    SimpleDateFormat sdFormate = new SimpleDateFormat("yyyy.MM.dd");
	

	
    /**
	 * 统计图标excel导出
	 * @param allSubjectDTO
	 * @param fileName
	 * @param req
	 * @param res
	 */
	public void createFile(AllSubjectDTO allSubjectDTO,String fileName,HttpServletRequest req, HttpServletResponse res){
	   //创建一个webbook，对应一个Excel文件
       HSSFWorkbook wb = new HSSFWorkbook();
       //在webbook中添加一个sheet,对应Excel文件中的sheet
       HSSFSheet sheet = wb.createSheet(allSubjectDTO.getTitle());
       HSSFRow row = sheet.createRow((int)0);
       //创建单元格，并设置值表头  设置表头居中
       HSSFCellStyle style = wb.createCellStyle();
       style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
       HSSFFont font = wb.createFont();
       font.setFontHeightInPoints((short)12); //字体大小
       font.setFontName("宋体");
       style.setFont(font);
       List<PageDTO> pageList = allSubjectDTO.getPageList();
       int num = 0;
       if(pageList != null) {
    	   for(int i=0; i < pageList.size(); i++) {
    		   row = sheet.createRow(num);
    		   row.createCell(0).setCellValue("第"+ (i+1) +"页");
    		   num = num+1;
			   row = sheet.createRow((int)num);
    		   List<SubjectDTO> subjectList = pageList.get(i).getSubjectList();

    		   for(int k=0; k<subjectList.size(); k++) {
    			   num = num+1;
    			   row = sheet.createRow((int)num);
    			   //处理了标题中的带格式数据
    			   row.createCell(0).setCellValue(k+1+"."+SummaryUtil.spiltByLabel(subjectList.get(k).getTopic()));
    			   num = num+1;
    			   row = sheet.createRow((int)num);
    			   
    			   row.createCell(0).setCellValue("选项");
    			   row.createCell(1).setCellValue("小计");
    			   row.createCell(2).setCellValue("百分比%");
    			   List<CountPicDTO> countPicList = subjectList.get(k).getCountPicDTO();
    			   for(int j=0; j<countPicList.size(); j++) {
    				   num = num+1;
    				   row = sheet.createRow((int)num);
                       //处理了选项中的带格式数据
    				   row.createCell(0).setCellValue(SummaryUtil.spiltByLabel(countPicList.get(j).getOptions()));
    				   row.createCell(1).setCellValue(countPicList.get(j).getNum());
    				   row.createCell(2).setCellValue(countPicList.get(j).getPercentage());
    			   }
    			   num = num+1;
				   row = sheet.createRow((int)num);
    		   }

    		   num = num+1;
			   row = sheet.createRow((int)num);
           }  
       }
       this.writeExcel(res, wb, fileName);
	}
	
	public void writeExcel(HttpServletResponse response, HSSFWorkbook workbook,
			String fileName) {
		// 设置输出流
		try {
			response.setHeader("content-disposition", "filename=" + URLDecoder.decode(fileName + ".xls", "ISO8859-1")); // 设置文件名称
			response.setContentType("application/vnd.ms-excel; charset=GBK"); // 设置文件类型
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

		// 向客户端写出文件流
		OutputStream out = null;
		try {
			out = response.getOutputStream(); // 获取输出流
			workbook.write(out); // 将文档写入输出流
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭输出流
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
}
