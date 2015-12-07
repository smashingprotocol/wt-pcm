package com.pcm.includes;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.grund.engine.Config;
import com.grund.form.SetInputField;
import com.grund.form.SetSelectField;
import com.grund.request.ClickElement;
import com.grund.utility.StatusLog;
import com.grund.utility.Wait;
import com.grund.verify.verifyXPath;


public class Search {

	public static final String SEARCH_BTN_CLICKHERE_XPATH="//a[@title='Click Here']";
	public static final String SEARCH_INPUT_UPPMODALEMAIL_XPATH="//input[@id='uppEmailAddress']";
	public static final String SEARCH_BTN_UPPMODALSUBMIT_XPATH="//input[@value='Submit']";
	public static final String SEARCH_BTN_UPPMODALADDTOCART_XPATH="//div[@class='btn add-to-cart']";
	public static final String SELECT_SORTBY_XPATH = "//select[@name='toolbarSort']";
	public static final String ITEMCOL_XPATH = "//div[@class='itemlist lst']";
	public static final String SELECT_VIEWBY_XPATH = "(//div[@class='viewby']//select) [position()=1]";
	public static final String CHK_SELECTEDBRANDFILTERED_XPATH = "//label[@for='filter_man_0']";
	public static final String LINK_PDP_XPATH = "//div[@class='rtitle']//a[@data-action='Link:Product Detail Page']";
	public static final String BTN_GRIDVIEW_XPATH = "//div[@title='Grid View']";
	public static final String INPUT_MINPRICE_XPATH = "//input[@class='minPrice']";
	public static final String INPUT_MAXPRICE_XPATH = "//input[@class='maxPrice']";
	public static final String DIV_GRIDVIEW_XPATH = "//div[@class='rcview grid']";
	public static final String IMG_FREEAFTERREBATES_XPATH = "//div[@class='violators icn-vio-far']";
	public static final String LABEL_INSTANTSAVINGS_XPATH = "//div[contains(text(),'Instant Savings')]";
	public static final String LABEL_MAILINSAVINGS_XPATH = "//div[contains(text(),'Mail-in Savings')]";
	public static final String LINK_WARRANTYMODAL_ADDITEMCART_XPATH = "(//a[@data-item-type='warranty']) [position()=1]";
	public static final String MODAL_ADDTOCART_XPATH = "//div[@id='add-to-cart-modal-widget-33' and not(contains(@style,'display: none'))]";
	public static final String MODAL_BTN_PROCEEDTOCART_XPATH = "//div[@id='add-to-cart-modal-widget-33' and not(contains(@style,'display: none'))]//a[@href='/n/Shopping-Cart/msc-cart' and @class='pdp-btn-style']";
	public static final String BTN_RELATEDCONTENT_XPATH = "//a[@class='ga' and contains(@data-action,'Related Content Search')]";
	public static final String DIV_RELATEDCONTENT_XPATH = "id('content-search-48')";
	public static final String UPP_UPPSUCCESSMSG_XPATH = "//div[@class='modal-content']//p[contains(text(),'A price quote has been sent to this email address')]";
	public static final String BTN_MODAL_CONTINUESHOPPING_XPATH = "//button[text()='Continue Shopping']";
	public static final String BTN_COMPARE_XPATH = "//a[@class='compare-btn']";
	public static final String IMG_ADDINCART_XPATH = "//div[@class='violators icn-vio-slp']";
	public static final String BTN_ADDINCART_XPATH = "//a[@title='Add to Cart']";
	public static final String LBL_SOLDOUT_XPATH = "//span[@class='statavlb' and text()='Sold Out']";
	public static final String INPUT_SEARCHFIELD_XPATH = "(//input[@placeholder='Search Products']) [position()=1]";
	public static final String LINK_SEARCHGO_XPATH = "//a[@class='searchInputBtn']";
	public static final String BTN_NEXTPAGE_XPATH = "(//a[@class='next ']) [position()=1]";
	
	public static String addedWarrantySku;

	public static Properties sys = System.getProperties();
	public static Properties properties;
	
	public static void keyword(WebDriver driver,String keyword) throws Exception {
		
		try {
			
			
			System.out.println("[STEP] SEARCH FOR A KEYWORD.");
			SetInputField.byXPath(driver,INPUT_SEARCHFIELD_XPATH,keyword, "SEARCH: Enter keyword on the Search field.");
			ClickElement.byXPath(driver,LINK_SEARCHGO_XPATH,"SEARCH: Click Go on the Search field.");
			
			//Temporary workaround for the search redesign.
			//driver.get(sys.getProperty("host") + "search?rch=&q=" + keyword);
			
		} catch (Exception e){
			
			
		}
		
		
	}

