package com.globalzeal.jasper;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;

import com.jaspersoft.jasperserver.api.common.crypto.CipherI;

public class SSOUtil implements CipherI {
	private static int httpGet ( Configs config, String targetURL, String urlParameters ) {        
		try {
			SSLContext sslctx = SSLContext.getInstance("SSL");
			sslctx.init(null, new X509TrustManager[] { new DummyTrustManager() }, null);
	
			HttpsURLConnection.setDefaultSSLSocketFactory(sslctx.getSocketFactory());
			URL url = new URL(targetURL);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			con.setRequestProperty("Content-Language", "en-US"); 
			con.setRequestProperty("x-engine-id", config.getProperty( "engine_id" ) ); 
			con.setRequestProperty("x-engine-secret", config.getProperty( "engine_secret" )); 
			con.setUseCaches(false);
			con.setDoOutput(false);
			
			InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
            String line;
            while((line = rd.readLine()) != null) {
                 response.append(line);
                 response.append('\r');
            }
            rd.close();
            
            int code = con.getResponseCode();
            con.disconnect(); 
            
            return code;
			
		} catch ( NoSuchAlgorithmException | KeyManagementException | IOException e ) {
			return 500;
		}
	}

	@Override
	public String decrypt(String token) {
		Configs config  = new Configs();
		String tokens[] = token.split( "_", 2 );
		String path     = config.getProperty( tokens[ 0 ] ) + config.getProperty( "auth_api_path" ) + tokens[ 1 ];
	    if( httpGet( config, path, tokens[ 1 ] ) == 200 ) {
	        return config.getProperty( "jasper_cred" );
	    }
	    
	    config.end();
	    return "err_connection_refused";
	}

	@Override
	public String encrypt(String arg0) {
		return null;
	}
}