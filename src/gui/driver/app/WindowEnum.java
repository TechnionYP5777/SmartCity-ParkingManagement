package gui.driver.app;

//An Enum used for returning to the previous window. still has no functionality.
public enum WindowEnum {
	OPENING(0), CHOOSE_ACTION(1), GET_PASS_BY_MAIL(2), MY_DETAILS(3), MY_DETAILS_EDIT(4), MAIN(5), SIGN_UP(6), LOG_IN(
			7), CHOOSE_DESTINATION(8), REGISTER(9), ABOUT(10), NONE(11);

	WindowEnum(int __) {

	}
}

// A small experiment that didn't help. saving it for later use perhaps:
// public enum WindowEnum {
// CHOOSE_ACTION (ChooseAction.class),
// GET_PASS_BY_MAIL (GetPassByMail.class),
// MAIN (Object.class),
// SIGN_UP (Object.class),
// LOG_IN (Object.class),
// CHOOSE_DESTINATION (Object.class),
// NONE (Object.class);
//
// Class<Object> myClass;
// private WindowEnum(Class classy) {
// // TODO Auto-generated constructor stub
// myClass = classy;
// System.out.println("myClass: " + myClass.getName() + ". classy: " +
// classy.getName());
// }
//
// public Class getClassByEnum(){
// return myClass;
// }
// }
