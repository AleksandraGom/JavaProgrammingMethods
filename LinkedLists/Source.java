// Aleksandra Gomolka - 8
import java.util.Scanner;

/*Celem zadania bylo przeprowadzenie symulacji zabawy opisanej w tresci zadania uzywajac efektywnych struktur takich jak:
jednostronnej listy dwukierunkowej, cyklicznej dla reprezentacji listy wagonow danego pociagu oraz listy pojedynczej dla reprezentacji
listy pociagow.*/


public class Source { // Implementacja glownej klasy.
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;

    public static void main(String[] args) {

        int numberOfsets; // zmienna okreslajaca liczbe zestawow danych, ktorych opisy wystepuja kolejno po sobie
        int numberOfcommands; // zmienna okreslajaca liczbe polecen
        String commandName = ""; //zmienna okreslajaca nazwe polecenia

        numberOfsets = scanner.nextInt(); // wczytywanie liczby zestawow danych

        for (int k = 1; k <= numberOfsets; k++) {
            numberOfcommands = scanner.nextInt(); // wczytywanie liczby polecen
            LinkList list = new LinkList(); // inicjalizacja nowego obiektu/listy
            for (int j = 1; j <= numberOfcommands; j++) {
                commandName = scanner.next(); // wczytywanie nazwy polecenia
                switch (commandName) { // W zaleznosci od wczytanego polecenia nastepuje wywolanie odpowiedniej metody z odpowiednia dla nadej metody liczba argumentow
                    case "New":
                        list.New(scanner.next(), scanner.next());
                        continue;
                    case "InsertFirst":
                        list.InsertFirst(scanner.next(), scanner.next());
                        continue;
                    case "InsertLast":
                        list.InsertLast(scanner.next(), scanner.next());
                        continue;
                    case "Display":
                        list.Display(scanner.next());
                        continue;
                    case "Trains":
                        list.Trains();
                        continue;
                    case "Reverse":
                        list.Reverse(scanner.next());
                        continue;
                    case "Union":
                        list.Union(scanner.next(), scanner.next());
                        continue;
                    case "DelFirst":
                        list.DelFirst(scanner.next(), scanner.next());
                        continue;
                    case "DelLast":
                        list.DelLast(scanner.next(), scanner.next());
                }
            }
        }
    }
}

class LinkList { // Definicja klasy listy wiazanej

    public LinkTrain trains; // inicjalizacja referencji wskazujacej na pierwszy element listy dla reprezentacji listy pociagow

    public LinkList() { // konstruktor listy pustej
        trains = null; // lista nie ma jeszcze elementow, inicjalizacja zmiennej trains w konstruktorze
    }

    /*Inicjalizacja metody, ktora tworzy nowy pociag o nazwie "train" z jednym wagonem o nazwie "wagon" i wstawia go do listy pociagow */
    public void New(String train, String wagon) {
        LinkTrain ref = trains; // zmienna przchowujaca referencje do pierwszego elementu listy, pierwszego pociagu

        while (ref != null) { // przechodzimy po wszystkich elementach
            if (ref.info.equals(train)) { // jezeli znajdziemy pociag o szukanej nazwie, nie mozemy ponownie utworzyc pociagu o tej samej nazwie
                System.out.println("Train " + train + " already exists");
                return;
            }
            ref = ref.next; // przejscie do nastepnego elementu
        }

        LinkTrain newTrain = new LinkTrain(train); // jezeli nie znalezlismy pociagu o zadanej nazwie nastepuje utworzenie nowego obiektu, nowego pociagu
        // wstawiamy nowy pociag na poczatek listy
        newTrain.next = trains; // elementem wystepujacym po nowo wstawionym pociagu jest element na ktory obecnie wskazuje "trains"
        trains = newTrain; // nowy pociag bedzie elementem na ktory teraz bedzie wskazywal "trains" - referencja wskazujaca na pierwszy element listy pociagow

        // utworznie nowego obiektu, nowego wagonu oraz wstawienie wagonu jako pierwszy element nowego pociagu
        newTrain.first = new LinkWagon(wagon); // teraz "first" wskazuje na nowy wagon
        // z uwagi na liste cykliczna, nowy element wskazuje sam na siebie
        newTrain.first.next = newTrain.first; // nastepnym elementem nowego wagonu jest nowy wagon
        newTrain.first.prev = newTrain.first; // poprzednim elementem nowego wagonu jest nowy wagon
    }
    /*Implementacja metody wypisujacej liste wagonow pociagu o nazwie "train" poczawszy od pierwszego wagonu.*/
    public void Display(String train) {
        LinkTrain ref = trains; // zmienna przchowujaca referencje do pierwszego elementu listy, pierwszego pociagu

        while (ref != null && !ref.info.equals(train)) { // przechodzimy po wszystkich elementach listy do czasu znalezienia szukanego pociagu lub zakonczenia listy
            ref = ref.next; // przejscie do nastepnego elementu
        }
        if (ref == null) { // nie znalezlismy szukanego pociagu
            System.out.println("Train " + train + " does not exist");
            return;
        }
        ref.display(); // znaleziono szukany pociag, nastepuje wypisywanie wszystkich wagonow zadanego pociagu
    }

