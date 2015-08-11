package com.pcm.engine;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Config {

	public static WebDriver driver;
	public static Properties properties;
	public static String browser = "";
	
	public static void setup(String host) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
			
		if(browser.equals("firefox") || browser.isEmpty()){
			driver = new FirefoxDriver();
		}
		
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		if(browser.equals("ie")){
			System.setProperty("webdriver.ie.driver", "lib/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		
		//driver = new FirefoxDriver();
      
		driver.get(host);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        
        System.out.println(host + " Initialized...");
        
	}

	public static Properties properties() throws Exception{
		try{
			FileReader reader = new FileReader("pcm.properties");
			properties = new Properties();
			properties.load(reader);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return properties;
		
	}

				
}
	
	


	

