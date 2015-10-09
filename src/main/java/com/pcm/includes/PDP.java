package com.pcm.includes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;

import com.pcm.engine.Config;
import com.pcm.form.SetInputField;
import com.pcm.request.ClickElement;
import com.pcm.utility.StringFormatter;
import com.pcm.verify.verifyXPath;

public class PDP {

	public static Properties properties;
	public static String PLUGIN_WEBCOLLAGE_XPATH="//img[@src='http://content.webcollage.net/pcmall/resources/images/tour_button.png']";
	public static String PLUGIN_SELLPOINT_XPATH="//img[@src='http://sbw.sellpoint.net/_apt_/_pgraphic_/300/74/default/view_demo.png']";
	public static String PLUGIN_CNETREVIEW_XPATH="//img[@src='http://cdn.cnetcontent.com/logo/icons/cnetreview/stars-nh/3_4.0.png']";
	public static String PLUGIN_BUYOFFICE_XPATH="//img[@src='http://cdn.cnetcontent.com/logo/icons/office2013/3_en.gif']";
	public static String PLUGIN_BUYMICROSOFT_XPATH="//img[@src='http://cdn.cnetcontent.com/logo/icons/msaccessories/3_en.gif']";
	public static String PLUGIN_CNETEPEAT_XPATH="//img[@src='http://cdn.cnetcontent.com/logo/icons/epeat/3_gold_en.gif']";
	public static String PLUGIN_HPCAREPACK_XPATH="//img[@src='http://cdn.cnetcontent.com/logo/icons/hp/crosssell/3_en.gif']";
	public static String EXPAND_ACCORDION_WARRANTIES_XPATH="//a[@href='#accordion-content-220']";
	public static String SPECIALOFFER_MAILINREBATE_XPATH="//div[@id='special-offers-widget-177']//p[contains(text(),'mail-in rebate')]";
	public static String SPECIALOFFER_INSTANTREBATE_XPATH="//div[@id='special-offers-widget-177']//p[contains(text(),'Instant Rebate')]";
	public static String INPUT_SEARCHFIELD_XPATH="id('search-menu-67')//input";
	public static String LINK_SEARCHGO_XPATH="//a[@class='searchInputBtn']//span";
	public static String PDP_INPUT_EDPNO_XPATH="//div[@id='add-to-cart-btn-157']";
	public static String PDP_NEW_MODAL_CLOSE_XPATH="(//button[@class='close' and @data-dismiss='modal']) [position()=1]";
	public static String PCMG_PDP_NEW_MODAL_CLOSE_XPATH="(//button[@class='close' and @data-dismiss='modal']) [position()=2]";
	public static String PDP_UPPMODAL_CLOSE_XPATH="(//button[@class='close']) [position()=1]";
	public static String PCMG_PDP_UPPMODAL_CLOSE_XPATH="(//button[@class='close']) [position()=2]";	
	public static String SCRIPT_SELLPOINT_XPATH="//div[@id='sellpoint']//script[contains(text(),'//a.sellpoint.net/c/74/sp.js')]";	
	public static String LABEL_DISCOUNTEDPRICE_XPATH="//div[@class='rebates-con']//div[text()='Discounted Price:']";	
	public static String BTN_CLICKHERELOWESTPRICE_XPATH="//div[text()='Click Here for Lowest Price']";	
	
	
	
	public static boolean pluginfound;
	public static boolean mailinfound;
	public static boolean instantrebatefound;
	public static boolean xpathfound;
	
	
	
	public static String getEDPNo(WebDriver driver, String sku) throws FileNotFoundException {
		
		FileReader reader = new FileReader("pcm.properties");
		properties = new Properties();
		
		String EDP;
		
		
		try{
			
			properties.load(reader);
			
			Search.keyword(driver, sku);
			//click the description in search result of skus
			ClickElement.byXPath(driver, properties.getProperty("SEARCH_LINK_PDP_XPATH"));
			EDP = verifyXPath.getAttributeValue(driver, PDP_INPUT_EDPNO_XPATH, "data-parent-edp");
			
			return EDP;
			
		} catch (Exception e){
			Assert.fail("[PDP] Error in getting the EDPNo in PDP" + e.getMessage());
			return null;
		} 
		
		
	}

	public static boolean is3rdPartyPluginFound(WebDriver driver,
			String pluginCode) throws Exception {
		
		if (pluginCode.equals("sellpoint")){
			pluginfound = verifyXPath.isfound(driver, PLUGIN_SELLPOINT_XPATH);
			return pluginfound;
			
		}else if (pluginCode.equals("webcollage")){
			pluginfound = verifyXPath.isfound(driver, PLUGIN_WEBCOLLAGE_XPATH);
			return pluginfound;	
			
		}else if (pluginCode.equals("cnetreview")){
			pluginfound = verifyXPath.isfound(driver, PLUGIN_CNETREVIEW_XPATH);
			return pluginfound;		
		
		}else if (pluginCode.equals("buyoffice")){
			pluginfound = verifyXPath.isfound(driver, PLUGIN_BUYOFFICE_XPATH);
			return pluginfound;			
		
		}else if (pluginCode.equals("cnetepeat")){
			pluginfound = verifyXPath.isfound(driver, PLUGIN_CNETEPEAT_XPATH);
			return pluginfound;			
			
		}else if (pluginCode.equals("hpcarepack")){
			pluginfound = verifyXPath.isfound(driver, PLUGIN_HPCAREPACK_XPATH);
			return pluginfound;			
			
		}else {
			return false;
		}
		
		
	}

