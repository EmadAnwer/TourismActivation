package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;

public class Review
{
    static final String BACKENDLESS_HOST = "https://api.backendless.com";
    static final String SERVICE_NAME = "Review";
    static final String APP_ID = "31908FE0-A688-43D5-879D-B815B9404108";
    static final String API_KEY = "2025C2E4-180B-492E-9BC0-2916C99A2851";

    private static final Review ourInstance = new Review();

    private Review(  )
    {
    }

    public static Review getInstance()
    {
        return ourInstance;
    }

    public static void initApplication()
    {
        Backendless.setUrl( Review.BACKENDLESS_HOST );
        // if you invoke this sample inside of android application, you should use overloaded "initApp" with "context" argument
        Backendless.initApp( Review.APP_ID, Review.API_KEY );

    }



    public void getAll(java.lang.String currentUserId, float pageSize, float offset, java.lang.String placeID)
    {
        Object[] args = new Object[]{currentUserId, pageSize, offset, placeID};
        Backendless.CustomService.invoke( SERVICE_NAME, "getAll", args );
    }

    public void getAllAsync(java.lang.String currentUserId, float pageSize, float offset, java.lang.String placeID, AsyncCallback<Object> callback)
    {
        Object[] args = new Object[]{currentUserId, pageSize, offset, placeID};
        Backendless.CustomService.invoke( SERVICE_NAME, "getAll", args, Object.class, callback);
    }

    public void likeReview(java.lang.String currentUserId, java.lang.String reviewsId)
    {
        Object[] args = new Object[]{currentUserId, reviewsId};
        Backendless.CustomService.invoke( SERVICE_NAME, "likeReview", args );
    }

    public void likeReviewAsync(java.lang.String currentUserId, java.lang.String reviewsId, AsyncCallback<Object> callback)
    {
        Object[] args = new Object[]{currentUserId, reviewsId};
        Backendless.CustomService.invoke( SERVICE_NAME, "likeReview", args, Object.class, callback);
    }

    public void unlikeReview(java.lang.String currentUserId, java.lang.String reviewsId)
    {
        Object[] args = new Object[]{currentUserId, reviewsId};
        Backendless.CustomService.invoke( SERVICE_NAME, "unlikeReview", args );
    }

    public void unlikeReviewAsync(java.lang.String currentUserId, java.lang.String reviewsId, AsyncCallback<Object> callback)
    {
        Object[] args = new Object[]{currentUserId, reviewsId};
        Backendless.CustomService.invoke( SERVICE_NAME, "unlikeReview", args, Object.class, callback);
    }

}