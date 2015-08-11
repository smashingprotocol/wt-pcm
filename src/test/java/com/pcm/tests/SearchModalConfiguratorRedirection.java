package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.pcm.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.pcm.utility.StatusLog;
import com.pcm.utility.TableContainer;
import com.pcm.utility.TakeScreenShot;
import com.pcm.verify.VerifyDocumentURL;
import com.pcm.verify.verifyXPath;



public class SearchModalConfiguratorRedirection {

	public String btnAction;
	public String navlink;
	public String sku;
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
	public void Search_Modal_Configurator_Redirection() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties(); //create a method for the pcm.properies
			
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				sku = TableContainer.getCellValue(i, "sku");
				btnAction = TableContainer.getCellValue(i, "btnAction");
				qty = TableContainer.getCellValue(i, "qty");
				navlink = TableContainer.getCellValue(i, "navlink"); 
				title = TableContainer.getCellValue(i, "title"); 
				

				if(btnAction.equals("configure")){
					
					Search.ctoModalClickConfigureItems(Config.driver,sku,qty);
					
					testStatus = VerifyDocumentURL.containsText(Config.driver,navlink);
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] CTO Modal redirect to Configurator Page.", testStatus);
				} //end if
				
				if(btnAction.equals("cart")){
					Search.ctoModalClickProceedtoCart(Config.driver,sku,qty);
					
					testStatus = verifyXPath.isfound(Config.driver,pr.getProperty("CART_INPUT_QTY_XPATH"));
					StatusLog.printlnPassedResultTrue(Config.driver,"[SEARCH] CTO Modal Proceed to cart.", testStatus);
				} //end if
				
				
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
