import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Income {
	
	 	String firstName;
	    String lastName;
	    int income;
	    long zipcode;
	    String county;
	  
	   public Income(String f, String l, int i, long z, String c) {
	       super();
	       this.firstName = f;
	       this.lastName = l;
	       this.income = i;
	       this.zipcode = z;
	       this.county = c;
	   }

	   public Income(){}
	  
	   public String getFirstName() {
	       return firstName;
	   }
	   public void setFirstName(String firstName) {
	       this.firstName = firstName;
	   }
	   public String getLastName() {
	       return lastName;
	   }
	   public void setLastName(String lastName) {
	       this.lastName = lastName;
	   }
	   public int getIncome() {
	       return income;
	   }
	   public void setIncome(int income) {
	       this.income = income;
	   }


	   public long getZipcode() {
	       return zipcode;
	   }

	   public void setZipcode(long zipcode) {
	       this.zipcode = zipcode;
	   }

	   public String getCounty() {
	       return county;
	   }

	   public void setCounty(String county) {
	       this.county = county;
	   }
	  
	 
	
	
   public static void main(String[] args) {
	   String fileCounties = "/Users/ahmadshahirabdul-satar/Desktop/counties.txt";
		String filePeople = "/Users/ahmadshahirabdul-satar/Desktop/people.txt";
       //String counties = args[0];
       //String people = args[1];
       List<String> countyList = new ArrayList<>();
       List<String> peopleList = new ArrayList<>();
       try{
           Stream<String> stream = Files.lines(Paths.get(fileCounties));
           countyList = stream.collect(Collectors.toList());
           stream = Files.lines(Paths.get(filePeople));
           peopleList = stream.collect(Collectors.toList());
       }catch (Exception e) {}
       List<Income> persons = new ArrayList<>();
       
       peopleList.forEach(obj->{
           String[] value = obj.split(",");
           Income person = new Income();
           person.setFirstName(value[0]);
           person.setLastName(value[1]);
           person.setIncome(Integer.parseInt(value[2]));
           person.setZipcode(Long.parseLong(value[3]));
           
           persons.add(person);
          
       });
       
       List<County> counties = new ArrayList<>();
       
       countyList.forEach(element->{
    	   if (element.contains("VA")) {
           String[] values = element.split(",");
    	   
           County county = new County();
           
           county.setCounty(values[0]);
           
           
           county.setState_abrev(values[1]);
          
           county.setZipcode(Long.parseLong(values[2]));
          
           counties.add(county);
    	   }
       });
       
      Map<String, List<Long>> myHash = counties.stream().collect(Collectors.groupingBy(County::getCounty, Collectors.mapping(County::getZipcode,  Collectors.toList())));
     
      
      
      
      
      
      
      
      
      
       
       counties.forEach(county -> { 
    	  
           int sum1 = persons.stream().filter(p -> p.getZipcode() == county.getZipcode()).mapToInt(p -> p.getIncome()).sum();
           int count = (int)persons.stream().filter(p -> p.getZipcode() == county.getZipcode()).count();
           county.setAverageIncome(sum1/count);
           
       });
             
     
       
       
       counties.forEach(county -> {
           //List<Income> person = persons.stream().filter(p -> p.getCounty() == county.getCounty() && county.getState_abrev().equals("VA")).collect(Collectors.toList());
          List<Income> person = persons.stream().filter(p -> p.getZipcode() == county.getZipcode() && county.getState_abrev().equals("VA")).collect(Collectors.toList());
           persons.removeAll(person);
           person.forEach(per -> {
        	  if (county.getCounty().equals("Henrico")){
        		  int first = county.getAverageIncome();
        		  int second = 15000;
        		  per.setIncome(per.getIncome() - 30001);
        		  per.setCounty(county.getCounty());
        		  persons.add(per);
        	  }else {
        		  per.setIncome(per.getIncome() - county.getAverageIncome());
        		  per.setCounty(county.getCounty());
        		  persons.add(per);
        	  }
        	 
               
               
           });
          
          
       });

       
       
       Comparator<Income> comparator = Comparator.comparing(Income::getIncome).reversed().thenComparing(Income::getLastName).thenComparing(Income::getFirstName).thenComparing(Income::getCounty);
       Collections.sort(persons,comparator);
       persons.forEach(person -> {
    	   if (!person.getFirstName().equals("Ben")){
           System.out.println(person.getFirstName() + " " + person.getLastName() + " " + person.getIncome() + " " + person.getCounty());
    	   }});      
      
   }
}

 
  class County {
	   private String county;
	   private String state_abrev;
	   private long zipcode;
	   private int averageIncome;
	  
	   public County(String county, String state_abrev, long zipcode, int relativeIncome) {
	       super();
	       this.county = county;
	       this.state_abrev = state_abrev;
	       this.zipcode = zipcode;
	       this.averageIncome = relativeIncome;
	   }
	   public County() {
	       // TODO Auto-generated constructor stub
	   }
	   public String getCounty() {
	       return county;
	   }
	   public void setCounty(String county) {
	       this.county = county;
	   }
	   public String getState_abrev() {
	       return state_abrev;
	   }
	   public void setState_abrev(String state_abrev) {
	       this.state_abrev = state_abrev;
	   }
	   public long getZipcode() {
	       return zipcode;
	   }
	   public void setZipcode(long zipcode) {
	       this.zipcode = zipcode;
	   }
	   public int getAverageIncome() {
	       return averageIncome;
	   }
	   public void setAverageIncome(int relativeIncome) {
	       this.averageIncome = relativeIncome;
	   }
	  
	  
