
package com.poly.smartfindpro.data.model.uploadphoto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBody {

    @SerializedName("addressImage")
    @Expose
    private List<String> addressImage = null;

    public List<String> getAddressImage() {
        return addressImage;
    }

    public void setAddressImage(List<String> addressImage) {
        this.addressImage = addressImage;
    }

}
