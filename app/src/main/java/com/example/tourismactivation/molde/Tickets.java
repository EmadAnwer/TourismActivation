package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;

import java.util.List;
import java.util.Date;

public class Tickets
{
    private Integer price;
    private String placeName;
    private Date ReservationDate;
    private String ownerId;
    private String type;
    private Integer id;
    private Date created;
    private String objectId;
    private Date updated;

    public Tickets() {
    }

    public Tickets(Integer price, String placeName, Date reservationDate, String type) {
        this.price = price;
        this.placeName = placeName;
        ReservationDate = reservationDate;
        this.type = type;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice( Integer price )
    {
        this.price = price;
    }

    public String getPlaceName()
    {
        return placeName;
    }

    public void setPlaceName( String placeName )
    {
        this.placeName = placeName;
    }

    public Date getReservationDate()
    {
        return ReservationDate;
    }

    public void setReservationDate( Date ReservationDate )
    {
        this.ReservationDate = ReservationDate;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
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


    public Tickets save()
    {
        return Backendless.Data.of( Tickets.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Tickets> callback )
    {
        Backendless.Data.of( Tickets.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Tickets.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Tickets.class ).remove( this, callback );
    }

    public static Tickets findById( String id )
    {
        return Backendless.Data.of( Tickets.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Tickets> callback )
    {
        Backendless.Data.of( Tickets.class ).findById( id, callback );
    }

    public static Tickets findFirst()
    {
        return Backendless.Data.of( Tickets.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Tickets> callback )
    {
        Backendless.Data.of( Tickets.class ).findFirst( callback );
    }

    public static Tickets findLast()
    {
        return Backendless.Data.of( Tickets.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Tickets> callback )
    {
        Backendless.Data.of( Tickets.class ).findLast( callback );
    }

    public static List<Tickets> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Tickets.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Tickets>> callback )
    {
        Backendless.Data.of( Tickets.class ).find( queryBuilder, callback );
    }
}