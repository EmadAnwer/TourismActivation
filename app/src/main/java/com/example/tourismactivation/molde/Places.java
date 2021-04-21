package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;

import java.util.List;
import java.util.Date;

public class Places
{
    private String governorate;
    private String user_id;
    private String description;
    private Integer id;
    private String cover_image;
    private Date updated;
    private Date created;
    private float rating_average;
    private String name;
    private String category;
    private String ownerId;
    private String objectId;
    private List<Tags> placeTags;
    private List<Prices> placePrice;
    private List<Images> placeImage;
    private List<Reviews> placeReviews;
    public String getGovernorate()
    {
        return governorate;
    }

    public void setGovernorate( String governorate )
    {
        this.governorate = governorate;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id( String user_id )
    {
        this.user_id = user_id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getCover_image()
    {
        return cover_image;
    }

    public void setCover_image( String cover_image )
    {
        this.cover_image = cover_image;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public Date getCreated()
    {
        return created;
    }

    public float getRating_average()
    {
        return rating_average;
    }

    public void setRating_average( float rating_average )
    {
        this.rating_average = rating_average;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory( String category )
    {
        this.category = category;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public List<Tags> getPlaceTags()
    {
        return placeTags;
    }

    public void setPlaceTags( List<Tags> placeTags )
    {
        this.placeTags = placeTags;
    }

    public List<Prices> getPlacePrice()
    {
        return placePrice;
    }

    public void setPlacePrice( List<Prices> placePrice )
    {
        this.placePrice = placePrice;
    }

    public List<Images> getPlaceImage()
    {
        return placeImage;
    }

    public void setPlaceImage( List<Images> placeImage )
    {
        this.placeImage = placeImage;
    }

    public List<Reviews> getPlaceReviews()
    {
        return placeReviews;
    }

    public void setPlaceReviews( List<Reviews> placeReviews )
    {
        this.placeReviews = placeReviews;
    }


    public Places save()
    {
        return Backendless.Data.of( Places.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Places> callback )
    {
        Backendless.Data.of( Places.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Places.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Places.class ).remove( this, callback );
    }

    public static Places findById( String id )
    {
        return Backendless.Data.of( Places.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Places> callback )
    {
        Backendless.Data.of( Places.class ).findById( id, callback );
    }

    public static Places findFirst()
    {
        return Backendless.Data.of( Places.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Places> callback )
    {
        Backendless.Data.of( Places.class ).findFirst( callback );
    }

    public static Places findLast()
    {
        return Backendless.Data.of( Places.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Places> callback )
    {
        Backendless.Data.of( Places.class ).findLast( callback );
    }

    public static List<Places> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Places.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Places>> callback )
    {
        Backendless.Data.of( Places.class ).find( queryBuilder, callback );
    }
}
