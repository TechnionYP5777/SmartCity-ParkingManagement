package data.management;

import java.util.List;
import java.util.Map;

import org.parse4j.ParseObject;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import Exceptions.LoginException;

public class DatabaseManagerImpl implements DatabaseManager {
	
	@Override
	public void initialize() {
		DBManager.initialize();
	}

	@Override
	public void insertObject(String objectClass, Map<String, Object> keyValues, Map<String, Object> fields) {
			DBManager.insertObject(objectClass, keyValues, fields);
	}

	@Override
	public void insertObject(String objectClass, Map<String, Object> keyValues, Map<String, Object> fields,
			SaveCallback c) {	
		DBManager.insertObject(objectClass, keyValues, fields, c);
	}

	@Override
	public void deleteObject(String objectClass, Map<String, Object> fields) {
		DBManager.deleteObject(objectClass, fields);
	}

	@Override
	public void deleteObject(String objectClass, Map<String, Object> fields, DeleteCallback c) {
		DBManager.deleteObject(objectClass, fields, c);		
	}

	@Override
	public void update(String objectClass, Map<String, Object> keys, Map<String, Object> newFields) {
		DBManager.update(objectClass, keys, newFields);
	}

	@Override
	public void getObjectByFields(String objectClass, Map<String, Object> values, GetCallback<ParseObject> o) {
		DBManager.getObjectByFields(objectClass, values, o);
	}

	@Override
	public Map<String, Object> getObjectFieldsByKey(String objectClass, Map<String, Object> keys) {
		return DBManager.getObjectFieldsByKey(objectClass, keys);
	}

	@Override
	public List<ParseObject> getAllObjects(String objectClass, int startLimit) {
		return getAllObjects(objectClass, startLimit);
	}

	@Override
	public void login(String userClass, String userNameKey, String userName, String passwordKey, String password)
			throws LoginException {
		DBManager.login(userClass, userNameKey, userName, passwordKey, password);		
	}

	@Override
	public void register(String userClass, Map<String, Object> userKeys, Map<String, Object> userFields)
			throws LoginException {
		DBManager.register(userClass, userKeys, userFields);
	}
}
