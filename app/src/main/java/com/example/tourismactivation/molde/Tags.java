package com.example.tourismactivation.molde;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.example.tourismactivation.constants;

import java.util.List;
import java.util.Date;

import static com.example.tourismactivation.constants.EN;

public class Tags
{
    private Date updated;
    private String ownerId;
    private Date created;
    private String name;
    private String name_EN;
    private String name_AR;
    private String objectId;
    public Date getUpdated()
    {
        return updated;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public Date getCreated()
    {
        return created;
    }

    public String getName()
    {
        if(constants.LANGUAGE == EN)
            this.name = name_EN;
        else
            this.name = name_AR;

        return name;
    }

    public String getName_EN()
    {
        this.name = name_EN;

        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getObjectId()
    {
        return objectId;
    }


    public Tags save()
    {
        return Backendless.Data.of( Tags.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Tags> callback )
    {
        Backendless.Data.of( Tags.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Tags.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Tags.class ).remove( this, callback );
    }

    public static Tags findById( String id )
    {
        return Backendless.Data.of( Tags.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Tags> callback )
    {
        Backendless.Data.of( Tags.class ).findById( id, callback );
    }

    public static Tags findFirst()
    {
        return Backendless.Data.of( Tags.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Tags> callback )
    {
        Backendless.Data.of( Tags.class ).findFirst( callback );
    }

    public static Tags findLast()
    {
        return Backendless.Data.of( Tags.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Tags> callback )
    {
        Backendless.Data.of( Tags.class ).findLast( callback );
    }

    public static List<Tags> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Tags.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Tags>> callback )
    {
        Backendless.Data.of( Tags.class ).find( queryBuilder, callback );
    }
}