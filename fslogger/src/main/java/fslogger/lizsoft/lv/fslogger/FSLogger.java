package fslogger.lizsoft.lv.fslogger;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Farhad
 *
 * A Logger Manager for Android
 */
public class FSLogger {

    //Variables----------------------------------------------------------------------------

    private static FSLogger _instance;

    private String TAG = "FSLogger";

    private static boolean LOGGING_ENABLED = true;

    private static boolean LOGGING_WITH_BACKTRACE_ENABLED = false;

    private static final int STACK_TRACE_LEVELS_UP = 6;

    private static ArrayList<Integer> Rules = new ArrayList<>();

    //Variables----------------------------------------------------------------------------


    private FSLogger(String tag) {
        this.TAG = tag;
    }

    /*
    * Instantiation of Logger
    * */
    public static FSLogger Init(String tag) {

        _instance = new FSLogger(tag);

        return _instance;

    }

    /*
        Enable Logger
    */
    public static void Enable() {
        LOGGING_ENABLED = true;
    }


    /*
         Disable Logger
    */
    public static void Disable() {
        LOGGING_ENABLED = false;
    }

    /*
        Enable LOGGING WITH BACKTRACE
    */
    public static void EnableLoggingWithBackTrace() {
        LOGGING_WITH_BACKTRACE_ENABLED = true;
    }

    /*
        Disable LOGGING WITH BACKTRACE
    */
    public static void DisableLoggingWithBackTrace() {
        LOGGING_WITH_BACKTRACE_ENABLED = false;
    }

    public static void ADDRules(ArrayList<Integer> rules) {
        Rules.clear();
        Rules = rules;
    }

    public static boolean ADDRule(Integer rule) {
        if (!Rules.contains(rule)) {
            Rules.add(rule);
            return true;
        }

        return false;
    }

    public static boolean RemoveRule(Integer rule) {
        if (!Rules.contains(rule)) {
            Rules.remove(rule);
            return true;
        }

        return false;
    }

    private static void logout(String message, int traceLevel)
    {
        if (_instance.LOGGING_ENABLED && BuildConfig.DEBUG)
        {
            if (message.length() > 3000) {
                int length = message.length();
                String str = message;

                while (length > 3000) {
                    Log.w(_instance.TAG, getClassNameMethodNameAndLineNumber(traceLevel) + str.substring(0, 3000));
                    str = str.substring(3000);
                    length = str.length();
                }

                Log.w(_instance.TAG, getClassNameMethodNameAndLineNumber(traceLevel) + str);
            } else {
                Log.w(_instance.TAG, getClassNameMethodNameAndLineNumber(traceLevel) + message);
            }
        }
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static void logout(Integer code, String message) {

        if (Rules.contains(code))
            logout(message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static void logout(Integer code) {

        if (Rules.contains(code))
            logout("", STACK_TRACE_LEVELS_UP);
    }

    //Normal Logout
    public static void logout(String message) {
        logout(message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static void logout() {
        logout("", STACK_TRACE_LEVELS_UP);
    }

    /**
     * Get the current line number. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @author Farhad
     * @return int - Current line number.
     */
    private static int getLineNumber(int traceLevel)
    {
        return Thread.currentThread().getStackTrace()[traceLevel].getLineNumber();
    }

    /**
     * Get the current class name. Note, this will only work as called from this
     * class as it has to go a predetermined number of steps up the stack trace.
     * In this case 5.
     *
     * @author Farhad
     * @return String - Current line number.
     */
    private static String getClassName(int traceLevel)
    {
        String fileName = Thread.currentThread().getStackTrace()[traceLevel].getFileName();

        // Farhad: Removing ".java" and returning class name
        return fileName.substring(0, fileName.length() - 5);
    }

    /**
     * Get the current method name. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @author Farhad
     * @return String - Current line number.
     */
    private static String getMethodName(int traceLevel)
    {
        return Thread.currentThread().getStackTrace()[traceLevel].getMethodName();
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <class_name>.<method_name>()-<line_number>
     *
     * @author Farhad
     * @return String - String representing class name, method name, and line
     *         number.
     */
    private static String getClassNameMethodNameAndLineNumber(int traceLevel)
    {
        String res = "";
        if(_instance.LOGGING_WITH_BACKTRACE_ENABLED)
            res += "[" + getClassName(traceLevel + 1) + "." + getMethodName(traceLevel + 1) + "()-" + getLineNumber(traceLevel + 1) + "]: ";
        res += "[" + getClassName(traceLevel) + "." + getMethodName(traceLevel) + "()-" + getLineNumber(traceLevel) + "]: ";

        return res;
    }
}
