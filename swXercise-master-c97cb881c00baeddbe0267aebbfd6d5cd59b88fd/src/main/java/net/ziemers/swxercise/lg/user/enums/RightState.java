package net.ziemers.swxercise.lg.user.enums;

/**
 * Stellt grundsätzliche Rechte zur Verfügung, die so mit hoher Wahrscheinlichkeit
 * in vielen Anwendungen verwendet werden möchten.
 */
public enum RightState {

    NOT_LOGGED_IN(Constants.NOT_LOGGED_IN),
    LOGGED_IN(Constants.LOGGED_IN),
    ADMIN(Constants.ADMIN),
    ;

    private String name;

    RightState(final String name) {
        if (!this.name().equals(name)) {
          throw new ExceptionInInitializerError("Constant string didn't match enum name");
        }
        this.name = name;
    }

    public static RightState getByName(final String name) {
        for (RightState value : values()) {
            // die Groß-/Kleinschreibung soll beim Vergleich keine Rolle spielen
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
	private void setName(String name) {
        this.name = name;
    }

    /*
     * Diese Klasse wird verwendet, damit wir innerhalb von Annotationen auf die Namen zugreifen können.
     */
    public static class Constants {
        public static final String NOT_LOGGED_IN = "NOT_LOGGED_IN";
        public static final String LOGGED_IN = "LOGGED_IN";
        public static final String ADMIN = "ADMIN";
    }

}