    /*Implementacja metody, ktora wypisuje aktualna liste pociagow.*/
    public void Trains() {
        LinkTrain ref = trains; // zmienna przechowujaca referencje do pierwszego elementu listy, pierwszego pociagu

        if(ref == null) { // lista pociagow jest pusta
            System.out.print("Trains: ");
        }
        else { // lista pociagow nie jest pusta, nastepuje wypisywanie wszystkich pociagow do czasu zakonczenia sie listy pociagow
            System.out.print("Trains:");
            while (ref != null) {
                System.out.print(" " + ref.info);
                ref = ref.next; // przejscie do nastepnego elementu
            }
        }
        System.out.println();
    }

    /*Implementacja metody, ktora wstawia wagon o nazwie "wagon" na poczatek pociagu o nazwie "train".*/
    public void InsertFirst(String train, String wagon) {
        LinkTrain ref = trains; // zmienna przchowujaca referencje do pierwszego elementu listy, pierwszego pociagu
        LinkWagon newWagon = new LinkWagon(wagon); // utworzenie nowego obiektu, nowego wagonu

        while (ref != null && !ref.info.equals(train)) { // przechodzimy po wszystkich elementach listy do czasu znalezienia szukanego pociagu lub zakonczenia listy
            ref = ref.next; // przejscie do nastepnego elementu
        }
        if (ref == null) { // nie znalezlismy szukanego pociagu
            System.out.println("Train " + train + " does not exist");
            return;
        }
        // first.prev - oznacza ostatni element
        newWagon.prev = ref.first.prev; // elemenetem poprzednim nowego wagonu jest obecnie ostatni element/ostatni wagon
        ref.first.prev.next = newWagon; // elementem nastepnym obecnie ostatniego wagonu jest nowy wagon
        newWagon.next = ref.first; // elementem nastepnym nowego wagonu jest obecnie pierwszy wagon
        ref.first.prev = newWagon; // elementem poprzednim obecnie pierwszego elementu jest nowy wagon
        ref.first = newWagon; // "first" wskazuje teraz na nowy wagon, nowy wagon stal sie pierwszym wagonem danego pociagu
    }

