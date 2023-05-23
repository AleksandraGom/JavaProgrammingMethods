// Aleksandra Gomolka - 8

import java.util.Scanner;

/* W programie dysponujemy tablica, w ktorej pod indeksem oznaczajacym numer piosenki na plycie przechowywana jest informacja o liczbie glosow
oddanych na te piosenke w glosowaniu Wieczornej Listy Przebojow. Celem zadania jest uzyskanie informacji o liczbie glosow, ktore
zdobyla piosenka na okreslonej pozycji w notowaniu. Dodatkowo przewidujemy sytuacje, w ktorej kilka piosenek zdobylo tyle samo glosow.
W celu otrzymania porzadanych wynikow wykorzystano, uzyta rekurencyjnie funkcje, znajdujaca k-ty co do wielkosci element tablicy
n-elementowej w porzadku niemalejacym, liczac od najmniejszego. Wartosc k=1 odpowiada najmniejszemu elementowi tablicy, czyli piosence,
ktora zdobyla najmniej glosow. Program, w przypadku pesymistycznym posiada liniowa zlozonosc czasowa. Kroki mające na celu podzial wejsciowej
tablicy na n/5 grup oraz odnalezienie mediany wszystkich grup zajmuja O(n) czasu, poniewaz znalezienie mediany tablicy o 5 elementach zajmuje
O(1) czasu, a istnieje n/5 podtablic o rozmiarze 5 elementow. Innymi slowy mowiac, te kroki skladaja sie lacznie z O(n) wywolan sortowania
przez wstawianie na zestawach o rozmiarze O(1). Znalezienie mediany median zajmuje O(n/5) czasu. Partycjonowanie (metoda Partition) zajmuje O(n) czasu.
Kolejne kroki korzystaja z wywolan rekurencyjnych, ktorych najgorszy rozmiar jest taki jak maksymalna liczba elementow wieksza niz medOfMed
lub maksymalna liczba elementow mniejsza niz medOfMed. Co najmniej polowa median jest wieksza lub rowna medOfMed.
Zatem co najmniej polowa z n/5 grup sklada sie z trzech elementow, ktore sa wieksze niz medOfMed, z wyjatkiem jednej grupy, ktora ma mniej niz
5 elementow (jezeli n nie dzieli sie calkowicie przez 5) oraz jednej grupy zawierajacej element dzielacy. Pomijajac te dwie grupy, wynika, ze
liczba elementow wieksza oraz liczba elementow mniejsza niz element dzielacy (medOfmed) wynosi co najmniej (3n/10-6).
Tak wiec, w najgorszym przypadku metoda songSelection jest wywolywana rekurencyjnie na maksymalnie (7n/10+6) elementach w ostatnim kroku.
Innymi slowy mowiac, w najgorszym przypadku funkcja powtarza sie co najwyzej dla (n-(3n/10-6), czyli (7n/10+6) elementow.
W programie nie zarezerwowano zadnej dodatkowej struktury danych o zlozonosci pamieciowej wiekszej niz stala. Zatem program ma stala zlozonosc
pamieciowa.*/

public class Source { // Implementacja glownej klasy.
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;


    public static void main(String[] args) {

        int numberOfsets; // zmienna okreslajaca liczbe zestawow danych, ktore pojawiaja sie na wejscie
        int numberOfelements; // zmienna przechowujaca liczbe elementow tablicy
        int numberOfcommends; // zmienna przechowujaca liczbe zapytan

        numberOfsets = scanner.nextInt(); // wczytywanie liczby zestawow danych

        for (int k = 1; k <= numberOfsets; k++) {
            numberOfelements = scanner.nextInt(); // wczytywanie liczby elementow tablicy
            int Array[] = new int[numberOfelements]; //zmienna tablicowa przechowujaca liczby calkowite, bedace elementami tablicy


            for (int i = 0; i < numberOfelements; i++) {
                Array[i] = scanner.nextInt(); // wstawianie elementow do tablicy
            }

            numberOfcommends = scanner.nextInt(); // wczytywanie liczby zapytan
            int commendArray[] = new int[numberOfcommends]; // zmienna tablicowa przechowujaca liczby calkowite, kazda oznacza k-ty element tablicy

            for (int j = 0; j < numberOfcommends; j++) {
                commendArray[j] = scanner.nextInt(); // wstawianie elementow do tablicy
                int wynik = songSelection(Array, 0, Array.length - 1, commendArray[j]);
                if (wynik == -1) { // jezeli metoda "sonSelection" zwroci wartosc -1, oznacza to ze nalezy wypisac komunikat "brak"
                    System.out.println(commendArray[j] + " brak");
                } else {
                    System.out.println(commendArray[j] + " " + wynik);
                }
            }
        }
    }

