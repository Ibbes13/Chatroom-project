# Java-baserat Chattprogram

Detta projekt är ett klient/server-baserat chattprogram utvecklat i Java. Applikationen möjliggör realtidskommunikation mellan flera användare genom att använda nätverksprotokollet Multicast. Projektet fungerar som en demonstration av grundläggande nätverksprogrammering och trådhantering i Java.

## Funktioner

* **Realtidsmeddelanden:** Användare kan skicka och ta emot meddelanden i realtid.
* **Fleranvändarchatt:** Stöd för flera användare som ansluter till samma chattgrupp.
* **Multicast-kommunikation:** Använder Multicast-sockets för effektiv distribution av meddelanden till alla klienter i nätverket.
* **Användarstatus:** Klienterna meddelar när användare ansluter eller lämnar chatten.

## Teknologier

* **Java:** Huvudsakligt programmeringsspråk.
* **`java.net.MulticastSocket`:** Används för gruppkommunikation över nätverket.
* **`java.io`:** Hanterar dataöverföring.
* **Multitrådning:** Använder trådar för att separera nätverksmottagning från användarinput, vilket säkerställer en responsiv användarupplevelse.

## Hur man kör projektet

1. **Klona repot:**
   `git clone https://github.com/Ibbes13/Chatroom-project.git`

2. **Kompilera koden:**
   `javac ChatClient.java` (eller din huvudklientfil)

3. **Kör applikationen:**
   `java ChatClient`

Projektet är utformat för att demonstrera grundläggande nätverksprinciper och kan enkelt utökas med ytterligare funktioner.

---
