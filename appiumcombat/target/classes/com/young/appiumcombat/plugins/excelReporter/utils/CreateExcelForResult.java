package com.young.appiumcombat.plugins.excelReporter.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;


/**
 * @author Young
 * @decription 创建测试结果excel文件
 * */
public class CreateExcelForResult{


	public static Logger L = Logger.getLogger(CreateExcelForResult.class);
	/**excel文件声明*/
	static File fileForExcel = null;
	/**用于写入excel的文件流*/
	static FileOutputStream fos = null;
	/**excel工作�?/
	static XSSFWorkbook workbook = null;
	/**log目录,生成的log会存储到此目录中*/
	static File logDir = new File("./result/log/");
	
	/**
	 * @author wangyang
	 * @description 生成excel自动化测试报告文�?
	 * */
	public static void createExcelReport(List<XmlSuite> xml, List<ISuite> suites,String dir) {
		//定义excel报告的存储目�?
		String excelReportDirStr = dir+File.separator+"excelReports";
		File excelReportDir = new File(excelReportDirStr);
		//如果报告文件夹不存在就存在一�?
		if(!excelReportDir.exists()){
		excelReportDir.mkdir();
		}
		//excel文件名中的日期格式器
		SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");  
		//excel汇�?sheet中的运行时间格式�?
		SimpleDateFormat  runTimeFormat = new SimpleDateFormat("yyyy年MM月dd�?);  
		//汇�?sheet中的�?��时间和结束时间格式器
		SimpleDateFormat startAndEndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		dir = File.separator+excelReportDirStr+File.separator+"TestReport_"+fileNameFormat.format(new Date())+"-"+suites.get(0).getName()+".xlsx";
	    fileForExcel = new File(dir);
	  
	    try{
	    // 创建新的Excel 工作�?
	    workbook = new XSSFWorkbook();
	    //设置公式自动计算，否则excel公式无法自动计算和生�?
	    workbook.setForceFormulaRecalculation(true);
        XSSFSheet sheet_All = workbook.createSheet("汇�?");
        //合并单元�?
        sheet_All.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        XSSFRow row1 = sheet_All.createRow(0);
        XSSFCell cellA1G1 = row1.createCell(0);
        cellA1G1.setCellValue(new XSSFRichTextString("自动化测试结�?));
        cellA1G1.setCellStyle(setCellStyle4(workbook));
        setRangeCellBorder(new CellRangeAddress(0, 0, 0, 5), sheet_All, workbook);
        //运行日期 cell文本
        XSSFRow row2 = sheet_All.createRow(1);
        XSSFCell cellA2 = row2.createCell(0);
        cellA2.setCellValue(new XSSFRichTextString("运行日期"));
        cellA2.setCellStyle(setCellStyle0(workbook));
        //运行日期 cell 内容
        XSSFCell cellB2 = row2.createCell(1);
        cellB2.setCellValue(runTimeFormat.format(new Date()));
        cellB2.setCellStyle(setContentCellBorder(workbook));
        //测试项目名称 cell文本
        XSSFCell cellC2 = row2.createCell(2);
        cellC2.setCellValue(new XSSFRichTextString("项目名称"));
        cellC2.setCellStyle(setCellStyle0(workbook));
        //合并测试项目名称的单元格
        sheet_All.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));
        //测试项目名称的具体内�?
        XSSFCell cellD2F2 = row2.createCell(3);
        setRangeCellBorder(new CellRangeAddress(1, 1, 3, 5), sheet_All, workbook);
        cellD2F2.setCellValue(suites.get(0).getName());
        //运行�?��时间
        Date startTime=null;
        //运行结束时间
        Date endTime = null;
        
        //合并单元�?- 测试功能列表
        sheet_All.addMergedRegion(new CellRangeAddress(4, 4, 0, 5)); 
        //测试功能列表 文本
        XSSFRow row5 = sheet_All.createRow(4);
        XSSFCell cellA5F5 = row5.createCell(0);
        cellA5F5.setCellValue(new XSSFRichTextString("测试功能列表�?));
        cellA5F5.setCellStyle(setCellStyle1(workbook));
        setRangeCellBorder(new CellRangeAddress(4, 4, 0, 5), sheet_All, workbook);
        //第六行内�?
        XSSFRow row6 = sheet_All.createRow(5);
        for (int i = 0; i < 6; i++) {
        	XSSFCell cell = row6.createCell(i);
        	String str[] ={"序号","模块名称","用例总数","成功条数","失败条数","通过�?} ;
        	cell.setCellValue(new XSSFRichTextString(str[i]));
        	cell.setCellStyle(setCellStyle2(workbook));
		}
        
       //获取模块名字
    	for (ISuite suite : suites){
    		//获取当前suite的运行结�?
    		Map<String, ISuiteResult> tests = suite.getResults();
    		//多少个tests
    		int testNum = tests.values().size();
    		//用例总数数组
    		int totalCases[] = new int[testNum];
    		//成功条数数组
    		int successCases[] = new int[testNum];
    		//失败条数数组
    		int failCases[] = new int[testNum];
    		//跳过条数数组
    		int skipCases[] = new int[testNum];
    		//定义计数器，来循环存用例总数、成功条数�?失败条数、跳过条数等
    		int temp = 0;
    		for (ISuiteResult r : tests.values()) {//这里循环的是每个tests
    		
				//ITestContext获取各个test�?内容
    			ITestContext overview = r.getTestContext();
    				//把各个test的�?数，成功的�?失败的�?跳过的存入数�?
    				totalCases[temp] = overview.getAllTestMethods().length;
    				successCases[temp] = overview.getPassedTests().size();
    				failCases[temp] = overview.getFailedTests().size();
    				skipCases[temp] = overview.getSkippedTests().size();
    				if(temp==0){
    					startTime = overview.getStartDate();
    				}
    				if(temp==(testNum-1)){
    				endTime= overview.getEndDate();
    				}
    				
    				temp++;
		
				//生成模块(sheet)名字
				 workbook.createSheet(overview.getName());
    			/**以下初始化各个模块详情的代码*/
				 //获得新建的sheet对象
				 XSSFSheet funtions = workbook.getSheet(overview.getName());
				 //新增的sheet都创建第�?��
				 XSSFRow rows1 = funtions.createRow(0);
				 //合并单元�?
				 funtions.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
				 //创建�?��cell
				 XSSFCell cells = rows1.createCell(0);
				 //给这个cell赋�?
				 cells.setCellValue(new XSSFRichTextString(overview.getName()+"模块测试详情"));
				 cells.setCellStyle(setCellStyle4(workbook));
			     setRangeCellBorder(new CellRangeAddress(0, 0, 0, 4), workbook.getSheet(overview.getName()), workbook);
				 //创建第二行并新建5个cell存储"序号"�?用例名称"�?测试结果"�?错误日志"�?完整日志"�?截图"
				 XSSFRow rows2 = funtions.createRow(1);
				 String labelsForFunctions[] ={"序号","用例名称","测试结果","完整日志","截图"} ;
				 for (int i = 0; i < labelsForFunctions.length; i++) {
					 //设置5个label
					 rows2.createCell(i).setCellValue(new XSSFRichTextString(labelsForFunctions[i])); 
					 rows2.getCell(i).setCellStyle(setCellStyle3(workbook));
				} 
					/**以上是初始化各个模块详情的代�?/
    		}
    		


    		
    		
    		for (int i = 0; i < testNum; i++) {
    			XSSFRow row = sheet_All.createRow(6+i); //创建�?
    			for (int j = 0; j < 6; j++) {
    				XSSFCell cell = row.createCell(j);
    				if(j==0){
    					//设置序号
    					cell.setCellValue(i+1);
    					cell.setCellStyle(setContentCellBorder(workbook));
    				}
    				if(j==1){
    					//设置模块名称
    					cell.setCellValue(new XSSFRichTextString(workbook.getSheetName(i+1)));
    					cell.setCellStyle(setClickableLinkToBlueColor(workbook));
    					//设置跳转链接
    					cell.setCellFormula("HYPERLINK(\"" + "#"+workbook.getSheetName(i+1)+"!A1"+ "\",\"" +workbook.getSheetName(i+1)+ "\")");
    					
    				}
    				if(j==2){
    				//用例总数	
        	    			cell.setCellValue(totalCases[i]);
        	    			cell.setCellStyle(setContentCellBorder(workbook));
    				}		
        				
    				if(j==3){
    					//成功条数
 					cell.setCellValue(successCases[i]);
 					cell.setCellStyle(setContentCellBorder(workbook));
    				}
    				if(j==4){
					//失败条数
 					cell.setCellValue(failCases[i]);		
 					cell.setCellStyle(setContentCellBorder(workbook));
    				}
 				
    				if(j==5){
					//通过�?			
 					cell.setCellValue(getPercent((float)successCases[i], (float)totalCases[i]));
 					cell.setCellStyle(setContentCellBorder(workbook));
    				}		  				
					}		
				}	
    		}
        
        
        
        //生成汇�?sheet的第3�?行的内容，从i=2�?��，表示从第三行开始创建i=2表示第三行，i=3表示�?�?
        for (int i = 2; i < 4; i++) {
        	//创建�?
        	XSSFRow row = sheet_All.createRow(i);
        	for (int j = 0; j < 6; j++) {
        		//根据行创建单元格
        		XSSFCell cell = row.createCell(j);
        		String str1[]={"�?��时间",startAndEndTimeFormat.format(startTime),"结束时间",startAndEndTimeFormat.format(endTime),"耗时",""};
        		String str2[]={"用例总数","","通过用例总数","","失败用例总数",""};
        		if(i==2){
        			//如果是第三行的时候；cell设置单元格�?，单元格值从str1中获�?
        			cell.setCellValue(str1[j]);
        			if(j==0||j==2||j==4){
        			//如果j=0;2;4的时候，设置标题的样�?
        				cell.setCellStyle(setCellStyle0(workbook));
        			}
        			if(j==5){
        				//如果j=5的时候设置D3和B3的时间相减获得�?�?
        				cell.setCellFormula("D3-B3");
        	            XSSFCellStyle cellStyle = workbook.createCellStyle();
        	            XSSFDataFormat format= workbook.createDataFormat();
        	            cellStyle.setDataFormat(format.getFormat("HH:mm:ss"));
        	            setCellBorder(workbook, cellStyle);
        	            cell.setCellStyle(cellStyle);
        				
        			}
        		}else if(i==3){
        			//i=3的时候表示在处第4行的内容
        			cell.setCellValue(str2[j]);
        			if(j==0||j==2||j==4){
        				cell.setCellStyle(setCellStyle0(workbook));
        			}
        			if(j==1){
        				//用例总数
        				cell.setCellFormula("SUM(C7:C1000)");
        	            XSSFCellStyle cellStyle = workbook.createCellStyle();
        				setCellBorder(workbook, cellStyle);
        				cell.setCellStyle(cellStyle);
        			}
        			if(j==3){
        				//通过用例总数
        				cell.setCellFormula("SUM(D7:D1000)");	
         	            XSSFCellStyle cellStyle = workbook.createCellStyle();
                		setCellBorder(workbook, cellStyle);
                		cell.setCellStyle(cellStyle);
        			}
        			if(j==5){
        				//失败用例总数
        				cell.setCellFormula("SUM(E7:E1000)");
         	            XSSFCellStyle cellStyle = workbook.createCellStyle();
                		setCellBorder(workbook, cellStyle);
                		cell.setCellStyle(cellStyle);
        			}
        		}
     
			}
        	
		}
        
        

        
        //以下处理每个模块详情
    	for (ISuite suite : suites){
    		//获取当前suite的运行结�?
    		Map<String, ISuiteResult> tests = suite.getResults();
    		//多少个tests
    		int testNum = tests.values().size();
    		//用例总数数组
    		int totalCases[] = new int[testNum];
  
    		//定义计数器，来循环存用例总数�?
    		int temp = 0;
    		for (ISuiteResult r : tests.values()) {//这里循环的是每个tests
				//ITestContext获取各个test�?内容
    			ITestContext overview = r.getTestContext();
    				//把各个test的�?数存入数�?
    				totalCases[temp] = overview.getAllTestMethods().length;
    				temp++;
    		}
    		
    		
    		for (int k = 0; k < testNum; k++) {//test的个数作为外层循�?
    			
    			for (int k2 = 0; k2 < totalCases[k]; k2++) {//以每个test下有多少个用例来做循�?
    				//�?��创建�?从第三行�?��，所以是k2+2
    				XSSFRow functionRow = workbook.getSheetAt(k+1).createRow(k2+2) ;			
    				for (int l = 0; l < 5; l++) {//以每个单元格来作为循环，5个标�?
    					XSSFCell functionCell = functionRow.createCell(l);
    					if(l==0){
    					//生成序号
    						functionCell.setCellValue(k2+1);
    						functionCell.setCellStyle(setContentCellBorder(workbook));
    					}
					}			
				}
				
			}
    		
    		int count = 0;
			//处理 “用例名字�?、�?完整日志”和“截图�?
	   		for (ISuiteResult r : tests.values()) {//多少个模块第�?��循环
    			ITestContext overview = r.getTestContext();
    			String function = overview.getName();
    			int failTestNum = overview.getFailedTests().size();
    			int skipTestNum = overview.getSkippedTests().size();
    			int passTestNum = overview.getPassedTests().size();
    			
    			
    			if(failTestNum!=0){//处理失败�?
    				XSSFCellStyle xcs = workbook.createCellStyle();
				 for (ITestNGMethod im : overview.getFailedTests().getAllMethods()) {	//模块下有多少个用例循�?
 					String caseName = im.getTestClass().getRealClass().getSimpleName();
 					String functionPack = caseName.substring(0, caseName.indexOf("P")).toLowerCase();
 					//设置用例名字
					workbook.getSheet(function).getRow(count+2).getCell(1).setCellValue(new XSSFRichTextString(caseName));
					//设置用例运行的状�?
					workbook.getSheet(function).getRow(count+2).getCell(2).setCellValue(new XSSFRichTextString("Failed"));
					//设置log，这是一个超链接
					workbook.getSheet(function).getRow(count+2).getCell(3).setCellFormula
					("HYPERLINK(\"" +new File("result/log/"+functionPack+"/"+caseName+".log").getAbsolutePath()+ "\",\"" +caseName+ ".log\")");
					xcs.setFillForegroundColor((short) 10);// 设置背景�?-红色
					xcs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
					//设置背景色为红色，将此style应用成功
					setCellBorder(workbook, xcs);
					//用例名称、测试结果�?截图和日志分别设置样�?
			        workbook.getSheet(function).getRow(count+2).getCell(2).setCellStyle(xcs);
			        workbook.getSheet(function).getRow(count+2).getCell(1).setCellStyle(setContentCellBorder(workbook));
			        workbook.getSheet(function).getRow(count+2).getCell(3).setCellStyle(setClickableLinkToBlueColor(workbook));
			        //添加截图
			        File png = new File("result/screenshot/"+caseName+".png");
			        if(png.exists()){
			        	//设置截图图片，这是超链接
						workbook.getSheet(function).getRow(count+2).getCell(4).setCellFormula
						("HYPERLINK(\"" +new File("result/screenshot/"+caseName+".png").getAbsolutePath()+ "\",\"" +caseName+ ".png\")");
			        }else{
			        	workbook.getSheet(function).getRow(count+2).getCell(4).setCellValue(new XSSFRichTextString("此用例没有截�?));
			        }
			        workbook.getSheet(function).getRow(count+2).getCell(4).setCellStyle(setClickableLinkToBlueColor(workbook));
		
			      
					count++;
				}
				
    			}
    			count=0;
    			
    			if(skipTestNum!=0){//处理跳过�?
    				XSSFCellStyle xcs = workbook.createCellStyle();
   				 for (ITestNGMethod im : overview.getSkippedTests().getAllMethods()) {	//模块下有多少个用例循�?
   					String caseName = im.getTestClass().getRealClass().getSimpleName();
   					String functionPack = caseName.substring(0, caseName.indexOf("P")).toLowerCase();
 					workbook.getSheet(function).getRow(count+2+failTestNum).getCell(1).setCellValue(new XSSFRichTextString(caseName));
 					workbook.getSheet(function).getRow(count+2+failTestNum).getCell(2).setCellValue(new XSSFRichTextString("Skipped"));
					workbook.getSheet(function).getRow(count+2+failTestNum).getCell(3).setCellFormula
					("HYPERLINK(\"" +new File("result/log/"+functionPack+"/"+caseName+".log").getAbsolutePath()+ "\",\"" +caseName+ ".log\")");
					xcs.setFillForegroundColor((short) 13);// 设置背景�?-黄色
					setCellBorder(workbook, xcs);
			        workbook.getSheet(function).getRow(count+2+failTestNum).getCell(1).setCellStyle(setContentCellBorder(workbook));
			        workbook.getSheet(function).getRow(count+2+failTestNum).getCell(3).setCellStyle(setClickableLinkToBlueColor(workbook));
					xcs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			        workbook.getSheet(function).getRow(count+2+failTestNum).getCell(2).setCellStyle(xcs);
			        File png = new File("result/screenshot/"+caseName+".png");
			        if(png.exists()){
					workbook.getSheet(function).getRow(count+2+failTestNum).getCell(4).setCellFormula
					("HYPERLINK(\"" +new File("result/screenshot/"+caseName+".png").getAbsolutePath()+ "\",\"" +caseName+ ".png\")");
			        }else{
			        	workbook.getSheet(function).getRow(count+2+failTestNum).getCell(4).setCellValue(new XSSFRichTextString("此用例没有截�?));
			        }
			        workbook.getSheet(function).getRow(count+2+failTestNum).getCell(4).setCellStyle(setClickableLinkToBlueColor(workbook));
					
 					count++;
 				}
    			}
    			count=0;
    			
    			if(passTestNum!=0){//处理成功�?
    				XSSFCellStyle xcs = workbook.createCellStyle();
      				 for (ITestNGMethod im : overview.getPassedTests().getAllMethods()) {	//模块下有多少个用例循�?
      					String caseName = im.getTestClass().getRealClass().getSimpleName();
      					String functionPack = caseName.substring(0, caseName.indexOf("P")).toLowerCase();
      					workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum).getCell(1).setCellValue(new XSSFRichTextString(caseName));
      					workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum).getCell(2).setCellValue(new XSSFRichTextString("Passed"));
    					workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum).getCell(3).setCellFormula
    					("HYPERLINK(\"" +new File("result/log/"+functionPack+"/"+caseName+".log").getAbsolutePath()+ "\",\"" +caseName+ ".log\")");
    					xcs.setFillForegroundColor((short) 57);// 设置背景�?-绿色
    					setCellBorder(workbook, xcs);
    			        workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum).getCell(1).setCellStyle(setContentCellBorder(workbook));
    			        workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum).getCell(3).setCellStyle(setClickableLinkToBlueColor(workbook));
    					xcs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    			        workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum+skipTestNum).getCell(2).setCellStyle(xcs);
    			        workbook.getSheet(function).getRow(count+2+failTestNum+skipTestNum).getCell(4).setCellStyle(setClickableLinkToBlueColor(workbook));
    	    			count++;
    				}
       			}
    			count=0;

    		
    		}
    		
    		

    		}
        
        
        
        
    	  //以上处理每个模块详情
        
        
        
        
        
         //处理列宽
    	int sheetNum = workbook.getNumberOfSheets();
    	for (int i = 0; i < sheetNum; i++) {
    		if(i==0){
    			//i=0的时候表�?第一个sheet就是汇�?setColumnWidth(0, 4*512)参数讲解�?表示第一列；4表示有多个个字符，比如你这个cell�?个字，那么就是四个字符；
    			//乘以512是什么意思？如果你是4个汉字，那么列宽就是4*512，如果你�?个数字或者英文字符，那么就是4*256�?
        			workbook.getSheetAt(i).setColumnWidth(0, 4*512);
        			workbook.getSheetAt(i).setColumnWidth(1, 20*256);
        			workbook.getSheetAt(i).setColumnWidth(2, 6*512);
        			workbook.getSheetAt(i).setColumnWidth(3, 20*256);
        			workbook.getSheetAt(i).setColumnWidth(4, 6*512);
        			workbook.getSheetAt(i).setColumnWidth(5, 9*256);
    		}else{
    			//i>0的时候表示其他模块sheet
        			workbook.getSheetAt(i).setColumnWidth(0, 2*512);
        			workbook.getSheetAt(i).setColumnWidth(1, 32*256);
        			workbook.getSheetAt(i).setColumnWidth(2, 4*512);
        			workbook.getSheetAt(i).setColumnWidth(3, 40*256);
        			workbook.getSheetAt(i).setColumnWidth(4, 40*256);
    		}
			
		}

   
        // 工作薄建立完成，下面将工作薄存入文件
        // 新建�?��出文件流
			fos = new FileOutputStream(fileForExcel);
        // 把相应的Excel 工作簿存�?
			workbook.write(fos);
			fos.flush();
        // 操作结束，关闭文�?
			fos.close();
			L.info("Excel报告已经生成，请查看：["+fileForExcel.getAbsolutePath()+"]");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	
	
	
	
	
	
	/**计算百分�?/
	public static String getPercent(float x,float total){
		   String result="";//接受百分比的�?
		   float tempresult=x/total;
		   DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的�?补齐
		   result= df1.format(tempresult);  
		   return result;
		}
	
	/**style for label in 汇�?  sheet*/
	public static XSSFCellStyle setCellStyle0(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
        font.setColor((short)8);
        font.setFontHeightInPoints((short) 11);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗      
		xssfCellStyle.setFont(font);
		XSSFColor xssfColor = new XSSFColor();
		byte[] rgb = {(byte) 244,(byte)164,(byte)96};
		xssfColor.setRgb(rgb);
        xssfCellStyle.setFillForegroundColor(xssfColor);
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);   
        setCellBorder(workbook,xssfCellStyle);
		return xssfCellStyle;
	} 
	/**style for label in 汇�?  sheet*/
	public static XSSFCellStyle setCellStyle1(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
        font.setColor((short)8);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗      
		xssfCellStyle.setFont(font);
		XSSFColor xssfColor = new XSSFColor();
		byte[] rgb = {(byte) 255,(byte)182,(byte)193};
		xssfColor.setRgb(rgb);
        xssfCellStyle.setFillForegroundColor(xssfColor);
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);   
        setCellBorder(workbook,xssfCellStyle);
		return xssfCellStyle;
	} 
	
	/**style for label in 汇�?  sheet*/
	public static XSSFCellStyle setCellStyle2(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
        font.setColor((short)8);
        font.setFontHeightInPoints((short) 11);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗      
		xssfCellStyle.setFont(font);
		XSSFColor xssfColor = new XSSFColor();
		byte[] rgb = {(byte) 250,(byte) 235,(byte) 215};
		xssfColor.setRgb(rgb);
        xssfCellStyle.setFillForegroundColor(xssfColor);
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND); 
        setCellBorder(workbook,xssfCellStyle);
		return xssfCellStyle;
	} 
	
	/**style for label in function sheet*/
	public static XSSFCellStyle setCellStyle3(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
        font.setColor((short)8);
        font.setFontHeightInPoints((short) 11);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗      
		xssfCellStyle.setFont(font);
		XSSFColor xssfColor = new XSSFColor();
		byte[] rgb = {(byte) 250,(byte) 235,(byte) 215};
		xssfColor.setRgb(rgb);
        xssfCellStyle.setFillForegroundColor(xssfColor);
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);   
        setCellBorder(workbook,xssfCellStyle);
		return xssfCellStyle;
	} 
	
	/**style for label in 汇�? sheet header*/
	public static XSSFCellStyle setCellStyle4(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
	    //设置cell样式
	    xssfCellStyle = workbook.createCellStyle();
	    //居中显示
        xssfCellStyle.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
        xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //字体
        XSSFFont xssfFont = workbook.createFont();   
        xssfFont.setFontName("微软雅黑");
        //设置白色字体
        xssfFont.setColor((short)9);
        xssfFont.setFontHeightInPoints((short) 14);//设置字体大小
        xssfFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗      
        xssfCellStyle.setFont(xssfFont);
		XSSFColor xssfColor = new XSSFColor();
		byte[] rgb = {100,(byte) 149,(byte) 237};
		xssfColor.setRgb(rgb);
        xssfCellStyle.setFillForegroundColor(xssfColor);
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        setCellBorder(workbook,xssfCellStyle);
		return xssfCellStyle;
	} 
	
	/**set border for cell*/
	public static void setCellBorder(XSSFWorkbook workbook,XSSFCellStyle xssfCellStyle){
		xssfCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边�?   
		xssfCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边�?   
		xssfCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边�?   
		xssfCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边�?  
	}
	
	/**content cell set border*/
	public static XSSFCellStyle setContentCellBorder(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
		xssfCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边�?   
		xssfCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边�?   
		xssfCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边�?   
		xssfCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边�?  
		return xssfCellStyle;
	}
	
	/**设置可以点击的部分为蓝色字体*/
	public static XSSFCellStyle setClickableLinkToBlueColor(XSSFWorkbook workbook){
		XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
		setCellBorder(workbook, xssfCellStyle);
		XSSFFont font = workbook.createFont();
		XSSFColor xssfColor = new XSSFColor();
		byte[] rgb = {0,0,(byte) 255};//纯蓝�?
		xssfColor.setRgb(rgb);
		font.setColor(xssfColor);
		xssfCellStyle.setFont(font);
		return xssfCellStyle;
		
	}
	
	/**合并单元格之后设置边�?- 合并之后的单元格必须用这种方式设置边�?/
	public static void setRangeCellBorder(CellRangeAddress cellRangeAddress, XSSFSheet sheet,XSSFWorkbook wb) {
		RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, wb);
		RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, wb);
		RegionUtil.setBorderRight(1, cellRangeAddress, sheet, wb);
		RegionUtil.setBorderTop(1, cellRangeAddress, sheet, wb);
		
}
	


}
