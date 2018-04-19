package wizard_team.wizards_tale.screens;

class ChatMessage {
    String sender;
    String message;
    long timestamp;

    public ChatMessage(long timestamp, String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}
