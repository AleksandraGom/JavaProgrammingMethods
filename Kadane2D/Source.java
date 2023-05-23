// Aleksandra Gomolka - 8
import java.util.Scanner;

/* W celu wyznaczenia maksymalnej podtablicy 2D o najmniejszej liczbie elementow skorzystano z algorytmu Kadane'a, ktory
docelowo wykorzystywany jest do tablicy jednowymiarowej. Algorytm zmodyfikowano i rozszerzono aby spelnial warunki zadania.*/

public class Source { //implementacja glownej klasy
    /*do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util.
    Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;*/
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfsets; //zmienna okreslajaca ilosc zestawow danych podanych na konsolowe wejscie, jest to dodatnia liczba calkowita
        int no; // zmienna okreslajaca numer zestawu (od 1)
        String sign; // zmienna pozwalajaca wpisac znak ":"
        int numberOfrows; // zmienna okreslajaca liczbe wierszy tablicy
        int numberOfcolumns; // zmienna okreslajaca liczbe kolumn tablicy
        int valueCounter = -1; // zmienna okreslajaca najwieksza wartosc tablicy
        int valueRows = 0; // zmienna okreslajaca indeks wiersza obecnie najwiekszej wartosci w tablicy
        int valueColumns = 0; // zmienna okreslajaca indeks kolumny obecnie najwiekszej wartosci w tablicy
        // zmienne: valueCounter, valueRows oraz valueColumns zostana opisane w miejscu ich wykorzystywania

        /* Wczytywanie danych odbywa sie za pomoca petli for. Wczytywanie wykonujemy tyle razy ile zestawow okreslilismy
           w zmiennej numberOfsets, poniewaz po wczytaniu liczby zestawow nastepuje wypelnienie tablic danymi.
           Tablic jest tyle ile zestawow okreslilismy.*/

