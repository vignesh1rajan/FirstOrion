package vertx.bo;

import java.io.Serializable;

/**
 * Created by nexis on 11/7/2016.
 */
public class Persons implements Serializable {

    private String PhoneNumberId;
    private String address;
    private String status;

    public String getPhoneNumberId() {
        return PhoneNumberId;
    }

    public void setPhoneNumberId(String phoneNumberId) {
        PhoneNumberId = phoneNumberId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Persons{" +
                "PhoneNumberId='" + PhoneNumberId + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
