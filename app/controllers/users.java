package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.ContactFormData;
import views.html.form;

/**
 * Created by alexis on 3/22/2016.
 */

public class users extends Controller {

/**
 * return the form, a page containing a form to add new contacts
 * return newContact page
 */
public  static Result show()
{
    Form<ContactFormData> formData = Form.form(ContactFormData.class);
    return ok(form.render(formData));
}

    /**
     * process the form submited from the form page the new formData
     * return the newContact page
      */
    public  static Result postData()
    {
System.out.println("In post contact");
Form<ContactFormData> formData = Form.form(ContactFormData.class).bindFromRequest();
        ContactFormData data =formData.get();
   System.out.format("%s,%s,%s,data.firstName,data.lastName,data.telephone");
        return ok(form.render(formData));
    }
}
