package net.ziemers.swxercise.lg.user.enums;

/**
 * Eine Enumeration aller jemals verwendeten Algorithmen zum Verhashen des Kennworts.
 */
public enum PasswordHashAlgorithm {

    SHA512("SHA-512"),
    ;

    private String algorithm;

    PasswordHashAlgorithm(final String algorithm) {
        setAlgorithm(algorithm);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    private void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

}
