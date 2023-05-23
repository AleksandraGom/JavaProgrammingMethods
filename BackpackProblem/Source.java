// Aleksandra Gomolka - 8
import java.util.Scanner;

/*Celem zadania jest rozwiazanie problemu pakowania plecaka. Innymi slowy mowiac nalezy znalezc sekwencje elementow, ktorych sumaryczna waga
  jest rowna pojemnosci plecaka a wybrane elementy dokladnie wypelniaja plecak. Wypisywany ciag musi byc pierwszym wzgledem porzadku
  leksykograficznego numerow swoich elementow. W celu rozwiazania problemu skorzystano z metody rekurencyjnej "rec_pakuj"
  oraz metody iteracyjnej "iter_pakuj" z wykorzystaniem stosu.*/

public class Source { // Implementacja glownej klasy
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;
    public static String solution = ""; // zmienna globalna przechowujaca szukany ciag, wykorzystana w metodzie "iter_pakuj"
    public static boolean find; // zmienna globalna okreslajaca, czy zostal juz odnaleziony szukany ciag
    public static int [] solutionTab; // zmienna globalna przechowujaca elementy szukanego ciagu
    public static int solutionSize; // zmienna globalna okreslajaca rozmiar tablicy globalnej przechowujacej elementy szukanego ciagu


    /*Implementacja metody, ktora w sposob rekurencyjny rozwiazuje problem pakowania plecaka opisany powyzej.*/
    public static void rec_pakuj (int[] arr, int currentSum, int index, int Sum) {
        if (currentSum == Sum) { // jezeli biezaca suma jest rowna szukanej sumie, jezli zostal znaleziony ciag spelniajacy warunki zadania
            find = true;
        }
        else if (currentSum > Sum) { // jezeli biezaca suma jest wieksza niz szukana suma
            return;
        }
        else if (index == arr.length) { // jezeli przeslismy juz wszystkie elementy, nie mamy wiecej elementow a nie znaleziono ciagu spelniajacego warunki zadania
            return;
        }
        else { // jezeli jeszcze nie przeszlismy wszystkich elementow i nie otrzymalismy szukanej sumy
            currentSum += arr[index]; // zwiekszamy biezaca sume o wartosc sprawdzanego elementu
            solutionTab[solutionSize] = arr[index]; // wlaczamy element do podzbioru, do tablicy wynikowej
            solutionSize++; // zwiekszamy rozmair tablicy globalnej przechowujacej szukane elementy
            rec_pakuj(arr, currentSum, index + 1, Sum); // ponowne wywolanie funkcji, aby pobrac i sprawdzic kolejny element
            if(find == true) { // jezeli po wykonaniu powyzszej funkcji zostal znaleziony ciag spelniajacy warunki zadania
                return;
            } // jezeli po wykonaniu powyzszej funkcji nie zostal znaleziony ciag spelniajacy warunki zadania
            currentSum -= arr[index]; // nie uwzgledniamy elementu do podzbioru, nie wlaczamy do tablicy wynikowej oraz zmniejszamy biezaca sume o wartosc sprawdzanego elementu
            solutionSize--; // zmniejszamy rozmair tablicy globalnej przechowujacej szukane elementy
            rec_pakuj(arr, currentSum, index + 1, Sum); // ponowne wywolanie funkcji, aby pobrac i sprawdzic kolejny element
            if(find == true) { // jezeli po wykonaniu powyzszej funkcji zostal znaleziony ciag spelniajacy warunki zadania
                return;
            }
        }
        return;
    }


