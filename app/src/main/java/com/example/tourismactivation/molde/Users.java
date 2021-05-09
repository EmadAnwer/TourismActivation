package com.example.tourismactivation.molde;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

public class Users {

    private Integer countryCode;
    private List<Tickets> placeTickets;
    private String profilePicture;
    private List<Tickets> userTickets;
    private String email;
    private String name;
    private String phone;
    private List<Places> userFavorites;
    private String blUserLocale;
    private String password;
    private String ownerId;
    private Date created;
    private String objectId;
    private Date updated;

    public Users() {
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public List<Tickets> getPlaceTickets() {
        return placeTickets;
    }

    public void setPlaceTickets(List<Tickets> placeTickets) {
        this.placeTickets = placeTickets;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Tickets> getUserTickets() {
        return userTickets;
    }

    public void setUserTickets(List<Tickets> userTickets) {
        this.userTickets = userTickets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Places> getUserFavorites() {
        return userFavorites;
    }

    public void setUserFavorites(List<Places> userFavorites) {
        this.userFavorites = userFavorites;
    }

    public String getBlUserLocale() {
        return blUserLocale;
    }

    public void setBlUserLocale(String blUserLocale) {
        this.blUserLocale = blUserLocale;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void saveAsync( AsyncCallback<Users> callback )
    {
        Backendless.Data.of( Users.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Users.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Users.class ).remove( this, callback );
    }

    public static Users findById( String id )
    {
        return Backendless.Data.of( Users.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Users> callback )
    {
        Backendless.Data.of( Users.class ).findById( id, callback );
    }

    public static Users findFirst()
    {
        return Backendless.Data.of( Users.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Users> callback )
    {
        Backendless.Data.of( Users.class ).findFirst( callback );
    }

    public static Users findLast()
    {
        return Backendless.Data.of( Users.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Users> callback )
    {
        Backendless.Data.of( Users.class ).findLast( callback );
    }

    public static List<Users> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Users.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Users>> callback )
    {
        Backendless.Data.of( Users.class ).find( queryBuilder, callback );
    }
}
