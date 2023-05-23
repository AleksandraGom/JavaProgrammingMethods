// Aleksandra Gomolka - 8
import java.util.Scanner;

/*Celem zadania bylo rostrzygniecie wynikow konkursu polegajacego na rywalizacji n ciagow. Kazdy ciag posiada m elementow. W kazdej z m rund bierze udzial
n reprezentantow danych ciagow. W pierwszej rundzie porownywane sa pierwsze wyrazy tych ciagow (pierwsze elementy tych ciagow), w naszym przypadku
sa to liczby calkowite. Runde wygrywa ten ciag, ktorego element jest najwiekszy. Kazdy ciag zaczyna turniej z zerowa liczba punktow. Wygrany zawodnik (ciag)
dostaje punkty, ktore sa dodawane do ogolnego wyniku danego ciagu, przy czym pozostale ciagi nie otrzymuja zadnych punktow. Elementy ciagow, ktore nie wygraly
danej rundy przechodza do kolejnej rundy. Reprezentant wygranego ciagu nie przechodzi do nastepnej rundy, jest zastepowany przez kolejny wyraz ciagu.
W przypadku remisu w rundzie, ktora zdarza sie gdy co najmniej dwoch reprezentantow osiaga ta sama najwieksza wartosc, zaden ciag nie otrzymuje punktow.
Wszyscy reprezentanci danych ciagow zostaja zastapieni przez kolejne wyrazy ciagow z ktorych pochodzili. Dla kazdego zestawu danych program w wyniku powinien
wypisac n linii w postaci: "nazwa_ciagu - k pkt.", gdzie k oznacza liczbe punktow zdobytych w turnieju przez dany ciag. Program dziala w zlozonosci
O(m log n + (1+r)n), gdzie r to liczba remisow, ktore wystapily podczas turnieju, n to liczba ciagow, m to liczba oznaczajaca dlugosc tych ciagow
(liczbe reprezentantow tych ciagow). W celu wykonania zadania wykorzystano strukturę kopca (ang. heap). Heap - max jest to tak zwane drzewo binarne,
w ktorym w korzeniu znajduje sie element największy. Przesuwając się w kierunku lisci obserwujemy zmniejszajace sie wartosci klucza.
Wartosc w kazdym wezle wewnetrznym jest wieksza lub rowna wartosci w dzieciach tego wezla. Kopiec-max jest zwykle reprezentowany jako tablica i tak
tez dokonano w tym zadaniu.*/

public class Source {

    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;
    public static void main(String[] args) {

        int numberOfsets; // zmienna okreslajaca liczbe zestawow
        int n; // zmienna okreslajaca liczbe ciagow
        int m; // zmienna okreslajaca dlugosc ciagow, liczbe elementow ciagow

        numberOfsets = scanner.nextInt(); // wczytywanie liczby zestawow

        for (int k = 1; k <= numberOfsets; k++) {
            n = scanner.nextInt(); // wczytywanie liczby ciagow
            m = scanner.nextInt(); // wczytywanie dlugosci ciagow

            int arr[][] = new int[n][m]; // zmienna tablicowa przechowujaca elementy ciagow, reprezentantow danych ciagow
            String names[] = new String[n]; // zmienna tablicowa przechowujaca nazwy danych ciagow

            for (int i = 0; i < n; i++) {
                names[i] = scanner.next(); // wpisywanie danych do zmiennej tablicowej przechowujacej nazwy danych ciagow
                for (int j = 0; j < m; j++) {
                    arr[i][j] = scanner.nextInt(); // wpisywanie danych do zmiennej tablicowej przechowujacej elementy ciagow, reprezentantow danych ciagow
                }
            }
            int[] indexes = new int[n]; // zmienna tablicowa przechowujaca indeksy, dostarcza wiedzy, na ktorym elemencie danego ciagu jestesmy
            int[] points = new int[n]; // zmienna tablicowa przechowujaca punkty zdobyte przez dany ciag
            Pair[] heap = new Pair[n]; // zmienna tablicowa przechowujaca obiekty klasy Pair tzn. wartosc danego elementu oraz indeks tablicy przechowujacej nazwy
            // ciagow, innymi slowy mowiac tablica przechowuje wartosc oraz indeks kierujacy do nazwy ciagu z ktorego pochodzi dany element

            for(int j = 0; j < n; j++) {
                heap[j] = new Pair(arr[j][indexes[j]],j); // poczatkowo wypelniamy tablice elementami z pierwszej rundy a dokladniej para wartosci
            }

            buildHeap(heap,n); // budowa kopca

            // Przechodzimy po wszystkich rundach
            for(int i = 0; i < m; i++) {
                // sprawdzamy remis
                /* sprawdzanie remisu polega na sprawdzeniu zarowno lewego jak i prawego dziecka, w przypadku braku, ktoregos dziecka lub obu dzieci
                moze pojawic sie blad, w zwiazku z tym dodano warunki (n > 1 oraz n > 2), ktore umozliwily wykonanie zadania dla wylacznie jednego lub dwoch
                ciagow bioracych udzial w turnieju. W przypadku operatora && jezeli warunek (n > 1) lub warunek (n > 2) nie beda spelnione
                dalsza czesc wyrazenia nie bedzie sprawdzana.*/
                if((n > 1 && heap[0].value == heap[1].value ) || (n > 2 && heap[0].value == heap[2].value)) { // jest remis
                    // jezeli jestesmy na ostatniej rundzie i jest remis to nie budujemy juz nowego kopca, poniewaz jest koniec turnieju
                    if(i < m-1) { // jezeli to nie jest ostatnia runda
                        for (int j = 0; j < n; j++) {
                            indexes[j]++; // zwiekszamy wszystkie indexy w tabicy indexes o jeden, w kolejnej rundzie biora udzial kolejne elementy danych ciagow
                            heap[j] = new Pair(arr[j][indexes[j]], j); // wstawiam nowe elementy do tablicy heap aby stworzyc z nich nowy kopiec
                        }
                        buildHeap(heap, n); // budowa kopca
                    }
                }
                else { // jezeli nie ma remisu, jest wygrany
                    Pair winner = extractMax(heap,n); // wybieramy zwyciezce, poprzez jego ekstrakcje z kopca
                    points[winner.stringIndex]++; // dodajemy punkty dla zwyciescy
                    indexes[winner.stringIndex]++; // zwiekszamy index w tabicy indexes o jeden dla wygranego, w kolejnej rundzie bierze udzial kolejny element wygranego ciagu
                    if(i < m-1) { // jezeli to nie jest ostatnia runda
                        insert(heap, new Pair(arr[winner.stringIndex][indexes[winner.stringIndex]],winner.stringIndex),n-1); // wstawiamy do kopca nowy
                        // element bioracy udzial w rozgrywce
                    }
                }
            }
            for(int i = 0; i < n; i++) { // wypisanie wynikow tzn. nazw ciagow oraz punkty przez nich zdobyte
                System.out.println(names[i] + " - " + points[i] + " pkt.");
            }
        }
    }

