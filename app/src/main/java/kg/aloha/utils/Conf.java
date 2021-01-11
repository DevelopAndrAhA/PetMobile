package kg.aloha.utils;

/**
 * Created by Altynbek on 12.12.2020.
 */

public class Conf {
    private String url = "https://api.anonfiles.com/upload";
    private String url_2 = "https://petition-mob-rest.herokuapp.com/";
    public static  long U_ID=1;

    public static final  String GET_ALL_DEP = "getalldep";
    public static final  String GET_ALL_PREZ = "getAllPrezident";
    public static final  String GET_ALL_AKT = "getAllAktivist";
    public static final  String SEARCH = "search";
    public static final  String GET_DEP_COMMENTS_ACT = "getDepCommentsAct";
    public static final  String GET_DEP_COMMENTS_DEP = "getDepCommentsDep";
    public static final  String GET_DEP_COMMENTS_PREZIDENT = "getDepCommentsPrezident";
    public static final  String GET_COUNT_USER = "getCountUsers";
    public static final  String GET_GOLOS_PREZIDENT = "getGolosPrezident";
    public static final  String GET_GOLOS_DEPUTAT = "getGolosDeputat";
    public static final  String GET_GOLOS_ACTIVIST = "getGolosActivist";


    public static final  String SAVE_COMMENT = "saveComment";
    public static final  String SAVE_ACTIVIST = "saveAktivist";
    public static final  String SAVE_PROFESSION = "saveProfession";
    public static final  String SAVE_PROFESSION_COUNT = "saveProfession_Count";
    public static final  String SAVE_PREZIDENT = "savePrezident";
    public static final  String SAVE_GOLOS_ACT = "saveGolosAct";
    public static final  String SAVE_GOLOS_DEP = "saveGolosDep";
    public static final  String SAVE_GOLOS_PREZIDENT = "saveGolosPrezident";
    public static final  String SAVE_ADDRESS = "saveAddress";
    public static final  String SAVE_FAMILY = "saveFamily";
    public static final  String SAVE_IMUSHESTVO = "saveImushestvo";



    public static final  String SETTINGS_STR = "SETTINGS_STR";




    public String getUrl() {
        return url;
    }

    public String getUrl_2() {
        return url_2;
    }




}
