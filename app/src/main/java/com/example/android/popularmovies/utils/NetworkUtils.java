package com.example.android.popularmovies.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

/**
 * Created by vivekpatel on 1/22/17.
 */

public class NetworkUtils {
    private final static String api_key="779b716a431271e898c37d99318ae2d9";
    private final static String popularUrl="http://api.themoviedb.org/3/movie/popular?api_key="+api_key;
    private final static String topUrl="http://api.themoviedb.org/3/movie/top_rated?api_key="+api_key;

    public static String getPopularUrl(){
        return popularUrl;
    }
    public static String getTopUrl(){
        return topUrl;
    }

    public static String getInternetConnection(URL url)throws IOException{
        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
        //urlConnection.setRequestMethod("GET");
        //urlConnection.connect();
        try{
            InputStream inputStream=urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb=new StringBuilder();
            String str;
            while((str=bufferedReader.readLine())!=null){
                sb.append(str);
            }
            if(sb.length()==0)return null;
            return sb.toString();
        }finally {
            urlConnection.disconnect();
        }
    }
}
