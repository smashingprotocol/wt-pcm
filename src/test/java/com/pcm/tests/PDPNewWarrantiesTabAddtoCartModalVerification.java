package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.grund.form.SetSelectField;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.pcm.includes.PDP;
import com.pcm.includes.Search;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class PDPNewWarrantiesTabAddtoCartModalVerification {

	public String sku;
	public String qty;
	public String warrantyqty;
	public String modalQtyAdded;	
	public String warrantySku;
	public String withModalServicePlan;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	public String baseURL;
	public String pdpURL;
	
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void PDP_New_Warranties_Tab_AddtoCart_Modal_Verification() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Define properties
			email = pr.getProperty("CHECKOUT_USER_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
	
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				
				sku = TableContainer.getCellValue(i, "sku");
				qty = TableContainer.getCellValue(i, "qty");
				warrantyqty = TableContainer.getCellValue(i, "warrantyqty");
				warrantySku = TableContainer.getCellValue(i, "warrantySku");
				withModalServicePlan = TableContainer.getCellValue(i, "withModalServicePlan");
				title = TableContainer.getCellValue(i, "title");
				baseURL = TableContainer.getCellValue(i, "baseURL");
				
				//Cleart cart first.
				Cart.clearcart(Config.driver);
				
				//Open PDp
				Search.openPDPbySearchSku(Config.driver, sku);
				
				SetSelectField.byXPath(Config.driver, pr.getProperty("PDP_NEW_SELECT_QTY_XPATH"), qty);
				
				PDP.expandWarrantiesAccordion(Config.driver);
				PDP.addtoCartfromWarrantiesTab(Config.driver,warrantySku);
				
				
				//Set qty from Warranty tab
				int newQty = Integer.parseInt(qty) * 2;
				qty = String.valueOf(newQty);
				
				
				//Modal Verification.
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_MODAL_ADDTOCART_XPATH") + "//span[@class='qty-cart-added' and contains(text(),'" + warrantyqty + "')]");
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify items added to your cart count in the modal from Warranties Tab is 2",testStatus);
				
				testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_MODAL_ADDTOCART_XPATH") + "//span[@data-bind='cart-count' and contains(text(),'" + warrantyqty + "')]");
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the View Cart link count in the modal matches the qty.",testStatus);
				
				TakeScreenShot.CurrentPage(Config.driver, "Warranties Tab click Add to Cart");
				
				//Modal add to cart warranties Verification
				if(withModalServicePlan.equals("true")){
					ClickElement.byXPath(Config.driver, pr.getProperty("PDP_NEW_MODAL_WARRANTY_ADD_XPATH"));
					int qtyModal = Integer.parseInt(warrantyqty) + 2;
					modalQtyAdded = String.valueOf(qtyModal);
					
					//Modal Warranties added to cart Verification.
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_MODAL_ADDTOCART_XPATH") + "//span[@class='qty-cart-added' and contains(text(),'" + modalQtyAdded + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify Warranties added from modal cart count.",testStatus);
					
					testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("PDP_NEW_MODAL_ADDTOCART_XPATH") + "//span[@data-bind='cart-count' and contains(text(),'" + modalQtyAdded + "')]");
					StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify the View Cart link count in the modal matches the qty.",testStatus);
					
					TakeScreenShot.CurrentPage(Config.driver, "Warranties Tab Add to Cart Modal");
					
				}
				
				//close modal
				PDP.closeAddtoCartModal(Config.driver,"pcm");
				
				
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
