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



public class SearchSortByViewResults {

	public String keyword;
	public String sortby;
	public String view;
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
	public void Search_Sort_By_View_Results() throws Exception{
		
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
				sortby = TableContainer.getCellValue(i, "sortby");
				view = TableContainer.getCellValue(i, "view");	
				title = TableContainer.getCellValue(i, "title");
				
				//Go to the Category page.
				Search.keyword(Config.driver, keyword);
				
				if(sortby.equals("lowest")){
					
					Search.sortByLowestPriceFirst(Config.driver);
					Search.selectViewBy(Config.driver,view);
					
					//Verify the Sort By selected.
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_SELECT_LOWESTPRICESELECTED_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Sort By Lowest Price.",testStatus);
					
					//Verify the Item count.
					testStatus = verifyXPath.isfound(Config.driver, "(" + pr.getProperty("SEARCH_ITEMCOL_XPATH") + ") [position()=" + view + "]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Item count matches the view.",testStatus);
										
				} //end if
				
				if(sortby.equals("highest")){
					
					Search.sortByHighestPriceFirst(Config.driver);
					Search.selectViewBy(Config.driver,view);
					
					//Verify the Sort By selected.
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_SELECT_HIGHESTPRICESELECTED_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Sort By Lowest Price.",testStatus);
					
					//Verify the Item count.
					testStatus = verifyXPath.isfound(Config.driver, "(" + pr.getProperty("SEARCH_ITEMCOL_XPATH") + ") [position()=" + view + "]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Item count matches the view.",testStatus);
										
				} //end if
				
				if(sortby.equals("bestmatch")){
					
					Search.sortByBestMatch(Config.driver);
					Search.selectViewBy(Config.driver,view);
					
					//Verify the Sort By selected.
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_SELECT_BESTMATCHSELECTED_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Sort By Best Match.",testStatus);
					
					//Verify the Item count.
					testStatus = verifyXPath.isfound(Config.driver, "(" + pr.getProperty("SEARCH_ITEMCOL_XPATH") + ") [position()=" + view + "]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Item count matches the view count.",testStatus);
										
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
