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



public class SearchwithFacetPriceRange {

	public String keyword;
	public String minPrice;
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
	public void Search_with_Facet_Price_Range() throws Exception{
		
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
				minPrice = TableContainer.getCellValue(i, "minPrice");
				maxPrice = TableContainer.getCellValue(i, "maxPrice");
				filterlabel = TableContainer.getCellValue(i, "filterlabel");
				title = TableContainer.getCellValue(i, "title");
				
				//Quick add the sku
				Search.keyword(Config.driver, keyword);
				
				Search.setFacetPriceRange(Config.driver,minPrice,maxPrice);
				
				if(filterlabel.equals("andabove")){
					
					testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=1]", "SEARCH: Price Range filtered shows items in result.");
					StatusLog.printlnPassedResultTrue(Config.driver,"SEARCH: Price Range filtered shows items in result.",testStatus);

					 
				} //end if
				
				if(filterlabel.equals("andbelow")){
						
					//Verify the Price Facet Label
					testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=1]", "SEARCH: Price Range filtered shows items in result.");
					StatusLog.printlnPassedResultTrue(Config.driver,"SEARCH: Price Range filtered shows items in result.",testStatus);
				 
				} //end if
				
				if(filterlabel.equals("invalidrange")){
					//Verify the Price Facet Label
					testStatus = verifyXPath.isfound(Config.driver, "(" + Search.ITEMCOL_XPATH + ") [position()=1]", "SEARCH: Price Range filtered shows items in result.");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] User cannot enter invalid Price Range. Items are still display.",testStatus);
				
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