    /*Implementacja metody, ktora szuka k-tego co do wielkosci elementu tablicy n-elementowej. Idea dzialania metody polega na podziale
    wejsciowej tablicy n-elementowej na n/5 grup, w ktorych wielkosc kazdej grup wynosi 5 elementow oraz co najwyzej jedna grupe zlozona
    z pozostalych n%5 elementow, ostatnia grupa moze miec mniej elementow. Nastepnie utworzone grupy zostaja posortowane i nastepuje wyszukiwanie
    median kazdej z grup. Mediany poszczegolnych grup sa przesuwane na poczatek tablicy. Nastepnie przy rekurencyjnym
    wywolaniu metody wyszukana jest mediana ze wszystkich median. W kolejnym kroku tablica wejsciowa ulega podzialowi wokol mediany median, przy
    uzyciu metody "Partition". Nastepnie w zaleznosci od polozenia k, wzgledem polozenia mediany median program zwraca okreslony wynik. */

    public static int songSelection(int arr[], int left, int right, int k) {

        int numberOfelem = right - left + 1; // liczba elementow tablicy, dlugosc tablicy
        int i; // zmienna ulatwiajaca przechodzenie miedzy grupami/podtablicami
        int numberOfgroup = numberOfelem / 5; // zmienna przechowujaca ilosc grup, na ktore zostanie podzielona wejsciowa tablica
        int median; // zmienna przechowujaca mediane elementow w danej podgrupie/podtablicy

        if (k <= numberOfelem && k > 0) { // jezeli k jest mniejsze niz liczba elementow w tablicy, badz rowne oraz jest wieksze od zera

            /*Podzial tablicy na podtablice o rozmiarze 5, z wyjatkiem mozliwie ostatniej grupy, ktora moze miec mniej niz 5 elementow,
           obliczenie median w kazdej z podtablic i przesuniecie znalezionych median na poczatek tablicy. */

            for (i = 0; i < numberOfgroup; i++) {
                median = findMedian(arr, left + i * 5, 5); // posortowanie utworzonych grup oraz znalezienie median kazdej z grup
                swap(arr, median, left + i); // przesuwanie znalezionych median na poczatek tablicy
            }

            // ostatnia grupa, mniejsza niz 5 elemen
            if (5 * i < numberOfelem) {
                median = findMedian(arr, left + i * 5, numberOfelem % 5); // posortowanie utworzonej grupy oraz znalezienie mediany
                swap(arr, median, left + i); // przesuniecie znalezionej mediany na poczatek tablicy
                i = i + 1;
            }

            int numberOfmed; // zmienna przechowujaca liczbe znalezionych median
            numberOfmed = (int) Math.ceil(numberOfelem / 5.);
            int medOfMed; // zmienna przechowujaca mediane ze znalezionych median

            /*Jezeli wejciowa tablica miala mniej elementow niz 5, badz rowno 5, co po podziale tablicy w konsekwencji daje jedna grupe,
            (czyli jedna mediane) to mediana median to pierwszy element tablicy/podtablicy. W przeciwnym przypadku dokonujemy znalezienia mediany
            wszystkich median, przy uzyciu rekurencyjnego wywolania metody. */
            if (i == 1) {
                medOfMed = arr[left];
            } else {
                medOfMed = songSelection(arr, left, left + numberOfmed - 1, numberOfmed/2);
            }

            /*Tablica wejsciowa ulega podzialowi wokol mediany median, przy uzyciu metody "Partition" */
            int part = Partition(arr, left, right, medOfMed);

            if (part == left + k - 1) // jezeli pozycja jest taka sama jak k
                return arr[part]; // zwracamy mediane median
            else if (part > left + k - 1) // jezeli pozycja jest wieksza niz k, wykonujemy metode rekurencyjnie dla lewej podtablicy
                return songSelection(arr, left, part - 1, k);
            else // jezeli pozycja jest mniejsza niz k, wykonujemy metode rekurencyjnie dla prawej potablicy
                return songSelection(arr, part + 1, right, k - part + left - 1);
        } // jezeli k jest większe niz liczba elementow w tablicy lub jest mniejsze lub rowne zero
        return -1;
    }

    /*Implementacja metody, ktora szuka mediany w podanej tablicy.*/
    public static int findMedian(int arr[], int left, int number) {
        InsertionSort(arr, left, left + number); // sortowanie tablicy
        return left + number / 2; // zwrocenie mediany, wartosci srodkowej
    }

