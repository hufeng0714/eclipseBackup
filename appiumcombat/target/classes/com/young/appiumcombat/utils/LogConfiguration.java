package com.young.appiumcombat.utils;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
/**
 * @author young
 * @decription 动�?生成各个模块中的每条用例的日志，运行完成用例之后请到result/log目录下查�?
 * */
public class LogConfiguration {
	
		public static void initLog(String fileName){
			//获取到模块名�?
			String founctionName = getFunctionName(fileName);
			//声明日志文件存储路径以及文件名�?格式
		    final String logFilePath  = "./result/logs/"+founctionName+"/"+fileName+".log";  
			Properties prop = new Properties();
			//配置日志输出的格�?
			prop.setProperty("log4j.rootLogger","info, toConsole, toFile");
			prop.setProperty("log4j.appender.file.encoding","UTF-8" );
			prop.setProperty("log4j.appender.toConsole","org.apache.log4j.ConsoleAppender");
			prop.setProperty("log4j.appender.toConsole.Target","System.out");
			prop.setProperty("log4j.appender.toConsole.layout","org.apache.log4j.PatternLayout ");
			prop.setProperty("log4j.appender.toConsole.layout.ConversionPattern","[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");		
			prop.setProperty("log4j.appender.toFile", "org.apache.log4j.DailyRollingFileAppender");
			prop.setProperty("log4j.appender.toFile.file", logFilePath);
			prop.setProperty("log4j.appender.toFile.append", "false");
			prop.setProperty("log4j.appender.toFile.Threshold", "info");
			prop.setProperty("log4j.appender.toFile.layout", "org.apache.log4j.PatternLayout");
			prop.setProperty("log4j.appender.toFile.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");
			//使配置生�?
			PropertyConfigurator.configure(prop);

		}
		
		
	    /**取得模块名字*/
	    public static String getFunctionName(String fileName){
			String functionName = null; 
			int firstUndelineIndex = fileName.indexOf("_"); 
			functionName = fileName.substring(0, firstUndelineIndex-4);
			return functionName;
	    
	}
	

}
