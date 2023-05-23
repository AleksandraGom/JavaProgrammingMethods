// Aleksandra Gomolka - 8

import java.util.Scanner;
/*Celem zadania jest potasowanie piosenek w takiej kolejnosci aby piosenki z pierwszej i drugiej plyty wystepowaly po sobie.
Plyty moga miec tyle samo utworow, albo pierwsza plyta ma o jeden utwor wiecej i jest on w efekcie grany na koncu. Dodatkowo nalezy
wyznaczyc najdluzszy przedrostek sposrod nazw wszystkich piosenek z obu wybranych plyt. Przy okreslaniu najdluzszego prefixu
nalezy rozroznic male i duze litery, a gdy przedrostek jest pusty, nalezy wypisac pusta linie.*/

public class Source { // Implementacja glownej klasy.
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;
    public static String longestCommonPrefix; // zmienna przechowujaca najdluzszy przedrostek

    public static void main(String[] args) {

        int numberOfsets; // zmienna okreslajaca liczbe zestawow danych, ktore pojawiaja sie na wejscie
        int numberOfsongs; // zmienna przechowujaca sumaryczna liczbe piosenek na obu plytach

        numberOfsets = scanner.nextInt(); // wczytywanie liczby zestawow danych

        for (int k = 1; k <= numberOfsets; k++) {
            numberOfsongs = scanner.nextInt(); // wczytywanie sumarycznej liczby piosenek
            String songArray[] = new String[numberOfsongs]; //zmienna tablicowa przechowujaca nazwy piosenek, kolejno z pierwszej i drugiej plyty

            for (int i = 0; i < numberOfsongs; i++) {
                songArray[i] = scanner.next(); // wstawianie piosenek do tablicy
            }
            longestCommonPrefix = songArray[0]; // inicjalizacja zmiennej przechowujacej najdluzszy przedrostek

            shufflingSongs(songArray,0,songArray.length-1); // wywolanie metody, ktora tasuje piosenki i szuka najdluzszego przedrostka
            for (int j = 0; j < songArray.length; j++) {
                System.out.print(songArray[j] + " ");
            }
            System.out.println();
            System.out.println(longestCommonPrefix); // wypisanie najdluzszego przedrostka
        }
    }

