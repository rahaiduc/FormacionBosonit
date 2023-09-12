package LecturaCSV;

public class Person {
	
	private String name;
	private String town;
	private int age;
	Person(String name){
		this.name=name;
		this.town="unknown";
		this.age=0;
	}
	Person(String name, String town, int age){
		this.name=name;
		this.town=town;
		this.age=age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String toString() {
		return "Name:" + this.name+ ". Town: "+this.town + ". Age: " + this.age;
	}
	
}
