package com.investigation.oracle_mongo_bridge.data_access;

import com.investigation.oracle_mongo_bridge.exceptions.EDataAccess;

public interface ISequenceGenerator {
	
	public long getNextId() throws EDataAccess;
}
