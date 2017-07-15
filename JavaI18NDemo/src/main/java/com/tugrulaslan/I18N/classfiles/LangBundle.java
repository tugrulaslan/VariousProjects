package com.tugrulaslan.I18N.classfiles;

import java.util.ListResourceBundle;

public class LangBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] { { "var1", "Hello World" }, { "var2", "Bye" } };
    }

}
