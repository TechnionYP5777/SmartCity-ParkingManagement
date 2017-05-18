package data.management;

import java.util.List;
import java.util.Map;

import org.parse4j.ParseObject;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import Exceptions.LoginException;

public interface DatabaseManager {

	public void initialize();
	
	public void insertObject(final String objectClass, Map<String, Object> keyValues,Map<String, Object> fields);
	
	public void insertObject(final String objectClass,Map<String, Object> keyValues, Map<String, Object> fields,SaveCallback c);

	public void deleteObject(final String objectClass,Map<String, Object> fields);
	
	public void deleteObject(final String objectClass,Map<String, Object> fields,DeleteCallback c);
	
	public void update(final String objectClass,Map<String, Object> keys,Map<String, Object> newFields);
	
	public void getObjectByFields(final String objectClass, Map<String, Object> values,GetCallback<ParseObject> o);
	
	public Map<String, Object> getObjectFieldsByKey(final String objectClass, Map<String, Object> keys);
	
	public List<ParseObject> getAllObjects (final String objectClass,int startLimit);
	
	public void login (String userClass,String userNameKey, String userName, String passwordKey, String password) throws LoginException;
	
	public void register(String userClass, Map<String, Object> userKeys, Map<String, Object> userFields)throws LoginException;
}
