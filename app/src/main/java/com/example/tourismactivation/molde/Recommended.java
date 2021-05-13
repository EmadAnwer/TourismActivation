package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.example.tourismactivation.constants;

import java.util.List;
import java.util.Date;

import static com.example.tourismactivation.constants.EN;

public class Recommended
{
    private Date updated;
    private String name_AR;
    private String ownerId;
    private Date created;
    private String name_EN;
    private String name;
    private String description;
    private String cover_Image;
    private String description_EN;
    private String description_AR;
    private String objectId;
    private List<Prices> recommendedPrices;
    private List<Places> recommendedPlaces;

    public String getName()
    {
        if(constants.LANGUAGE == EN)
            this.name = name_EN;
        else
            this.name = name_AR;
        return name;
    }
    public String getDescription()
    {
        if(constants.LANGUAGE == EN)
            this.description = description_EN;
        else
            this.description = description_AR;

        return description;
    }
    public Date getUpdated()
    {
        return updated;
    }

    public String getName_AR()
    {
        return name_AR;
    }

    public void setName_AR( String name_AR )
    {
        this.name_AR = name_AR;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public Date getCreated()
    {
        return created;
    }

    public String getName_EN()
    {
        return name_EN;
    }

    public void setName_EN( String name_EN )
    {
        this.name_EN = name_EN;
    }

    public String getCover_Image()
    {
        return cover_Image;
    }

    public void setCover_Image( String cover_Image )
    {
        this.cover_Image = cover_Image;
    }

    public String getDescription_EN()
    {
        return description_EN;
    }

    public void setDescription_EN( String description_EN )
    {
        this.description_EN = description_EN;
    }

    public String getDescription_AR()
    {
        return description_AR;
    }

    public void setDescription_AR( String description_AR )
    {
        this.description_AR = description_AR;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public List<Prices> getRecommendedPrices()
    {
        return recommendedPrices;
    }

    public void setRecommendedPrices( List<Prices> recommendedPrices )
    {
        this.recommendedPrices = recommendedPrices;
    }

    public List<Places> getRecommendedPlaces()
    {
        return recommendedPlaces;
    }

    public void setRecommendedPlaces( List<Places> recommendedPlaces )
    {
        this.recommendedPlaces = recommendedPlaces;
    }


    public Recommended save()
    {
        return Backendless.Data.of( Recommended.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Recommended> callback )
    {
        Backendless.Data.of( Recommended.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Recommended.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Recommended.class ).remove( this, callback );
    }

    public static Recommended findById( String id )
    {
        return Backendless.Data.of( Recommended.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Recommended> callback )
    {
        Backendless.Data.of( Recommended.class ).findById( id, callback );
    }

    public static Recommended findFirst()
    {
        return Backendless.Data.of( Recommended.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Recommended> callback )
    {
        Backendless.Data.of( Recommended.class ).findFirst( callback );
    }

    public static Recommended findLast()
    {
        return Backendless.Data.of( Recommended.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Recommended> callback )
    {
        Backendless.Data.of( Recommended.class ).findLast( callback );
    }

    public static List<Recommended> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Recommended.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Recommended>> callback )
    {
        Backendless.Data.of( Recommended.class ).find( queryBuilder, callback );
    }
}
