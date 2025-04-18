# Aplikacja sklep z kluczami licencyjnymi na oprogramowanie "LicenKey"

## 1. Opis Aplikacji
    Aplikacja LicenKey jest sklepem z kluczami licencyjnymi,
    w którym zalogowani użytkownicy mogą dokonywać zakupów kluczy na oprogramowanie od różnych producentów. 
    Klucze są generowane przez zewnętrzne API producentów oprogramowania,
    zatem gdy użytkownik kupuje klucz, aplikacja odbiera klucz od zewnętrznego API.

    Aplikacja działa w architekturze bezserwerowej(Serverless)
    z komunikacją pomiędzy komponentami sterowaną zdarzeniami (Event Driven)
    z użyciem zdarzeń chmurowych(CloudEvent). Część zadań głównego komponentu
    zostaje oddelegowana do mniejszych, skalowalnych programów zwanych funkcjami chmurowymi(Cloud Function),
    które w momencie czasowego braku zadań mogą zostać zeskalowane do 0, czyli żaden program tego typu
    nie działa. W momencie wzrostu obciążenia zostaje zwiększona liczba instancji danej funkcji.

    Aplikacja działa na klastrze Kubernetes.
    Klaster zostaje utworzony na maszynach wirtualnych virtualbox z użyciem narzędzia do automatyzacji Vagrant.
    Konfiguracja maszyn i samego klastra została zautomatyzowana z użyciem narzędzia Ansible.

    Do komunikacji wykorzystane są zdarzenia chmurowe, tworzone i zarządzane przez narzędzie KNative,
    z wykorzystaniem brokera wiadomości Apache Kafka jako magazyn na wiadomości i źródło zdarzeń chmurowych.
    
## 2. Baza Danych

### 2.1. Opis
    Wykorzystana została pojedyncza baza danych PostgreSQL:
    - nazwa: licen_key_db
    - użytkownik: licen_key_user

### 2.2. Tabele
    1. users - przechowuje dane o użytkownikach, informacje autoryzacyjne i uwierzytelniające
    2. licences - przechowuje informacje o dostępnych w sklepie licencjach
    3. keys - przechowuje klucze zakupione przez użytkowników - tabela złączeniowa pomiędzy licencjami i użytkownikami
    4. publishers - przechowuje informacje o wydawcy licencji
    5. services - przechowuje informacje o serwisie generującym klucze 
    6. categories - kategorie licencji
    7. licence_types - typy licencji np. roczna, dożywotnia itp.
    8. platforms - platforma na której można zrealizować/aktywować klucz.
    9. shopping_carts - zawartość koszyków zakupowych użytkowników
    10. orders - zamówienia złożone przez użytkowników
    11. confirmed_carts - zatwierdzone koszyki czekające na przetworzenie na zamówienie - pozwalają użytkownikom kontynuowanie 
        zakupów bez wpływu na nie przetworzone zamówienie

### 2.3. Diagram
    
![database diagram](database/db_diagram.png)

## 3. Technologie
    1. Java
    2. Spring Boot
    3. Gradle
    4. PostgreSQL
    5. React
    6. JavaScript
    7. HTML
    8. CSS
    9. Apache Kafka
    10. Docker
    11. ContainerD
    12. Kubernetes
    13. Strimzi
    14. KNative Eventing
    15. Knative Serving
    16. Vagrant
    17. Ansible
    18. VirtualBox
    19. CloudEvents