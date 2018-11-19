package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.res.Resources;
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
 //   final static String apikey = Resources.getSystem().getString(android.R.string.News_API_Key);



    public static URL buildUrl (String apikey)
    {

 //       newsapiSearchQuery = test_source;

        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(query_parameter, test_source)
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
