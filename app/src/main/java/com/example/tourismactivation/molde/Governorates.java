package com.example.tourismactivation.molde;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;

import java.util.List;
import java.util.Date;

public class Governorates
{
    private String objectId;
    private Date created;
    private Integer id;
    private String coverImage;
    private String ownerId;
    private String name;
    private Date updated;
    private List<Places> governoratePlaces;
    public String getObjectId()
    {
        return objectId;
    }

    public Governorates(String name,String coverImage) {
        this.name = name;
        this.coverImage = coverImage;
    }

    public Governorates() {

    }

    public Date getCreated()
    {
        return created;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage( String coverImage )
    {
        this.coverImage = coverImage;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public List<Places> getGovernoratePlaces()
    {
        return governoratePlaces;
    }

    public void setGovernoratePlaces( List<Places> governoratePlaces )
    {
        this.governoratePlaces = governoratePlaces;
    }


    public Governorates save()
    {
        return Backendless.Data.of( Governorates.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Governorates> callback )
    {
        Backendless.Data.of( Governorates.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Governorates.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Governorates.class ).remove( this, callback );
    }

    public static Governorates findById( String id )
    {
        return Backendless.Data.of( Governorates.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Governorates> callback )
    {
        Backendless.Data.of( Governorates.class ).findById( id, callback );
    }

    public static Governorates findFirst()
    {
        return Backendless.Data.of( Governorates.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Governorates> callback )
    {
        Backendless.Data.of( Governorates.class ).findFirst( callback );
    }

    public static Governorates findLast()
    {
        return Backendless.Data.of( Governorates.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Governorates> callback )
    {
        Backendless.Data.of( Governorates.class ).findLast( callback );
    }

    public static List<Governorates> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Governorates.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Governorates>> callback )
    {
        Backendless.Data.of( Governorates.class ).find( queryBuilder, callback );
    }
}