    /*Implementacja metody, ktora tasuje piosenki oraz szuka najdluzszego prefixu. Metoda posiada ogolna zlozonosc czasowa rowna O(N log(N)),
      poniewaz opiera sie na algorytmie dziel i zwyciezaj. Metoda dzieli duze problemy na mniejsze problemy a dokladniej wejsciowa tablice dzieli
      na mniejsze tablice w miare mozliwosci o rownych czesciach. Rekurencje wykonujemy poczatkowo na jednej polowie a pozniej na drugiej polowie
      do czasu kiedy uzyskamy prawidlowo potasowane elementy. Kazda operacja partycjonowania (dzielenia na czesci) dokonuje O(N) operacji (jeden przebieg tablicy).
      Kazde partycjonowanie dzieli tablice na dwie czesci co daje lacznie logN operacji. W sumie otrzymujemy O(N*logN) operacji.
      Innymi slowy mowiac kazde wywolanie funkcji "shufflingSongs" dziala w zlozonosci O(N). W kolejnym etapie, rozmiar tablicy jest redukowany,
      poniewaz dzielimy tablice na dwie czesci. Automatycznie czas rowniez zredukuje sie do O(logN). W kazdym etapie wyszukiwania liniowego,
      przed przejsciem do nastepnego etapu, rozmiar tablicy redukuje sie do polowy (dzieli sie na dwie czesci) a ostateczna zlozonosc programu wynosi O(N*log(N)).
      Kolejnym etapem jest odnalezienie najdluzszego przedrostka. Ta czesc metody posiada zlozonosc czasowa O(N),
      poniewaz porownujemy wszystkie ciagi ze soba (wszystkie nazwy piosenek). W najgorszym przypadku, czyli w sytuacji gdy wszystkie piosenki
      sa takie same (maja taka sama nazwe) nalezy dokonac N porownan. Zlozonosc pamieciowa tej czesci zadania wynosi O(1), poniewaz wykorzystalismy
      tylka stala dodatkowa przestrzen. Fragment swapowania (metoda swap) dziala liniowo a w programie nie zarezerwowano zadnej dodatkowej struktury
      danych o zlozonosci pamieciowej wiekszej niz stala.*/
    public static void shufflingSongs (String[] array, int startPlaylist, int endPlaylist){

        int length = endPlaylist - startPlaylist + 1;// dlugosc tablicy, liczba elementow biezacej tablicy
        int firstSongSecondAlbum = checkParityLength(endPlaylist + startPlaylist + 1); // zmienna okreslajaca poczatek drugiej plyty
        int lastSongFirstAlbum = firstSongSecondAlbum - 1; // zmienna okreslajaca koniec pierwszej plyty
        /*Zmienne "firstSongSecondAlbum" oraz "lastSongFirstAlbum" okreslaja w pewien sposob przedzialy tasowania, poniewaz za kazdym wywolaniem
        metody dzielimy tablice na mniejsze podtablice i na nich wykonujemy tasowanie, czyli zmienianie kolejnosci elementow (swap).*/

        if (length > 2) { // jezeli dlugosc biezacej tablicy jest wieksza od 2, tzn, ze nalezy dokonac tasowania
            // jezeli dlugosc tablicy na wejsciu jest mniejsza, badz rowna 2, nalezy pominac tasowanie i przejsc do szukania prefixu
            if (firstSongSecondAlbum % 2 != 0) { // jezeli reszta z dzielenia zmiennej okreslajacej poczatek drugiej plyty przez dwa nie jest rowna 0,
                // nalezy sprawdzic czy tablica wejsciowa miala parzysta, czy nieparzysta liczbe elementow
                if (length % 2 != 0) { // jezeli sufit z dzielenie przez dwa dlugosci wejsciowej tablicy uzyskany jest z tablicy o nieparzystej liczbie elementow
                    for (int i = lastSongFirstAlbum; i < endPlaylist; i++) { // przesuwam ostatni element pierwszej plyty na koniec tablicy
                        swap(array, i, i + 1);
                    }
                } else { // jezeli sufit z dzielenie przez dwa dlugosci wejsciowej tablicy uzyskany jest z tablicy o parzystej liczbie elementow
                    for (int i = lastSongFirstAlbum; i < endPlaylist - 1; i++) { // przesuwam ostatni element pierwszej plyty na przedostatnia pozycje tablicy
                        swap(array, i, i + 1);
                    }
                }
                endPlaylist = endPlaylist - 2; // zmniejszam tablice o 2, poniewaz dwa ostatnie elementy sa poprawnie ustawione
                length = endPlaylist - startPlaylist + 1; // aktualizacja dlugosci tablicy
                firstSongSecondAlbum = checkParityLength(endPlaylist + startPlaylist + 1); // aktualizacja zmiennej okreslajacej poczatek drugiej plyty
                lastSongFirstAlbum = firstSongSecondAlbum - 1; // aktualizacja zmiennej okreslajacej koniec pierwszej plyty
            }
            int halfCeilMid = (firstSongSecondAlbum + startPlaylist) / 2; // zmienna okreslajaca indeks polowy podtablicy utworzonej przez zmienna "firstSongSecondAlbum"
            // zmienna wykorzystywana jest do ustalenia przedzialow tasowania
            // Zamiana dotyczy n elementow po lewej stronie srodka na n elementow po prawej stronie srodka, gdzie n jest wyznaczane w zaleznosci od wartosci "halfCeilMid" oraz "firstSongSecondAlbum"
            int k = firstSongSecondAlbum; // tasowanie odbywa sie poprzez zmiane koncowych elementow pierwszej plyty z poczatkowymi elementami drugiej plyty
            for (int i = halfCeilMid; i < firstSongSecondAlbum; i++, k++) {
                swap(array, i, k);
            }
        }
        if (length <= 4) { // jezeli po tasowaniu dlugosc tablicy/podtablicy jest mniejsza rowna 4, nalezy odnalezc najdluzszy przedrostek i zakonczyc program
            for (int i = 1; i < array.length; i++) {
                while (!array[i].startsWith(longestCommonPrefix)) {
                    // metoda "startsWith" zwraca wartosc logiczna (prawda lub falsz) w zaleznosci, czy dany ciag zaczyna sie od okreslonej litery lub slowa
                    longestCommonPrefix = longestCommonPrefix.substring(0, longestCommonPrefix.length() - 1);
                    /*metoda "substring" zwraca nowy ciag znakowy zawierajacy podciag danego ciagu od okreslonego start (poczatek ciagu znakowego)
                    do end (koniec ciagu znakowego). W przypadku naszego zadania porownujemy kazdy element tablicy z pierwszym elementem tablicy, jezeli
                    kolejne elemnty tablicy nie sa rowne pierwszemu, za pomoca metody "substring" nalezy zmniejszyc dlugosc obecnie najluzszego prefixu
                    i ponownie rozpoczac sprawdzanie. W przypadku gdy szukany przedrostek jest pusty, nalezy wypisac pusta linie. */
                    if (longestCommonPrefix.isEmpty()) { longestCommonPrefix = "";return; } // jezeli szukany przedrostek jest pusty
                }
            }
            return;
        }

        /*Tablica zostaje podzielona na dwie czesci, a powyzsze kroki sa powtarzane dla obu czesci rekurencyjnie.*/
        shufflingSongs(array,startPlaylist, lastSongFirstAlbum); // wywolanie rekurenycjne metody "shufflingSongs" dla lewej podtablicy
        shufflingSongs(array,firstSongSecondAlbum, endPlaylist); // wywolanie rekurencyjne metody "shufflingSongs" dla prawej podtablicy
    }

