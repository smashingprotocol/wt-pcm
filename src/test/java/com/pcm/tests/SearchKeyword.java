package com.pcm.tests;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.grund.engine.Config;
import com.pcm.includes.Homepage;
import com.pcm.includes.Search;
import com.grund.utility.StatusLog;
import com.grund.utility.TakeScreenShot;
import com.grund.verify.verifyXPath;

public class SearchKeyword {
	
	public String keyword;
	public String qty;
	public String env;
	public boolean testStatus;
	Properties sys = System.getProperties();
	
	@Test
	public void searchkeyword() throws Exception{
		
		try{
			
			Homepage.setupConfig(sys.getProperty("host"),sys.getProperty("browser"));
			env = sys.getProperty("pcmHost");
			Properties pr = Config.properties("pcm.properties"); //create a method for the pcm.properies
			
			keyword = pr.getProperty("SEARCH_KEYWORD_DFLT");
			qty = pr.getProperty("SEARCH_INPUT_QTY_XPATH");
			
			Search.keyword(Config.driver,keyword);
			Assert.assertTrue(verifyXPath.isfound(Config.driver,qty));  //Verify the Qty field in search results appears
			StatusLog.printlnPassedResult(Config.driver,"[PCM] Search for keyword: " + keyword);
			
		
		} catch (Exception e){
			StatusLog.printlnFailedResult(Config.driver,"[PCM] Search for keyword: " + keyword);
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
