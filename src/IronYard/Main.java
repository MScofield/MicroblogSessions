package IronYard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scofieldservices on 12/5/16.
 */

public class Main {
    public static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    //String password = session.attribute("userPassword");
                    User user = users.get(name);

                    HashMap userHash = new HashMap();

                    if (user == null){

                        return new ModelAndView(userHash, "login.html");
                    } else {
                        userHash.put("name", user.name);
                        userHash.put("messageAdd", user.messageList);
                        userHash.put("password", user.password);
                        return new ModelAndView(userHash, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    User user = users.get(name);
                    if (user == null)
                    {
                        user = new User(name, password);
                        users.put(name, user);
                    }
                    if (user.password.equals(password));
                    {
                        Session session = request.session();
                        session.attribute("userName", name);
                        session.attribute("userPassword", password);
                    }
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }

                    String messageString = request.queryParams("messageBox");

                    Messages messages = new Messages(messageString);
                    user.messageList.add(messages);
                    response.redirect("/");
                    return "";
                })
        );


    }

}

