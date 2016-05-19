package models;

import com.wepay.exception.WePayException;
import com.wepay.model.Account;
import com.wepay.model.Checkout;
import com.wepay.model.OAuth2;
import com.wepay.model.data.AccountData;
import com.wepay.model.data.CheckoutData;
import com.wepay.model.data.OAuth2Data;
import controllers.Session;
import org.json.JSONException;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Farmer extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String useremail;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String farm;

    @Constraints.Required
    public String username;

    @Constraints.Required
    public String produce;

    @Constraints.Required
    public BigDecimal produce_price;

    public String wepay_access_token;
    public Long wepay_account_id;

    public static Finder<Long,Farmer> find = new Finder(Long.class, Farmer.class);

    public static List<Farmer> all() {
        return find.all();
    }

    public static Farmer findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static Farmer findById(Long id) {
        return find.ref(id);
    }

    public static void create(Farmer farmer) {
        farmer.password = Session.encryptPassword(farmer.password);
        farmer.save();
    }

    public static void delete(Long id) {
        findById(id).delete();
    }

    public void update(Farmer farmer) {
        farmer.password = Session.encryptPassword(farmer.password);
        farmer.update(this.id);
    }

    public static Farmer authenticate(String useremail, String password) {
        Farmer farmer = find.where().eq("useremail", useremail).findUnique();
        if (Session.checkPassword(password, farmer.password)) return farmer;
        return null;
    }

    public boolean hasWepayAccessToken() {
        return this.wepay_access_token != null;
    }

    public boolean hasWepayAccount() {
        return this.wepay_account_id != null;
    }

    public String wepayOauth2Authorize() throws IOException, JSONException {
        OAuth2Data data = new OAuth2Data();

        return wepay_access_token;
    }

    public void wepayOauth2Token(String code) throws IOException, JSONException, WePayException {
        OAuth2Data data = new OAuth2Data();

        String token = OAuth2.token(data, null);
        this.wepay_access_token = token;
        this.save();
        if (this.hasWepayAccessToken()) this.wepayAccountCreate();
    }

    public void wepayAccountCreate() throws JSONException, IOException, WePayException {
        if (this.hasWepayAccessToken() && !this.hasWepayAccount()) {
            AccountData data = new AccountData();
            data.name = this.username;
            data.description = "Farm selling " + this.produce;
            Account account = Account.create(data, this.wepay_access_token);

            this.save();
        }
    }

    public Checkout wepayCheckoutCreate(String redirect_uri) throws IOException, JSONException, WePayException {
        CheckoutData data = new CheckoutData();

        Checkout checkout = Checkout.create(data, this.wepay_access_token);
        return checkout;
    }

}
