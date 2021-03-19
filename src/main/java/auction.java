public class auction {
   private int number;
   private String letter;
   private int price;
   private Boolean active;
   private String user;

    auction(int number, String letter, int price, String user){
        this.number=number;
        this.letter=letter;
        this.price=price;
        this.active=true;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "auction{" +
                "number=" + number +
                ", letter='" + letter + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", user='" + user + '\'' +
                '}';
    }
}
