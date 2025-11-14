package me.blackwater.blogsai2.api.handler;

public interface Handler {

    default boolean trace(){
        return false;
    }


}
