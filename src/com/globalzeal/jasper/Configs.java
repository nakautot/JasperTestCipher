package com.globalzeal.jasper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configs {
	private static String PATH = "/gz_config.properties";
	private Properties configFile;
	private FileInputStream file;
	
	Configs () {
		try {
			configFile = new Properties();
			File jarPath=new File( Configs.class.getProtectionDomain().getCodeSource().getLocation().getPath() );
	        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	        configFile.load( new FileInputStream( propertiesPath + PATH ) );
		} catch ( IOException e ) {
			// Do nothing
		}
	}
	
	public String getProperty ( String key ) {
		return configFile.getProperty( key );
	}
	
	public void end () {
		try {
			file.close();
		} catch (IOException e) {
			// Do nothing
		}
	}
}
