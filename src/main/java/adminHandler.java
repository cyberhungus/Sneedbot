public class adminHandler {

    static String activator = "!sneed";
    static void checkContent(MessageWrapper message){
        if (message.getMessage().contains(activator)&&message.getMessage().contains("desire")){
            String[] array = message.getMessage().split(" ");
            saleAuthority saleBoy = saleAuthority.getInstance();
            saleBoy.fillDesiredArray(array[2]);
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(),"Set desired String to "+saleBoy.originalTerm());
        }

        //folgender block ist für eine erfolgreiche auktion
        else if (message.getMessage().contains(activator)&&message.getMessage().contains("balance")){
            financier f = financier.getInstance();
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(), String.format("Currently, I have %s credits", f.getBalance()));

        }
        else if (message.getMessage().contains(activator)&&message.getMessage().contains("wanted")){
            saleAuthority saleBoy = saleAuthority.getInstance();
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(), String.format("Currently, I am looking for %s, but whats left is %s ",saleBoy.originalTerm(), saleBoy.arrayListToString()));
        }
        else if (message.getMessage().contains(activator)&&message.getMessage().contains("message")){
            String[] array = message.getMessage().split(" ");

            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(), arrayToMessage(array));
        }
        else if (message.getMessage().contains(activator)&&message.getMessage().contains("help")){
            BotMain bot = BotMain.getInstance();
            bot.messager(message.getChannel(), "Every command must be initiated with "+activator);
            bot.messager(message.getChannel(), "desire [term]-makes the bot hunt for the letters in [term]");
            bot.messager(message.getChannel(), "message [term]-makes the bot send a message with content [term]");
            bot.messager(message.getChannel(), "balance-makes the bot output the current balance");
            bot.messager(message.getChannel(), "wanted-makes the bot show the desired term and what letters have been bought already");
        }


    }

    private static String arrayToMessage(String[] array) {
        String result="";
        for (int i=2;i<array.length;i++){
            result+=array[i]+" ";
        }
        return result;
    }
}
