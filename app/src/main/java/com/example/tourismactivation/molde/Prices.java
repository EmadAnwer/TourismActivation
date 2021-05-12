package com.example.tourismactivation.molde;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.example.tourismactivation.constants;

import java.util.List;
import java.util.Date;

import static com.example.tourismactivation.constants.EN;

public class Prices
{
    private String ownerId;
    private Integer ticketCost;
    private Date created;
    private String objectId;
    private Date updated;
    private Integer id;
    private String ticketType_AR;
    private String ticketType_EN;
    private String ticketType;
    public String getOwnerId()
    {
        return ownerId;
    }

    public Integer getTicketCost()
    {
        return ticketCost;
    }

    public void setTicketCost( Integer ticketCost )
    {
        this.ticketCost = ticketCost;
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

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getTicketType()
    {
        if(constants.LANGUAGE == EN)
            this.ticketType = ticketType_EN;
        else
            this.ticketType = ticketType_AR;

        return ticketType;
    }


    public String getTicketType_AR() {
        return ticketType_AR;
    }

    public String getTicketType_EN() {
        return ticketType_EN;
    }

    public void setTicketType(String ticketType )
    {
        this.ticketType = ticketType;
    }


    public Prices save()
    {
        return Backendless.Data.of( Prices.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Prices> callback )
    {
        Backendless.Data.of( Prices.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Prices.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Prices.class ).remove( this, callback );
    }

    public static Prices findById( String id )
    {
        return Backendless.Data.of( Prices.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Prices> callback )
    {
        Backendless.Data.of( Prices.class ).findById( id, callback );
    }

    public static Prices findFirst()
    {
        return Backendless.Data.of( Prices.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Prices> callback )
    {
        Backendless.Data.of( Prices.class ).findFirst( callback );
    }

    public static Prices findLast()
    {
        return Backendless.Data.of( Prices.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Prices> callback )
    {
        Backendless.Data.of( Prices.class ).findLast( callback );
    }

    public static List<Prices> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Prices.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Prices>> callback )
    {
        Backendless.Data.of( Prices.class ).find( queryBuilder, callback );
    }
}