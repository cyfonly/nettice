package com;

public class StaticTest {
	
	public static String name = "aaa";
	public String address = "gd";
	
	static{
		name = "bbb";
		System.out.println("--->  静态代码块被执行   <---   name=" + name);
	}
	
	{
		address = "bj";
		System.out.println("--->  非静态代码块被执行   <---   address=" + address);
	}
	
	public StaticTest() {
		System.out.println("--->  构造函数被执行   <---");
	}
	
	public static String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public static void main(String[] args){
		System.out.println(StaticTest.name);
		System.out.println("==================");
		StaticTest test = new StaticTest();
	}
	
}
