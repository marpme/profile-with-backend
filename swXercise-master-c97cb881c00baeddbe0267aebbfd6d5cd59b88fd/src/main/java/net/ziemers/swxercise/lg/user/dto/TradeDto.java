package net.ziemers.swxercise.lg.user.dto;

import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;

import javax.validation.constraints.NotNull;

/**
 * Das Data Transfer Object im Kontext der Benutzerverwaltung. Es wird unter anderem auch aus einem
 * JSON-Objekt des {@link net.ziemers.swxercise.ui.UserViewController}s gefüllt.
 */
public class TradeDto {

    private Trade trade = null;

    @NotNull
    private String title;    // aus dem Profile

    @NotNull
    private String description;    // aus dem Profile

    private User author;

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
