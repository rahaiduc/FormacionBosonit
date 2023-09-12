package LecturaDesdeCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LecturaFicheros {
	public static void main(String [] args) throws IOException {
		Scanner sc=new Scanner(System.in);
		List<Person> people=new ArrayList<Person>();
		LeerDesdeCSV(people);
		System.out.println("--------Lista Entera\r\n");
		for(Person per:people) {
			System.out.println(per.toString());
		}
		System.out.println("\r\n--------Apartado A: Personas con edad menor a 25 años\r\n"); 
		List<Person> under25=people.stream().filter(person->person.getAge()<25 && person.getAge()>0).collect(Collectors.toList());
		for(Person p:under25) {
			System.out.println(p.toString());
		}
		System.out.println("\r\n---------Apartado B: Personas sin la inicial A en el nombre\r\n");
		List<Person> sinInicialA=people.stream().filter(person->!person.getName().startsWith("A") && !person.getName().startsWith("Á") ).collect(Collectors.toList());
		for(Person p:sinInicialA) {
			System.out.println(p.toString());
		}
		System.out.println("\r\n---------Apartado C: Primera persona de Madrid\r\n");
		Optional<Person> firstPersonFromMadrid = people.stream()
                .filter(person -> person.getTown().equalsIgnoreCase("Madrid"))
                .findFirst();

        firstPersonFromMadrid.ifPresentOrElse(
            person -> System.out.println("Primera persona de Madrid: " + person.toString()),
            () -> System.out.println("No se han encontrado personas de Madrid.")
        );
        System.out.println("\r\n---------Apartado D: Primera persona de Barcelona\r\n");
		Optional<Person> firstPersonFromBarcelona = people.stream()
                .filter(person -> person.getTown().equalsIgnoreCase("Barcelona"))
                .findFirst();

		firstPersonFromBarcelona.ifPresentOrElse(
            person -> System.out.println("Primera persona de Barcelona: " + person.toString()),
            () -> System.out.println("No se han encontrado personas de Barcelona.")
        );
        
		
	} 
	
	public static void LeerDesdeCSV(List<Person> people) throws IOException {
		try {
			Path path = Paths.get("C:\\Users\\rauldan.haiduc\\Desktop\\people.csv");
			BufferedReader reader = Files.newBufferedReader(path);
			String line = reader.readLine();
			int lineasLeidas=1;
			while(line!=null) {
				Person p;
				 String[] partesLine=line.split(":");
				 Matcher matcher = Pattern.compile(":").matcher(line);
			     for(int i=0;i<2;i++) {
			    	 if(!matcher.find()) {
			    		 if(i==0) {
			    			 throw new InvalidLineFormatException("Se ha producido un error en la linea "+lineasLeidas+": Faltan dos delimitadores de campo");
			    		 }else {
			    			 throw new InvalidLineFormatException("Se ha producido un error en la linea "+lineasLeidas+": Falta el último delimitador de campo (:)");
			    		 }
			    	 }
			     }				 
				 if(partesLine[0]=="" || partesLine[0]==null) {
					 throw new InvalidLineFormatException("Se ha producido un error en la linea "+lineasLeidas+": El nombre es obligatorio. Hay 3 espacios en el campo y esto se considera como blank.");
				 }else {
					 p=new Person(partesLine[0]);
				 }
				 if(partesLine.length>1 && partesLine[1]!="" && partesLine[1]!=null) {
					 p.setTown(partesLine[1]);
				 }
				 if(partesLine.length>2 && partesLine[2]!="" && partesLine[2]!=null) {
					 p.setAge(Integer.valueOf(partesLine[2]));
				 }
				 people.add(p);
				 line=reader.readLine();
				 lineasLeidas++;
			}
			
		}
		catch(InvalidLineFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	
	}
}
