package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class SearchRelatedContentSearchNotDisplay {

	public String keyword;
	public String isContent;
	public String maxPrice;
	public String filterlabel;
	public String priceStrike;
	public String unitPrice;
	public String FinalPrice;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Related_Content_Search_Not_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Define properties
			qty = "1";
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				keyword = TableContainer.getCellValue(i, "keyword");
				isContent = TableContainer.getCellValue(i, "isContent");
				title = TableContainer.getCellValue(i, "title");
				
				//Quick add the sku
				Search.keyword(Config.driver, keyword);
				

				if(isContent.equals("false")){
					
					//Verify the Related Search content button is not  displayed.
					testStatus = verifyXPath.isfound(Config.driver, Search.BTN_RELATEDCONTENT_XPATH);
					StatusLog.printlnPassedResultFalse(Config.driver,"[SEARCH] Related Search Content is not displayed.",testStatus);
				
					//Related Content search is not displayed
					testStatus = verifyXPath.isfound(Config.driver, Search.DIV_RELATEDCONTENT_XPATH);
					StatusLog.printlnPassedResultFalse(Config.driver,"[SEARCH] Related Search Content is not displayed.",testStatus);
					 
				} //end if
				
				if(isContent.equals("true")){
										
					//Verify the Related Search content button is displayed.
					testStatus = verifyXPath.isfound(Config.driver, Search.BTN_RELATEDCONTENT_XPATH);
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Related Search Content Button is not displayed.",testStatus);
				
					//Verify the Related Search content is displayed.
					testStatus = verifyXPath.isfound(Config.driver, Search.DIV_RELATEDCONTENT_XPATH);
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Related Search Content is not displayed.",testStatus);
					 
				} //end if
				
				
			} //end for i
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		} catch (Exception e){
			Assert.fail(e.getMessage());
		}
	}
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
