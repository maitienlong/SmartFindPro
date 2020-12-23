
package com.poly.smartfindpro.data.model.comment.getcomment.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments {

    @SerializedName("is_favorite")
    @Expose
    private Boolean isFavorite;

    @SerializedName("is_favorite_reply")
    @Expose
    private Boolean isFavoriteReply;

    @SerializedName("comment")
    @Expose
    private Comment comment;
    @SerializedName("reply")
    @Expose
    private Reply reply;
    @SerializedName("favorites")
    @Expose
    private Favorites favorites;

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }


    public Boolean getFavoriteReply() {
        return isFavoriteReply;
    }

    public void setFavoriteReply(Boolean favoriteReply) {
        isFavoriteReply = favoriteReply;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

}
