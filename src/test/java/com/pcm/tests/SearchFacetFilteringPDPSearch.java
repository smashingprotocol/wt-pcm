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



public class SearchFacetFilteringPDPSearch {

	public String keyword;
	public String brand;
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
	public void Search_Facet_Filtering_PDP_Search() throws Exception{
		
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
					System.out.println("Initialize data from the table container.");
					keyword = TableContainer.getCellValue(i, "keyword");
					brand = TableContainer.getCellValue(i, "brand");
					title = TableContainer.getCellValue(i, "title");
					
					Search.keyword(Config.driver, keyword);
					Search.filterByBrand(Config.driver,brand);
					String brndcount = Search.getBrandFacetCount(Config.driver,brand);
					
					
					//Verify the Item list count.
					
					if(Integer.valueOf(brndcount) < 25){
						testStatus = verifyXPath.isfound(Config.driver, "(//div[@class='pager-info']//span[@class='totalPage' and text()='" + brndcount + "']) [position()=1]");
						StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Facet brand count matches the search last page count.",testStatus);
						
						//Verify the Item count.
						testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=" + brndcount + "]");
						StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Facet brand matches the count of items in search.",testStatus);
					} //end if 
					
					Search.openPDPbySearchResult(Config.driver, keyword);
					Search.keyword(Config.driver, pr.getProperty("SEARCH_KEYWORD_DFLT"));
					
					//Verify the Item count is the default 25.
					testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=25]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Search from PDP shows Search results.",testStatus);
					
					StatusLog.printlnPassedResult(Config.driver,"[SEARCH] " + title);
					
					
				
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
