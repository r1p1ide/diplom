package org.example.diplom.util;

public class RegexResources {

    public final static String LOGIN_CONDITION = "^\\S*$";

    public final static String NAME_CONDITION = "^[^\\s]+(\\s+[^\\s]+)*$";

    public final static String EMAIL_CONDITION = "^[^\\s@]+@([^\\s@.,]+\\.)+[^\\s@.,]{2,}$";

    public final static String PHONE_CONDITION = "^\\+[\\d]+$";
}
