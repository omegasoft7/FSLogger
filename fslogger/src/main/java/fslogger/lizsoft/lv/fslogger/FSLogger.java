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

    private static FSLoggerLimitationType Type = FSLoggerLimitationType.NOLimit;

    private static ArrayList<Integer> Rules = new ArrayList<>();
    private static ArrayList<String> Classes = new ArrayList<>();

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

    public static void ADDCodes(ArrayList<Integer> rules) {
        Rules.clear();
        Rules = rules;
    }

    public static boolean ADDCode(Integer rule) {
        if (!Rules.contains(rule)) {
            Rules.add(rule);
            return true;
        }

        return false;
    }

    public static boolean RemoveCode(Integer rule) {
        if (Rules.contains(rule)) {
            Rules.remove(rule);
            return true;
        }

        return false;
    }

    public static boolean ADDThisClass(Class cls) {
        String className = cls.getSimpleName();
        if (!Classes.contains(className)) {
            Classes.add(className);
            return true;
        }

        return false;
    }

    public static boolean RemoveThisClass() {
        String className = getClassName(5);

        if (Classes.contains(className)) {
            Classes.remove(className);
            return true;
        }

        return false;
    }


    public static void setType(FSLoggerLimitationType type) {
        Type = type;
    }

    private static void logout(String message, int traceLevel)
    {
        if (LOGGING_ENABLED)
        {
            if (message.length() > 3000) {
                int length = message.length();
                String str = message;

                while (length > 3000) {
                    Log.d(_instance.TAG, getClassNameMethodNameAndLineNumber(traceLevel) + str.substring(0, 3000));
                    str = str.substring(3000);
                    length = str.length();
                }

                Log.d(_instance.TAG, getClassNameMethodNameAndLineNumber(traceLevel) + str);
            } else {
                Log.d(_instance.TAG, getClassNameMethodNameAndLineNumber(traceLevel) + message);
            }
        }
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static void logout(Integer code, String message) {

        if (Validate(code))
            logout(message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static void logout(Integer code) {

        if (Validate(code))
            logout("", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static void logout(String message) {
        if (Validate(null))
            logout(message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static void logout() {
        if (Validate(null))
            logout("", STACK_TRACE_LEVELS_UP);
    }

    private static boolean Validate(Integer code) {

        String ClassName = getClassName(5);
        switch (Type) {
            case ALL:
                //Apply ALL Limitations
                if ((ClassName != null && Classes.contains(ClassName)) && (code != null && Rules.contains(code))) return true;
                break;

            case ALLOR:
                //Apply ALL Limitations
                if (ClassName != null && Classes.contains(ClassName)) return true;
                if (code != null && Rules.contains(code)) return true;
                break;

            case NONE:
                //Don't show any log
                return false;

            case NOLimit:
                //There is no limitation. Then show ALL Logs
                return true;

            case Class:
                //Apply Class limitations only
                if (Classes.contains(ClassName)) return true;
                break;

            case Code:
                //Apply Code Limitations only
                if (code != null && Rules.contains(code)) return true;
                break;
        }

        return false;
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

    public enum FSLoggerLimitationType {
        Class,
        Code,
        ALL,
        ALLOR,
        NONE,
        NOLimit;
    }
}