	public static void addtocart(WebDriver driver, String sku, String qty) throws Exception{
		
		try{
		System.out.println("[STEP] IN SEARCH, SELECT ITEM QTY AND CLICK ADD TO CART.");
		SetSelectField.selectTextbyXPath(driver, "//div[@data-sku='" + sku + "']//select[@class='qty-text qty-input']", qty, "SEARCH: Select quantity");
		ClickElement.byXPath(driver,"//a[@href='#sku=" + sku + "']"); // Click the Add to cart button in the search.
		
		
		} catch (Exception e){
			Assert.fail("[SEARCH] ADD ITEM TO CART...FAILED");
		}
	}

	public static void uppSubmitEmailFinalPrice(WebDriver driver,
			String sku, String email) throws Exception {
		
		//Search keyword and click Click Here
		Search.keyword(driver, sku);
		ClickElement.byXPath(driver, SEARCH_BTN_CLICKHERE_XPATH,"SEARCH: Click Here Button to display UPP.");
		SetInputField.byXPath(driver, SEARCH_INPUT_UPPMODALEMAIL_XPATH, email, "SEARCH: Enter email address at the UPP Modal:" + email);
		ClickElement.byXPath(driver, SEARCH_BTN_UPPMODALSUBMIT_XPATH, "SEARCH: Click Submit button at the UPP Modal.");
		
	}

	public static void uppAddtoCart(WebDriver driver, String sku) throws Exception {
		
		//Search keyword and click Click Here
		Search.keyword(driver, sku);
		ClickElement.byXPath(driver, SEARCH_BTN_CLICKHERE_XPATH, "SEARCH: Click Here to Display the UPP Modal");
		ClickElement.byXPath(driver, SEARCH_BTN_UPPMODALADDTOCART_XPATH, "SEARCH: Click Add to Cart button at the UPP Modal");
		
	}

	public static void setFacetPriceRange(WebDriver driver, String minPrice,
			String maxPrice) throws Exception {

		if(!minPrice.isEmpty()){
			//SetInputField.byXPathThenSubmit(driver, INPUT_MINPRICE_XPATH, "");
			SetInputField.byXPathThenSubmit(driver, INPUT_MINPRICE_XPATH, minPrice, "SEARCH: Enter Minimum Price at Price Range field.");
			Wait.sleep("3");
		}
		
		if(!maxPrice.isEmpty()){
			//SetInputField.byXPathThenSubmit(driver, INPUT_MAXPRICE_XPATH, "");
			SetInputField.byXPathThenSubmit(driver, INPUT_MAXPRICE_XPATH, maxPrice, "SEARCH: Enter Maximum Price at Price Range field.");
			Wait.sleep("3");
		}
		
	}

	public static void clickFilterResultsBtn(WebDriver driver) throws Exception {
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_FILTERRESULTS_XPATH"));
		
	}

	public static void selectComparebySku(WebDriver driver, String sku) throws Exception {
		System.out.println("[STEP] SELECT ITEMS TO COMPARE" );
		ClickElement.byXPath(driver, "//input[@id='chkCompare" + sku + "']", "SEARCH: Select Compare checkbox: " + sku);
		
	}

	public static void compareSkus(WebDriver driver,
			ArrayList<String> skuList) throws Exception {
		
		System.out.println("Initialize number of items to compare: " + skuList.size());
		
		for(int i = 0; i < skuList.size(); i++){
			Search.keyword(driver,skuList.get(i));
			Search.selectComparebySku(driver,skuList.get(i));
		}
		
		ClickElement.byXPath(driver, BTN_COMPARE_XPATH, "SEARCH: Click Compare Button.");
		
		
	}

	public static void sortByLowestPriceFirst(WebDriver driver) throws Exception {
		
		/*WebElement sortBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_SORTBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",sortBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_SORTBY_XPATH"), "PcMall_CartPrice:::asc");*/
		
		SetSelectField.byXPath(driver, SELECT_SORTBY_XPATH, "asc", "SEARCH: Sort By Lowest Price First");
		
		
	}

