package com.tokiomarine.claim.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class RestRequestHttp {
	
	public String RequestTokenApi(String getUrl,JSONObject restJson){
		
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(getUrl).openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json");

			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(restJson.toString().getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			System.out.println("RequestTokenApi POST Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				//System.out.println(response.toString());				
				
				return response.toString();
			} else {
				System.out.println("POST request not worked");
				return "POST request not worked";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "POST request not worked";
		
	}
	
	public String getPublicKeyTest(String getUrl){
		
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(getUrl).openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type","application/json");
			

			int responseCode = con.getResponseCode();
		//	System.out.println("GET Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				//System.out.println(response.toString());				
				
				return response.toString();
			} else {
				System.out.println("POST request not worked");
				return "POST request not worked";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "POST request not worked";
		
	}
	
	public String CreateRequst(String getUrl,String token,JSONObject restJson){
		
		try {

			HttpURLConnection con = (HttpURLConnection) new URL(getUrl).openConnection();

			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json");

			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(restJson.toString().getBytes());
			os.flush();
			os.close();
			// For POST only - END
			//System.out.println(os.toString());
			int responseCode = con.getResponseCode();
			System.out.println("CreateRequst POST Response Code :: " + responseCode);
			InputStream inputStream;
			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println(response.toString());				
				
				return response.toString();
			} else {
				inputStream = con.getErrorStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder builder = new StringBuilder();
				String lines;
				while ((lines = reader.readLine()) != null) {
					builder.append(lines);
					builder.append(System.getProperty("line.separator"));
				}

				String retStr = builder.toString().trim();
				reader.close();

				//System.out.println("retStr: " + retStr);


				//System.out.println("POST request not worked");
				return "retStr: " + retStr;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "POST request not worked";
		
	}

}
