package com;

import com.router.ActionDispatcher;

public class RouterTest {
	
	public static void main(String[] args) throws Exception{
		ActionDispatcher dispatcher = new ActionDispatcher();
		dispatcher.init("router.xml");
	}

}