        numberOfsets = scanner.nextInt(); // wczytywanie liczby okreslajacej liczbe zestawow danych
        for (int k = 1; k <= numberOfsets; k++) {
            no =scanner.nextInt(); // wczytywanie liczby okreslajacej numer zestawu
            sign = scanner.next(); // wczytywanie znaku ":"
            numberOfrows = scanner.nextInt(); // wczytywanie liczby okreslajacej liczbe wierszy tablicy
            numberOfcolumns = scanner.nextInt(); // wczytywanie liczby okreslajacej liczbe kolumn tablicy

            /* Ponizej zaimplementowano dwie tablice. Pierwsza tablica o nazwie newtab jest tablica do ktorej wpisywane sa wartosci
               przez konsolowe wejscie. W zadaniu nalezy obliczyc sume maksymalnej podtablicy o najmniejszej liczbie elementow
               wedlug wzoru: ms(i, j, k, l) = 3*D+2*U, gdzie D to suma dodatnich elementow, U suma ujemnych elementow.
               W celu ulatwienia obliczen, juz na etapie wpisywania danych do tablicy elementy ujemne pomnozono razy 2
               a elementy dodatnie razy 3. Dzieki temu mozemy wyznaczyc sume maksymalnej podtablicy na podstawie zmodyfikowanej tablicy
               nazwanej a */
            int[][] newtab = new int[numberOfrows][numberOfcolumns]; // implementacja tablicy
            int[][] a = new int[numberOfrows][numberOfcolumns]; // implementacja tablicy

            /* Wypelnienie tablicy odbywa sie w nastepujacej kolejnosci. Poczatkowo wypelniamy wszystkie kolumny pierwszego wiersza.
              Po wypelnieniu calego wiersza pierwszego przechodzimy do kolejnego wiersza i tak do momentu
              wypelnienia calej tablicy*/

            for (int i = 0; i < numberOfrows; i++) {
                for (int j = 0; j < numberOfcolumns; j++) {
                    newtab[i][j] = scanner.nextInt();
                    if (newtab[i][j] < 0) {a[i][j] = newtab[i][j] * 2;}
                    else {a[i][j] = newtab[i][j] * 3;}

                    /* Na tym etapie wykonujemy wyszukiwanie najwiekszej wartosci w tablicy. Taka operacja jest pomocna w przypadku
                      wystapienia szczegolnych przypadkow takich jak: wszystkie elementy tablicy sa ujemne lub wystapienie
                      tablicy skladajacej sie z liczb ujemnych i zer. Juz na etapie wpisywania danych do tablicy sprawdzamy
                      czy wpisywana wartosc jest wieksza od valueCounter, ktore zostalo okreslone na wartosc poczatkowa rowna -1
                      Zauwazmy, ze jezeli tablica sklada sie z wartosci ujemnych zmienne
                      valueCounter, valueRows oraz valueColumns pozostaja takie jak domyslnie ustawione na poczatku programu*/

                    /* Na etapie wpisywania danych to tablicy sprawdzamy czy wpisywana wartosc jest wieksza od valueCounter.
                       Jezeli tak to za najwieksza wartosc tablicy wstawiamy obecny (obecnie sprawdzany) element tablicy,
                       za indeks wiersza najwiekszej wartosci w tablicy wstawiamy obecny indeks wiersza,
                       za indeks kolumny najwiekszej wartosci w tablicy wstawiamy obecny indeks kolumny.*/

                    if (a[i][j] > valueCounter) {
                        valueCounter = a[i][j];
                        valueColumns = j;
                        valueRows = i;
                    }
                }
            }
            /* Po zakonczeniu wpisywania danych do tablicy w zaleznosci od wyniku valueCounter program wykona inna operacje.
              W przypadku gdy wszystkie elementy tablicy sa ujemne, valueCounter przyjmuje wartosc -1.
              W przypadku gdy najwieksza wartoscia jest zero, valueCounter przyjmuje wartosc 0.
              W kazdym innym przypadku uruchamiana jest metoda KadaneAlgorithm()
              ktora liczy sume maksymalnej podtablicy i wyswietla konkretny wynik. W zaleznosci od przypadku na konsolowe
              wyjscie wyswietlane sa dane wyszczegolnione w tresci zadania. Po wyswietleniu wyniku dla konkretnej tablicy
              nastepuje powrot wartosci valueCounter do wartosci domyslnej, poniewaz nastepuje ponowne wpisywanie danych
              dla kolejnej tablicy.*/
            if (valueCounter == 0) {
                System.out.println(no + ": " + "n=" + numberOfrows + " m=" + numberOfcolumns + ", " + "ms= " + 0 + ", " +
                        "mstab= a[" + valueRows + ".." + valueRows + "]" + "[" + valueColumns + ".." + valueColumns + "]");
            }
            else if (valueCounter == -1) {
                {System.out.println(no + ": " +"n=" + numberOfrows + " m=" + numberOfcolumns + ", " + "ms= "+ 0 + ", "+
                        "mstab is empty");}
            } else {
                KadaneAlgorithm(a, no, numberOfrows, numberOfcolumns);
            }
            valueCounter = -1;
        }
    }

    // implementacja metody wykorzystujacej algorytm Kadane

    public static void KadaneAlgorithm(int matrix[][], int number, int Rows, int Columns) {
        int[] temp = new int[Rows]; // zaimplementowanie tymczasowej tablicy jednowymiarowej w wielkosci liczbie wierszy tablicy matrycowej
        int TmpSuma = 0; /* zaimplementowanie sumy tymczasowej, wykorzystywana jest podczas liczenia sumy maksymalnej podtablicy
                          dla tablicy jednowymiarowej, z wykorzystaniem tablicy tymczasowej */
        int Suma = 0; /* zmienna do ktorej przypisywana jest tymczasowa (bazujaca na tablicy tymczasowej) maksymalna suma po przejsciu
                         po wszystkich wierszach tablicy tymczasowej. Nastepnie taka suma porownywana jest z obecnie maksymalna suma podtablicy*/
        int TmpMaxSuma = -1; /* implementacja zmiennej okreslajacej tymczasowa, maksymalna sume po przejsiu po wszystkich wierszach tablicy tymczasowej*/
        int MaxSuma = 0; // zmienna okreslajaca maksymalna sume podtablicy
        int Start = 0; // zmienna okreslajaca indeks poczatkowy maksymalnej podtablicy, wiersz poczatkowy, zmienna bazujaca na tablicy tymczasowej
        int End = 0; // zmienna okreslajaca indeks koncowy maksymalnej podtablicy, wiersz koncowy, zmienna bazujaca na tablicy tymczasowej
        int TmpStart = 0; /* zmienna okreslajaca tymczasowy wiersz poczatkowy, zmienna bazujaca na tablicy tymczasowej, jednak jest
                             wykorzystywana w przypadku gdy tymczasowa suma jest mniejsza, badz rowna zero, poniewaz wtedy musimy zwiekszyc
                             indeks poczatkowy o jeden, kolokwialnie mowiac pominac dana wartosc i rozpoczac indeksowanie od kolejnego wiersza.
                             Dopiero po spelnieniu odpowiednich kryteriow dana wartosc przypisana do tej zmiennej moze stac sie
                             wierszem poczatkowym bazujacym na tablicy tymczasowej (Start)*/
        int TmpLeft = 0; // zmienna okreslajaca indeks poczatkowy maksymalnej podtablicy, kolumne poczatkowa, zmienna bazujaca na tablicy tymczasowej
        int TmpRight = 0; // zmienna okreslajaca indeks koncowy maksymalnej podtablicy, kolumne koncowa, zmienna bazujaca na tablicy tymczasowej
        int MaxLeft = 0; // zmienna okreslajaca ostateczny indeks poczatkowy maksymalnej podtablicy, kolumne poczatkowa
        int MaxRight = 0; // zmienna okreslajaca ostateczny indeks koncowy maksymalnej podtablicy, kolumne koncowa
        int MaxBottom = 0; // zmienna okreslajaca ostateczny indeks koncowy  maksymalnej podtablicy, wiersz koncowy (dolny)
        int MaxTop = 0; // zmienna okreslajaca ostateczny indeks poczatkowy maksymalnej podtablicy, wiersz poczatkowy (gorny)

        /* Algorytm przechodzeni kolejno po kolumnach. Poczatkowo ustawiamy indeks lewy (poczatkowy)
         na kolumnie numer 0 (pierwsza kolumna). W tym czasie wypelniamy tablice tymczasowa zerami. Taka czynnosc wykonujemy po kazdym
         przejsciu indeksu lewego na kolejna kolumne, swiadczy to o ponownym rozpoczeciu szukania maksymalnej podtablicy na mniejszym juz zakresie.
         kolumn. Indeks prawy, rowniez ustawiamy na pierwszej kolumnie. Indeks prawy przesuwa sie po wszystkich kolumnach, po przejsciu
         wszystkich kolumn nastepuje przesuniecie indeksu lewego na kolejna kolumne a indeks prawy ustawia sie na ta sama kolumne co indeks lewy.
         Przechodzac po kolumnie pobieramy wartosci z kolejnych wierszy i wypelniamy nimy odpowiednie wiersze w tablicy tymczasowej.
         Nastepnie na tablicy tymczasowej uruchamiany jest algorytm Kadane dla tablicy jednowymiarowej. Wyznaczajac tymczasowa, maksymalna sume,
         bazujaca na tablicy pomocniczej musimy uwzglednic kilka warunkow: tymczasowa suma jest mniejsza, badz rowna zero; tymczasowa suma
         jest wieksza od obecnie wystepujacej, maksymalnej sumy tymczasowej oraz przypadek w ktorym suma tymczasowa jest rowna obecnie
         wystepujacej,maksymalnej sumie tymczasowej.W ostatniej sytuacji nalezy wybrac podtablice o najmniejszej liczbie elementow.
         Jezeli jednak podtablice maja taka sama sume oraz taka sama liczbe elementow nalezy uwzglednic ciag leksykograficznie najmniejszy.
         Ustawiajac dane dotyczace maksymalnej podtablicy (tymczasowej), zastepujemy dotychczasowe dane nowymi. Po przejsciu kolumny tablicy tymczasowej
         sprawdzam czy otrzymana maksymalna suma tymczasowa jest wieksza od obecnie maksymalnej sumy. Ponownie musze uwzglednic przypadek
         w ktorym sumy sa rowne, nastepuje to w podobny sposob jak opisano powyzej.Po wyznaczeniu ostatecznych danych, obecnie wystepujace dane zastepuje nowymi.
         Nastepnie indeks prawy przesuwa sie na kolejna kolumne. Do obecnie wystepujacych wartosci w tabeli tymczasowej dodawane sa wartosci
         z kolumny na ktora przesunal sie indeks prawy oraz ponownie uruchamiany jest algorytm Kadane.
         Po przejsciu po calej tablicy w wyniku otrzymujemy sume maksymalnej podtablicy wraz z wszystkimi potrzebnymi danymi.*/

        for (int left = 0; left < Columns; left++) {
            for (int i = 0; i < Rows; i++) {
                temp[i] = 0;
            }
            for (int right = left; right < Columns; right++) {
                TmpSuma = 0; TmpStart = 0;
                for (int i = 0; i < Rows; i++) {
                    temp[i] += matrix[i][right];
                    TmpSuma += temp[i];

                    if (TmpSuma <= 0) {
                        TmpSuma = 0;
                        TmpStart = i + 1;

                    }
                    else if (TmpSuma > TmpMaxSuma) {
                        TmpMaxSuma = TmpSuma;
                        Start = TmpStart;
                        End = i;
                        TmpRight = right;
                        TmpLeft = left;

                    }
                    else if (TmpSuma == TmpMaxSuma) {
                        if (((i-TmpStart+1) * (right-left+1)) < ((End-Start+1) * (TmpRight-TmpLeft+1))) {

                            Start = TmpStart;
                            End = i;
                            TmpRight = right;
                            TmpLeft = left;
                        }
                        else if ((((i-TmpStart+1) * (right-left+1)) == ((End-Start+1) * (TmpRight-TmpLeft+1)))
                                && (TmpStart < Start || i < End|| left < TmpLeft || right < TmpRight)) {
                            Start = TmpStart;
                            End = i;
                            TmpRight = right;
                            TmpLeft = left;
                        }
                    }
                }
                Suma = TmpMaxSuma;
                if (Suma > MaxSuma) {

                    MaxSuma = Suma;
                    MaxLeft = TmpLeft;
                    MaxRight = TmpRight;
                    MaxTop = Start;
                    MaxBottom = End;
                }
                else if (Suma == MaxSuma) {
                    if (((End-Start+1) * (TmpRight-TmpLeft+1)) < ((MaxBottom-MaxTop+1) * (MaxRight-MaxLeft+1))) {

                        MaxLeft = TmpLeft;
                        MaxRight = TmpRight;
                        MaxTop = Start;
                        MaxBottom = End;
                    }
                    else if ((((End-Start+1) * (TmpRight-TmpLeft+1)) == ((MaxBottom-MaxTop+1) * (MaxRight-MaxLeft+1)))
                            && (Start < MaxTop || End < MaxBottom || TmpLeft < MaxLeft || TmpRight < MaxRight)) {
                        MaxLeft = TmpLeft;
                        MaxRight = TmpRight;
                        MaxTop = Start;
                        MaxBottom = End;
                    }
                }
            }
        }

        System.out.println(number + ": " +"n=" + Rows + " m=" + Columns + ", " + "ms= "+ MaxSuma + ", "+
                "mstab= a[" + MaxTop + ".." + MaxBottom + "]" + "[" + MaxLeft + ".." + MaxRight + "]");
    }
}

