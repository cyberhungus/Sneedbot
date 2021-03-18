import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;


import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.User;
import discord4j.discordjson.json.gateway.MessageReactionAdd;

public class BotMain {
    private String token;
    GatewayDiscordClient client;
    String ownID;
    static BotMain instance;

    BotMain(String token){
        this.token=token;
        createClient();
        createMessageReceiver();
        createMessageUpdateListener();
        this.instance=this;
        client.onDisconnect().block();


    }

    public static BotMain getInstance(){
        if (instance!=null){
            return instance;
        }
        else return null;

    }


    private void createClient() { //erschafft den Client, loggt sich bei discord ein
        this.client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();
        informOfPresence();
    }

    private void informOfPresence() {
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    final User self = event.getSelf();
                    System.out.println(String.format(
                            "Logged in as %s#%s", self.getUsername(), self.getDiscriminator()
                    ));
                    ownID=self.getDiscriminator();
                });
    }

    private void createMessageReceiver() { //listener, der bei jeder eintreffenden nachricht eine funktion ausführt
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    String message = event.getMessage().getContent();
                    String user = event.getMessage().getUserData().username();
                    String time = event.getMessage().getTimestamp().toString();
                    String userID = event.getMessage().getUserData().discriminator();
                    Snowflake channelSnowflake = event.getMessage().getChannelId();
                    //System.out.println("Message from: " + user);
                    //System.out.println("Message Content: " + message);
                    //System.out.println("Message time: " + time);
                    //System.out.println("Message channel: " + channel);

                    MessageWrapper mw= new MessageWrapper(user,message,time,channelSnowflake);
                    System.out.println(mw.toString());


                    if (!userID.equals(ownID)){ //verhindert dass sich der Bot selbst antwortet
                       // System.out.println(userID+"---"+ownID);
                        userAnalyzer uA = userAnalyzer.getInstance();
                        uA.analyse(mw);
                    }
                });

    }
    private void createMessageUpdateListener() {
        client.getEventDispatcher().on(MessageUpdateEvent.class)
                .subscribe(event -> {
                    String self = event.getMessage().toString();

                    System.out.println("Message changed: " +self);

                });
    }
    public void messager(Snowflake channel, String reply){
        client.getChannelById(channel).subscribe(value -> value.getRestChannel().createMessage(reply).subscribe());
    }

}
