// Aleksandra Gomolka - 8

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

/*Celem zadania jest implementacja algorytmu do programu sortujacego dane w formacie tsv. Wykorzystujac algorytm
QuickSort w wersji iteracyjnej bez uzycia stosu, nalezalo posortowac wartosci w tabeli wedlug podanej kolejnosci. Algorytm jest wykonywany,
 dopoki wielkosc podzadan jest wieksza lub rowna 20. Pozostale podzadania nalezalo wykonac za pomoca sortowania przez wstawianie.
 Dzialanie programu sprowadza sie do wyswietlenia odpowiednich danych, w zaleznosci od wczytanej na wejscie komendy.
 W przypadku zapytania "single" na wyjscie nalezalo wypisac tylko wartosci w podanej jako argument kolumnie a w przypadku zapytania "all",
 nalezalo wypisac wartosci wszystkich kolumn oddzielone znakiem tabulacji. Kazdy zestaw powinien zaczynac sie od wypisania zapytania
 poprzedzonego znakiem ($) oraz spacja (np. $ all <nazwa kolumny>). Kolejne linie powinny zawierac dane wraz z naglowkiem, posortowane wedlug
 wymogow zapytania. W przypadku, kiedy zapytanie zawiera kolumne, ktorej nie ma w zbiorze danych zapytanie powinno zwrocic wiadomosc
 "invalid column name: <nazwa kolumny>". Podsumowujac, wyjsciem z programu sa zestawy kolumn posortowane wedlug wymagan poszczegolnych zapytan.*/

public class Source {
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;
    public static DecimalFormat format = new DecimalFormat("0.####"); // formatowanie danych w posortowanej tabeli


    public static void main(String[] args) {
        scanner.useLocale(Locale.GERMAN); // format do czytania liczb zmiennoprzecinkowych z przecinkiem oddzielajacym czesc dziesietna
        int numberOfsets; // liczba oznaczajaca liczbe zestawow danych
        int numberOfrows; // liczba okreslajaca liczbe wierszy w tabeli
        int numberOfcolumns; // liczba okreslajaca liczbe kolumn w tabeli
        String columnName; // kolumna, po ktorej bedziemy sortowac
        String commandName; // nazwa komendy, "single" lub "all"
        int numberOfcommand; // liczba zapytan


        numberOfsets = scanner.nextInt(); // wczytywanie liczby okreslajacej liczbe zestawow danych
        for (int k = 1; k <= numberOfsets; k++) {
            numberOfcolumns = scanner.nextInt(); // wczytywanie liczby kolumn w tabeli
            numberOfrows = scanner.nextInt(); // wczytywanie liczby wierszy w tabeli

            String[] tab = new String[numberOfcolumns]; // tabela naglowkow
            float[][] array = new float[numberOfrows][numberOfcolumns]; // tabela zawierajaca liczby rzeczywiste, wieksze od zera


            for (int m = 0; m < numberOfcolumns; m++) {
                tab[m] = scanner.next(); // wypelnianie tabeli naglowkami
            }

            for (int i = 0; i < numberOfrows; i++) {
                for (int j = 0; j < numberOfcolumns; j++) {
                    array[i][j] = scanner.nextFloat(); // wypelnianie tabeli liczbami rzeczywistymi, wiekszymi od zera
                }
            }
            numberOfcommand = scanner.nextInt(); // wczytywanie liczby zapytan
            for (int n = 1; n <= numberOfcommand; n++) {
                commandName = scanner.next(); // wczytywanie nazwy komendy
                columnName = scanner.next(); // wczytywanie nazwy kolumny po ktorej nalezy sortowac logi

                int IndexColumn = 0; // zmienna przechowujca index kolumny, po ktorej nalezy sortowac
                IndexColumn = find(tab,columnName,numberOfcolumns); // wyszukanie indeksu kolumny, po ktorej nalezy sortowac logi

                if(IndexColumn == -1) { // nie znaleziono szukanej kolumny, nalezy wypisac odpowiedni komunikat
                    System.out.println("$ " + commandName + " " + columnName);
                    System.out.println("invalid column name: " + columnName);
                }
                else { // jezeli szukana kolumna zostala znaleziona, nalezy wywolac metode "SortingMethod" a nastepnie w zaleznosci od komendy wypisac odpowiednie dane na wyjscie
                    SortingMethod(array, 0, array.length - 1, IndexColumn);
                    if (commandName.equals("all")) { // jezeli nazwa zapytania to "all" na wyjscie wypisywane sa wartosci wszystkich kolumn
                        System.out.println("$ all " + columnName);
                        printAll(array, tab, numberOfrows, numberOfcolumns);
                    } else if (commandName.equals("single")) { // jezeli nazwa zapytania to "single" na wyjscie wypisywane sa wartosci tylko w podanej kolumnie
                        System.out.println("$ single " + columnName);
                        printSingle(array, tab, numberOfrows, numberOfcolumns, columnName, IndexColumn);
                    }
                }
            }
        }
    }

