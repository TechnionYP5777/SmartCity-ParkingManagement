package gui.driver.shaharTesting;

//An Enum used for returning to the previous window. still has no functionality.
public enum WindowEnum {
	CHOOSE_ACTION 		, 
	GET_PASS_BY_MAIL	,
	MY_DETAILS			,
	MY_DETAILS_EDIT		,
	MAIN				,
	SIGN_UP				,
	LOG_IN 				,
	CHOOSE_DESTINATION	,
	NONE				;
}


//A small experiment that didn't help. saving it for later use perhaps:
//public enum WindowEnum {
//	CHOOSE_ACTION 		(ChooseAction.class), 
//	GET_PASS_BY_MAIL	(GetPassByMail.class),
//	MAIN				(Object.class),
//	SIGN_UP				(Object.class),
//	LOG_IN 				(Object.class),
//	CHOOSE_DESTINATION	(Object.class),
//	NONE				(Object.class);
//	
//	Class<Object> myClass;
//	private WindowEnum(Class classy) {
//		// TODO Auto-generated constructor stub
//		myClass = classy;
//		System.out.println("myClass: " + myClass.getName() + ". classy: " + classy.getName());
//	}
//	
//	public Class getClassByEnum(){
//		return myClass;
//	}
//}





