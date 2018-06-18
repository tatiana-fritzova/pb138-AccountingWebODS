package backend;

import java.util.Objects;


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
    
    public void setName(String name){
        this.fullName = name;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    
    @Override
    public String toString(){
        return "Person{" +
                "fullName=" + fullName +
                ", address=" + address + "}";
    }
    
    @Override
    public int hashCode() {
        return  Objects.hash(this.fullName, this.address);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Person other = (Person) obj;
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }
}