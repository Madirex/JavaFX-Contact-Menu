package com.madirex.javafxmenu.util;

import com.madirex.javafxmenu.model.Persona;
import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Util {
    private static ResourceBundle resourceBundle;

    private Util(){

    }

    public static Pane loadSource(String file) {
        Pane pane = null;

        try {
            pane = (Pane) Util.getParentRoot(file);
        } catch (IOException e) {
            System.err.println(Util.getString("bug.title") + " " + Util.getString("bug.panelLoad"));
            e.printStackTrace();
            System.exit(0);
        }

        if (pane == null){
            System.err.println(Util.getString("bug.title") + " " + Util.getString("bug.panelLoad"));
            System.exit(0);
        }
        return pane;
    }

    private static void setResourceBundleLanguage(){
        String[] language = UserStorageAndConfiguration.getInstance().getActualLanguage().split("_");
        Locale locale = new Locale(language[0], language[1]);
        resourceBundle = ResourceBundle.getBundle("/i18n/strings", locale);
    }

    public static Parent getParentRoot(String file) throws IOException {
        setResourceBundleLanguage();
        return FXMLLoader.load(Objects.requireNonNull(Util.class.getResource("/fxml/" + file + ".fxml")),
                resourceBundle);
    }

    public static String getString(String str){
        if (resourceBundle == null){
            setResourceBundleLanguage();
        }
        return resourceBundle.getString(str);
    }

    public static String removeFirstAndLastChar(String str){
        return str.substring(1, str.length() -1);
    }

    public static String getJsonObjStr(JsonObject jsonObj, String str){
        return removeFirstAndLastChar(jsonObj.get(str).toString());
    }

    public static Optional<Persona> getRandomUser(){
        String str="https://randomuser.me/api/";
        Optional<Persona> opt = Optional.empty();
        Persona persona = new Persona();
            try {
                URL url = new URL(str);
                InputStream is = url.openStream();
                JsonReader rdr = Json.createReader(new InputStreamReader(is, "UTF-8"));
                JsonObject jsonObject = rdr.readObject();
                JsonArray results = jsonObject.getJsonArray("results");

                //Asignación de propiedades
                JsonObject jsonObj = results.getJsonObject(0);
                JsonObject location = jsonObj.getJsonObject("location");
                JsonObject name = jsonObj.getJsonObject("name");
                JsonObject picture = jsonObj.getJsonObject("picture");
                JsonObject birth = jsonObj.getJsonObject("dob");
                persona.setEmail(getJsonObjStr(jsonObj, "email"));
                persona.setGender(getJsonObjStr(jsonObj, "gender"));
                persona.setCountry(getJsonObjStr(location, "country"));
                persona.setPhone(getJsonObjStr(jsonObj, "phone"));
                persona.setName(getJsonObjStr(name, "first") + " " + getJsonObjStr(name, "last"));
                persona.setPicture(getJsonObjStr(picture, "large"));
                persona.setAge(birth.getInt("age"));
                opt = Optional.of(persona);
            }catch(UnknownHostException | UnsupportedEncodingException e){
                System.err.println("No se pudo resolver el host. Comprueba que tengas conexión a internet 🌐");
                System.exit(0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return opt;
    }
}
