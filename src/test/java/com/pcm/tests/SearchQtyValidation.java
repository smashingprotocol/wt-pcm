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



public class SearchQtyValidation {

	public String btnAction;
	public String navlink;
	public String sku;
	public String finalPrice;
	public String qty;
	public String errortype;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Qty_Validation() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			sku = pr.getProperty("SEARCH_SKU_DFLT");
			
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				//sku = TableContainer.getCellValue(i, "sku");
				qty = TableContainer.getCellValue(i, "qty");
				errortype = TableContainer.getCellValue(i, "errortype"); 
				title = TableContainer.getCellValue(i, "title"); 
				
				Search.keyword(Config.driver, sku);
				Search.addtocart(Config.driver, sku, qty);
				
				testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("SEARCH_LBL_ERRMSG_MODALGLOBAL_XPATH"));
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] " + title, testStatus);
				
				if(errortype.equals("inventory")){
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_ERRMSG_OK_XPATH"));
				} else{
					ClickElement.byXPath(Config.driver, pr.getProperty("SEARCH_BTN_ERRMSGWARRANTYQTY_MODAL_XPATH"));
				}
				
				
			}	//end for
			
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
