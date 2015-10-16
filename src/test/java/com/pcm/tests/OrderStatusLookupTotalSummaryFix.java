package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.OrderStatus;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;

public class OrderStatusLookupTotalSummaryFix {

	public String email;
	public String password;
	public String env;
	public String orderNumber;
	public String zipCode;
	public String status;
	public String title;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public boolean testStatus;
	public boolean tcStatus = true;
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	@Test
	public void Order_Status_Lookup_Total_Summary_Fix() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			
			//Get table container value and declared as property values
			//get the number of test cases in the excel
			int rowCount = TableContainer.getRowCount();
	
			for(int i = 1; i <= rowCount; i++){
				
					orderNumber = TableContainer.getCellValue(i, "orderNumber");
					zipCode = TableContainer.getCellValue(i, "zipCode");
					status = TableContainer.getCellValue(i, "status");
					title = TableContainer.getCellValue(i, "title"); 
					
					OrderStatus.orderLookup(Config.driver,orderNumber,zipCode);
					
					if(status.equals("iscancelled")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_LESSCANCELLED_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[ORDER STATUS] Less Cancelled Items Label is display.", testStatus);
						
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_INVOICETOTAL_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[ORDER STATUS] Invoice Total Label is display.", testStatus);
					}
					
					
					if(status.equals("isnocancelled")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_LESSCANCELLED_XPATH"));
						StatusLog.printlnPassedResultFalse(Config.driver, "[ORDER STATUS] Less Cancelled Items Label is not display.", testStatus);
						
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_INVOICETOTAL_XPATH"));
						StatusLog.printlnPassedResultFalse(Config.driver, "[ORDER STATUS] Invoice Total Label is not display.", testStatus);
					}
					
					if(status.equals("isponumber")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_PONUMBER_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[ORDER STATUS] Purchase Order Number display.", testStatus);
					}
					
					if(status.equals("isnoponumber")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_PONUMBER_XPATH"));
						StatusLog.printlnPassedResultFalse(Config.driver, "[ORDER STATUS] No Purchase Order Number display.", testStatus);
					}
					
					if(status.equals("isewaste")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_EWASTE_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[ORDER STATUS] No California E Waste label is display.", testStatus);
					}
					
					
					if(status.equals("isnoewaste")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_EWASTE_XPATH"));
						StatusLog.printlnPassedResultFalse(Config.driver, "[ORDER STATUS] No California E Waste label is display.", testStatus);
					}
					
					if(status.equals("isshipped")){
						testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("ORDERSTATUS_LBL_TRACKNO_XPATH"));
						StatusLog.printlnPassedResultTrue(Config.driver,"[ORDER STATUS] Tracking Info Link is display.", testStatus);
					}

			} //end for
			
			//Overall Test Result
			Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
		
		} catch (Exception e){
			Assert.fail(e.getMessage());
		} //end try catch
	} //end void
	

	@AfterClass
	public static void quit() throws IOException{
		TakeScreenShot.CurrentPage(Config.driver, "Last Page Test Result");
		Config.driver.close();
		Config.driver.quit();
		
	}
	
	
}
