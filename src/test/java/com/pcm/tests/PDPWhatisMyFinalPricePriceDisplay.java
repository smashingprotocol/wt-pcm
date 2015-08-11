package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.pcm.request.ClickElement;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.verifyXPath;



public class PDPWhatisMyFinalPricePriceDisplay {

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
	public void PDP_What_is_My_Final_Price_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
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
				
				//Search the sku
				
				Search.openPDPbySearchSku(Config.driver, sku);
				
				//Verify the What is my Final Price? link.
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_LINK_WHATISMYFINALPRICE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] What is my Final Price? link is display in PDP",testStatus);
				
				//Verify Why don't we show price link
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_LINK_WHYDONTSHOWFINALPRICE_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Why don't we show price link is display in PDP.",testStatus);
				
				//Verify the Strikethrough Price in Search and UPP Modal
				if(withSlashed.equals("true")){
					//strikethrough price
					testStatus = verifyXPath.isfound(Config.driver, "//span[@style='text-decoration: line-through;' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Expected Strikethrough Price " + unitPrice + " is display in PDP",testStatus);
					
					//Modal List Slashed Price verification
					ClickElement.byXPath(Config.driver, pr.getProperty("PDP_LINK_WHATISMYFINALPRICE_XPATH"));
					
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='strike-through' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Expected Strikethrough Price " + unitPrice + " is display in the UPP Modal of PDP",testStatus);
					
					
					//Close modal
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_CLOSEUPPMODAL_XPATH"));
					
				} else{
					//No slashed
					testStatus = verifyXPath.isfound(Config.driver, "//span[@style='text-decoration: none;' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Expected No slashed Price " + unitPrice + " is display in PDP",testStatus);
					
					//Modal List No Slashed Price verification
					ClickElement.byXPath(Config.driver, pr.getProperty("PDP_LINK_WHATISMYFINALPRICE_XPATH"));
					
					testStatus = verifyXPath.isfound(Config.driver, "//span[@class='sub-price' and contains(text(),'" + unitPrice + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Expected No slashed Price " + unitPrice + " is display in the UPP Modal of PDP.",testStatus);
					
					//Close modal
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_CLOSEUPPMODAL_XPATH"));
						
				}
				
				
				
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