    /*Implementacja klasy, ktora przechowuje pary, wartosc oraz indeks tablicy przechowujacej nazwe ciagu tej wartosci.*/
    static public class Pair {
        public int value = 0; // zmienna przechowujaca wartosc elementu
        public int stringIndex = 0; // zmienna przechowujaca indeks tablicy ciagow, aby wiedziec do jakiego ciagu nalezy dana wartosc

        public Pair(int value,int index){ // konstruktor
            this.value = value;
            this.stringIndex = index;
        }
    }

    /*Implementacja metody, ktora kolokwialnie mowiac naprawia kopiec. Jezeli jest zaburzona wlasnosc kopca, nalezy przywrocic warunek
    kopca, aby otrzymac poprawny porzadek elementow. Zakladajac, ze musimy zbudowac Max-Heap z podanych elementow tablicy.
    Oby otrzymane drzewo binarne bylo zgodne z wlasciwoscia Heap, musimy "stertowac" (wykonac metode heapify) cale drzewo binarne.
    Obserwujemy jednak fakt, ze wezly lisci nie musza byc "stertowane", poniewaz juz podazaja za wlasciwoscia sterty. Nalezy wiec znalezc
    pozycje ostatniego wezla niebedacego lisciem (przy metodzie buildHeap) i wykonac operacje heapify kazdego wezla nie bedacego lisciem.
    Metoda heapify jest wykorzystywana, rowniez w metodzie extractMax a jej wykorzystanie jest wytlumaczone przy implementacji tej metody.*/
    static void heapify(Pair arr[], int n, int i) {
        int largest = i; // inicjalizacja obecnie najwiekszego elementu, element na ktorym dokonujemy "naprawy" staje sie obecnie najwiekszym elementem
        // w dalszej czesci metody dokunujemy sprawdzania czy rzeczywiscie jest najwiekszy, czy moze nalezy dokonac zamiany elementow, aby zachowac
        // wlasciwa budowe Max-Heap
        int left = 2 * i + 1; // pozycja lewego wezla, lewe dziecko
        int right = 2 * i + 2; // pozycja prawego wezla, prawe dziecko


        // Jezeli lewe dziecko jest wieksze od najwiekszego elementu
        // warunek (left < n), konieczny aby nie sprawdzac jezeli wezel nie ma dzieci
        if (left < n && arr[left].value > arr[largest].value)
            largest = left; // aktualizacja obecnie najwiekszego elementu, lewe dziecko jest teraz najwiekszym elementem

        // Jezeli prawe dziecko jest wieksze od najwiekszego elementu
        // warunek (right < n), konieczny aby nie sprawdzac jezeli wezel nie ma dzieci
        if (right < n && arr[right].value > arr[largest].value)
            largest = right; // aktualizacja obecnie najwiekszego elementu, prawe dziecko jest teraz najwiekszym elementem

        // Jezeli najwiekszy element nie jest tym, ktory byl ustalony na poczatku jako najwiekszy, nalezy dokonac zamiany elementow
        if (largest != i) {
            Pair tmp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = tmp;

            // rekurencyjne wywolanie metody, aby dokonac sprawdzenia dalszej poprawnosci polozenia interesujacego nas elementu
            heapify(arr, n, largest);
        }
    }

