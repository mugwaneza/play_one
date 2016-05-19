package controllers;

import play.libs.WS;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
/**
 * Created by alexis on 3/22/2016.
 */
public class proxy extends Controller{

    public static Result index( String url){
F.Promise<WS.Response> responsePromise= WS.url(url).get();

        return async(responsePromise.map(new F.Function<WS.Response, Result>() {
            @Override
            public Result apply(WS.Response response) throws Throwable {
                return ok(response.getBody()).as("text/html");
            }


        }));
        }
}