    /*Implementacja metody, ktora wstawia wagon o nazwie "wagon" na koniec pociagu o nazwie "train".*/
    public void InsertLast(String train, String wagon) {
        LinkTrain ref = trains; // zmienna przchowujaca referencje do pierwszego elementu listy, pierwszego pociagu
        LinkWagon newWagon = new LinkWagon(wagon); // utworzenie nowego obiektu, nowego wagonu

        while (ref != null && !ref.info.equals(train)) { // przechodzimy po wszystkich elementach listy do czasu znalezienia szukanego pociagu lub zakonczenia listy
            ref = ref.next; // przejscie do nastepnego elementu
        }
        if (ref == null) { // nie znalezlismy szukanego pociagu
            System.out.println("Train " + train + " does not exist");
            return;
        }
        // first.prev - oznacza ostatni element
        newWagon.prev = ref.first.prev; // elemenetem poprzednim nowego wagonu jest obecnie ostatni element/ostatni wagon
        ref.first.prev.next = newWagon; // elementem nastepnym obecnie ostatniego wagonu jest nowy wagon
        newWagon.next = ref.first; // elementem nastepnym nowego wagonu jest obecnie pierwszy wagon
        ref.first.prev = newWagon; // elementem poprzednim obecnie pierwszego elementu jest nowy wagon
    }

    /*Implementacja metody, ktora odwraca kolejnosc wagonow w pociagu o nazwie "train".*/
    public void Reverse(String train) { // zmienna przchowujaca referencje do pierwszego elementu listy, pierwszego pociagu
        LinkTrain ref = trains;
        while (ref != null && !ref.info.equals(train)) { // przechodzimy po wszystkich elementach listy do czasu znalezienia szukanego pociagu lub zakonczenia listy
            ref = ref.next; // przejscie do nastepnego elementu
        }
        if (ref == null) { // nie znalezlismy szukanego pociagu
            System.out.println("Train " + train + " does not exist");
            return;
        }
        // znalezlismy szukany pociag, dokonujemy zamiany (odwrocenia) pierwszego i ostatniego wagonu
        LinkWagon First = ref.first; // zmienna przechowujaca referencje do pierwszego wagonu
        LinkWagon Last = ref.first.prev; // zmienna przechowujaca referencje do ostatniego wagonu

        LinkWagon tmpFirst = First.next; // do zmiennej tymczasowej przypisuje element nastepny pierwszego wagonu
        First.next = First.prev; // elementem nastepnym pierwszego wagonu jest obecny element poprzedni pierwszego wagonu
        First.prev = tmpFirst; // elementem poprzednim pierwszego wagonu jest zmienna tymczasowa (element nastepny pierwszego wagonu)

        LinkWagon tmpLast = Last.prev; // do zmiennej tymczasowej przypisuje element poprzedni ostatniego wagonu
        Last.prev = Last.next; // elementem poprzednim ostatniego wagonu jest obecny element nastepny ostatniego wagonu
        Last.next = tmpLast; // elementem nastepnym ostatniego wagonu jest zmienna tymczasowa (element poprzedni ostatniego wagonu)

        ref.first = Last; // "first" wskazuje teraz na ostatni element, ostatni element jest teraz pierwszym elementem
    }

    /*Implementacja metody, ktora dolacza pociag o nazwie "secondTrain" na koniec pociagu o nazwie "firstTrain" i usuwa pociag o nazwie
     "secondTrain" z listy pociagow.*/
    public void Union(String firstTrain, String secondTrain) {
        LinkTrain[] ref; // zmienna tablicowa przechowujaca referencje do szukanych pociagow
        ref = checkTrain(firstTrain, secondTrain);

        if (ref[0] == null) { // nie znalezlismy pociagu o nazwie "firstTrain"
            System.out.println("Train " + firstTrain + " does not exist");
            return;
        }
        if (ref[1] == null) { // nie znalezlismy pociagu o nazwie "secondTrain"
            System.out.println("Train " + firstTrain + " does not exist");
            return;
        }
        // znalezlismy szukane pociagi, nastepuje dolaczenie pociagu o nazwie "secondTrain" na koniec pociagu o nazwie "firstTrain"
        LinkWagon tmp = ref[0].first.prev; // zmienna tymczasowa przechowujaca referencje do ostatniego wagonu pociagu "firstTrain"

        ref[0].first.prev.next = ref[1].first; // elementem nastepnym obecnie ostatniego wagonu pociagu "firstTrain" jest pierwszy wagon pociagu "secondTrain"
        ref[1].first.prev.next = ref[0].first; // elementem nastepnym ostatniego wagonu pociagu "secondTrain" jest pierwszy wagon pociagu "firstTrain"
        ref[0].first.prev = ref[1].first.prev; // elementem poprzednim pierwszego wagonu pociagu "firstTrain" jest ostatni wagon pociagu "secondTrain"
        ref[1].first.prev = tmp; // elementem poprzednim pierwszego wagonu pociagu "secondTrain" jest zmienna tymczasowa (ostatni wagon pociagu "firstTrain")

        delTrain(secondTrain); // usuwanie pociagu o nazwie "secondTrain"
    }

