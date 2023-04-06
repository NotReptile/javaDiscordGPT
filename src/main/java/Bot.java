import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

    private final String openaiApiKey;
    private final String discordBotToken;
    private final OpenAI openai;
    private final String channelId = "your channelId";

    public Bot(String openaiApiKey, String discordBotToken) {
        this.openaiApiKey = openaiApiKey;
        this.discordBotToken = discordBotToken;
        this.openai = new OpenAI(openaiApiKey);
    }

    public void start() throws LoginException {
        JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("GPT"))
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;
        Message.suppressContentIntentWarning();
        String message = event.getMessage().getContentRaw();
        if (event.getChannel().getId().equals(channelId)) {
            String response = openai.generateResponse(message);
            System.out.println("Message: " + message);
            System.out.println("Response: " + response);
            event.getChannel().sendMessage(response).queue();
        }
    }
    public static void main(String[] args) {
        String openaiApiKey = BotConfig.OPENAI_API_KEY;
        String discordBotToken = BotConfig.DISCORD_BOT_TOKEN;
        Bot bot = new Bot(openaiApiKey, discordBotToken);

        try {
            bot.start();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
