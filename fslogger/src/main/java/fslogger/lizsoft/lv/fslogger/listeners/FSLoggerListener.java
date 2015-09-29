package fslogger.lizsoft.lv.fslogger.listeners;

import fslogger.lizsoft.lv.fslogger.enums.FSLoggerLogType;

/**
 * Created by farhad on 15.29.9.
 */
public interface FSLoggerListener {
    void logout(FSLoggerLogType type, String tag, String message);
}