	public static void sortByHighestPriceFirst(WebDriver driver) throws Exception {
		
		/*WebElement sortBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_SORTBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",sortBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_SORTBY_XPATH"), "PcMall_CartPrice:::desc");*/
		
		SetSelectField.byXPath(driver, SELECT_SORTBY_XPATH, "desc", "SEARCH: Sort By Highest Price First");
		
	}

	public static void selectViewBy(WebDriver driver, String view) throws Exception {
		
		/*WebElement viewBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_VIEWBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",viewBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_VIEWBY_XPATH"), view);*/
		Wait.sleep("3");
		SetSelectField.byXPath(driver, SELECT_VIEWBY_XPATH, view);
		
	}

	public static void sortByBestMatch(WebDriver driver) throws Exception {
		
		/*WebElement sortBySelect = driver.findElement(By.xpath(properties.getProperty("SEARCH_SELECT_SORTBY_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style', 'display: block;')",sortBySelect);
		SetSelectField.byXPath(driver, properties.getProperty("SEARCH_SELECT_SORTBY_XPATH"), "");*/
		
		SetSelectField.byXPath(driver, SELECT_SORTBY_XPATH, "", "SEARCH: Sort By Best Match");
		
		
	}

	public static void ctoModalClickConfigureItems(WebDriver driver, String sku, String qty) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//Search keyword and click Click Here
		Search.keyword(Config.driver, sku);
		Search.addtocart(Config.driver, sku, qty);
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_MODAL_CONFIGURE_XPATH"));
		
	}

	public static void ctoModalClickProceedtoCart(WebDriver driver,
			String sku, String qty) throws Exception {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		properties.load(reader);
		
		//Search keyword and click Click Here
		Search.keyword(Config.driver, sku);
		Search.addtocart(Config.driver, sku, qty);
		
		ClickElement.byXPath(driver, properties.getProperty("SEARCH_BTN_MODALCTO_PROCEEDCART_XPATH"));
	}

	public static void filterByBrand(WebDriver driver, String brand) throws Exception {
		System.out.println("[STEP] IN SEARCH FACET, SELECT A BRAND.");
		ClickElement.byXPath(driver, "//input[@value='" + brand + "']");
		//Search.clickFilterResultsBtn(driver);
		
	}

	public static String getBrandFacetCount(WebDriver driver, String brand) throws Exception {
		
		String brandCount;
		
		brandCount = verifyXPath.getText(driver, CHK_SELECTEDBRANDFILTERED_XPATH);
		brandCount = brandCount.replaceAll("[^\\d.]", "");
		brandCount = brandCount.replaceAll("[.]", "");
		
		return brandCount;
		
	}

	public static void openPDPbySearchResult(WebDriver driver, String keyword) throws Exception {
		
		ClickElement.byXPath(Config.driver, "(" + LINK_PDP_XPATH + ") [position()=1]", "SEARCH: Click the 1st Item Product Name to open PDP.");
		
	}

	public static void setGridView(WebDriver driver) throws Exception {
		
		ClickElement.byXPath(Config.driver, BTN_GRIDVIEW_XPATH);
	}

	public static void warrantyModalAddtoCart(WebDriver driver,
			String warrantyqty) throws Exception {
		
		Boolean testStatus = verifyXPath.isfound(Config.driver,LINK_WARRANTYMODAL_ADDITEMCART_XPATH);
		StatusLog.printlnPassedResultTrue(driver,"[VERIFY] Add item to cart button of Warranty Modal is display",testStatus);
		//Failed test case if add to cart is not found.
		Assert.assertTrue(StatusLog.errMsg, StatusLog.tcStatus);
		
		addedWarrantySku = verifyXPath.getAttributeValue(driver, LINK_WARRANTYMODAL_ADDITEMCART_XPATH, "data-sku", "SEARCH: Store the warranty sku that will be added to cart.");
		
		ClickElement.byXPath(driver,LINK_WARRANTYMODAL_ADDITEMCART_XPATH); // Click the Add to cart button in the modal.
		
		
		
		
	}

	public static void openPDPbySearchSku(WebDriver driver, String sku) throws Exception {
		
		Search.keyword(Config.driver, sku);
		ClickElement.byXPath(Config.driver, "//a[@data-action='Link:Product Detail Page' and contains(@href,'" + sku + "')]");

		
	}

	public static void clickBuyNowCustomize(WebDriver driver, String sku) {
	
		ClickElement.byXPath(driver, "//a[@id='customizeButtonFor" + sku + "']", "SEARCH: Click Buy Now Customize button.");
		
	}

}
