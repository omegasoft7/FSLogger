package fslogger.lizsoft.lv.fslogger;

import android.app.Application;
import android.test.ApplicationTestCase;


public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        String str = null;

        FSLogger.init("Test");



        //With no Limitation
        FSLogger.setType(FSLogger.FSLoggerLimitationType.NOLimit);

        assertTrue(FSLogger.logout("hello"));

        assertTrue(FSLogger.logout(str));
        assertTrue(FSLogger.logout(125, "hello"));
        assertTrue(FSLogger.logout());




        //With Class Limitation
        FSLogger.setType(FSLogger.FSLoggerLimitationType.Class);

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertFalse(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());

        FSLogger.addClass(this.getClass());

        assertTrue(FSLogger.logout("hello"));
        assertTrue(FSLogger.logout(str));
        assertTrue(FSLogger.logout(125, "hello"));
        assertTrue(FSLogger.logout());

        FSLogger.removeClass(this.getClass());

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertFalse(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());


        //With Code Limitation
        FSLogger.setType(FSLogger.FSLoggerLimitationType.Code);

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertFalse(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());



        FSLogger.addCode(125);

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertTrue(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());



        FSLogger.removeCode(125);

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertFalse(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());



        //With ALL Limitation
        FSLogger.setType(FSLogger.FSLoggerLimitationType.ALL);
        FSLogger.addClass(this.getClass());
        FSLogger.addCode(125);

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertTrue(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());


        //With ALLOR Limitation
        FSLogger.setType(FSLogger.FSLoggerLimitationType.ALLOR);
        assertTrue(FSLogger.logout("hello"));
        assertTrue(FSLogger.logout(str));
        assertTrue(FSLogger.logout(125, "hello"));
        assertTrue(FSLogger.logout());

        FSLogger.removeCode(125);
        FSLogger.removeClass(this.getClass());



        //Without Limitation
        FSLogger.setType(FSLogger.FSLoggerLimitationType.NOLimit);

        assertTrue(FSLogger.logout("hello"));
        assertTrue(FSLogger.logout(str));
        assertTrue(FSLogger.logout(125, "hello"));
        assertTrue(FSLogger.logout());


        //Stop working
        FSLogger.setType(FSLogger.FSLoggerLimitationType.NONE);

        assertFalse(FSLogger.logout("hello"));
        assertFalse(FSLogger.logout(str));
        assertFalse(FSLogger.logout(125, "hello"));
        assertFalse(FSLogger.logout());
    }
}


