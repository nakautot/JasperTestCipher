package com.globalzeal.jasper;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

//TODO: will get actual certificates, server authentication is not necessary yet since we can guarantee that no one will be able to alter the traffic
public class DummyTrustManager implements X509TrustManager {
	public void checkClientTrusted( X509Certificate[] chain, String authType ) {}
	public void checkServerTrusted( X509Certificate[] chain, String authType ) {}

	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}
