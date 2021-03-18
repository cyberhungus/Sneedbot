public class adminHandler {
    static void checkContent(MessageWrapper message){
        if (message.getMessage().contains("desire")){
            String[] array = message.getMessage().split(" ");
            saleAuthority saleBoy = saleAuthority.getInstance();
            saleBoy.fillDesiredArray(array[1]);
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(),"Set desired String to "+array[1]);
        }

        //folgender block ist für eine erfolgreiche auktion
        else if (message.getMessage().contains("balance")){
            financier f = financier.getInstance();
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(), String.format("Currently, I have %s credits", f.getBalance()));

        }
        else if (message.getMessage().contains("wanted")){
            saleAuthority saleBoy = saleAuthority.getInstance();
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(), String.format("Currently, I am looking for %s, but whats left is %s ",saleBoy.originalTerm(), saleBoy.arrayListToString()));
        }


    }
}