    /*Implementacja metody, ktora usuwa pierwszy wagon z pociagu o nazwie "firstTrain" i tworzy z niego nowy pociag o nazwie "secondTrain".
     Jezeli to byl jedyny wagon w pociagu "firstTrain" to pociag "firstTrain" jest usuwany z listy pociagow*/
    public void DelFirst(String firstTrain, String secondTrain) {
        LinkTrain[] ref; // zmienna tablicowa przechowujaca referencje do szukanych pociagow
        ref = checkTrain(firstTrain, secondTrain);

        if (ref[0] == null) { // nie znalezlismy pociagu o nazwie "firstTrain"
            System.out.println("Train " + firstTrain + " does not exist");
            return;
        }
        if (ref[1] != null) { // pociag o nazwie "secondTrain" juz istnieje
            System.out.println("Train " + secondTrain + " already exists");
            return;
        }

        New(secondTrain, ref[0].first.info); // tworzenie nowego pociagu z wagonem przeznaczonym do usuniecia

        if (ref[0].first.next != ref[0].first) {  // jezeli usuwany wagon nie jest jedynym wagonem pociagu "firstTrain"

            if (ref[0].first.next.prev != ref[0].first) { // jezeli pociag jest odwrocony

                // dla W1 W2 W3 W4
                LinkWagon First = ref[0].first.next; // W3
                First.next = ref[0].first.prev.prev; //W3 next -> W2
                First.prev = ref[0].first.prev; // W3 prev -> W1
                ref[0].first.prev.next = First; // W1 next -> W3
                // W1 prev -> W2
                First.next.prev = First; // W2 prev -> W3
                First.next.next = ref[0].first.prev ;// W2 next -> W1
                ref[0].first = First;
            }
            else { // jezeli pociag nie jest odwrocony
                ref[0].first.next.prev = ref[0].first.prev; // elementem poprzednim obecnego nastepnego elementu pierwszego wagonu pociagu "firstTrain" jest ostatni wagon tego pociagu
                ref[0].first.prev.next = ref[0].first.next; // elementem nastepnym ostatniego wagonu pociagu "firstTrain" jest obecny element nastepny pierwszego wagonu tego pociagu
                ref[0].first = ref[0].first.next; // "first" wskazuje teraz na obecny element nastepny pociagu "firstTrain"
            }
        }
        else { // jezeli usuwany wagon byl jedynym wagonem pociagu "firstTrain", pociag jest usuwany z listy pociagow
            delTrain(firstTrain);
        }
    }

