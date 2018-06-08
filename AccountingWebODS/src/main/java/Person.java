/**
 *
 * @author aneta
 */
public class Person {
    private String fullName;
    private String address;
    
    public Person(String name, String address){
        this.fullName = name;
        this.address = address;
    }
    
    public String getName(){
        return fullName;
    }
    
    public String getAddress(){
        return address;
    }

}
