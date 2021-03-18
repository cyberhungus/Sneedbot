import java.util.ArrayList;

public class auctionRepository {
    static auctionRepository instance;
    ArrayList<auction> activeAuctions =new ArrayList<auction>();


    static auctionRepository getInstance(){
        if (instance==null){
            instance=new auctionRepository();
            return instance;
        }
        else return instance;
    }

    void receiveAuction(MessageWrapper message){
       String text = message.getMessage();

    }

    public void registerAuction(auction newAuction) {
        activeAuctions.add(newAuction);
        System.out.println(newAuction.toString());
    }

    public int getAuctionID(int query){ //hier soll man eine AuktionsID suchen können, um sie zu manipulieren oder zu verschieben oder so
        for (auction e:activeAuctions){
            if (e.getNumber()==query){
                return activeAuctions.indexOf(e);
            }
        }
        return -1;
    }

    public void finishAuctionSuccess(int query){
        int position = getAuctionID(query);
        accountant acc = accountant.getInstance();
        acc.passAuction(activeAuctions.get(position));
        activeAuctions.remove(position);
    }
}
