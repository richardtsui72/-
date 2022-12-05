package com.cubd.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OkHttpClientUtil {

    private Logger logger = LogManager.getLogger(this.getClass());

    private OkHttpClient client = new OkHttpClient().newBuilder().build();

    public String get(String url)
    {
        String responseStr = "";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful())
                responseStr = response.body().string();
            else
                logger.error("Action : GET {} Fail ! ",url);
        }catch (Exception e){
            logger.error(e.getStackTrace());
        }

        return responseStr;
    }

}
