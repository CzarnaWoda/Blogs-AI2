package me.blackwater.blogsai2.api.handler;

import me.blackwater.blogsai2.api.logger.HandlerLogger;

public interface GetHandler<O,ID> extends Handler{

    O execute(ID id);

    default void log(){
        if(trace()){
            HandlerLogger.logHandler(this);
        }
    }
}