    /*Implementacja metody, ktora na wyjscie wypisuje wartosci wszystkich kolumn oddzielone znakiem tabulacji.*/
    public static void printAll(float [][] arr, String [] nagl, int rownum, int colnum) {

        for (int i = 0; i < nagl.length; i++) {
            System.out.print(nagl[i] + "\t"); // wypisanie wszystkich naglowkow
        }
        System.out.println();
        for( int j = 0; j < rownum; j++) {
            for (int k = 0; k < colnum; k++) {
                System.out.print(format.format(arr[j][k]) + "\t"); // wypisanie posortowanych wartosci ze wszystkich kolumn
            }
            System.out.println();
        }
    }

    /*Implementacja metody, ktora na wyjscie wypisuje wartosci tylko w podanej kolumnie.*/
    public static void printSingle(float [][] arr, String [] nagl, int rownum, int colnum, String colName, int indx) {

        for (int i = 0; i < nagl.length; i++) {
            if (nagl[i].equals(colName)) {
                System.out.print(nagl[i]); // wypisanie naglowka danej kolumny
            }
        }
        System.out.println();
        for (int j = 0; j < rownum; j++) {
            System.out.print(format.format(arr[j][indx]) + "\n"); // wypisanie posortowanych wartosci danej kolumny
        }
    }

    /*Implementacja metody, ktora wyszukuje index kolumny, po ktorej nalezy posortowac logi. Jezeli metoda zwroci -1, oznacza to, ze
    takiej kolumny nie ma i nalezy wypisac odpowiedni komunikat.*/
    public static int find(String[] tab, String colName, int numberColumn) {
        int index; // zmienna przechowujca index szukanej kolumny
        for (int i = 0; i< numberColumn; i++){
            if(tab[i].equals(colName)) {
                index = i;
                return index;
            }
        }
        return -1; // nie znaleziono szukanej kolumny
    }

    /*Implementacja metody, ktora wyznacza zmienna "right", indeks prawego konca tablicy/podtablicy. Prawy koniec podtablicy
    wyznaczono, wyszukujac pierwszej napotkanej liczby ujemnej, poniewaz ostatnia pozycja prawego konca tablicy/podtablicy
    jest zapamietywana poprzez zmiane znaku elementu wystepujacego na danej pozycji.*/
    public static int findNewRight(float [][] arr, int left, int columnIndex, int size) {
        for (int i = left; i < size; ++i) {
            if (arr[i][columnIndex] < 0) { return i; } // zwracamy szukany index, pozycje prawego konca tablicy
        }
        return size-1; // jezeli wartosc ujemna nie zostala odnaleziona metoda zwraca wielkosc tablicy pomniejszona o jeden,
        // innymi slowy mowiac, w takim przypadku metoda zwraca index ostatniego elementu.
    }

