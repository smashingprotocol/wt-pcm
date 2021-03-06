package com.pcm.includes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.grund.form.SetInputField;
import com.grund.request.ClickElement;
import com.grund.verify.verifyXPath;

public class Cart {

	public static Properties properties;
	static boolean carthasitem = true;
	static boolean savecarthasitem;
	
	
	public static void existingLogin(WebDriver driver, String email, String password) throws Exception {
		// TODO Auto-generated method stub
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try {
			properties.load(reader);
			System.out.println("[STEP] IN CART, LOGIN EXISTING CUSTOMER.");
			ClickElement.byXPath(driver, properties.getProperty("CART_BTN_EXIST_XPATH"));
			SetInputField.byXPath(driver,properties.getProperty("CART_INPUT_EMAIL_XPATH"),email);
			SetInputField.byXPath(driver,properties.getProperty("CART_INPUT_PSWD_XPATH"),password);
			try {
				ClickElement.byXPath(driver, properties.getProperty("CART_BTN_SIGNIN_XPATH"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void clearcart(WebDriver driver) throws IOException {
		// TODO Auto-generated method stub
		//Check if cart has items, then click Remove All Button.
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try {
			
			properties.load(reader);
			navigate(driver);
			System.out.println("[STEP] IN CART, CHECK FOR ITEMS AND CLEAR THE CART.");
			if (carthasitem = verifyXPath.isfound(driver,properties.getProperty("CART_BTN_REMOVEALL_XPATH"))){
				
				ClickElement.byXPath(driver, properties.getProperty("CART_BTN_REMOVEALL_XPATH")); //Click Remove All in cart.
				ClickElement.byXPath(driver, properties.getProperty("CART_BTN_REMOVEALL_CONFIRM_XPATH")); //Confirmed Remove All in cart.
			}
			
			//Check also Saved Items have data, clear Saved items.
			if(savecarthasitem = verifyXPath.isfound(driver,properties.getProperty("CART_BTN_MOVEALLITEMS_XPATH"))){
				ClickElement.byXPath(driver, properties.getProperty("CART_BTN_SAVEDITEMCLEARALL_XPATH")); //Clear All
			}
			
			
		} catch (Exception e) {
			Assert.fail("[CART] Error in Removing all items in cart: " + e.getMessage());
		}
	}

	public static String getItemID(WebDriver driver, String edpno) {
		// TODO Auto-generated method stub
		try{
			String itemName = verifyXPath.getAttributeValue(driver, "//input[@value='" + edpno + "' and @type='hidden']", "name"); //by passing the edpno in xpath, get the attribute NAME value
			itemName = itemName.replace("cartEdp_", "");
			return itemName;
		} catch (Exception e) {
			Assert.fail("[CART] Unable to get the item id value in cart.");
			return null;
		}
		
	}

	public static void navigate(WebDriver driver) throws Exception {
		System.out.println("[STEP] NAVIGATE TO CART PAGE.");
		
		boolean xpathfound = verifyXPath.isfoundwithWait(driver, Header.LINK_CART_XPATH, "2");
		
		if(xpathfound){
			ClickElement.byXPath(driver, Header.LINK_CART_XPATH);
		}
				
	}
	
	public static void saveforlater(WebDriver driver, String sku, String itemid) {
		
		try{
			System.out.println("[STEP] IN CART, SAVE FOR LATER AN ITEM.");
			ClickElement.byXPath(driver, "//a[@class='item-save-for-later' and @item-id='" + itemid + "']");
			Assert.assertTrue(verifyXPath.isfound(driver, "//div[@id='saveMainCon']//span[contains(text(),'" + sku + "')]"));
		
		} catch (Exception e){
			Assert.fail("[CART] Error in Save for Later.");
		}
		
	}

	public static void saveitemsmoveall(WebDriver driver) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();	
		
		try{
			properties.load(reader);
			ClickElement.byXPath(driver, properties.getProperty("CART_BTN_MOVEALLITEMS_XPATH"));
			Boolean testStatus = verifyXPath.isfound(driver, properties.getProperty("CART_BTN_MOVEALLITEMS_XPATH")); //Verify the Move all items button is not displayed.
			Assert.assertFalse("[CART] Move All items to cart button is not displayed after moving items back to cart.", testStatus);
			
		} catch (Exception e) {
			Assert.fail("[CART] Error in moving all items to cart: " + e.getMessage());
		}	
		
	}

	public static void proceedtocheckout(WebDriver driver) throws FileNotFoundException {
		
		boolean testStatus;
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();	
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN CART, PROCEED TO CHECKOUT.");
			ClickElement.byXPathwithWait(driver,properties.getProperty("CART_BTN_CHECKOUT_XPATH"),"2");
			
			 //Verify the Header in Checkout page is pCM contact no.
			//testStatus = verifyXPath.isfound(driver,properties.getProperty("CHECKOUT_LABEL_HEADERCONTACTNO_XPATH_pcm")); //problem with skype appearing in phone no.
			//Assert.assertTrue("[CHECKOUT] Header shows PCM Contact No.",testStatus);
			
			//Verify the Change Address link to verify the page is checkout.
			testStatus = verifyXPath.isfoundwithWait(driver,properties.getProperty("CHECKOUT_LINK_CHANGESHIP_XPATH"),"2");  
			Assert.assertTrue("[CHECKOUT] Verify Order Page is display.",testStatus);
			
			
		} catch (Exception e) {
			Assert.fail("[CART] Error in Proceed to Cart " + e.getMessage());
		}
		
	}

	public static void quickaddsku(WebDriver driver, String sku) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();	
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN CART, ADD ITEM VIA QUICK ADD");
			SetInputField.byXPath(driver, properties.getProperty("CART_INPUT_QUICKADD_XPATH"), sku);
			ClickElement.byXPath(driver, properties.getProperty("CART_BTN_QUICKADD_XPATH"));
			
		} catch (Exception e) {
			Assert.fail("[CART] Error on Quick Add of items: " + e.getMessage());
		}
		
		
	}

	public static void addIpodWithoutEngraving(WebDriver driver) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();	
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN SERVICE PLAN, CLICK NEXT BUTTON WITHOUT SELECTING SERVICE PLAN");
			ClickElement.byXPath(driver, properties.getProperty("CART_BTN_BUYNOWWITHOUTENRAVE_XPATH"));
			ClickElement.byXPath(driver, properties.getProperty("CART_BTN_NEXTSERVICEPLAN_XPATH"));
			
			
		} catch (Exception e){
			Assert.fail("[CART] Error on Adding items from Engraving page: " + e.getMessage());
		}
		
		
	}

	public static void guestCheckout(WebDriver driver) throws FileNotFoundException {
		// TODO Auto-generated method stub
		boolean testStatus;
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();	
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN CART, CLICK GUEST CHECKOUT.");
			ClickElement.byXPath(driver,properties.getProperty("CART_BTN_GUEST_XPATH"));
			
			//Verify the Create Account link in Guest Checkout.
			testStatus = verifyXPath.isfoundwithWait(driver,properties.getProperty("CHECKOUT_LINK_CREATEACT_XPATH"),"2");  
			Assert.assertTrue("[CHECKOUT] Page is Guest Checkout.",testStatus);
			
			
		} catch (Exception e) {
			Assert.fail("[CART] Error in clicking Guest Checkout. " + e.getMessage());
		} //end try
		
	} //end guestCheckout

	public static void newCustomer(WebDriver driver) throws FileNotFoundException {
		
		boolean testStatus;
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();	
		
		try{
			properties.load(reader);
			System.out.println("[STEP] IN CART, CLICK NEW CUSTOMER");
			ClickElement.byXPath(driver,properties.getProperty("CART_BTN_NEW_XPATH"));
			
			//Verify the Guest Checkout link in New Customer
			testStatus = verifyXPath.isfound(driver,properties.getProperty("CHECKOUT_LINK_GUESTCHECKOUT_XPATH"));  
			Assert.assertTrue("[CHECKOUT] Page is Guest Checkout.",testStatus);
			
			
		} catch (Exception e) {
			Assert.fail("[CART] Error in clicking New Customer. " + e.getMessage());
		} //end try
		
	}

	public static void couponCodeSubmit(WebDriver driver, String couponCode) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			System.out.println("[STEP] IN CART, ENTER COUPON CODE");
			properties.load(reader);
			SetInputField.byXPathThenSubmit(driver, properties.getProperty("CART_INPUT_COUPONCODE_XPATH"), couponCode);
			
		} catch(Exception e){
			Assert.fail("[CART] Error in submitting Coupon Code. " + e.getMessage());
		} //end try
		
		
	}// end couponCodeSubmit

	public static void estimateShippingSubmitZip(WebDriver driver, String zipCode) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		try{
			
			properties.load(reader);
			SetInputField.byXPathThenSubmit(driver, properties.getProperty("CART_INPUT_ZIPCODE_XPATH"), zipCode);
			Long wait = Long.parseLong(properties.getProperty("WAIT_SEC"));
			Thread.sleep(wait);
			
			//make the Shipping method dropdown visible.
			WebElement shipMtdSelecty = driver.findElement(By.xpath(properties.getProperty("CART_SELECT_SHIPMTD_XPATH")));
			JavascriptExecutor jse=(JavascriptExecutor) driver;
			jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",shipMtdSelecty);
			
			
		} catch(Exception e){
			Assert.fail("[CART] Error in submitting Zip Code. " + e.getMessage());
		} //end try
		
	}

	public static boolean verifyShipMethodAvailability(WebDriver driver,
			String shipMtd) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		boolean flag;
		
		try{
			properties.load(reader);
			String shipMtdName = properties.getProperty(shipMtd);
			
			flag = verifyXPath.isfound(driver, "//select[@name='shippingMethod']//option[contains(text(),'" + shipMtdName + "')]");
			return flag;
			
		} catch (Exception e){
			return false;
		}
	}

	public static void updateItemQty(WebDriver driver, String qty) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		SetInputField.byXPath(driver, properties.getProperty("CART_INPUT_QTY_XPATH"), qty);
		ClickElement.byXPath(driver, properties.getProperty("CART_LINK_UPDATEQTY_XPATH"));
		
	}

	public static boolean verifySkuinCart(WebDriver driver, String sku) {

		boolean iteminCart;
		
		try{
			System.out.println("[STEP] IN CART, INSPECT THE ITEM IS ADDED.");
			//iteminCart = verifyXPath.isfound(driver, "//div[@class='prodInfo']//span[contains(text(),'" + sku + "')]", "CART: Item is found in cart." + sku);
			iteminCart = verifyXPath.isfound(driver, "//div[@class='cartItemsCon']//td[@class='cartinfotd']//a[contains(@href,'" + sku + "')]", "CART: Item is found in cart." + sku);
			return iteminCart;
			
		} catch (Throwable e){
			return false;
		}
		
		
		
	}

 
}


	
	
	
	
	
