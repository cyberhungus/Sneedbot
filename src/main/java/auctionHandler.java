public class auctionHandler {
    static String botID="<@821364570966327337>";

    static void checkContent(MessageWrapper message){
        if (message.getMessage().contains("start")){
            char detectedLetter= message.getMessage().charAt(message.getMessage().length()-1);
            System.out.println("spotted for sale:" + detectedLetter);
            if (checkIfDesired(detectedLetter)){
                financier f = financier.getInstance();
                f.makeBid(message);
            };
        }

        //folgender block ist für eine erfolgreiche auktion
        else if (message.getMessage().contains("won")&&message.getMessage().contains(botID)){ //nachricht enthält won und die ID des Bots
            char detectedLetter= message.getMessage().charAt(message.getMessage().length()-1);
            String[] array =message.getMessage().split(" ");
            int numberOfWonAuction = Integer.parseInt(array[2]);
            int pricePaid = Integer.parseInt(array[4]);
            auctionRepository aH = auctionRepository.getInstance();
            aH.finishAuctionSuccess(numberOfWonAuction);
            financier f = financier.getInstance();
            f.withdraw(pricePaid);


        }
        else if (message.getMessage().contains("won")&&!message.getMessage().contains(botID)){ //nachricht enthält won und nicht die ID des Bots,dann prüfen, ob wir auf die auktion geboten haben, dann verschieben nach "failed auctions"
            char detectedLetter= message.getMessage().charAt(message.getMessage().length()-1);
            String[] array =message.getMessage().split(" ");
            int numberOfWonAuction = Integer.parseInt(array[2]);
            int pricePaid = Integer.parseInt(array[4]);
            auctionRepository aH = auctionRepository.getInstance();
            aH.finishAuctionFailure(numberOfWonAuction);
        }
    }

    private static boolean checkIfDesired(char detectedLetter) {
        saleAuthority saleBoy = saleAuthority.getInstance();
        if(saleBoy.checkIfDesired(detectedLetter)){
            return true;
        }
        else return false;
    }
}