    /*Implementacja metody, ktora wyznacza element dzielacy (ang.pivot). Wszystkie elementy nie wieksze (<=) od dzielacego beda znajdowac
    sie po lewej stronie od elementu dzielacego a po prawej stronie beda znajdowac sie wszystkie elementy nie mniejsze (>=) od dzielacego.
    Element dzielacy po ewentualnej zamianie bedzie zajmowal wlasciwa pozycje w tablicy.
    Metoda "Partition" jest zmodyfikowana na potrzeby zadania, poniewaz wyszukanie elementu dzielacego jest wykonywane na elementach
    podanej kolumny, ktorej index jest jednym z argumentow metody.*/
    public static int Partition(float [][] arr, int left, int right, int colIndex) {
        float pivot = arr[right][colIndex]; // ostatni element jest dzielacy
        int i=left-1;
        /* Index "i" ustawiono przed pierwszym elementem, natomiast index "j" ustawiono w miejscu pierwszego elementu (left).
        Index "j" przechodzi po calej tablicy (skanuje elementy) do elementu wystepujacego przed elementem ostatnim. Jezeli element znajdujacy sie
        pod indexem "j" jest mniejszy, badz rowny elementowy dzielacemu, inkrementacji ulega index "i" a elmenety znajdujace sie pod
        indexami "i" oraz "j" zamieniaja sie miejscami. Taki zabieg pozwala na przesuniecie wszystkich elementow <= pivot na lewa strone
        oraz elementow >= pivot na prawa strone. Index "i" wyznacza miejsce ostatniego elementu mniejszego od pivot.
        Zatem ostatnim etapem jest zamiana miejscami elementu pivot oraz elementu wystepujacego po ostatnim elemencie mniejszym od pivot (i+1).
        W wyniku otrzymujemy index elementu zwanego pivot, ktory po wyzej opisanej operacji znajduje sie na pozycji i+1.*/

        for(int j = left; j < right; j++) {
            if (arr[j][colIndex] <= pivot) { // jezeli element wskazywany przez index "j" jest mniejszy, badz rowny elementowi "pivot"
                i = i + 1; // inkrementuje index i;
                swap(arr, i, j); // dokonujemy zamiany elementow znajdujacych sie pod  indexami "i" oraz "j"
            }
        }
        swap(arr, i+1, right); // ostatnim etapem jest zamiana miejsc elementu wystepujacego pod indexem i z elementem wystepujacym pod indexem o wartosci "right"
        return i+1;
    }