    /*Implementacja metody, ktora wyszukuje elementu zadanego jako argument w tablicy oraz dzieli tablice wokol tego elementu.
     Wszystkie elementy nie wieksze (<=) od dzielacego beda znajdowac sie po lewej stronie od elementu dzielacego a po prawej stronie
     beda znajdowac sie wszystkie elementy nie mniejsze (>=) od dzielacego. Element dzielacy po ewentualnej zamianie bedzie zajmowal wlasciwa
     pozycje w tablicy. Index "i" oraz index "j" ustawiono w miejscu pierwszego elementu (left). Index "j" przechodzi po calej tablicy
     (skanuje elementy) do elementu wystepujacego przed elementem ostatnim. Jezeli element znajdujacy sie pod indexem "j" jest mniejszy,
     badz rowny elementowy dzielacemu, inkrementacji ulega index "i" a elementy znajdujace sie pod indexami "i" oraz "j" zamieniaja sie miejscami.
     Taki zabieg pozwala na przesuniecie wszystkich elementow <= medianOfmedian na lewa strone oraz elementow >= medianOfmedian na prawa strone.
     Ostatnim etapem jest zamiana miejscami elementu medianOfmedian oraz elementu (i). W wyniku otrzymujemy index elementu zwanego medianOfmedian,
     ktory po wyzej opisanej operacji znajduje sie na pozycji i.*/

    public static int Partition(int arr[], int left, int right, int medianOfmedian) {

        int i;
        for (i = left; i < right; i++) { // po tej czesci operacji element dzielacy bedzie znajdowal sie na koncu tablicy
            if (arr[i] == medianOfmedian)
                break;
        }
        swap(arr, i, right);

        i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] <= medianOfmedian) { // jezeli element wskazywany przez index "j" jest mniejszy, badz rowny elementowi "medianOfmedian"
                swap(arr, i, j); // dokonujemy zamiany elementow znajdujacych sie pod  indexami "i" oraz "j"
                i = i + 1; // inkrementuje index i;
            }
        }
        swap(arr, i, right); // ostatnim etapem jest zamiana miejsc elementu wystepujacego pod indexem i z elementem wystepujacym pod indexem o wartosci "right"
        return i;
    }

    /*Implementacja metody, ktora sluzy do zmiany elementow na okreslonych pozycjach.
    Do zmiennej tymczasowej przypisujemy pierwszy z elementow okreslonych jako argument funkcji "swap", po to aby moc na jego
    miejsce wstawic drugi element podany jako argument tej funkcji, bez jego utraty.*/
    public static void swap(int[] arr, int m, int n) {
        int tmp = arr[m];
        arr[m] = arr[n];
        arr[n] = tmp;
    }

    /* Implementacja algorytmu sortowania przez wstawianie.
   Poczatkowo algorytm ustawia znacznik po pierwszym elemencie. Tym samym tworzymy dwie sekcje, posortowana i nieposortowana.
   Nastepujace czynnosci powtarzamy do momentu az nieposortowana sekcja (znajdujaca sie bardziej na prawo) bedzie pusta.
   Wybieramy pierwszy nieposortowany element (i = left+1). Przesuwamy inne elementy w prawo zgodnie z warunkiem znajdujacym sie w petli while,
   aby utworzyc wlasciwa kolejnosc oraz przesuwamy nieposortowany element w odpowiednie miejsce (arr[j + 1] = tmp).
   Nastepnie przesuwamy znacznik o jeden element w prawo.*/
    public static void InsertionSort(int[] arr, int left, int right) {
        int i, j, tmp;
        for (i = left + 1; i < right; i++) { // petla zewnetrzna, i to pierwszy nieposortowany element
            tmp = arr[i]; // kopiujemy wyrozniony element do zmiennej tmp
            j = i - 1; // sortowanie zaczynamy od elementu i-1
            while (j >= 0 && arr[j] > tmp) {
                arr[j + 1] = arr[j]; // przesuwamy element znajdujacy sie pod indeksem j w prawo
                j -= 1; //  sprawdzenie poprzedniego elementu
            }           // przypadek gdzie (j==-1) lub (tmp >= a[j])
            arr[j + 1] = tmp; // wstawianie wyroznionego elementu w odpowiednie miejsce
        }
    }
}

/*Przykladowe testy:

IN:
7
4
1 4 3 6
6
0 1 2 3 4 5
6
5 5 3 3 6 6
3
2 4 6
4
5 15 20 25
4
2 5 3 4
10
1 1 1 3 3 3 2 2 2 5
6
1 0 3 0 12 2
2
25 10
5
0 1 3 4 2
13
10 12 4 3 5 6 8 7 9 1 11 2 0
13
1 2 3 4 5 6 7 8 9 10 11 12 13
4
0 0 0 0
6
0 1 2 3 4 5


OUT:
0 brak
1 1
2 3
3 4
4 6
5 brak
2 3
4 5
6 6
2 15
5 brak
3 20
4 25
1 1
0 brak
3 1
0 brak
12 brak
2 1
0 brak
1 10
3 brak
4 brak
2 25
1 0
2 1
3 2
4 3
5 4
6 5
7 6
8 7
9 8
10 9
11 10
12 11
13 12
0 brak
1 0
2 0
3 0
4 0
5 brak
*/