/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import io.jooby.Jooby;
import io.jooby.Route;
import java.nio.file.Paths;



/**
 *
 * @author yeah2
 */
public class AssetModule extends Jooby {

    public AssetModule() {
        // prevent 404 errors due to browsers requesting favicons
        get("/favicon.ico", Route.FAVICON);
        
        // make index.html the default page
        assets("/", Paths.get("public/home.html"));

        assets("/*", Paths.get("public"));
        assets("/css/*.css", Paths.get("public/css"));
        assets("/js/*.js", Paths.get("public/js"));
        assets("/js/external/*.js", Paths.get("public/js/external"));
//        assets("/images/*.png", Paths.get("public/images"));
//        assets("/images/*.jpg", Paths.get("public/images"));
    }
}
