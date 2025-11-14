package me.blackwater.blogsai2.api.handler;

import me.blackwater.blogsai2.api.logger.HandlerLogger;

public interface DeleteHandler<ID> extends Handler{

    void execute(ID id);


    default void log(){
        if(trace()){
            HandlerLogger.logHandler(this);
        }
    }
}
