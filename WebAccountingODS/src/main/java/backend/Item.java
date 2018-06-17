package backend;


import java.text.DecimalFormat;
import java.util.Objects;

public class Item {
    private String description;
    private Double price;

    private Double fancyFormat(Double d){
        DecimalFormat df = new DecimalFormat("####0.00");
        return Double.parseDouble(String.valueOf(df.format(d)));
    }

    public Item(String description, Double price) {
        this.description = description;
        this.price = fancyFormat(price);
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(double price){
        this.price = price;
    }
    
    @Override
    public String toString(){
        return "Item{" +
                "description=" + description +
                ", price=" + price + "}";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Item other = (Item) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }
}
