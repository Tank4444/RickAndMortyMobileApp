package ru.itcube.kalc.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import ru.itcube.kalc.listener.RickAndMortyListener;

public class RickAndMortyService {
    private RequestQueue queue;
    private static RickAndMortyService service;
    private final String BASIC_PATH= "https://rickandmortyapi.com/api/";
    private final String CHARACTER_PATH ="character/";

    public static RickAndMortyService getInstance(Context context){
        if(service==null)service = new RickAndMortyService(context);
        return service;
    }
    private RickAndMortyService(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getCharacter(RickAndMortyListener listener){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                BASIC_PATH + CHARACTER_PATH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()) listener.getResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }
}
