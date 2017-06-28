package ch.ccapps.android.zeneggen.util.http;

import ch.ccapps.android.zeneggen.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by celineheldner on 28.06.17.
 */

public class ZeneggenRestClient {

    private static HttpApi zeneggenApi = null;

    public static HttpApi getClient(){
        if (zeneggenApi==null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.IFBASE_STRING)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build();
            zeneggenApi = retrofit.create(HttpApi.class);
        }
        return zeneggenApi;
    }
}
