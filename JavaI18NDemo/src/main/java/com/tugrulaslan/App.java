package com.tugrulaslan;

import java.util.Locale;
import java.util.ResourceBundle;

public class App {
    public static void main(String[] args) {
        propertiesVersion();
        classVersion();
    }

    private static void propertiesVersion() {
        System.out.println("===Property Files===");
        ResourceBundle bundleDefault = ResourceBundle.getBundle("com.tugrulaslan.I18N.propertyfiles.LangBundle");
        ResourceBundle bundleDE = ResourceBundle.getBundle("com.tugrulaslan.I18N.propertyfiles.LangBundle", Locale.GERMANY);
        ResourceBundle bundleIT = ResourceBundle.getBundle("com.tugrulaslan.I18N.propertyfiles.LangBundle", Locale.ITALIAN);
        ResourceBundle bundleFrCA = ResourceBundle.getBundle("com.tugrulaslan.I18N.propertyfiles.LangBundle", new Locale("fr", "CA"));
        ResourceBundle bundleTr = ResourceBundle.getBundle("com.tugrulaslan.I18N.propertyfiles.LangBundle", new Locale("tr"));
        printLocale(bundleDefault);
        printLocale(bundleDE);
        printLocale(bundleIT);
        printLocale(bundleFrCA);
        printLocale(bundleTr);
    }

    private static void classVersion() {
        System.out.println("\n===Class Files===");
        ResourceBundle bundleDefault = ResourceBundle.getBundle("com.tugrulaslan.I18N.classfiles.LangBundle");
        ResourceBundle bundleDE = ResourceBundle.getBundle("com.tugrulaslan.I18N.classfiles.LangBundle", Locale.GERMANY);
        ResourceBundle bundleIT = ResourceBundle.getBundle("com.tugrulaslan.I18N.propertyfiles.LangBundle", Locale.ITALIAN);
        ResourceBundle bundleFrCA = ResourceBundle.getBundle("com.tugrulaslan.I18N.classfiles.LangBundle", new Locale("fr","CA"));
        ResourceBundle bundleTr = ResourceBundle.getBundle("com.tugrulaslan.I18N.classfiles.LangBundle", new Locale("tr"));
        printLocale(bundleDefault);
        printLocale(bundleDE);
        printLocale(bundleIT);
        printLocale(bundleFrCA);
        printLocale(bundleTr);
    }

    private static void printLocale(ResourceBundle bundle) {
        System.out.println("Display Language: " + bundle.getLocale().getDisplayLanguage());
        System.out.println("Message One: " + bundle.getString("var1"));
        System.out.println("Message Two: " + bundle.getString("var2"));
        System.out.println("--------------------");
    }

}
