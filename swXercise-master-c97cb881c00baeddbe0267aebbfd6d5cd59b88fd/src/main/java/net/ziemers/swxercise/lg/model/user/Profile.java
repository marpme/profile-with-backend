package net.ziemers.swxercise.lg.model.user;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;

import net.ziemers.swxercise.db.BaseEntity;
import net.ziemers.swxercise.lg.user.enums.PasswordHashAlgorithm;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Verwaltet die Anmeldedaten eines anmeldefähigen Benutzers. Das Kennwort wird niemals im Klartext
 * gespeichert, sondern stets nur in seiner verhashten Form.
 */
@Entity
public class Profile extends BaseEntity {

    @NotNull
    private String username;

    @NotNull
    private String passwordHash;

    @NotNull
    private PasswordHashAlgorithm hashAlgorithm = PasswordHashAlgorithm.SHA512;

    @NotNull
    private String salt;

    private String mailaddress;

    private Role role;

    /*
	 * *****************************************************************************************************************************
	 * Konstruktoren
	 * *****************************************************************************************************************************
	 */

    public Profile() {
        super();

        setSalt(this.generateSalt());
    }

    public Profile(final String username, final String password) {
        this();

        setUsername(username);
        setPassword(password);
    }

    /*
	 * *****************************************************************************************************************************
	 * Methoden
	 * *****************************************************************************************************************************
	 */

    private byte[] base64ToByte(final String str) {
        byte[] bytes;
        // Java 6 ships the javax.xml.bind.DatatypeConverter this class provides two static methods that support the same decoding &
        // encoding: parseBase64Binary() and printBase64Binary(). Use this and you will not need an extra library, like Apache
        // Commons Codec.
        // BASE64Decoder decoder = new BASE64Decoder();

        // try {
        // bytes = decoder.decodeBuffer(str);
        bytes = DatatypeConverter.parseBase64Binary(str);
        // } catch(IOException e) {
        // e.printStackTrace();
        // }
        return bytes;
    }
    private String byteToBase64(final byte[] bytes) {
        // Java 6 ships the javax.xml.bind.DatatypeConverter this class provides two static methods that support the same decoding &
        // encoding: parseBase64Binary() and printBase64Binary(). Use this and you will not need an extra library, like Apache
        // Commons Codec.
        // BASE64Encoder encoder = new BASE64Encoder();

        // String str = encoder.encode(bytes);
        String str = DatatypeConverter.printBase64Binary(bytes);
        return str;
    }

    private String cryptString(String string) {
        final int HASH_ITERATION_COUNT = 5;

        String hashedString = "";

        // vor dem Verschlüsseln den benutzerspezifischen Salt an das Kennwort anhängen
        string += this.getSalt();

        // Quelle: http://codeschnipsel.wordpress.com/2008/11/13/passwort-hash-in-java/
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(getHashAlgorithm().getAlgorithm());
            md.reset();
            md.update(base64ToByte(this.getSalt()));

            byte[] bytes = md.digest(string.getBytes("UTF-8"));
            for(int i = 0; i < HASH_ITERATION_COUNT; i++) {
                md.reset();
                bytes = md.digest(bytes);
            }
            hashedString = byteToBase64(bytes);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return hashedString;
    }

    private String generateSalt() {
        final int HASH_LENGTH = 20; // Ferguson & Schneier overcautiously recommend 32

        // Quelle: http://www.javamex.com/tutorials/cryptography/pbe_salt.shtml
        Random random = new SecureRandom();
        byte[] salt = new byte[HASH_LENGTH];

        random.nextBytes(salt);
        return byteToBase64(salt);
    }

    /*
	 * *****************************************************************************************************************************
	 * Getters und Setters
	 * *****************************************************************************************************************************
	 */

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    // da die Methode "private" ist, wird der Kennwort-Hashwert niemals per REST in einem JSON-Objekt übermittelt
    private String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setzt das im Profil hinterlegte Kennwort. Es wird darin sicher verschlüsselt abgelegt.
     *
     * @param password das gewünschte Kennwort
     */
    public void setPassword(String password) {
        // das Klartextkennwort wird niemals gespeichert!
        this.passwordHash = cryptString(password);
    }

    @SuppressWarnings("unused")
	private void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Vergleicht das übergebene Kennwort mit dem im Profil hinterlegten.
     *
     * @param password das Klartext-Kennwort, mit dem das im Profil hinterlegte verglichen werden soll
     * @return <code>true</code>, wenn die Kennwörter identisch sind.
     */
    public boolean isValidPassword(final String password) {
        final String passwordHash = cryptString(password);

        return getPasswordHash().equals(passwordHash);
    }

    @Enumerated(EnumType.STRING)
    private PasswordHashAlgorithm getHashAlgorithm() {
        return hashAlgorithm;
    }

    @SuppressWarnings("unused")
	private void setHashAlgorithm(PasswordHashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    // da die Methode "private" ist, wird der Kennwort-Saltwert niemals per REST in einem JSON-Objekt übermittelt
    private String getSalt() {
        return salt;
    }

    private void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public Profile withMailaddress(final String mailaddress) {
        setMailaddress(mailaddress);
        return this;
    }

    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
