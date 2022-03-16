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
//Класс который отправляет запросы на сервер и передаёт полученные ответы тем кто их запросил
public class RickAndMortyService {
    //Объект очереди запросов
    private RequestQueue queue;
    //Объект реализующий текущий класс
    private static RickAndMortyService service;
    //Базовый путь до нашего сервера
    private final String BASIC_PATH= "https://rickandmortyapi.com/api/";
    private final String CHARACTER_PATH ="character/";
    private final String PAGE = "page=";
    //Объект для работы с JSON
    private final Gson gson = new Gson();

    //Функция которая возвращает экземпляр сервиса
    //Таким образом мы не множим сущности
    public static RickAndMortyService getInstance(Context context){
        if(service==null)service = new RickAndMortyService(context);
        return service;
    }
    //Конструктор класса в котором создаём очередь запросов
    private RickAndMortyService(Context context){
        queue = Volley.newRequestQueue(context);
    }
    //Возвращаем экземпляр объекта для работы с JSON
    public Gson getGson(){return gson;};

    //Функция которая делает запрос на сервер
    // и привязывает обработчик на событие к этому запросу
    public void getCharacter(int page,RickAndMortyListener listener){
        //Создаём новый объект "Запрос"
        StringRequest request = new StringRequest(
                //Метод запроса
                Request.Method.GET,
                //Путь запроса
                BASIC_PATH + CHARACTER_PATH+"?"+PAGE+page,
                //Обработчик успешного запроса на сервер
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()) listener.onSuccess(gson.fromJson(response, RMAllResponse.class));
                        else listener.onError("Response empty");
                    }
                },
                //Обработчик провального запроса на сервер
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                }
        );
        //Обязательно помещейте новый запрос в очередь запросов
        queue.add(request);
    }
}
