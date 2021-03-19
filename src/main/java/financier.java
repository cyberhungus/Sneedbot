import java.util.ArrayList;

public class financier {
    static financier instance;
    ArrayList<Character> desired;
    String letters="qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM";
    int price = 1;
    int balance=1000;


    final int NORMAL_MODE=0;
    final int HURRY_MODE=1;
    final int BORING_MODE=2;
    private int mode=1;

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
         //hier soll der preis in abhängigkeit der benötigten Buchstaben gesetzt werden
        bidPrice*=calculatePriceBasedOnStrategy(this.mode);
        String[] array =message.getMessage().split(" ");
        for (String s: array){
            if (isNumeric(s)){ //eigentlich dämlich, array[2] würde reichen
                result+=s;
                result+=" "+bidPrice; //hier muss ein smart pricing modell rein
                //result+=" 1";
                auctionRepository aH = auctionRepository.getInstance();
                aH.registerAuction(new auction(Integer.parseInt(array[2]),array[3],bidPrice,message.getUser()));

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

    private int maxPossiblePriceForLetter(){ //soll wie im hurry mode den preis ermitteln, den wir für jeden Buchstaben bezahlen können
        saleAuthority saleBoy = saleAuthority.getInstance();
        financier f = financier.getInstance();
        int result=0;
        result = f.getBalance()/saleBoy.neededItemsCount();
        return result;
    }

    private int calculatePriceBasedOnStrategy(int mode){
        int result =1;
        saleAuthority saleBoy = saleAuthority.getInstance();
        financier f = financier.getInstance();
        switch (mode){

            case NORMAL_MODE:       //normal, biete für jeden gewünschten buchstaben 1, dann 2, dann 3 usw.

                result*=saleBoy.calculateCompletedLetters();
                break;
            case HURRY_MODE:        //hurry, biete für jeden gewünschten buchstaben so viel wie wir uns leisten können
                result = maxPossiblePriceForLetter();
                break;
            case BORING_MODE: //BORING, zahlt immer 1
                result=1;
                break;


        }
        return result;
    }

    //für einen Preisalgorithmus sollte eine Rolle spielen, wie naher wir an der vervollständigung unseres Satzes stehen.
    //saleAuthority muss bemerken, wie viele der Buchstaben noch fehlen. je weniger fehlen, desto teurer kaufen wir
    //saleauthority muss bemerken, wenn wir einen der gewünschten Buchstaben haben, und ihn aus der liste der gewünschten Buchstaben entfernen. für diesen zahlen wir nicht mehr



}
