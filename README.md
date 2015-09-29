
# FSLogger

An Android Logger with a lot of options for debugging that helps a lot to developers.
You can easily add logs everywhere in your APP and manage showing them by limit them by Codes or by their Class name. It also will show you that logs was called from which file and which line, you also can see which class and which line called your current method.


![alt tag](https://github.com/omegasoft7/FSLogger/blob/master/logger.png)


# Gradle:

compile 'com.github.omegasoft7.FSLogger:fslogger:1.9.0@aar'


##Iniatialization:
for initialization you just need to add following line into your Application class.

```sh
//initialize FSLogger
FSLogger.init("MyAwesomeAPP");
```

##Types of Limitation:
-)You can set different types for make limitation of showing logs.
>- Class --> Show logs of added class only.
>- Code --> Show logs of added Codes only. For this type you have to use logout(int code, String message). Other types of logs will not show in this type.
>- ALL --> This type is exactly the same with "Code" but the only difference is that it will also check added Class too. If your sender class is not added it will not show any log.
>- ALLOR --> In this type it will check both Code and Class codes and if on of them was true it will show the code
>- NONE --> Do not show any log
>- NOLimit --> Show all logs

You can set your type like following in your Application class:
```sh
FSLogger.setType(FSLogger.FSLoggerLimitationType.NOLimit);
```

##Usage:
You have different options to use it.
>- Simple: With a simple message

```sh
FSLogger.logout("your log message");

//Result:
//[MainActivity.onClick()-336]: your log message
```

>- Without Message: To track lines of code. you don't need to specify any text for that.

```sh
FSLogger.logout();

//Result:
//[MainActivity.onClick()-336]:
```

>- Without Message with Code: To track lines of code. you don't need to specify any text for that. Specify a code for your log. If you add this code into FSLogger it will show logs with this code otherwise it will just ignore them

```sh
//Specify code in Application class like:
//FSLogger.addCode(12);
FSLogger.logout(12);

//Result:
//[MainActivity.onClick()-336]:
```

>- With Code: Specify a code for your log. If you add this code into FSLogger it will show logs with this code otherwise it will just ignore them.

```sh
//Specify code in Application class like:
//FSLogger.addCode(12);

FSLogger.logout(12);

//Result:
//[MainActivity.onClick()-336]:
```


##Log Levels:
You have a few options for logging
>- FSLogger.logout();  ==  Debug
>- FSLogger.d();  ==  Debug
>- FSLogger.e();  ==  Error
>- FSLogger.v();  ==  Verbose
>- FSLogger.i();  ==  Info
>- FSLogger.wtf();  ==  Assert



##Check Caller of our current function:
If you want to see which class and in which line called your current message you have to add following code into your Application class:

```sh
//Result Before:
//[ClassB.test()-120]: your log message

FSLogger.enableLoggingWithBackTrace();

//Result After:
//[MainActivity.onClick()-336]: [ClassB.test()-120]: your log message
```

# Crashlytics
If you are using Crashlytics(Fabric.io), you can implement a log collector in Application class for your crashlytics like following:

```sh
FSLogger.disable();
FSLogger.setListener(new FSLoggerListener() {
    @Override
    public void logout(FSLoggerLogType fsLoggerLogType, String tag, String message) {
        logCrashlytics(fsLoggerLogType, tag, message);
    }
    
    @Override
    public void logoutUnsuccess(FSLoggerLogType fsLoggerLogType, String tag, String message) {
        logCrashlytics(fsLoggerLogType, tag, message);
    }
    
    private void logCrashlytics(FSLoggerLogType fsLoggerLogType, String tag, String message) {
        int log;
        switch (fsLoggerLogType) {
            case Debug:
                log = Log.DEBUG;
                break;
            case Error:
                log = Log.ERROR;
                break;
            case Info:
                log = Log.INFO;
                break;
            case Verbose:
                log = Log.VERBOSE;
                break;
            case Warn:
                log = Log.WARN;
                break;
            case WTF:
                log = Log.VERBOSE;
                break;
            default:
                log = Log.VERBOSE;
        }
        Crashlytics.log(log, tag, message);
    }
});
```

# License
Apache 2.0
