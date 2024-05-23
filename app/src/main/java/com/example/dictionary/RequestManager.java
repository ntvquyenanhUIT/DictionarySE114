package com.example.dictionary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dictionary.Models.APIResponse;
import com.example.dictionary.Models.WordOfTheDayAPIResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;



public class RequestManager {
    Context context;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retrofit retrofitWordOfTheDay = new Retrofit.Builder()
    .baseUrl("https://urban-dictionary7.p.rapidapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getWordMeaning(OnFetchDataListener listener, String word){
        CallDictionary callDictionary = retrofit.create(CallDictionary.class);
        Call<List<APIResponse>> call = callDictionary.callMeanings(word);
        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body().get(0), response.message());
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable throwable) {
                    listener.onError("Request Failed!");
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "An Error Occurred!!!",Toast.LENGTH_SHORT).show();
        }
    }


    public void getWordOfTheDay(WordOfTheDayOnFetchDataListener listener){
        GetWordOfTheDay getWordOfTheDay = retrofitWordOfTheDay.create(GetWordOfTheDay.class);
        Call<WordOfTheDayAPIResponse> call = getWordOfTheDay.getWordOfTheDay();
        try{
            call.enqueue(new Callback<WordOfTheDayAPIResponse>() {
                @Override
                public void onResponse(Call<WordOfTheDayAPIResponse> call, Response<WordOfTheDayAPIResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<WordOfTheDayAPIResponse> call, Throwable throwable) {
                    Log.d("RequestManager", "Request Failed", throwable);
                    listener.onError("Request Failed!");
            }
        });
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "An Error Occurred!!!",Toast.LENGTH_SHORT).show();
        }
}



    public interface CallDictionary{
        @GET("entries/en/{word}")
        Call<List<APIResponse>> callMeanings(
                @Path("word") String word
        );
    }
    public interface GetWordOfTheDay {
    @Headers({
        "X-RapidAPI-Key: 9174706774msh76dba5d0ef015ffp121c2ejsn26b8d41b884f",
        "X-RapidAPI-Host: urban-dictionary7.p.rapidapi.com"
    })
    @GET("v0/words_of_the_day")
    Call<WordOfTheDayAPIResponse> getWordOfTheDay();
    }

}
