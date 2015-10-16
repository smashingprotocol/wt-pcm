package com.pcm.tests;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Cart;
import com.pcm.includes.Checkout;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.TableContainer;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;

public class VerifyOrderQASModalisDisplay {

	public String email;
	public String password;
	public String env;
	public boolean testStatus;
	public String edpno;
	public String cartOrderTotal;
	public String verifyOrderSubtTotal;
	public String sku;
	public String qty;
	public String zipCode;
	public String itemid;
	public String cardNo;
	public String exMonth;
	public String exYear;
	public String securityCode;
	
	
	
	
	Properties sys = System.getProperties(); //Get property values in the build.xml junit tagged.
	
	
	@Test
	public void Verify_Order_QAS_Modal_is_Display() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies

			email = pr.getProperty("CHECKOUT_USER_TAX_EMAIL_" + env);
			password = pr.getProperty("CHECKOUT_USER_PASSWORD");
			sku = pr.getProperty("SEARCH_SKU_INSTANTREBATES");
			qty = "1";
			cardNo = pr.getProperty("CHECKOUT_CCNO_VISA");
			exMonth = pr.getProperty("CHECKOUT_EXPMONTH");
			exYear = pr.getProperty("CHECKOUT_EXPYEAR");
			securityCode = pr.getProperty("CHECKOUT_SCODE_VISA");
			
			
			//Search sku and add to cart
			Cart.clearcart(Config.driver);
			Search.keyword(Config.driver, sku);
			Search.addtocart(Config.driver, sku, qty);
			
			//Go to cart and Proceed to checkout.
			Cart.navigate(Config.driver);
			//Get the Cart Order Total
			cartOrderTotal = verifyXPath.getText(Config.driver,pr.getProperty("CART_LABEL_ORDERTOTAL_XPATH"));
			cartOrderTotal = cartOrderTotal.replaceAll("[$, ]", "");
			
			Cart.newCustomer(Config.driver);
			
			//Create array list for the billing fields.
			ArrayList<String> billFields = new ArrayList<String>();
			String now = new SimpleDateFormat("mmddhhmmss").format(new Date());
			int rowCount = TableContainer.getRowCount();
			for(int i = 1; i <= rowCount; i++){
				
				String NewEmail = "manilaqa+" + now +  "_" + i +  "@gmail.com";
				
				billFields.add(0, NewEmail);
				billFields.add(1, NewEmail);
				billFields.add(2, TableContainer.getCellValue(i, "billPassword"));
				billFields.add(3, TableContainer.getCellValue(i, "billConfirmPassword"));
				billFields.add(4, TableContainer.getCellValue(i, "billFirstName"));
				billFields.add(5, TableContainer.getCellValue(i, "billLastName"));
				billFields.add(6, TableContainer.getCellValue(i, "billCompany"));
				billFields.add(7, TableContainer.getCellValue(i, "billAddress1"));
				billFields.add(8, TableContainer.getCellValue(i, "billAddress2"));
				billFields.add(9, TableContainer.getCellValue(i, "billZipCode"));
				billFields.add(10, TableContainer.getCellValue(i, "billTelNo"));
				billFields.add(11, TableContainer.getCellValue(i, "billMobile"));
				
			}
			
			Checkout.enterNewCustomerBillAdd(Config.driver,billFields);
			ClickElement.byXPath(Config.driver, pr.getProperty("CHECKOUT_CHECKBOX_SAMEASBILL_XPATH"));
			
			verifyOrderSubtTotal = verifyXPath.getText(Config.driver, pr.getProperty("CHECKOUT_LABEL_ORSUBTOTAL_XPATH")).replaceAll("[$, ]", "");
			Assert.assertEquals(verifyOrderSubtTotal,cartOrderTotal);
			
			Checkout.enterCreditCardExisting(Config.driver,cardNo,exMonth,exYear,securityCode);
			Checkout.placeOrderAccept(Config.driver);
			
			//Verify the QAS Modal I understand use address anyway link
			testStatus = verifyXPath.isfound(Config.driver, pr.getProperty("CHECKOUT_LINK_QASMODAL_USEBILLADD_XPATH"));
			StatusLog.printlnPassedResultTrue(Config.driver,"[CHECKOUT] QAS Modal form appears.",testStatus);
			
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
