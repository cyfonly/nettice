package com;

import com.mongodb.MongoClient;
import com.server.dbclient.Mongodb;

public class DbClientTest {
	
	public static void main(String[] args) throws Exception{
		MongoClient client = Mongodb.getClient();
	}

}
