package ru.itcube.kalc.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import ru.itcube.kalc.listener.RickAndMortyListener;
import ru.itcube.kalc.model.RMAllResponse;

public class RickAndMortyService {
    private RequestQueue queue;
    private static RickAndMortyService service;
    private final String BASIC_PATH= "https://rickandmortyapi.com/api/";
    private final String CHARACTER_PATH ="character/";
    private final String PAGE = "page=";
    private final Gson gson = new Gson();

    public static RickAndMortyService getInstance(Context context){
        if(service==null)service = new RickAndMortyService(context);
        return service;
    }
    private RickAndMortyService(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public Gson getGson(){return gson;};


    public void getCharacter(int page,RickAndMortyListener listener){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                BASIC_PATH + CHARACTER_PATH+"?"+PAGE+page,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()) listener.onSuccess(gson.fromJson(response, RMAllResponse.class));
                        else listener.onError("Response empty");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                }
        );
        queue.add(request);
    }
}
