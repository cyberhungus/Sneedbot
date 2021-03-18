import java.util.ArrayList;

public class financier {
    static financier instance;
    ArrayList<Character> desired;
    String letters="qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM";
    int price = 1;
    int balance=1000;

    static financier getInstance(){
        if (instance==null){
            instance=new financier();
            return instance;
        }
        else return instance;
    }
   financier(){}

   public void makeBid(MessageWrapper message){
        BotMain bot = BotMain.getInstance();
        bot.messager(message.getChannel(),createBidString(message));


   }

    private String createBidString(MessageWrapper message) {
        String result="!seg auction bid ";
        int bidPrice = price;
        saleAuthority saleBoy = saleAuthority.getInstance(); //hier soll der preis in abhängigkeit der benötigten Buchstaben gesetzt werden
        bidPrice*=saleBoy.neededItemsCount();
        String[] array =message.getMessage().split(" ");
        for (String s: array){
            if (isNumeric(s)){ //eigentlich dämlich, array[2] würde reichen
                result+=s;
                result+=" "+bidPrice; //hier muss ein smart pricing modell rein
                auctionRepository aH = auctionRepository.getInstance();
                aH.registerAuction(new auction(Integer.parseInt(array[2]),array[3],bidPrice));

                return result;
            }
        }
        return "error";
    }

    private boolean isNumeric(String s) {
        char[] items = s.toCharArray();
        for (char c: items){
            if (letters.contains(c+"")){
                return false;
            }

        }
        return true;
    }

    public void withdraw(int value){
        balance-=value;
        System.out.println("WITHDREW: "+value+" NEW BALANCE: "+balance);
    }
    public void deposit(int value){
        balance+=value;
    }
    public int getBalance(){
        return balance;
    }

    //für einen Preisalgorithmus sollte eine Rolle spielen, wie naher wir an der vervollständigung unseres Satzes stehen.
    //saleAuthority muss bemerken, wie viele der Buchstaben noch fehlen. je weniger fehlen, desto teurer kaufen wir
    //saleauthority muss bemerken, wenn wir einen der gewünschten Buchstaben haben, und ihn aus der liste der gewünschten Buchstaben entfernen. für diesen zahlen wir nicht mehr



}