    /*Implementacja metody, ktora buduje kopiec-max przy uzyciu tablicy. Obserwujemy fakt, ze wezly lisci nie musza byc "stertowane",
    poniewaz juz podazaja za wlasciwoscia sterty. Nalezy wiec znalezc pozycje ostatniego wezla niebedacego lisciem (przy metodzie buildHeap) i
    wykonac operacje heapify kazdego wezla nie bedacego lisciem.*/
    static void buildHeap(Pair arr[], int n) {
        int idx = (n / 2) - 1; // index ostatniego wezla niebedacego lisciem
        // wykonujemy przechodzenie na poziomie od ostatniego wezla innego niz lisc i wykonujemy heapify dla kazdego wezla
        for (int i = idx; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }

    /*Implementacja metody, ktora zwraca pozycje wezla rodzicielskiego, poprzednika.*/
    static int parent(int i) {
        return (i-1)/2; // wezel rodzicielski w tablicowej reprezentacji heap
    }

    /*Implementacja metody, ktora wstawia nowy "klucz", nowa wartosc do kopca. Na koncu drzewa dodajemy nowy klucz. Jesli nowy klucz jest mniejszy
    niz jego rodzic, nie musimy nic robic. W przeciwnym razie musimy przejsc w gorę, aby naprawic naruszona wlasciwosc sterty.*/
    static void insert(Pair arr[], Pair element, int n) {
        n++; // zwiekszamy liczbe elementow
        int i = n-1; // indeks nowego elementu
        arr[i] = element; // wstawiamy nowy element w miejsce nowego indexu
        // warunek ((i != 0) sprawdza czy przeszlismy juz po wszystkich elementach, do samej gory kopca)
        while(i != 0 && arr[parent(i)].value < arr[i].value){ // jezeli nowy element jest wiekszy od rodzica, nalezy dokonac zamiany elementow,
            // poniewaz dazymy to tego aby wieksze elementy znajdowaly sie wyzej w kopcu
            Pair tmp = arr[i];
            arr[i] = arr[parent(i)];
            arr[parent(i)] = tmp;
            i = parent(i); // indeks "i" ustawiamy na rodzica obecnego indeksu "i"
        }
    }

    /*Implementacja metody, ktora zwraca maksymalny element z MaxHeap, ktory powinien byc zastapiony nowym elementem, "usuniety".
    Usiniecie dokonujemy niejawnie, przez zmiane zakresu w glownej czesci zadania, przez przekazanie do metody insert mniejszej liczby elementow (n-1).
    Jednak aby znac zwyciesce danej rundy i moc policzyc dla niego zdobyta liczbe punktow musimy go zwrocic przed jego zastapieniem przez nowy element.*/
    static Pair extractMax(Pair arr[],int n) {
        if(n == 1)return arr[0]; // jezeli jest tylko jeden element to zwracamy wlasnie ten element
        Pair root = arr[0];
        arr[0] = arr[n-1]; // ostatni element ustawiamy jako pierwszy element i dokonujemy "naprawy" kopca
        heapify(arr,n-1,0);
        return root; // zwracamy maksymalny element, przeznaczony na usuniecie
    }
}

/*Przykladowe testy:

IN:
5
4 5
Zosia 1 2 -1 2 0
Kasia 2 1 3 2 0
Basia 0 2 4 2 1
Stasia 5 2 -2 3 4
3 4
Kix 1 2 3 2
Kox 2 3 4 5
Kim -1 2 3 5
2 6
Batman 2 5 1 7 3 4
Spiderman 1 8 2 3 7 2
1 5
Winner 1 2 3 4 5
3 3
Zeus 10 2 -2
Afrodyta -3 2 10
Posejdon 2 10 -4

OUT:
Zosia - 0 pkt.
Kasia - 0 pkt.
Basia - 1 pkt.
Stasia - 1 pkt.
Kix - 0 pkt.
Kox - 4 pkt.
Kim - 0 pkt.
Batman - 4 pkt.
Spiderman - 1 pkt.
Winner - 5 pkt.
Zeus - 1 pkt.
Afrodyta - 0 pkt.
Posejdon - 1 pkt.
*/