/* Przykladowe testy:
11
1 : 2 5
0 0 0 0 0
0 0 0 0 0

2 : 2 5
1 1 -1 -1 0
1 1 -1 -1 7

3 : 2 5
0 -1 -1 1 1
3 -2 -2 1 1

4 : 2 5
0 -1 -1 6 0
6 -2 -2 0 0

5 : 1 3
0 0 0

6 : 2 5
-1 -2 -3 -1 -1
-2 -2 -2 -2 -2

7 : 1 6
-3 8 -5 9 -6 5

8 : 1 6
-1 -2 -2 0 -4 0

9 : 1 6
0 -2 -1 0 -2 -3

10 : 1 2
0 1

11 : 3 4
-10 3 6 7
-10 -11 -10 -11
-10 -11 -10 11

1: n=2 m=5, ms= 0, mstab= a[0..0][0..0]
2: n=2 m=5, ms= 25, mstab= a[0..1][0..4]
3: n=2 m=5, ms= 12, mstab= a[0..1][3..4]
4: n=2 m=5, ms= 24, mstab= a[0..1][0..3]
5: n=1 m=3, ms= 0, mstab= a[0..0][0..0]
6: n=2 m=5, ms= 0, mstab is empty
7: n=1 m=6, ms= 44, mstab= a[0..0][1..5]
8: n=1 m=6, ms= 0, mstab= a[0..0][3..3]
9: n=1 m=6, ms= 0, mstab= a[0..0][0..0]
10: n=1 m=2, ms= 3, mstab= a[0..0][1..1]
11: n=3 m=4, ms= 48, mstab= a[0..0][1..3]
*/