    /*Implementacja metody, ktora w sposob iteracyjny rozwiazuje problem pakowania plecaka opisany powyzej.*/
    public static void iter_pakuj(int[] arr, int Sum) {
        if(arr.length == 0 || Sum == 0) return; // jezeli biezaca suma jest rowna 0 lub jezeli przeszlismy juz po wszytskich elementach a nie znalezlismy szukanego wyniku

        int index = 0; // poczatkowy indeks, poczatkowo pierwszy element
        stackData stack = new stackData(arr.length+1); // utworzenie nowego obiektu, stosu; stos przechowuje biezaca sume, biezacy indeks i uzyte elementy

        // dla kazdego zbioru danych wykonujemy "include" oraz "notInclude"
        data d1 = new data(arr.length); // utworzenie nowego obiektu
        d1.include(index, arr[index]); // zawieranie elementu, wlaczanie elementu do zbioru wynikowego; jako argumenty przekazuje indeks oraz wartosc przechowywana w tablicy pod tym indeksem

        data d2 = new data(arr.length); // utworzenie nowego obiektu
        d2.notInclude(index); // nie zawieranie elementu, nie uwzglednianie elementu w podzbiorze wynikowym

        // wkladam dane na stos
        stack.push(d2);
        stack.push(d1);

        while (!stack.IsEmpty()) { // dopoki stos nie jest pusty, to sciagam ze stosu wywolanie
            data d = stack.pop(); // sciagniete ze stosu wywolanie przypisuje do zmiennej "d"

            if(d.getSum() == Sum) { // jezeli biezaca suma jest rowna sumie szukanej to oznacza, ze znaleziono rozwiazanie, ciag spelniajacy warunki zadania
                solution = d.solutionToString(arr); // przypisanie rozwiazania do zmiennej typu String z wykorzystaniem metody "solutionToString"
                return;
            } // jezeli nie znaleziono rozwiazania, sprawdzamy dalej
            else if(d.getSum() < Sum && d.getIndex() < arr.length) { // // jezeli biezaca suma jest mniejsza od szukanej sumy oraz jezeli nie przeslismy jeszcze wszystkich elementow (indeks w drzewie jest mniejszy od konca tablicy)
                int nextIndex = d.getIndex(); // indeks jest powiekszany o 1 po zawieraniu lub nie zawieraniu elementu

                // ponizsze czesci kodu wykonuje ponownie, juz na sciagnietym ze stosu wywolaniu
                data d3 = d.copy();
                d3.include(nextIndex, arr[nextIndex]); // zawieranie elementu, wlaczanie elementu do zbioru wynikowego

                data d4 = d.copy();
                d4.notInclude(nextIndex); // nie zawieranie elementu, nie uwzglednianie elementu w podzbiorze wynikowym

                // wkladam dane na stos
                stack.push(d4);
                stack.push(d3);
            }
        }
    }

    public static void main(String[] args) {

        int numberOfsets; // zmienna okreslajaca liczbe zestawow danych
        int backpackCapacity = 0; // zmienna okreslajaca pojemnosc plecaka
        int numberOfelements; // zmienna okreslajaca liczbe elementow mogacych wypelnic plecak

        numberOfsets = scanner.nextInt(); // wczytywanie liczby okreslajacej liczbe zestawow danych

        for (int k = 1; k <= numberOfsets; k++) {
            backpackCapacity = scanner.nextInt(); // wczytywanie pojemnosci plecaka
            numberOfelements = scanner.nextInt(); // wczytywanie liczby elementow mogacych wypelnic plecak

            int[] Tab =  new int[numberOfelements]; // implementacja tablicy zawierajacej elementy mogace wypelnic plecak
            find =false; // implementacja zmiennej okreslajacej, czy zostal juz odnaleziony szukany ciag
            solutionTab = new int[numberOfelements]; // implementacja tablicy przechowujacej elementy szukanego ciagu
            solutionSize = 0; // implementacja zmienne okreslajacej rozmiar tablicy przechowujacej elementy szukanego ciagu


            for (int i = 0; i < numberOfelements; i++) { // wypelnianie tablicy danymi
                Tab[i] = scanner.nextInt();
            }

            /*Wywolanie metod. Jezeli wywolujac metode rec_pakuj w wyniku otrzymamy "BRAK", nie przeprowadzamy juz metody iter_pakuj.
            Metode "iter_pakuj" wywoluje tylko w przypadku gdy metoda "rec_pakuj" zwroci szukany wynik.*/
            rec_pakuj(Tab,0,0,backpackCapacity);
            if (find == true) {
                System.out.print("REC:  " + backpackCapacity + " =");
                for (int i = 0; i < solutionSize; i++) {
                    System.out.print(" " + solutionTab[i]);
                }
                System.out.println();
                iter_pakuj(Tab, backpackCapacity);
                System.out.println("ITER: " + backpackCapacity + " = " + solution);
                solution = "";
            }
            else if (find == false){
                System.out.println("BRAK");
            }
        }
    }
}

/*Implementacja klasy "data". */
class data {
    private int sum; // implementacja zmiennej przechowujacej biezaca sume na danym poziomie
    private int index; // implementacja zmiennej przechowujacej poziom wywolania (liczony od zera), liczba poziomow rowna jest liczbie elementow przekazanych na wejsciu
    private int[] solution; // implementacja zmiennej tablicowej przechowujacej rozwiazanie

    public data(int length) {
        this.sum = 0;
        this.index = 0;
        this.solution = new int[length]; // tablica "solution" jest dlugosci ilosci przekazanych elementow na wejscie
    }

    public data(int sum, int index, int[] solution) {
        this.sum = sum;
        this.index = index;
        this.solution = solution;
    }

