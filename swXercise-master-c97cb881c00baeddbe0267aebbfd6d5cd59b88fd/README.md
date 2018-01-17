# Ein Java-Beispielprojekt für die Lehrveranstaltungen "Software Engineering 2" (SE2), "Softwarequalität und Test" (SwQT) sowie "Webentwicklung".

Achtung! Das Durcharbeiten dieses Tutorials ist nicht erforderlich, wenn Sie das Disk-Image für die virtuelle Maschine unter "http://www.ziemers.de/static/swXercise/" heruntergeladen haben. In dieser Maschine sind alle Komponenten bereits installiert.

Anmerkung: Dieses Tutorial lebt! Es wird ständig verbessert, um Problemlösungen noch genauer zu beschreiben. Bitte melden Sie auftretende Probleme deshalb.

## Projekt-Voraussetzungen

- Versionsverwaltungswerkzeug "Git"
- Installiertes "Java 8 JDK" (mit Dokumentation)
- Build-System "maven" (unter Windows optional)
- Ein relationales Datenbankverwaltungssystem mit JDBC-Anbindung ("MySQL" empfohlen, oder "MariaDB", "PostgreSQL", "Oracle Express" etc.)

Für die Weiterentwicklung am Projekt in der Veranstaltung SE2 (jedoch nicht für die Implementierung von JUnit-Tests in der Veranstaltung SwQT) sind ferner erforderlich:

- Eine Java-Entwicklungsumgebung ("Eclipse", "JetBrains IntelliJ IDEA", "NetBeans" o.ä.)
- JEE-Application Server "JBoss WildFly Version 8.2.1-final"
- Ein REST-Client (empfohlen "Advanced Rest Client Application" für den Chrome Browser)

## Git installieren

Auf dem Mac ist Git bereits vorhanden, sofern "Xcode" installiert ist.

Unter Linux installiert man Git folgendermaßen:

> sudo apt-get update

> sudo apt-get install git

Unter Windows muss der Git-Installer aus dem Internet heruntergeladen und installiert werden.

## Maven installieren

Unter Linux wird Maven einfach folgendermaßen installiert:

> sudo apt-get update

> sudo apt-get install maven

Für den Mac wird Maven von der Herstellerseite "https://maven.apache.org/download.cgi" heruntergeladen und die Archivdatei in einem beliebigen Verzeichnis ausgepackt (konventionsgemäß in "/Library/Maven").

Dann wird die Datei ".profile" im Home-Verzeichnis des Benutzer erweitert (oder diese erstellt, falls noch nicht vorhanden):

```
export M2_HOME="/Library/Maven/apache-maven-3.5.0"
export MYSQL_HOME="/usr/local/mysql"
export PATH="$PATH:$M2_HOME/bin:$MYSQL_HOME/bin"
```

Hinweis: es wird bei dieser Gelegenheit auch gleich der MySQL-Pfad angepasst.

Natürlich müssen die Verzeichnispfade von Maven und MySQL angepasst werden, wie es den tatsächlichen Installationsorten entspricht.

Unter Windows muss der Maven-Installer aus dem Internet heruntergeladen und installiert werden.

## Java-Projekt herunterladen

In einem beliebigen Verzeichnis ausführen:

> git -c http.sslVerify=false clone https://git.ziemers.de/edu/swXercise.git

Es wird hierbei ein neues Verzeichnis namens "swXercise" erstellt, in dem sich nun das Projekt befindet. Beim Klonen werden etwaige Zertifikatsfehler ignoriert (dies tritt trotz korrekten Zertifikats gelegentlich unter Windows auf).

## Projekt in die Entwicklungsumgebung integrieren

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

Danach das entstandene Projektverzeichnis "swXercise" in der Entwicklungsumgebung als "existierendes Maven-Projekt" importieren, indem man die im Wurzelverzeichnis des Projekts vorhandene Datei "pom.xml" auswählt:

Unter "IntelliJ IDEA" das Menü "File -> New... -> Project from Existing Sources..." aufrufen und den Dialogen folgen (wie geschrieben: die "pom.xml" muss ausgewählt werden!).

Bei "Eclipse" das Menü "File -> Import... -> Maven -> Existing Maven Projects" aufrufen und den Dialogen folgen (Datei "pom.xml" auswählen!).

## MySQL-Datenbanksystem installieren

### Windows und Mac OS

Unter Windows und auf dem Mac einfach den entsprechenden MySQL-Installer von den Oracle-Webseiten herunterladen und installieren.

### Unter Linux

> sudo apt-get update

> sudo apt-get install mysql-server

Danach (ohne Aktivierung des "Validating password plugin"s!):

> sudo mysql_secure_installation

