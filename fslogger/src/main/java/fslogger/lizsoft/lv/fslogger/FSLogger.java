package fslogger.lizsoft.lv.fslogger;

import android.util.Log;

import java.util.ArrayList;

import fslogger.lizsoft.lv.fslogger.enums.FSLoggerLogType;
import fslogger.lizsoft.lv.fslogger.listeners.FSLoggerListener;

/**
 * Created by Farhad
 * A Log Manager for Android
 */
public class FSLogger {

    //Variables----------------------------------------------------------------------------

    private static final int STACK_TRACE_LEVELS_UP = 6;
    private static String TAG = "FSLogger";
    private static boolean LOGGING_ENABLED = true;
    private static boolean LOGGING_WITH_BACKTRACE_ENABLED = false;
    private static FSLoggerLimitationType Type = FSLoggerLimitationType.NOLimit;

    private static ArrayList<Integer> Rules = new ArrayList<>();
    private static ArrayList<String> Classes = new ArrayList<>();

    private static FSLoggerListener listener;

    //Variables----------------------------------------------------------------------------


    private FSLogger(String tag) {
        this.TAG = tag;
    }

    /*
    * Instantiation of Logger
    * */
    public static void init(String tag) {

        TAG = tag;
        Rules.clear();
        Classes.clear();

    }

    /*
        Enable Logger
    */
    public static void enable() {
        LOGGING_ENABLED = true;
    }


    /*
         Disable Logger
    */
    public static void disable() {
        LOGGING_ENABLED = false;
    }

    /*
        Enable LOGGING WITH BACKTRACE
    */
    public static void enableLoggingWithBackTrace() {
        LOGGING_WITH_BACKTRACE_ENABLED = true;
    }

    /*
        Disable LOGGING WITH BACKTRACE
    */
    public static void disableLoggingWithBackTrace() {
        LOGGING_WITH_BACKTRACE_ENABLED = false;
    }

    public static void setCodes(ArrayList<Integer> rules) {
        Rules.clear();
        Rules = rules;
    }

    public static boolean addCode(Integer rule) {
        if (!Rules.contains(rule)) {
            Rules.add(rule);
            return true;
        }

        return false;
    }

    public static boolean removeCode(Integer rule) {
        if (Rules.contains(rule)) {
            Rules.remove(rule);
            return true;
        }

        return false;
    }

    public static boolean addClass(Class cls) {
        String className = cls.getSimpleName();

        if (!Classes.contains(className)) {
            Classes.add(className);
            return true;
        }

        return false;
    }

    public static boolean removeClass(Class cls) {
        String className = cls.getSimpleName();

        if (Classes.contains(className)) {
            Classes.remove(className);
            return true;
        }

        return false;
    }


    public static void setType(FSLoggerLimitationType type) {
        Type = type;
    }

    private static boolean logout(boolean isValid, FSLoggerLogType type, String message, int traceLevel) {
        if (message == null) message = "NULL";

        if (LOGGING_ENABLED && isValid) {
            if (message.length() > 3000) {
                int length = message.length();
                String str = message;

                while (length > 3000) {
                    logout(type, getClassNameMethodNameAndLineNumber(traceLevel) + str.substring(0, 3000));
                    str = str.substring(3000);
                    length = str.length();
                }

                logout(type, getClassNameMethodNameAndLineNumber(traceLevel) + str);
            } else {
                logout(type, getClassNameMethodNameAndLineNumber(traceLevel) + message);
            }
            return true;
        } else {
            if (listener != null) {
                if (message.length() > 3000) {
                    int length = message.length();
                    String str = message;

                    while (length > 3000) {
                        listener.logoutUnsuccess(type, TAG, getClassNameMethodNameAndLineNumber(traceLevel) + str.substring(0, 3000));
                        str = str.substring(3000);
                        length = str.length();
                    }

                    listener.logoutUnsuccess(type, TAG, getClassNameMethodNameAndLineNumber(traceLevel) + str);
                } else {
                    listener.logoutUnsuccess(type, TAG, getClassNameMethodNameAndLineNumber(traceLevel) + message);
                }
            }
            return false;
        }
    }

    private static void logout(FSLoggerLogType type, String message) {
        if (listener != null) {
            listener.logout(type, TAG, message);
        }
        switch (type) {
            case Debug:
                Log.d(TAG, message);
                break;
            case Error:
                Log.e(TAG, message);
                break;
            case Info:
                Log.i(TAG, message);
                break;
            case Verbose:
                Log.v(TAG, message);
                break;
            case Warn:
                Log.w(TAG, message);
                break;
            case WTF:
                Log.wtf(TAG, message);
                break;
        }
    }

    //LogOut-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean logout(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.Debug, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean logout(Integer code) {

