package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Category;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class SearchCategoryPagetoSearchPage {

	public String category;
	public String specialoffers;
	public String catCount;
	public String qty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public boolean tcStatus = true;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Category_Page_to_Search_Page() throws Exception{
		
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
				
					category = TableContainer.getCellValue(i, "category");
					specialoffers = TableContainer.getCellValue(i, "specialoffers");
					title = TableContainer.getCellValue(i, "title");
					
					//Go to the Category page.
					Category.gotoCategoryviaHeader(Config.driver, category);
					if(specialoffers.equals("clearance")){
						catCount = Category.getClearanceOfferCount(Config.driver, category);
						Category.clickClearanceOffer(Config.driver);
						
					}
					
					if(specialoffers.equals("refurbished")){
						catCount = Category.getRefurbishedOfferCount(Config.driver, category);
						Category.clickRefurbishedOffer(Config.driver);
					}
					
					if(specialoffers.equals("openbox")){
						catCount = Category.getOpenBoxOfferCount(Config.driver, category);
						Category.clickOpenBoxOffer(Config.driver);
					}
					
					//Verify the Item list count.
					
					if(Integer.valueOf(catCount) < 25){
						testStatus = verifyXPath.isfound(Config.driver, "(//div[@class='pager-info']//span[@class='totalPage' and text()='" + catCount + "']) [position()=1]");
						StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Category count matches the search last page label.",testStatus);
						
						//Verify the Item count.
						testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=" + catCount + "]");
						StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Category count matches the count of items in search.",testStatus);
					} //end if 
					
					//Moved to next page if items are more than 25
					if(Integer.valueOf(catCount) > 25){
						ClickElement.byXPath(Config.driver, Search.BTN_NEXTPAGE_XPATH);
						//Verify next page is not empty.
						testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=1]");
						StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Next page shows results.",testStatus);
					}
				

			} //end for i
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
			
		} catch (Exception e){
			StatusLog.printlnFailedResult(Config.driver,"[SEARCH] " + title);
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