Während der Installation wird ein temporäres Root-Kennwort generiert. Das muss man sich gut merken.

## MySQL konfigurieren

Unter Windows muss man ein beliebiges MySQL-Frontend installieren, beispielsweise "phpMyAdmin" o.ä. Damit müssen die beiden erforderlichen Datenbanken "swxercise" und "swxercise_test" erstellt werden.

Unter Linux und auf dem Mac kann stattdessen das MySQL-Frontend gestartet werden:

> mysql -u root -p

Es wird zunächst nach dem temporären Kennwort gefragt. Dieses kann/muss geändert werden, beispielsweise in 'root':

> ALTER USER 'root'@'localhost' IDENTIFIED BY ‘root‘;

Dann müssen die beiden projektspezifischen Datenbanken (für "Test" und "Produktion") erstellt und mit den nötigen Rechten versehen werden:

> CREATE DATABASE swxercise_test;

> GRANT ALL PRIVILEGES ON swxercise_test.* TO 'root'@'localhost' IDENTIFIED BY 'root';

(Hinweis: Das Folgende ist nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

> CREATE DATABASE swxercise;

> GRANT ALL PRIVILEGES ON swxercise.* TO 'root'@'localhost' IDENTIFIED BY 'root';

## MySQL-Datenbank im Projekt konfigurieren

Heißt die Testdatenbank bei Ihnen anders als "swxercise_test", haben Sie andere Zugangsdaten gewählt als "root"/"root", befindet sich die Datenbank nicht auf Ihrem lokalen Rechner oder lauscht sie auf einem anderen Port als 3306, dann müssen in der Datei "pom.xml" (im Projekt-Hauptverzeichnis) die Properties entsprechend angepasst werden.

## MySQL-Datenbank im WildFly einbinden

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

Zunächst muss der JBoss WildFly Application Server installiert werden. Dies ist betriebssystemspezifisch.

Unter Linux und auf dem Mac einfach das Tar- oder Zip-Archiv mit dem WildFly-Server von den JBoss-Webseiten herunterladen und in ein beliebiges Verzeichnis kopieren. Auf dem Mac wird hierzu standardmäßig "/Library/JBoss" verwendet. Darin die Archivdatei entpacken. Fertig.

Unter Windows dem WildFly-Installer von den JBoss-Webseiten herunterladen und installieren.

### Datei "module.xml" im Verzeichnis "$JBOSS_HOME/modules/system/layers/base/com/mysql/driver/main" erstellen

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

Vermutlich existiert im Verzeichnis "com" schon das Unterverzeichnis "mysql" nicht. Also muss dieses Verzeichnis (und die entsprechenden Unterverzeichnisse ebenfalls) erstellt werden.

Dann **module.xml** erstellen mit folgendem Inhalt:

```
<module xmlns="urn:jboss:module:1.3" name="com.mysql.driver">
  <resources>
    <resource-root path="mysql-connector-java-5.1.41-bin.jar" />
  </resources>
  <dependencies>
    <module name="javax.api"/>
    <module name="javax.transaction.api"/>
  </dependencies>
</module>
```

Und die jar-datei mit dem angegebenen MySQL-Treiber in dieses Verzeichnis hineinkopieren (im Beispiel in der Version "5.1.41-bin"). Er muss von den Oracle-Internetseiten heruntergeladen werden.

### In der Datei "standalone.xml" im Verzeichnis "$JBOSS_HOME/standalone/configuration" folgenden Inhalt unterhalb der WildFly-Beispieldatenbank "com.h2database.h2" ergänzen:

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

Aufgepasst: Der WildFly-Server darf während der Bearbeitung der XML-Datei nicht laufen, da er Ihre Änderungen ansonsten beim späteren Beenden wieder überschreiben würde!!!

```
<drivers>
  <!-- (hier befindet sich der bereits vorhandene Eintrag für den "H2"-Treiber; unserer kommt direkt darunter) -->

  <driver name="mysql" module="com.mysql.driver">
    <driver-class>com.mysql.jdbc.Driver</driver-class>
  </driver>
</drivers>
```

## MySQL-Datasource im WildFly definieren

### In der Datei "standalone.xml" im "Verzeichnis $JBOSS_HOME/standalone/configuration" folgenden Inhalt innerhalb der Datasources ergänzen:

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

Aufgepasst: Der WildFly-Server darf während der Bearbeitung der XML-Datei nicht laufen, da er Ihre Änderungen ansonsten beim späteren Beenden wieder überschreiben würde!!!

```
<datasources>
  <!-- (hier befindet sich die bereits vorhandene "H2"-Datasource; unsere kommt direkt darunter) -->

  <datasource jndi-name="java:jboss/datasources/swXerciseDS" pool-name="swXerciseDS" enabled="true" use-java-context="true" jta="true">
    <connection-url>jdbc:mysql://localhost:3306/swxercise?useSSL=false</connection-url>
    <driver>mysql</driver>
    <!-- der Default-Eintrag "TRANSACTION_REPEATABLE_READ" kann zu Camunda-Deadlocks kommen (ThZ, 24.04.2017) -->
    <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
    <pool>
      <min-pool-size>10</min-pool-size>
      <max-pool-size>100</max-pool-size>
      <prefill>true</prefill>
    </pool>
    <security>
      <user-name>root</user-name>
      <password>root</password>
    </security>
    <statement>
      <prepared-statement-cache-size>32</prepared-statement-cache-size>
      <share-prepared-statements>true</share-prepared-statements>
    </statement>
  </datasource>
</datasources>
```

Passen Sie die Angaben "connection-url" (Host, Port und Datenbankname), "user-name" und "password" entsprechend der Konfiguration Ihrer MySQL-Datenbank an.

## JBoss WildFly in Eclipse einbinden

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

### JBoss Tools-Plugin installieren (nur für Eclipse)

- Auswählen "Help -> Eclipse Marketplace..."
- Find: "JBoss Tools"
- die gefundenen "JBoss Tools" installieren (zum Zeitpunkt des Schreibens dieser Zeilen ist "JBoss Tools 4.4.3 Final" aktuell)
- Auswählen "JBoss AS, Wildfly & EAP Server Tools" mit allen Unter-Plugins, die dranhängen

Quelle: https://tools.jboss.org/downloads/jbosstools/neon/4.4.3.Final.html

### JBoss WildFly Server in Eclipse einbinden (nur für Eclipse)

- Auswählen "File -> New -> Other..."
- Auswählen "Server -> Server" (Next >)
- WildFly 8.x
  - Server's host name: localhost
  - Server name: WildFly 8.x beliebig wählen (Next >)
- "Create a new Server Adapter"
  - The server is: Local
  - Controlled by: Filesystem and shell Operations
  - Create new runtime (next page) (Next >)
- "New Server"
  - Name WildFly 8.x Runtime
  - Home Directory /home/<user>/wildfly-8.2.1.Final/ (oder das Verzeichnis, das Sie gewählt haben)
  - Execution Environment: JavaSE-1.8
  - Server base directory: standalone
  - Configuration file: standalone.xml (Next >)
  - "Add and Remove", das gewünschte Maven-Artefakt "swXercise" auswählen (Finish)

## JBoss WildFly in IntelliJ IDEA einbinden

(Hinweis: Nicht unbedingt für die Implementierung von JUnit-Tests in der Veranstaltung SwQT erforderlich)

### JBoss WildFly-Server initial bekanntmachen (nur für IntelliJ IDEA)

- IDE-Menü "Run -> Edit Configurations..."
  - "Defaults -> JBoss Server -> Local" und Reiter "Server"
    - "Configure..." und einen neuen JBoss Server mittels seines JBoss Home-Verzeichnisses angeben

### Run Configuration erstellen (nur für IntelliJ IDEA)

- Wiederum IDE-Menü "Run -> Edit Configurations..."
  - Plus-Symbol, um eine neue WildFly-Run Configuration zu definieren
  - "JBoss Server -> Local" auswählen
    - Reiter "Server" und den zuvor bekanntgemachten JBoss-Server wählen
    - Reiter "Deployment"
      - Selected artifacts will be deployed at server startup -> das gewünschte Maven-Artefakt "swXercise" auswählen, und zwar das mit "exploded" markierte; deployt als ausgepackte Ordnerstruktur und nicht als "WAR-Datei" (vorteilhaft beim Hot-Deployment)

## Projekt verwenden

In der Entwicklungsumgebung:

Beim Deployen des Projekts auf den Application Server wird durch einen Data Bootstrapping-Mechanismus automatisch der Benutzer "admin" mit dem Kennwort "admin" erstellt und diesem die Rolle "Adminrolle" zugewiesen. Mit ihm kann man sich anmelden und erste Übungen vornehmen. Hierzu verwendet man den REST-Client "Advanced Rest Client Application" für den Chrome Browser.

Nach der Implementierung von JUnit-Tests auf der Kommandozeile, und zwar in demjenigen Verzeichnis des Projekts, in dem sich die Datei "pom.xml" befindet (das ist wichtig!):

> mvn clean install

Das Projekt wird gebaut und die Tests werden ausgeführt. Möchte man die Tests gelegentlich mal nicht ausführen, macht man:

> mvn clean install -DskipTests