// Aleksandra Gomolka - 8
import java.util.Scanner;
 /* Celem zadania bylo wyznaczenie liczby mozliwych trojek indeksow w tablicy zawierajacej odcinki,
 z ktorych mozna zbudowac trojkat. Innymi slowy nalezalo wyznaczyc liczbe trojkatow, ktore moga zostac stworzone z podanych odcinkow.
  Nalezalo uwglednic przypadki, w ktorych kazdy odcinek mogl wystapic tylko raz w budowanym trojkacie,
  moglo byc jednak wiele odcinkow o tej samej dlugosci. Mogl wystapic rowniez przypadek, gdzie otrzymamy kilka trojkatow o
  takich samych dlugosciach bokow, w takim przypadku nalezalo zliczyc wszystie te trojkaty.*/

public class Source { // Implementacja glownej klasy.
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;

    public static void main(String[] args) {
        int numberOfsets; // zmienna okreslajaca ilosc zestawow danych podanych na konsolowe wejscie, jest to dodatnia liczba calkowita.
        int numberOfdata; // zmienna okreslajaca  ilosc odcinkow danych wczytywanego zestawu.

        /* Wczytywanie danych odbywa sie za pomoca petli for. Wczytywanie wykonujemy tyle razy ile zestawow okreslilismy
           w zmiennej numberOfsets, poniewaz po wczytaniu liczby zestawow nastepuje wypelnienie tablic danymi.
           Tablic jest tyle ile zestawow okreslilismy.*/

        numberOfsets = scanner.nextInt(); // wczytywanie liczby okreslajacej ilosc zestawow danych
        for (int k = 1; k <= numberOfsets; k++) {
            numberOfdata = scanner.nextInt(); // wczytywanie liczby okreslajacej ilosc odcinkow danych wczytywanego zestawu

            int[] Tab = new int[numberOfdata]; // implementacja tablicy

            for (int i = 0; i < numberOfdata; i++) { // wypelnianie tablicy danymi
                Tab[i] = scanner.nextInt();
            }
            makeATriangle(Tab, numberOfdata); // wywolanie metody ktora w wyniku daje liczbe mozlowych trojkatow jakie mozna otrzymac z podanych odcinkow
        }
    }

    // Implementacja algorytmu sortowania przez wstawianie (ang. InsertionSort). Zlozonosc czasowa tego algorytmu to O(n2).

    /* Poczatkowo algorytm ustawia znacznik po pierwszym elemencie. Tym samym tworzymy dwie sekcje, posortowana i nieposortowana.
     Nastepujace czynnosci powtarzamy do momentu az nieposortowana sekcja (znajdujaca sie bardziej na prawo) bedzie pusta.
     Wybieramy pierwszy nieposortowany element (i = 1). Przesuwamy inne elementy w prawo zgodnie z warunkiem znajdujacym sie w petli while,
     aby utworzyc wlasciwa kolejnosc oraz przesuwamy nieposortowany element w odpowiednie miejsce (arr[j + 1] = tmp).
     Nastepnie przesuwamy znacznik o jeden element w prawo.*/

    public static void InsertionSort(int[] arr, int length) {
        int i, j, tmp;
        for (i = 1; i < length; i++) { // petla zewnetrzna, i to pierwszy nieposortowany element
            tmp = arr[i]; // kopiujemy wyrozniony element do zmiennej tmp
            j = i - 1; // sortowanie zaczynamy od elementu i-1
            while (j >= 0 && arr[j] > tmp) {
                arr[j + 1] = arr[j]; // przesuwamy element znajdujacy sie pod indeksem j w prawo
                j -= 1; //  sprawdzenie poprzedniego elementu
            }           // przypadek gdzie (j==-1) lub (tmp >= a[j])
            arr[j + 1] = tmp; // wstawianie wyroznionego elementu w odpowiednie miejsce
        }
    }

    // Implementacja metody ktora zlicza liczbe mozliwych trojkatow uzyskanych z podanych odcinkow

