package controllers;

import play.*;
import play.mvc.*;
import views.html.index;
import views.html.second;

public class Application extends Controller {

    public static Result index() {

        return ok(index.render() );
    }
    public static Result foo(String name,int age ) {

        return ok(views.html.second.render(name,age) );
    }

}
