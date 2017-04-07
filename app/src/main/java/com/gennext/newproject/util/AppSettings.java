package com.gennext.newproject.util;

/**
 * Created by Abhijit on 13-Dec-16.
 */

/**
 * ERROR-101 (Unformetted Json Response)
 * ERROR-102 ()
 */
public class AppSettings {

    public static final String SERVER = "http://bizzzwizzz.com";
    public static final String COMMON = SERVER+ "/index.php/ConsultantRest/";

    public static final String SEND_FEEDBACK  = COMMON + "sendFeedback "; //personName,bankId,branch,accountNumber,ifscCode,userId,checkbookPassbook
    public static final String TERMS_OF_USE  = SERVER + "/files/Terms%20of%20Use.pdf"; //personName,bankId,branch,accountNumber,ifscCode,userId,checkbookPassbook
    public static final String PRIVACY_PACY_POLICY  = SERVER + "/files/Privacy%20Policy.pdf"; //personName,bankId,branch,accountNumber,ifscCode,userId,checkbookPassbook


    public static final String REPORT_SERVER_ERROR = COMMON + "serverErrorReporting";;
}

/* New API's required for BizzzWizzz Consultant App


*/

/* New API's required for BizzzWizzz App

API 1:   http://bizzzwizzz.com/index.php/Rest/signup

Params: name,mobile,email,panNo,pass,profileType

Response: success/failure.(return success and basic info similar to login api)

*/

/*
    1 1xx Informational.
    2 2xx Success.
    3 3xx Redirection.
    4 4xx Client Error.
    5 5xx Server Error.
*/
