public class auction {
   private int number;
   private String letter;
   private int price;
   private Boolean active;

    auction(int number, String letter, int price){
        this.number=number;
        this.letter=letter;
        this.price=price;
        this.active=true;
    }

    public int getNumber() {
        return number;
    }

    public String getLetter() {
        return letter;
    }

    public int getPrice() {
        return price;
    }
    public void finish(){
        active=false;
    }

    @Override
    public String toString() {
        return "auction{" +
                "number=" + number +
                ", letter='" + letter + '\'' +
                ", price=" + price +
                ", active=" + active +
                '}';
    }
}