    /*Implementacja metody, ktora sluzy do zmiany elementow na okreslonych pozycjach.
    Do zmiennej tymczasowej przypisujemy pierwszy z elementow okreslonych jako argument funkcji "swap", po to aby moc na jego
    miejsce wstawic drugi element podany jako argument tej funkcji, bez jego utraty. Metoda "swap" jest zmodyfikowana na potrzeby zadania,
    poniewaz dokonujemy zamiany miejsc calych wierszy tablicy.*/
    public static void swap(float [][] arr, int i, int j) {
        float [] tmp;
        tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /*Implementacja metody sortujacej dane. Metoda wykorzystuje algorytm QuickSort, ktory dziala zgodnie z zasada "dziel i zwyciezaj".
    Algorytm zaimplementowany jest w wersji iteracyjnej bez uzycia stosu a jego zlosonosc czasowa wynosi O(nlogn).
    Wartosci w zadanej tabeli sa sortowane wedlug podanej kolejnosci,dopoki wielkosc podzadan jest wieksza lub rowna 20.
    W przypadku malych podzadan (n < 20) do sortowania wykorzystano metode sortowania przez wstawianie (InsertionSortMethod).
    Taka implementacja metody (iteracyjna, bez uzycia stosu) zmniejsza zlozonosc pamieciowa do O(1).
    Pewna symulacja zastosowania stosu, jest zmiana znakow elementow na ujemne. Zakladajac, ze tablica sklada sie
    wylacznie z dodatnich liczb rzeczywistych, indeksy prawych koncow podtablic mozna znaczyc, zmieniajac znak ostatniej liczby na ujemny.
    W petli bedziemy szukac ujemnej liczby, a kiedy ja znajdziemy bedziemy dalej wykonywac reszte operacji.
    */
    public static void SortingMethod(float [][] arr, int left, int right, int columnIndex) {

        int i = 0; // zmienna okreslajaca koniec dzialania metody, jezeli nie ma juz zadnych elementow do posortowania (i < 0), nalezy zakonczyc dzialanie metody
        int pivot; // zmienna przechowujaca index elementu dzielacego
        int sizeArr = arr.length; // zmienna przechowujaca dlugosc tablicy

        while (true) { // dopoki dzialanie metody nie zostanie zakonczone
            i -=1; // dekrementacja zmiennej "i"
            while (left < right) { // dopoki lewy koniec tablicy jest mniejszy od prawego konca tablicy, nalezy wykonywac sortowanie a dokladniej czynnosci zawarte
                // w petli while, w zaleznosci od spelnianego warunku (wielkosci podzadan)
                if ((right - left  < 19)) { // jezeli wielkosc podzadan jest mniejsza, badz rowna 19, innymi slowy mowiac wielkosc podtablicy jest mniejsza niz 20
                    InsertionSortMethod(arr,left,right,columnIndex); // nalezy dokonac sortowania przy uzyciu sortowania przez wstawianie
                    left = right; // aktualizacja zmiennej lewego konca podtablicy, innymi slowy ustalenie nowych przedzialow sortowania
                } else { // jezeli wielkosc podzadan jest wieksza, badz rowna 20, nalezy dokonac sortowania przy uzyciu sortowania QuickSort
                    // dopoki warunek w petli while jest spelniony tablica jest dzielona na mniejsze podtablice, wyznaczane przez element dzielacy pivot
                    pivot = Partition(arr, left, right, columnIndex); // odnalezienie elementu dzielacego
                    // ostatnia pozycja "right" (prawego konca tablicy/podtablicy) jest zapamietywana poprzez zmiane znaku elementu wystepujacego na danej pozycji
                    arr[right][columnIndex] = -arr[right][columnIndex]; // zamiana znakow elementow
                    right = pivot - 1; // symulacja rekurencji, ktora odbywa sie w przedzilach (left, pivot-1) oraz (pivot+1, right)
                    /* prawa podtablice, czyli (pivot+1, right) sortuje z wykorzystaniem dzialan przedstawionych ponizej.*/
                    i += 1; // inkrementacja zmiennej "i"
                }
            }
            if (i < 0) { break; } // jezeli (i < 0), zakoncz dzialanie metody, poniewaz sortowanie zostalo zakonczone
            /*Gdy "left" nie jest mniejsze od "right" oraz "i" nie jest mniejsze od 0, przesuwamy "left" i wyznaczamy nowy "right".
            Kiedy przejdziemy po wszystkich elementach lewej podtablicy (left=right), mozemy dokonac sortowania prawej podtablicy.*/
            left +=1; // inkrementacja zmiennej "left", przesuniecie lewego konca tablicy/podtablicy
            right = findNewRight(arr, left, columnIndex, sizeArr); // ustalenie nowego "right"
            /*Nowy "right" wyznaczam za pomoca metody "findNewTmpRight(), wyszukujac pierwszej napotkanej liczby ujemnej. */
            arr[right][columnIndex] = -arr[right][columnIndex]; // zmiana znaku elementow
        }
    }

    /* Implementacja algorytmu sortowania przez wstawianie.
   Metoda zostaje wywolana w funkcji sortujacej, wykorzystujacej algorytm QuickSort, w momencie gdy wielkosc podzadan jest mniejsza od 20.
   Poczatkowo algorytm ustawia znacznik po pierwszym elemencie. Tym samym tworzymy dwie sekcje, posortowana i nieposortowana.
   Nastepujace czynnosci powtarzamy do momentu az nieposortowana sekcja (znajdujaca sie bardziej na prawo) bedzie pusta.
   Wybieramy pierwszy nieposortowany element (i = start+1). Przesuwamy inne elementy w prawo zgodnie z warunkiem znajdujacym sie w petli while,
   aby utworzyc wlasciwa kolejnosc oraz przesuwamy nieposortowany element w odpowiednie miejsce (arr[j + 1] = tmpRow).
   Nastepnie przesuwamy znacznik o jeden element w prawo. Metoda "InsertionSortMethod" jest zmodyfikowana na potrzeby zadania, aby
   mimo sortowania po zadanej kolumnie, przesunieciu ulegaly cale wiersze. W tym celu wykorzystuje zmienna tmpRow, do ktorej przypisuje caly
   wiersz.*/
    public static void InsertionSortMethod(float[][] arr, int start, int end, int colIndex) {
        int i, j;
        float tmp;
        float tmpRow[];
        for (i = start+1; i <= end; i++) { // petla zewnetrzna, i to pierwszy nieposortowany element
            tmpRow = arr[i];
            tmp = arr[i][colIndex]; // kopiujemy wyrozniony element do zmiennej tmp
            j = i - 1; // sortowanie zaczynamy od elementu i-1
            while (j >= 0 && arr[j][colIndex] > tmp) {
                arr[j + 1] = arr[j]; // przesuwamy element znajdujacy sie pod indeksem j w prawo
                j -= 1; //  sprawdzenie poprzedniego elementu
            }           // przypadek gdzie (j==-1) lub (tmp >= a[j][colIndex])
            arr[j + 1] = tmpRow; // wstawianie wyroznionego elementu w odpowiednie miejsce
        }
    }
}

/*
* Przykladowe testy:
IN:
4
5 3
Id    minute    hour    pressure    temperature
2    20    15    12,5    11,3
4    15    10    20,4    20,14
6    12    25    30,05    72,13
4
all minute
single hour
single temperature
all time
4 5
Id    size    width    length
1    10    25    20,5
2    12    30    35,2
3    12    10    12,03
4    13    40    15,03
5    11    20    10,23
5
single size
single width
single Id
all length
single volume
6 4
Leng    sum    median    max    min    sd
1    10,5    35    40    20,3    11,2
2    12,5    20    50    23,14    1,2
3    14,5    10    30    51,92    0,11
4    16,5    15    20    14,5    111,2
8
all Leng
single sum
all median
single max
all min
all var
single sd
single var
1 6
Number
8,4
2,2
1,4
9,7
5,5
1,3
2
all Number
single Number

OUT:
$ all minute
Id	minute	hour	pressure	temperature
6	12	25	30,05	72,13
4	15	10	20,4	20,14
2	20	15	12,5	11,3
$ single hour
hour
10
15
25
$ single temperature
temperature
11,3
20,14
72,13
$ all time
invalid column name: time
$ single size
size
10
11
12
12
13
$ single width
width
10
20
25
30
40
$ single Id
Id
1
2
3
4
5
$ all length
Id	size	width	length
5	11	20	10,23
3	12	10	12,03
4	13	40	15,03
1	10	25	20,5
2	12	30	35,2
$ single volume
invalid column name: volume
$ all Leng
Leng	sum	median	max	min	sd
1	10,5	35	40	20,3	11,2
2	12,5	20	50	23,14	1,2
3	14,5	10	30	51,92	0,11
4	16,5	15	20	14,5	111,2
$ single sum
sum
10,5
12,5
14,5
16,5
$ all median
Leng	sum	median	max	min	sd
3	14,5	10	30	51,92	0,11
4	16,5	15	20	14,5	111,2
2	12,5	20	50	23,14	1,2
1	10,5	35	40	20,3	11,2
$ single max
max
20
30
40
50
$ all min
Leng	sum	median	max	min	sd
4	16,5	15	20	14,5	111,2
1	10,5	35	40	20,3	11,2
2	12,5	20	50	23,14	1,2
3	14,5	10	30	51,92	0,11
$ all var
invalid column name: var
$ single sd
sd
0,11
1,2
11,2
111,2
$ single var
invalid column name: var
$ all Number
Number
1,3
1,4
2,2
5,5
8,4
9,7
$ single Number
Number
1,3
1,4
2,2
5,5
8,4
9,7
*/

