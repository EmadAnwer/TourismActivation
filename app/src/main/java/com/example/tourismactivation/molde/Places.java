package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.example.tourismactivation.constants;

import java.util.List;
import java.util.Date;


import static com.example.tourismactivation.constants.EN;

public class Places
{
    private String governorate;
    private String user_id;
    private String name_AR;
    private String name_EN;
    private String description_AR;
    private String description_EN;

    private String description;
    private Integer id;
    private String cover_image;
    private Date updated;
    private Date created;
    private Float rating_average;
    private String name;
    private String category;
    private String ownerId;
    private Integer reviewsCount;
    private String objectId;
    private List<Tags> placeTags;
    private List<Prices> placePrice;
    private List<Images> placeImage;
    private List<Reviews> placeReviews;
    private List<Tickets> placeTickets;




    public Places() {
    }

    public List<Tickets> getPlaceTickets()
    {
        return placeTickets;
    }

    public void setPlaceTickets( List<Tickets> placeTickets )
    {
        this.placeTickets = placeTickets;
    }

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
        if(constants.LANGUAGE == EN)
            this.description = description_EN;
        else
            this.description = description_AR;

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

    public Float getRating_average()
    {
        return rating_average;
    }

    public void setRating_average( Float rating_average )
    {
        this.rating_average = rating_average;
    }

    public String getName_AR() {
        return name_AR;
    }

    public String getName_EN() {
        return name_EN;
    }

    public String getName()
    {
        if(constants.LANGUAGE == EN)
            this.name = name_EN;
        else
            this.name = name_AR;

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

    public Integer getReviewsCount()
    {
        return reviewsCount;
    }

    public void setReviewsCount( Integer reviewsCount )
    {
        this.reviewsCount = reviewsCount;
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
