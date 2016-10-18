package net.lschwarz.javadocker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class JavaDockerRuleTest {

	@ClassRule
	public static JavaDockerRule rule = new JavaDockerRule("wildfly");
	
	@Test
	public void testMain() throws IOException {
		
		URL myWeb = new URL("http://localhost");
        URLConnection yc = myWeb.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
		
	}
}
