package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;



public class SearchWarrantyModalQtyValidation {

	public String btnAction;
	public String navlink;
	public String sku;
	public String finalPrice;
	public String qty;
	public String warrantyqty;
	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String title;
	
	
	Properties sys = System.getProperties();
	
	
	@Test
	public void Search_Warranty_Modal_Qty_Validation() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			//Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			//Get the data in the excel and quick add the skus
			int rowCtr = TableContainer.getRowCount();
			
			for(int i = 1; i <= rowCtr; i++){
				System.out.println("Initialize data from the table container.");
				sku = TableContainer.getCellValue(i, "sku");
				qty = TableContainer.getCellValue(i, "qty");
				warrantyqty = TableContainer.getCellValue(i, "warrantyqty");
				title = TableContainer.getCellValue(i, "title"); 
				
				Search.keyword(Config.driver, sku);
				Search.addtocart(Config.driver, sku, qty);
				
				//Modal Verification.
				testStatus = verifyXPath.isfoundwithWait(Config.driver, Search.MODAL_ADDTOCART_XPATH + "//span[@class='qty-cart-added' and contains(text(),'" + qty + "')]","2");
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify items added to your cart count in the modal matches the qty.",testStatus);
				
				Search.warrantyModalAddtoCart(Config.driver,warrantyqty);
				
				int qtyModal = Integer.parseInt(qty) + 2;
				qty = String.valueOf(qtyModal);
				
				//Modal Verification.
				testStatus = verifyXPath.isfoundwithWait(Config.driver, Search.MODAL_ADDTOCART_XPATH + "//span[@class='qty-cart-added' and contains(text(),'" + qty + "')]","2");
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify items added to your cart count in the modal matches the qty.",testStatus);
				
				ClickElement.byXPath(Config.driver, Search.MODAL_BTN_PROCEEDTOCART_XPATH);
				
				testStatus = Cart.verifySkuinCart(Config.driver,Search.addedWarrantySku);
				StatusLog.printlnPassedResultTrue(Config.driver,"[TESTCASE] Verify Warranty sku is found in cart.",testStatus);
				
				
				
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
