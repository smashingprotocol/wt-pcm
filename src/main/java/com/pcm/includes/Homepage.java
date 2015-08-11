package com.pcm.includes;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pcm.engine.Config;

public class Homepage {

	public static void setupConfig(String host, String browser) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Config.browser = browser;
		Config.setup(host);		
	}

}
