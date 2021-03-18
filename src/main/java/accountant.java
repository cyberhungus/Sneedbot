import java.util.ArrayList;

public class accountant {
    String stock="";
    ArrayList<auction> pastAuctions =new ArrayList<auction>();
    static accountant instance;



    static accountant getInstance(){
        if (instance==null){
            instance=new accountant();
            return instance;
        }
        else return instance;
    }

    void passAuction(auction pastAuction){
        pastAuction.finish();
        pastAuctions.add(pastAuction);
        stock+=pastAuction.getLetter();
        saleAuthority saleBoy = saleAuthority.getInstance();
        saleBoy.desireHandler(pastAuction.getLetter());
        System.out.println("In the StoreRoom:" + stock);
    }




}
