import discord4j.common.util.Snowflake;

import java.util.ArrayList;

public class userAnalyzer { //soll messagewrapper klassen auf ihren user untersuchen und dann an den korrekten contentAnalyser weiterschicken
    String auctionHouseName="Sneed-SEG"; //soll dazu dienen, Nachrichten vom Auktionshaus zu erkennen - entweder fest oder variabel
    String bigboss ="morromen"; //meine ID, zu testzwecken
    ArrayList<String> admins; //soll unsere IDS enthalten, und Nachrichten von uns gesondert behandeln
    ArrayList<String> trustedPartners; //soll einer Untergruppe von Nutzern bevorzugte behandlung erlauben
    ArrayList<String> bustedPartners; //soll einer Untergruppe von Nutzern keine aktion erlauben
    boolean registrationState=false;
    static userAnalyzer instance;

    userAnalyzer(){} //vielleicht einfach nur eine static methode?

    static userAnalyzer getInstance(){
        if (instance==null){
            instance=new userAnalyzer();
            return instance;
        }
        else return instance;
    }

    public void analyse(MessageWrapper message){
        if (message.getUser().equals(bigboss)){
                    adminHandler.checkContent(message);
        }
        else if (message.getUser().equals(auctionHouseName)){
            checkIfRegisteredWithAuction(message.getChannel());
            auctionHandler.checkContent(message);

        }
    }

    private void checkIfRegisteredWithAuction(Snowflake channel) {
        if (!registrationState){
            BotMain bot = BotMain.getInstance();
            bot.messager(channel,"!seg register");
            registrationState=true;
        }

    }


}