    /* Metoda poczatkowo sortuje rosnaco podane dane w tablicy. Do tego wykorzystano metode InsertionSort(). Liczba mozliwych trojkatow poczatkowo
     wynosi zero. Na poczatku iteracji zmienna "a" ustawia sie na poziomie indeksu 0, zmienna "b" jest o jeden wieksza od a
     (indeks drugi znajdujacy sie pomiedzy indeksem pierwszym a trzecim), zmienna "c" jest o 2 wieksza od a (indeks trzeci, znajdujacy sie najbardziej na prawo).
     W kolejnych iteracjach, zmienna "a" pozostaje na swoim miejscu a przesuwaja sie kolejno zmienna "c" a pozniej
     zmienna "b". Po wszystkich mozliwych przejsciach zmiennych "b" oraz "c", zmienna "a" zwieksza sie o jeden i ponownie rozpoczynamy ustawianie
     pozostalych zmiennych w odpowiednie miejsca. Szukamy trzeciego elementu, mniejszego niz suma dwoch pozostalych elementow (T[c] < T[a] + T[b]).
     Nalezy zwrocic uwage na to ze jezeli T[a] + T[b-1] jest wieksze niz T[c], to wtedy T[a] + T[b] rowniez musi byc
     wieksze nic T[c], poniewaz tablica jest posortowana rosnaco. Wszystkie elementy miedzy T[b + 1] a T[c-1] moga utworzyc trojkat z T[a] i T[b].
     Wyszczegolniony zostal przypadek gdzie T[a] = 0, poniewaz wtedy automatycznie w wyniku otrzymamy wartosc poczatkowa zmiennej numberOftriangle wynoszaca zero.
     Dla przypadkow gdzie dane sa ujemne rowniez w wyniku otrzymamy wartosc poczatkowa zmiennej numberOftriangle wynoszaca zero, taka sytuacje wyklucza nam warunek w petli
     while (T[c] < T[a] + T[b]), poniewaz z uwagi na rosnace dane ten warunek nie bedzie spelniony. Inkrementacja "c" nastepuje tylko wtedy gdy spelniony
     jest warunek petli while, w przeciwnym wypadku zmienna "c" nie ulega inkrementacji a przy przesuwaniu zmiennej "b" o jeden rozpoczynamy
     sprawdzanie od ostatniej wartosci "c" na ktorej pozostalismy.
     Zlozonosc obliczeniowa rozwiazania to O(n^2), poniewaz zauwazmy, ze c jest inicjalizowane tylko raz w najbardziej zewnetrznej petli.
     Najbardziej wewnetrzna petla jest wykonywana co najwyzej O(n) dla kazdej iteracji najbardziej zewnetrznej petli, poniewaz c zaczyna sie od a + 2 i rosnie do n
     dla wszystkich wartosci b. Poniewaz istnieje n takich iteracji, dlatego zlozonosc obliczeniowa wynosi O(n^2). Petla while nie bedzie iterowac wszystkich elementow za kazdym razem.
     Innymi slowy nie dokonujemy ponownej inicjalizacji wartosci c dla nowej wybranej wartosci b, dla tego samego a.*/

    public static void makeATriangle(int[] T, int n) {

        InsertionSort(T, n);
        int numberOftriangle = 0; // zmienna okreslajaca liczbe mozliwych trojkatow
        for (int a = 0; a < n - 2; a++) {
            int c = a + 2; // zainicjowanie indeksu trzeciego elementu znajdujacego sie najbardziej na prawo
            for (int b = a + 1; b < n - 1; b++) {
                if (T[a] != 0) {
                    while (c < n && T[c] < T[a] + T[b]) {
                        c += 1;
                        numberOftriangle += (c - 1) - b; // wyliczenie obecnie calkowitej liczby mozliwych trojkatow. Jeden jest odejmowane od c, poniewaz c jest zwiekszane o jeden w petli while
                    }
                }
            }
        }
        System.out.println("Num_triangles= " + numberOftriangle);
    }
}

/* Przykladowe testy:
11
4
4 7 4 7
5
0 0 0 0 0
2
3 4
6
3 4 3 4 5 5
4
2 2 2 2
4
1 2 1 3
6
2 3 2 3 4 4
5
-1 -2 -3 -4 -5
5
2 5 6 7 9
6
1 3 5 8 9 10
6
2 5 3 3 4 4

Num_triangles= 4
Num_triangles= 0
Num_triangles= 0
Num_triangles= 20
Num_triangles= 4
Num_triangles= 0
Num_triangles= 18
Num_triangles= 0
Num_triangles= 6
Num_triangles= 7
Num_triangles= 18
*/

