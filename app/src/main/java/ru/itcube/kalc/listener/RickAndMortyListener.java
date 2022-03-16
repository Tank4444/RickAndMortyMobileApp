package ru.itcube.kalc.listener;

import ru.itcube.kalc.model.RMAllResponse;

//Интерфейс который используеться для создания объекта
//который мы передадим в сервис для обработки ответа от запроса
public interface RickAndMortyListener {
    //В случае успеха
    void onSuccess(RMAllResponse response);
    //В случае провала
    void onError(String message);
}
