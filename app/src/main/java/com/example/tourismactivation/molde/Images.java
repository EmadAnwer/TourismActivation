package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;

import java.util.List;
import java.util.Date;

public class Images
{
    private String src;
    private Integer id;
    private String user_id;
    private Date created;
    private String objectId;
    private Date updated;
    private String ownerId;
    private Integer place_id;
    public String getSrc()
    {
        return src;
    }

    public Images(String src) {
        this.src = src;
    }

    public void setSrc(String src )
    {
        this.src = src;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id( String user_id )
    {
        this.user_id = user_id;
    }

    public Date getCreated()
    {
        return created;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public Integer getPlace_id()
    {
        return place_id;
    }

    public void setPlace_id( Integer place_id )
    {
        this.place_id = place_id;
    }


    public Images save()
    {
        return Backendless.Data.of( Images.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Images> callback )
    {
        Backendless.Data.of( Images.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Images.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Images.class ).remove( this, callback );
    }

    public static Images findById( String id )
    {
        return Backendless.Data.of( Images.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Images> callback )
    {
        Backendless.Data.of( Images.class ).findById( id, callback );
    }

    public static Images findFirst()
    {
        return Backendless.Data.of( Images.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Images> callback )
    {
        Backendless.Data.of( Images.class ).findFirst( callback );
    }

    public static Images findLast()
    {
        return Backendless.Data.of( Images.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Images> callback )
    {
        Backendless.Data.of( Images.class ).findLast( callback );
    }

    public static List<Images> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Images.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Images>> callback )
    {
        Backendless.Data.of( Images.class ).find( queryBuilder, callback );
    }
}