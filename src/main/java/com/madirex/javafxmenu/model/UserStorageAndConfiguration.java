package com.madirex.javafxmenu.model;

import com.madirex.javafxmenu.dto.PersonYearAverageDTO;
import com.madirex.javafxmenu.util.ApplicationProperties;
import com.madirex.javafxmenu.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UserStorageAndConfiguration implements Serializable {
    private static transient UserStorageAndConfiguration userStorageAndConfigurationInstance;
    public static final transient String SETTINGS_FILE_NAME = "settings.dat";
    private transient Set<String> availableLanguages;
    private transient Set<String> availableThemes;
    private transient ObservableList<Persona> listPersonaData;

    //Datos serializados
    private static final long serialVersionUID = 230;
    private String actualLanguage;
    private String actualTheme;

    private UserStorageAndConfiguration() {
        initConfiguration();
    }

    private void initListPersona() {
        listPersonaData = FXCollections.observableArrayList();
    }

    public static void loadData(){
        //Cargar datos persistencia
        try {
            FileInputStream input = new FileInputStream(UserStorageAndConfiguration.SETTINGS_FILE_NAME);
            ObjectInputStream inStream = new ObjectInputStream(input);
            userStorageAndConfigurationInstance = (UserStorageAndConfiguration) inStream.readObject();
            userStorageAndConfigurationInstance.initConfiguration();
        } catch (FileNotFoundException e){
            System.out.println(Util.getString("msg.configNotFound"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initConfiguration() {
        initListPersona();
        initLanguages();
        initThemes();
    }

    private void initThemes() {
        availableThemes = new HashSet<>();
        String[] themeValues = ApplicationProperties.getInstance().readProperty("theme_list").split(";");
        availableThemes.addAll(Arrays.asList(themeValues));
    }

    /**
     * Asigna los idiomas disponibls según el fichero de settings de la carpeta i18n
     */
    private void initLanguages() {
        availableLanguages = new HashSet<>();

        String[] languageValues = ApplicationProperties.getInstance()
                .readProperty("language_list").split(";");
        availableLanguages.addAll(Arrays.asList(languageValues));
    }

    public String getActualLanguage() {
        if (actualLanguage == null){
            assignDefaultLanguage();
        }
        return actualLanguage;
    }

    private void assignDefaultLanguage() {
        String systemLang = System.getProperty("user.language");
        actualLanguage = null; //Sin lenguaje inicial
        availableLanguages.forEach(e -> {
            String[] sp = e.split("_");
            if (systemLang.equalsIgnoreCase(sp[1])){
                actualLanguage = e;
            }
        });

        //En el caso de que el idioma no esté disponible
        if (actualLanguage == null){
            actualLanguage = "en_UK";
            System.out.println("❗ The detected system language has not been implemented in this application." +
                    "\n English assigned by default.");
        }
    }

    public String getActualTheme() {
        if (actualTheme == null){
            assignDefaultTheme();
        }
        return actualTheme;
    }

    private void assignDefaultTheme() {
        actualTheme = "White"; //Default theme
    }

    public void setActualLanguage(String actualLanguage) {
        this.actualLanguage = actualLanguage;
        saveData();
    }

    public void setActualTheme(String actualTheme) {
        this.actualTheme = actualTheme;
        saveData();
    }

    public static UserStorageAndConfiguration getInstance() {
        if (userStorageAndConfigurationInstance == null) {
            userStorageAndConfigurationInstance = new UserStorageAndConfiguration();
        }
            return userStorageAndConfigurationInstance;
        }

        private void saveData(){
            try {
                FileOutputStream output = new FileOutputStream(UserStorageAndConfiguration.SETTINGS_FILE_NAME);
                ObjectOutputStream outStream = new ObjectOutputStream(output);
                outStream.writeObject(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public Set<String> getAvailableLanguages() {
        return availableLanguages;
    }

    public Set<String> getAvailableThemes() {
        return availableThemes;
    }

    public ObservableList<Persona> getListPersonaData(){
        return listPersonaData;
    }

    public Integer returnPersonaGenderQuantity(String gender){
        AtomicInteger q = new AtomicInteger(0);
        if (listPersonaData != null){
            listPersonaData.forEach(e -> {
                if (e.getGender().equalsIgnoreCase(gender)){
                    q.incrementAndGet();
                }
            });
        }
        return q.get();
    }

    public PersonYearAverageDTO returnYoungPersonYearAverage(){
        List<Integer> youngAge = new ArrayList<>();
        List<Integer> adultAge = new ArrayList<>();
        double youngAvg = 0;
        double adultAvg = 0;

        if (getListPersonaData() != null){
            this.getListPersonaData().forEach(e ->{
                if (e.getAge() <= 24){
                    youngAge.add(e.getAge());
                }else{
                    adultAge.add(e.getAge());
                }
            });
        }

        OptionalDouble mOpt = youngAge.stream().mapToDouble(Integer::doubleValue).average();
        OptionalDouble aOpt = adultAge.stream().mapToDouble(Integer::doubleValue).average();

        if (mOpt.isPresent()) {
            youngAvg = mOpt.getAsDouble();
        }
        if (aOpt.isPresent()) {
            adultAvg = aOpt.getAsDouble();
        }

        return new PersonYearAverageDTO(youngAvg, adultAvg);
    }
}