package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String base_url = "https://newsapi.org/v1/articles";
    final static String query_parameter = "source";

    final static String test_source = "the-next-web";

    final static String PARAM_SORT = "sortBy";
    final static String sortby = "latest";

    final static String PARAM_KEY = "apiKey";
    final static String apikey = "3769b21d5e9a405cb90a7fec9468bdc0";



    public static URL buildUrl (String newsapiSearchQuery)
    {

        newsapiSearchQuery = test_source;

        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(query_parameter, newsapiSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortby)
                .appendQueryParameter(PARAM_KEY, apikey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static String getResponseFromHttpUrl( URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
            {
                return scanner.next();
            }
            else
            {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }

    }



}