	public static void expandWarrantiesAccordion(WebDriver driver) throws Exception {
		
		ClickElement.byXPath(driver, EXPAND_ACCORDION_WARRANTIES_XPATH);
		
	}

	public static void addtoCartfromWarrantiesTab(WebDriver driver,
			String warrantysku) throws Exception {
			
		ClickElement.byXPath(driver, "//a[@href='#sku=" + warrantysku + "']");
		
		
	}

	public static boolean mailinRebateSpecialOfferFound(WebDriver driver) {
		try {
			mailinfound = verifyXPath.isfound(driver, SPECIALOFFER_MAILINREBATE_XPATH);
			return mailinfound;
			
		} catch (Throwable e) {
			
			return false;
		}
		
	}

	public static boolean instantRebateSpecialOfferFound(WebDriver driver) {
		try {
			instantrebatefound = verifyXPath.isfound(driver, SPECIALOFFER_INSTANTREBATE_XPATH);
			return instantrebatefound;
			
		} catch (Throwable e) {
			
			return false;
		}
	}

	public static void searchKeyword(WebDriver driver, String keyword) throws Exception {
		
		//header search field and go responsive element.
		SetInputField.byXPath(driver,INPUT_SEARCHFIELD_XPATH,keyword);
		ClickElement.byXPath(driver, LINK_SEARCHGO_XPATH);
		
	}

	public static void closeAddtoCartModal(WebDriver driver, String store) throws Exception {
		
		try{
			ClickElement.byXPath(Config.driver, PDP_NEW_MODAL_CLOSE_XPATH);
		} catch (Throwable e) {
			ClickElement.byXPath(Config.driver, PCMG_PDP_NEW_MODAL_CLOSE_XPATH);
		}
		
	}

	public static void closeUPPModal(WebDriver driver) throws Exception {
		
		try{
			ClickElement.byXPath(Config.driver, PDP_UPPMODAL_CLOSE_XPATH);
		} catch (Throwable e) {
			ClickElement.byXPath(Config.driver, PCMG_PDP_UPPMODAL_CLOSE_XPATH);
		}
		
		
		
		
	}

	public static boolean isSellPointScriptFound(WebDriver driver, String sku) throws Exception {
		boolean spfound = verifyXPath.isfound(driver, SCRIPT_SELLPOINT_XPATH);
		boolean spworldfound = verifyXPath.isfound(driver,"//script[contains(text(),'" + sku + "') and contains(text(),'SPWORLD.push')]");
		
		if(spfound && spworldfound){
			return true;
		} else {
			return false;
		}
		
		
	}

	public static boolean discountedPrice(WebDriver driver, String price) throws Exception {
		
		boolean dpFound = verifyXPath.isfound(driver, LABEL_DISCOUNTEDPRICE_XPATH);
		boolean dpAmtFound = verifyXPath.isfound(driver,"//div[@class='rebates-con']//div[text()='$" + price + "']");
		
		if(dpFound && dpAmtFound){
			return true;
		} else {
			
			return false;
		}
	}

	public static void displayPriceDetailClickLowestPrice(WebDriver driver) throws Exception {
		
		ClickElement.byXPath(driver, BTN_CLICKHERELOWESTPRICE_XPATH);
		
	}

	public static String getDiscountAmt(WebDriver driver, String listPrice,
			String finalPrice) {
		
		Float intDiscount = Float.valueOf(listPrice.replaceAll(",", "")) - Float.valueOf(finalPrice.replaceAll(",", ""));
		String amt = StringFormatter.formatFloattoCommaDecimalNumString(intDiscount,"#,###");
		
		return amt;
		
	}

	public static boolean discountAmtFound(WebDriver driver, String discountAmt) throws Exception {
		
		boolean discountAmtFound = verifyXPath.isfound(driver,"//div[@class='save-price-con']//span[text()='$" + discountAmt + "']");
	
		
		if(discountAmtFound){
			return true;
		} else {
			return false;
		}
	}

	public static String getDiscountPercentage(WebDriver driver,
			String listPrice, String discountAmt) throws Exception{
		
		Float percentValue = (Float.valueOf(discountAmt.replaceAll(",", "")) / Float.valueOf(listPrice.replaceAll(",", "")))*100;
		String percentage = StringFormatter.formatFloattoCommaDecimalNumString(percentValue,"#,###");
		
		return percentage;
	}

	public static boolean discountPercentageFound(WebDriver driver,
			String discountPercent) throws Exception {
		
		boolean discountPercentageFound = verifyXPath.isfound(driver,"//div[@class='pricing-details']//span[@class='percent' and contains(text(),'(" + discountPercent + "%)')]");
	
		
		if(discountPercentageFound){
			return true;
		} else {
			return false;
		}
		
		
	}

}
