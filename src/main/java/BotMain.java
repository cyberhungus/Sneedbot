import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;


import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.discordjson.json.gateway.MessageReactionAdd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BotMain {
    private String token;
    GatewayDiscordClient client;
    String ownID;
    static BotMain instance;
    private Map<String, List<Snowflake>> channels = new HashMap<>();
    BotMain(String token){
        this.token=token;
        createClient();
        //findChannels();
        //registerWithBot();
        createMessageReceiver();
        createMessageUpdateListener();
        this.instance=this;


        client.onDisconnect().block();


    }

    private void registerWithBot() {
        getInfoChannel().forEach(snowflake -> messager(snowflake,"Hello, Sneedbot has come online"));
        getInfoChannel().forEach(snowflake -> messager(snowflake,"!register"));
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
                    MessageWrapper mw= new MessageWrapper(user,message,time,channelSnowflake);
                    //System.out.println(mw.toString());


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

    public void findChannels(){ //von eike geklaut
        client.getGuilds().toStream().forEach(
                guild -> guild.getChannels().toStream().filter(channel -> channel.getType() == Channel.Type.GUILD_TEXT).filter(channel -> getChannels()
                        .containsKey(channel.getName())).forEach(channel -> getChannels()
                        .get(channel.getName())
                        .add(channel.getId())));
        System.out.println(channels.toString());
    }
    public Map<String, List<Snowflake>> getChannels() {
        return channels;
    }
    public Stream<Snowflake> getAuctionChannel() {
        return channels.get("auction").stream();
    }
    public Stream<Snowflake> getInfoChannel() {
        return channels.get("info").stream();
    }
    public Stream<Snowflake> getLogChannel() {
        return channels.get("log").stream();
    }
}
