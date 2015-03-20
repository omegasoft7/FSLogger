Download:

compile 'com.github.omegasoft7.FSLogger:FSLogger:1.2.0@aar'

Easy Initialize:

//initialize 
FSLogger FSLogger.Init("MyAPP");

Pro Initialize: 
//initialize FSLogger 
FSLogger.Init("MyAPP"); 
ArrayList types = new ArrayList<>(); 
for (LoggerType lt:LoggerType.values()) { 
	types.add(lt.getCode()); 
} 
FSLogger.ADDRules(types);


public enum LoggerType { 
	MainActivity(1), 
	MenuActivity(2);

	private int Code;
	LoggerType(int code) {
	    this.Code = code;
	}

	public int getCode() {
	    return Code;
	}
}