    /*Implementacja metody, ktora usuwa ostatni wagon z pociagu o nazwie "firstTrain" i tworzy z niego nowy pociag o nazwie "secondTrain"
     Jezeli to byl jedyny wagon w pociagu "firstTrain" to pociag "firstTrain" jest usuwany z listy pociagow*/
    public void DelLast(String firstTrain, String secondTrain) {
        LinkTrain[] ref; // zmienna tablicowa przechowujaca referencje do szukanych pociagow
        ref = checkTrain(firstTrain, secondTrain);

        if (ref[0] == null) { // nie znalezlismy pociagu o nazwie "firstTrain"
            System.out.println("Train " + firstTrain + " does not exist");
            return;
        }
        if (ref[1] != null) { // pociag o nazwie "secondTrain" juz istnieje
            System.out.println("Train " + secondTrain + " already exists");
            return;
        }

        New(secondTrain, ref[0].first.prev.info); // tworzenie nowego pociagu z wagonem przeznaczonym do usuniecia

        if (ref[0].first.next != ref[0].first) { // jezeli usuwany wagon nie jest jedynym wagonem pociagu "firstTrain"

            if (ref[0].first.next.prev != ref[0].first) { // jezeli pociag jest odwrocony

                // dla W1 W2 W3 W4
                LinkWagon Last = ref[0].first.next.prev; //W2
                Last.next.next = Last; //W3 next -> W2
                ref[0].first.prev = Last; // W4 prev -> W2
                Last.next = ref[0].first; // W2 next -> W4
                Last.prev = ref[0].first.next; // W2 prev -> W3
                ref[0].first.next.prev = ref[0].first; // W3 prev -> W4

            } else { // jezeli pociag nie jest odwrocony
                LinkWagon Last = ref[0].first.prev; // zmienna tymczasowa przechowujaca referencje do ostatniego wagonu pociagu "firstTrain"
                ref[0].first.prev = Last.prev; // elementem poprzednim obecnie pierwszego wagonu pociagu "firstTrain" jest element poprzedni ostatniego wagonu tego pociagu
                ref[0].first.prev.next = ref[0].first; // elementem nastepnym ostatniego wagonu pociagu "firstTrain" jest pierwszy wagon tego pociagu
            }
        }
        else { // jezeli usuwany wagon byl jedynym wagonem pociagu "firstTrain", pociag jest usuwany z listy pociagow
            delTrain(firstTrain);
        }
    }

    /*Implementacja metody, ktora usuwa szukany pociag.*/
    public void delTrain(String train) {
        LinkTrain ref = trains; // zmienna przechowujaca referencje do pierwszego elementu listy, pierwszego pociagu

        LinkTrain prev = null; // zmienna przechowujaca referencje do elementu poprzedniego szukanego pociagu
        while (ref.next != null && !ref.info.equals(train)) { // przechodzimy po wszystkich elementach listy do czasu znalezienia szukanego pociagu lub zakonczenia listy
            prev = ref; // aktualizacja poprzedniego elementu
            ref = ref.next; // przejscie do nastepnego elementu
        }

        if (ref != null) { // znalezniono element do usuniecia
            if (prev == null) // usuwamy pierwszy element
                trains = ref.next; // "trains" wskazuje na obecny element nastepny szukanego pociagu, ktory bedzie teraz pierwszym pociagiem na liscie
            else if (ref.next == null) // usuwamy ostatni element
                prev.next = null; // elementem nastepnym elementu poprzedzajcego szukany pociag jest null, ostatni element bedzie wskazywal na null
            else // usuwamy element ze srodka
                prev.next = ref.next; // elementem nastepnym elementu poprzedzajcego szukany pociag jest obecny element nastepny szukanego pociagu
        }
    }

    /*Implementacja metody, ktora wyszukuje szukane pociagi.*/
    public LinkTrain[] checkTrain(String firstTrain, String secondTrain) {
        LinkTrain ref = trains; // zmienna przechowujaca referencje do pierwszego elementu listy, pierwszego pociagu
        LinkTrain[] newTrain = new LinkTrain[]{null, null}; // zmienna tablicowa przechowujaca referencje do szukanych pociagow

        while (!(newTrain[0] != null && newTrain[1] != null) && ref != null) {
            if (ref.info.equals(firstTrain)) { // znalezlismy pociag "firstTrain"

                newTrain[0] = ref; // do tablicy przypisujemy referencje do pociagu "firstTrain"
            }
            if (ref.info.equals(secondTrain)) { // znalezlismy pociag "secondTrain"

                newTrain[1] = ref; // do tablicy przypisujemy referencje do pociagu "secondTrain"
            }
            ref = ref.next; // przejscie do nastepnego elementu
        }
        return newTrain; // metoda zwraca tablice z refernecjami do szukanych pociagow, jezeli nie znaleziono, ktoregos z pociagow w danym miejscu jest null
    }
}

