package ch.ccapps.android.zeneggen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.glue.fagime.Config;


/**
 * Created by pauli on 25.07.14.
 */
public final class HttpHelper {

    public static final String CONNECTION_FAIL = "NO_CONNECTION";
    private static final String TAG = HttpHelper.class.getSimpleName();
    private static final int TIMEOUT = 15000; // in milliseconds
    private String endpoint;


    public HttpHelper(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param body     the POST body.
     * @throws IOException propagated from POST.
     */
    public static void doPost(String endpoint, String body)
            throws IOException {

        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }

        ch.glue.fagime.util.Logger.i(TAG, "Posting body: '" + body + "' to " + url);


        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");

            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            // handle the response
            int status = conn.getResponseCode();
            ch.glue.fagime.util.Logger.i(TAG, "PostStatus: " + status + "/" + conn.getResponseMessage());


            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    public String doGet(Map<String, String> paramMap) {
        Map<String, List<String>> paramMapList = new HashMap<String, List<String>>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                List<String> list = new ArrayList<String>();
                list.add(paramMap.get(key));
                paramMapList.put(key, list);
            }
        }
        return doGetMap(paramMapList, false);
    }

    public String doGetMulti(Map<String, List<String>> paramMap) {
        return doGetMap(paramMap, false);
    }

    public String doGet(Map<String, String> paramMap, boolean checkConnection) {
        Map<String, List<String>> paramMapList = new HashMap<String, List<String>>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                List<String> list = new ArrayList<String>();
                list.add(paramMap.get(key));
                paramMapList.put(key, list);
            }
        }
        return doGetMap(paramMapList, false);
    }

    public String doGetMap(Map<String, List<String>> paramMap, boolean checkConnection) {
        try {
            String url = buildUrl(endpoint, paramMap);
            ch.glue.fagime.util.Logger.i(TAG, "doGet: " + url);

            URL src = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) src.openConnection();
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);

            InputStream is = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            StringBuilder result = new StringBuilder();
            char[] buffer = new char[1024];
            int len;
            while ((len = reader.read(buffer, 0, buffer.length)) > 0) {
                result.append(buffer, 0, len);
            }
            reader.close();
            ch.glue.fagime.util.Logger.i(TAG, "result: " + result.toString());

            return result.toString();

        } catch (IOException e) {
            ch.glue.fagime.util.Logger.e(TAG, "HttpHelper.doGet failed: " + e.getMessage());

        }
        if (checkConnection) {
            return CONNECTION_FAIL;
        }
        return null;
    }

    private String buildUrl(String baseUrl, Map<String, List<String>> paramMap)
            throws UnsupportedEncodingException {

        StringBuilder urlBuilder = new StringBuilder(baseUrl);

        if (baseUrl.contains("?")) {
            urlBuilder.append("&");
        } else {
            urlBuilder.append("?");
        }
        urlBuilder.append("app=");
        urlBuilder.append(URLEncoder.encode(Config.APP_PARAM, "UTF-8"));

        /*urlBuilder.append("deviceType=");
        urlBuilder.append(URLEncoder.encode(Device.Model(), "UTF-8"));

        paramMap.put("id", Device.AndroidId(context));
        paramMap.put("deviceType", Device.Model());
        paramMap.put("version", Device.OsVersion());*/

        if (paramMap == null) {
            return urlBuilder.toString();
        }

        for (String key : paramMap.keySet()) {

            List<String> values = paramMap.get(key);
            for (String value : values) {
                urlBuilder.append("&");

                if (value != null) {
                    urlBuilder.append(URLEncoder.encode(key, "UTF-8"));
                    urlBuilder.append("=");
                    urlBuilder.append(URLEncoder.encode(value, "UTF-8"));
                } else {
                    ch.glue.fagime.util.Logger.w(TAG, "Value for Key=" + key + " is null");
                }
            }

        }
        return urlBuilder.toString();
    }

    public String postJson(String json) throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            ch.glue.fagime.util.Logger.e(TAG, "Malformed URL'");
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }

        ch.glue.fagime.util.Logger.i(TAG, "Posting body: '" + json + "' to " + url);


        byte[] bytes = json.getBytes();
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");

            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            // handle the response
            int status = conn.getResponseCode();
            ch.glue.fagime.util.Logger.i(TAG, "PostStatus: " + status + "/" + conn.getResponseMessage());


            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
            InputStream in = conn.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();

        } finally {
            if (conn != null) conn.disconnect();
        }

    }

}
