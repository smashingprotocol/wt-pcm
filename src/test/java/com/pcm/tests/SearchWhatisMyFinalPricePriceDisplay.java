package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class SearchWhatisMyFinalPricePriceDisplay {

	public String sku;
	public String withSlashed;
	public String withMIR;
	public String unitPrice;
	public String finalPrice;
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
	public void Search_What_is_My_Final_Price_Price_Display() throws Exception{
		
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
				
				sku = TableContainer.getCellValue(i, "sku");
				withSlashed = TableContainer.getCellValue(i, "withSlashed");
				withMIR = TableContainer.getCellValue(i, "withMIR");
				unitPrice = TableContainer.getCellValue(i, "unitPrice");
				finalPrice = TableContainer.getCellValue(i, "finalPrice");
				title = TableContainer.getCellValue(i, "title");
				
				//Quick add the sku
				Search.keyword(Config.driver, sku);
				
				//Verify the Click Here Button display
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Click Here button is display.",testStatus);
				
				//Verify the What is My Final Price text.
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_IMG_WHATISMYFINALPRICE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] What is My Final Price item image is display.",testStatus);
				
				//Verify the Strikethrough Price in Search and UPP Modal
				if(Boolean.valueOf(withSlashed)){
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice str prod-lprice' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Strikethrough Price " + unitPrice + " is display.",testStatus);
					
					//Modal List Slashed Price verification
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
					
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='strike-through' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Strikethrough Price " + unitPrice + " is display in the UPP Modal.",testStatus);
					StatusLog.printlnPassedResult(Config.driver,"[SEARCH] UPP MODAL" + title);
					
					//Close modal
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_CLOSEUPPMODAL_XPATH"));
					
				} else{
					//No slashed
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='lprice prod-lprice' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected No slashed Price " + unitPrice + " is display.",testStatus);
					
					//Modal List No Slashed Price verification
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_CLICKHERE_XPATH"));
					
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='sub-price' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected No slashed Price " + unitPrice + " is display in the UPP Modal.",testStatus);
					StatusLog.printlnPassedResult(Config.driver,"[SEARCH] UPP MODAL" + title);
					
					//Close modal
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_CLOSEUPPMODAL_XPATH"));
						
				}
				
				//Verify the What is My Final Price text
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("SEARCH_LABEL_WHATISMYFINALPRICE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] Expected Add to cart for Lower Price text is display.",testStatus);
				
				
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