        return logout(validate(code), FSLoggerLogType.Debug, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean logout(String message) {
        return logout(validate(null), FSLoggerLogType.Debug, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean logout() {
        return logout(validate(null), FSLoggerLogType.Debug, "", STACK_TRACE_LEVELS_UP);
    }

    //LogOut-----------------------------------------------------------------------------------------------


    //Debug-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean d(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.Debug, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean d(Integer code) {

        return logout(validate(code), FSLoggerLogType.Debug, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean d(String message) {
        return logout(validate(null), FSLoggerLogType.Debug, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean d() {
        return logout(validate(null), FSLoggerLogType.Debug, "", STACK_TRACE_LEVELS_UP);
    }

    //Debug-----------------------------------------------------------------------------------------------


    //Warn-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean w(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.Warn, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean w(Integer code) {

        return logout(validate(code), FSLoggerLogType.Warn, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean w(String message) {
        return logout(validate(null), FSLoggerLogType.Warn, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean w() {
        return logout(validate(null), FSLoggerLogType.Warn, "", STACK_TRACE_LEVELS_UP);
    }

    //Warn-----------------------------------------------------------------------------------------------


    //Info-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean i(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.Info, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean i(Integer code) {

        return logout(validate(code), FSLoggerLogType.Info, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean i(String message) {
        return logout(validate(null), FSLoggerLogType.Info, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean i() {
        return logout(validate(null), FSLoggerLogType.Info, "", STACK_TRACE_LEVELS_UP);
    }

    //Info-----------------------------------------------------------------------------------------------

    //WTF-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean wtf(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.WTF, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean wtf(Integer code) {

        return logout(validate(code), FSLoggerLogType.WTF, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean wtf(String message) {
        return logout(validate(null), FSLoggerLogType.WTF, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean wtf() {
        return logout(validate(null), FSLoggerLogType.WTF, "", STACK_TRACE_LEVELS_UP);
    }

    //WTF-----------------------------------------------------------------------------------------------

    //Error-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean e(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.Error, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean e(Integer code) {

        return logout(validate(code), FSLoggerLogType.Error, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean e(String message) {
        return logout(validate(null), FSLoggerLogType.Error, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean e() {
        return logout(validate(null), FSLoggerLogType.Error, "", STACK_TRACE_LEVELS_UP);
    }

    //Error-----------------------------------------------------------------------------------------------

    //Verbose-----------------------------------------------------------------------------------------------

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    public static boolean v(Integer code, String message) {

        return logout(validate(code), FSLoggerLogType.Verbose, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with type, It will check type and if we are OK with logging it out then it will log it out
    //Message here will be blank
    public static boolean v(Integer code) {

        return logout(validate(code), FSLoggerLogType.Verbose, "", STACK_TRACE_LEVELS_UP);
    }


    //Normal Logout
    public static boolean v(String message) {

        return logout(validate(null), FSLoggerLogType.Verbose, message, STACK_TRACE_LEVELS_UP);
    }

    //Logout with empty message to just track class name and line number
    public static boolean v() {

        return logout(validate(null), FSLoggerLogType.Verbose, "", STACK_TRACE_LEVELS_UP);
    }

    //Verbose-----------------------------------------------------------------------------------------------

    private static boolean validate(Integer code) {

        String ClassName = getClassName(5);
        switch (Type) {
            case ALL:
                //Apply ALL Limitations
                if ((ClassName != null && Classes.contains(ClassName)) && (code != null && Rules.contains(code)))
                    return true;
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
                if (Classes.size() > 0 && Classes.contains(ClassName)) return true;
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
     * @return int - Current line number.
     * @author Farhad
     */
    private static int getLineNumber(int traceLevel) {
        return Thread.currentThread().getStackTrace()[traceLevel].getLineNumber();
    }

    /**
     * Get the current class name. Note, this will only work as called from this
     * class as it has to go a predetermined number of steps up the stack trace.
     * In this case 5.
     *
     * @return String - Current line number.
     * @author Farhad
     */
    private static String getClassName(int traceLevel) {
        String fileName = Thread.currentThread().getStackTrace()[traceLevel].getFileName();

        // Farhad: Removing ".java" and returning class name
        return fileName.substring(0, fileName.length() - 5);
    }

    /**
     * Get the current method name. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return String - Current line number.
     * @author Farhad
     */
    private static String getMethodName(int traceLevel) {
        return Thread.currentThread().getStackTrace()[traceLevel].getMethodName();
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <class_name>.<method_name>()-<line_number>
     *
     * @return String - String representing class name, method name, and line
     * number.
     * @author Farhad
     */
    private static String getClassNameMethodNameAndLineNumber(int traceLevel) {
        String res = "";
        if (LOGGING_WITH_BACKTRACE_ENABLED)
            res += "[" + getClassName(traceLevel + 1) + "." + getMethodName(traceLevel + 1) + "()-" + getLineNumber(traceLevel + 1) + "]: ";
        res += "[" + getClassName(traceLevel) + "." + getMethodName(traceLevel) + "()-" + getLineNumber(traceLevel) + "]: ";

        return res;
    }

    public static void setListener(FSLoggerListener listener) {
        FSLogger.listener = listener;
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
