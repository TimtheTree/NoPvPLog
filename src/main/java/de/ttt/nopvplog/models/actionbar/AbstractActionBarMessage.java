package de.ttt.nopvplog.models.actionbar;

public abstract class AbstractActionBarMessage implements ActionBarMessage{

    protected static final String PLACEHOLDER = "%timer%";

    protected final String message;

    private int currentTimer;

    protected AbstractActionBarMessage(String message, int currentTimer) {
        this.message = message;
        this.currentTimer = currentTimer;
    }

    @Override
    public void update() {

        this.renderMessage();
        this.countDown();

    }

    private void countDown() {
        currentTimer -= 1;
    }

    private String renderMessage() {

        return message.replaceAll(PLACEHOLDER, String.valueOf(this.currentTimer));

    }

}
