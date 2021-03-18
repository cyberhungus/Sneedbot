import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;



import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class Bot {
static  String token = "ODIxMzY0NTcwOTY2MzI3MzM3.YFCpXQ.v0hQsSgDl-Tugo36YAMQro8qO6o";
    public static void main(String[] args) {
        BotMain bot = new BotMain(token);

    }

}