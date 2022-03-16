package ru.itcube.kalc.listener;

import ru.itcube.kalc.model.RMAllResponse;

public interface RickAndMortyListener {
    void onSuccess(RMAllResponse response);
    void onError(String message);
}
