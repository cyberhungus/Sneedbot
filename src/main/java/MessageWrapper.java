import discord4j.common.util.Snowflake;

public class MessageWrapper {  //soll informationen zu einer erhaltenen Nachrichten enthalten
    private String user;
   private String message;
   private String time;
   private Snowflake channel;


    MessageWrapper(String user, String message, String time, Snowflake channel){
        this.user=user;
        this.message=message;
        this.time=time;
        this.channel=channel;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", channelID='" + channel.asLong() + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public Snowflake getChannel() {
        return channel;
    }
}