    private void add(int n) { // metoda, ktora zwieksza sume o wartosc danego elementu
        this.sum += n;
    }

    private void nextIndex() { // metoda, ktora zwieksza indeks, przejscie na kolejny poziom wywolania
        this.index++;
    }

    public void include(int index, int value) { // metoda, ktora wlacza elementu do wyniku
        this.solution[index] = 1; // oznaczenie zawierania indeksu
        this.add(value); // wywolanie metody "add"
        this.nextIndex(); // wywolanie metody "nextIndex"
    }

    public void notInclude(int index) { // metoda, ktora nie wlacza elementu do wyniku
        this.solution[index] = 0; // oznaczenie nie zawierania indeksu
        this.nextIndex(); // wywolanie metody "nextIndex"
    }

    public int getSum() { // metoda, ktora zwraca biezaca sume
        return this.sum;
    }

    public int getIndex() { // metoda, ktora zwraca biezaca indeks
        return this.index;
    }

    public data copy() { // metoda, ktora przekazuje ("kopiuje") dane do nowego obiektu, stosu, wraz z potrzebnymi informacjami, takimi jak: suma, indeks oraz obecna zawartosc tablicy "solution"
        data newStack = new data(this.getSum(), this.getIndex(), this.arrayCopy(this.solution));
        return newStack;
    }

    public String solutionToString(int[] arr) { // metoda, ktora zwraca wynik, szukany ciag znakowy
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            if(this.solution[i] == 1) { // jezeli w tabeli wynikowej "solution" na danej pozycji znajduje sie 1, przypisujemy dany element do wyjsciowego ciagu znakowego
                result += arr[i];
                if(i < arr.length-1) result += " "; // ciagi elementow oddzielone pojedyncza spacja
            }
        }
        return result;
    }

    private int[] arrayCopy(int[] from) { // metoda, ktora przekazuje ("kopiuje")dane do nowej tablicy
        int[] newArr = new int[from.length]; // utworzenie nowej tablicy o dlugosci tablicy "solution"
        for(int i = 0; i < from.length; i++) {
            newArr[i] = from[i];
        }
        return newArr;
    }
}

/*Implmentacja stosu danych, wykorzystywanego w metodzie iteracyjnej. Elementami stosu sa obiekty klasy "data"*/
class stackData {
    private int MaxSize; // rozmiar tablicy zawierajacej stos
    private data[] Elements; // tablica zawierajaca stos
    private int TopElementIndex; // indeks szczytu stosu

    public stackData(int Size) { // konstruktor
        MaxSize = Size; // ustawiamy rozmiar tablicy
        Elements = new data[MaxSize]; // tworzymy tablice dla elementow
        TopElementIndex = 0; // na poczatku nie ma elementow (stos rosnie w dol)
    }

    public boolean IsEmpty() { // metoda okresla czy stos jest pusty
        return (TopElementIndex == 0);
    }

    public boolean IsFull() { // metoda okresla czy stos jest pelny
        return (TopElementIndex == MaxSize);
    }

    public data top() { // metoda zwraca wartosc, ktora jest na szczycie stosu
        if (IsEmpty())
            return null;
        else
            return Elements[TopElementIndex];
    }

    public void push(data x) { //metoda wstawia element na szczyt stosu
        if (!IsFull())
            Elements[TopElementIndex++] = x;
    }

    public data pop() { // jezeli stos nie jest pusty metoda usuwa element ze szczytu stosu oraz zwraca go
        if (IsEmpty())
            return null;
        else
            return Elements[--TopElementIndex];
    }
}

/*Przykladowe testy:
IN:
1
5
10
1 2 3 4 2 6 4 5 3 2


OUT:
REC:  5 = 1 2 2
ITER: 5 = 1 2 2

IN:
1
15
3
1 4 6
OUT:
BRAK

IN:
1
9
6
6 2 3 4 6 9
OUT:
REC:  9 = 6 3
ITER: 9 = 6 3

IN:
1
25
10
5 22 17 12 9 17 14 18 4 5
OUT:
REC:  25 = 12 9 4
ITER: 25 = 12 9 4

IN:
1
13
6
2 5 8 4 6 11
OUT:
REC:  13 = 2 5 6
ITER: 13 = 2 5 6

IN:
3
25
5
1 5 5 25 14
30
5
10 5 10 25 10
22
4
1 5 6 7
OUT:
REC:  25 = 1 5 5 14
ITER: 25 = 1 5 5 14
REC:  30 = 10 10 10
ITER: 30 = 10 10 10
BRAK
*/

