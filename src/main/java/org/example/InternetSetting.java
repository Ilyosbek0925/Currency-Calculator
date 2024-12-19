package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InternetSetting {
	public static String setCurrency() {
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpRequest request = null;
		HttpResponse < String > response=null;
		try {
			request = HttpRequest.newBuilder()
					.uri(new URI("https://cbu.uz/uz/arkhiv-kursov-valyut/json/"))
					.GET()
					.build();
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (InterruptedException | URISyntaxException | IOException e) {
			System.out.println(e.getMessage());;
		}
		System.out.println(response.statusCode());
		String body = response.body();
return body;

	}
}