class LinkTrain { //Definicja klasy LinkTrain w reprezentacji listy pojedynczej
    public String info; // dane, element listy
    public LinkTrain next; // referencja do nastepnego elementu, do nastepnej komorki w liscie
    public LinkWagon first; // referencja wskazujaca na pierwszy element listy dla reprezentacji listy wagonow danego pociagu


    public LinkTrain(String element){ // konstruktor, stworzenie obiektu odpowiadajacego pojedynczemu elementowi listy
        next = null;
        info = element;
    }

    /*Dalsza czesc implementacji metody wypisujacej liste wagonow pociagu o nazwie "train" poczawszy od pierwszego wagonu. */
    public void display() {

        LinkWagon ref = first; // zmienna przechowujaca referencje do pierwszego elementu, pierwszego wagonu

        System.out.print(info + ":");

        do{
            if (ref.next.prev != ref) { // jezeli pociag jest odwrocony, "next" i "prev" sa ustawione na nieodpowiedni wagon

                LinkWagon temp = ref.next.next; // zmienna tymczasowa przechowujaca element nastepny obecnie nastepnego elementu pierwszego wagonu
                ref.next.next = ref.next.prev; // elementem nastepnym nastepnego elementu pierwszego wagonu jest element poprzedni obecnie nastepnego elementu tego wagonu
                ref.next.prev = temp; // elementem poprzednim nastepnego elementu pierwszego wagonu jest zmienna tymczasowa
            }

            System.out.print(" " + ref.info); // wypisywanie kolejnych elementow listy
            ref = ref.next; // przejscie do nastepnego elementu

        }while (ref != first); // elementy wypisujemy do momentu, w ktorym ponownie znajdujemy sie na pierwszym elemencie, przeszlismy juz wszystkie elementy

        System.out.println();
    }
}

class LinkWagon { // Definicja klasy LinkWagon w reprezentacji listy jednostronnej, dwukierunkowej, cyklicznej
    public String info; // dane, element listy
    public LinkWagon next; // referencja do nastepnego elementu, do nastepnej komorki w liscie
    public LinkWagon prev; // referencja do poprzedniego elementu, do poprzedniej komorki w liscie

    public LinkWagon(String element) { // konstruktor, stworzenie obiektu odpowiadajacego pojedynczemu elementowi listy
        info = element;
    }
}
/*
IN:
1
8
New T1 W1
New T2 W2
InsertLast T1 W3
InsertLast T2 W4
InsertFirst T1 W5
Display T1
Reverse T1
Display T1
OUT:
T1: W5 W1 W3
T1: W3 W1 W5

IN:
1
16
New T1 W1
InsertLast T1 W2
InsertFirst T1 W3
New T2 W6
InsertLast T2 W5
InsertFirst T2 W4
Reverse T2
New T3 W8
InsertLast T3 W7
Reverse T3
New T4 W9
InsertLast T4 W10
Union T1 T2
Union T3 T4
Union T1 T3
Display T1
OUT:
T1: W3 W1 W2 W5 W6 W4 W7 W8 W9 W10

IN:
1
9
New T1 W1
InsertLast T1 W2
InsertLast T1 W3
InsertLast T1 W4
Display T1
DelLast T1 T2
Display T1
Display T2
Trains
OUT:
T1: W1 W2 W3 W4
T1: W1 W2 W3
T2: W4
Trains: T2 T1

IN:
1
9
New T1 W1
InsertLast T1 W2
InsertFirst T1 W3
InsertLast T1 W4
InsertFirst T1 W5
InsertLast T1 W6
Display T1
Reverse T1
Display T1
OUT:
T1: W5 W3 W1 W2 W4 W6
T1: W6 W4 W2 W1 W3 W5

IN:
1
3
New T3 W1
DelFirst T3 T3
Trains
OUT:
Train T3 already exists
Trains: T3

IN:
1
7
New T1 W1
InsertLast T1 W2
InsertLast T1 W3
InsertLast T1 W4
DelLast T2 T3
Display T1
Trains
OUT:
Train T2 does not exist
T1: W1 W2 W3 W4
Trains: T1
*/