    /*Implementacja metody, ktora sluzy do zmiany elementow na okreslonych pozycjach.
    Do zmiennej tymczasowej przypisujemy pierwszy z elementow okreslonych jako argument funkcji "swap", po to aby moc na jego
    miejsce wstawic drugi element podany jako argument ten funkcji, bez jego utraty.*/
    public static void swap(String[] arr,int m, int n){
        String tmp = arr[m];
        arr[m] = arr[n];
        arr[n] = tmp;
    }

    /*Implementacja metody, ktora oblicza sufit z dzielenia przez dwa. Jezeli wynik dzielenia przez dwa jest liczba calkowita oznacza
    to, ze jest to szukana wartosc. Jezeli wynik dzielenia przez dwa nie jest liczba calkowita, nalezy do niej dodac 1. Otrzymany wynik
    okresla poczatek drugiej plyty.*/
    public static int checkParityLength (int len) {
        int ceilOfMid; // zmienna przechowujaca sufit z dzielenia przez dwa
        if (len % 2 == 0) { ceilOfMid = (len/2); }
        else { ceilOfMid = (len/2) + 1; }
        return ceilOfMid;
    }
}
/* Przykladowe testy:
IN:
8
4
Power1 power2 Power3 Power4
5
Feel1 Feel2 Feel3 Feel4 Feel5
8
pios1 piose2 pios3 pios4 pios5 pios6 Pios7 Pios8
10
Pam12 Pam13 Pam2 Pam3 Pam14 Pam15 Pam4 Pam5 Pam16 Pam6
2
Piosenka1 piosenka2
6
Kiel1 Kiel2 Kiel3 Kiem4 Kiem5 Kiem6
9
a1 a2 a3 a4 a5 b1 b2 b3 b4
7
xyla1 xyla2 xyla3 xyla4 xyLa5 xyLa6 xyLa7

OUT:
Power1 Power3 power2 Power4

Feel1 Feel4 Feel2 Feel5 Feel3
Feel
pios1 pios5 piose2 pios6 pios3 Pios7 pios4 Pios8

Pam12 Pam15 Pam13 Pam4 Pam2 Pam5 Pam3 Pam16 Pam14 Pam6
Pam
Piosenka1 piosenka2

Kiel1 Kiem4 Kiel2 Kiem5 Kiel3 Kiem6
Kie
a1 b1 a2 b2 a3 b3 a4 b4 a5

xyla1 xyLa5 xyla2 xyLa6 xyla3 xyLa7 xyla4
xy
*/




