package me.blackwater.blogsai2.api.handler;

import me.blackwater.blogsai2.api.logger.HandlerLogger;

public interface UpdateHandler<O,D> extends Handler{

    O execute(D dto);


    default void log(){
        if(trace()){
            HandlerLogger.logHandler(this);
        }
    }
}
