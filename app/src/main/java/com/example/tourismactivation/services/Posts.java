package com.example.tourismactivation.services;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import java.util.*;

public class Posts
{
    static final String BACKENDLESS_HOST = "https://api.backendless.com";
    static final String SERVICE_NAME = "Posts";
    static final String APP_ID = "31908FE0-A688-43D5-879D-B815B9404108";
    static final String API_KEY = "2025C2E4-180B-492E-9BC0-2916C99A2851";

    private static final Posts ourInstance = new Posts();

    private Posts(  )
    {
    }

    public static Posts getInstance()
    {
        return ourInstance;
    }

    public static void initApplication()
    {
        Backendless.setUrl( Posts.BACKENDLESS_HOST );
        // if you invoke this sample inside of android application, you should use overloaded "initApp" with "context" argument
        Backendless.initApp( Posts.APP_ID, Posts.API_KEY );

    }



    public void getAll(java.lang.String currentUserId, float pageSize, float offset)
    {
        Object[] args = new Object[]{currentUserId, pageSize, offset};
        Backendless.CustomService.invoke( SERVICE_NAME, "getAll", args );
    }

    public void getAllAsync(java.lang.String currentUserId, float pageSize, float offset, AsyncCallback<Object> callback)
    {
        Object[] args = new Object[]{currentUserId, pageSize, offset};
        Backendless.CustomService.invoke( SERVICE_NAME, "getAll", args, Object.class, callback);
    }

    public void likePost(java.lang.String currentUserId, java.lang.String postId)
    {
        Object[] args = new Object[]{currentUserId, postId};
        Backendless.CustomService.invoke( SERVICE_NAME, "likePost", args );
    }

    public void likePostAsync(java.lang.String currentUserId, java.lang.String postId, AsyncCallback<Object> callback)
    {
        Object[] args = new Object[]{currentUserId, postId};
        Backendless.CustomService.invoke( SERVICE_NAME, "likePost", args, Object.class, callback);
    }

    public void unlikePost(java.lang.String currentUserId, java.lang.String postId)
    {
        Object[] args = new Object[]{currentUserId, postId};
        Backendless.CustomService.invoke( SERVICE_NAME, "unlikePost", args );
    }

    public void unlikePostAsync(java.lang.String currentUserId, java.lang.String postId, AsyncCallback<Object> callback)
    {
        Object[] args = new Object[]{currentUserId, postId};
        Backendless.CustomService.invoke( SERVICE_NAME, "unlikePost", args, Object.class, callback);
    }

}
