package com.koopey.service;

import com.koopey.model.Game;

public class GameService {

    public interface GameCrudListener {
        void onGameCreate(int code, String message,String gameId);
        void onGameDelete(int code, String message, Game game);
        void onGameUpdate(int code, String message,Game game);
        void onGameRead(int code, String message,Game game);
    }
}
