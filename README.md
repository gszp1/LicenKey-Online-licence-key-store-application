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