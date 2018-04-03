package id.aliqornan.themovie.data;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.util.Logger;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor
        implements Interceptor {

    Context context;

    public HttpInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl url =  original.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("language", Locale.getDefault().toString())
                .build();



        Logger.log(Log.INFO, String.format(context.getString(R.string.log_encoded_path), url.encodedPath()));

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();

        long t1 = System.nanoTime();
        Logger.log(Log.INFO, String.format(context.getString(R.string.log_sending_response),
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Logger.log(Log.INFO, String.format(context.getString(R.string.log_receive_response),
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
