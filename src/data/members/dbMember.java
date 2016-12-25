package data.members;

import org.parse4j.ParseObject;

public abstract class dbMember {
	protected ParseObject parseObject;
	protected String objectId;

	public String getobjectId() {
		return objectId;
	}

	public ParseObject getParseObject() {
		return parseObject;
	}

	public void setParseObject(String tableName) {
		parseObject = new ParseObject(tableName);
	}

	public void setObjectId() {
		objectId = parseObject.getObjectId();
	}
}
