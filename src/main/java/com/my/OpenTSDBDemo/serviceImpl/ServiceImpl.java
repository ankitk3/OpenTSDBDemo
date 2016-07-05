package com.my.OpenTSDBDemo.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import com.my.OpenTSDBDemo.pojo.OpenTSDBQueryParameter;

public class ServiceImpl {

	public static final String DATE_FORMAT = "yyyy/MM/dd-HH:mm:ss";
	
	public void putData() throws Exception{
		OpenTSDBQueryParameter parameter = getParameter();
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(getBaseURL()+"/api/put");
		//httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json");
		//httpPost.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		HttpResponse response = null;
		//HttpEntity entity = null;
		//InputStream instream = null;
		String body="{\"metric\":\"CONN_ERROR.COUNT\",\"timestamp\":\"" + System.currentTimeMillis() + "\", \"value\": \"22\", \"tags\": { \"host\": \"localhost\", \"dc\": \"mum\" }}";
		HttpEntity entity = new StringEntity(body);
		httpPost.setEntity(entity);
		try {
			StringBuilder sb = new StringBuilder();
			response = httpclient.execute(httpPost);
			/*entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(line+"\n");
				}
			}*/
			System.out.println(response);
			//System.out.println(sb.toString());
			//JSONArray json = new JSONArray(sb.toString());
			//System.out.println(json);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			//instream.close();
			httpclient.getConnectionManager().shutdown();
		}
	}
	@SuppressWarnings("deprecation")
	public void getData() throws Exception{
		OpenTSDBQueryParameter parameter = getParameter();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpget = new HttpPost(getURLForMetricsData(parameter));
		httpget.addHeader("Accept", "application/json");
		httpget.addHeader("Content-type", "application/json");
		HttpResponse response = null;
		HttpEntity entity = null;
		InputStream instream = null;
		try {
			StringBuilder sb = new StringBuilder();
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			if (entity != null) {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(line+"\n");
				}
			}
			//System.out.println(sb.toString());
			JSONArray json = new JSONArray(sb.toString());
			System.out.println(json);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			instream.close();
			httpclient.getConnectionManager().shutdown();
		}
		//timeSeriesMetricVO.setMetricName(parameter.getMetricName());
		//return timeSeriesMetricVO;
	}

	/*private TimeSeriesRecord getTimeSeriesRecord(String timeSeriesResponse) {
		String[] timeSeriesDataArray = timeSeriesResponse.split(" ");
		if (timeSeriesDataArray.length < 3)
			return null;
		TimeSeriesRecord record = new TimeSeriesRecord();
		record.setTimestamp(Long.parseLong(timeSeriesDataArray[1]));
		record.setValue(Long.parseLong(timeSeriesDataArray[2]));
		return record;
	}*/

	public String getURLForMetricsData(OpenTSDBQueryParameter parameter) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(getBaseURL()).append("/q?start=").append(formatDate(parameter.getStartDate())).append("&end=")
				.append(formatDate(parameter.getEndDate())).append("&m=").append(parameter.getAggregator()).append(":")
				.append(parameter.getMetricName()).append("&ascii");
		System.out.println(sb.toString());
		return sb.toString();
	}

	private String formatDate(Date pDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return simpleDateFormat.format(pDate);
	}

	private String getBaseURL() throws IOException {
		return "http://localhost:4242";
		/*
		 * Properties configProperties = new Properties();
		 * configProperties.load(
		 * this.getClass().getClassLoader().getResourceAsStream
		 * ("opentsdb.properties")); return
		 * configProperties.getProperty("base.url");
		 */
	}
	private OpenTSDBQueryParameter getParameter(){
		OpenTSDBQueryParameter parameter = new OpenTSDBQueryParameter();
		parameter.setAggregator("sum");
		Date now = new Date();
		now.setMinutes(0);
		parameter.setStartDate(now);
		parameter.setEndDate(new Date());
		parameter.setMetricName("CONN_ERROR.COUNT");
		return parameter;
	}
}