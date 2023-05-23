// Aleksandra Gomolka - 8
import java.util.Scanner;

/* Program realizuje nastepujace operacje: usuwa znaki, ktore nie moga wystepowac w zadanych wyrazeniach; sprawdza poprawnosc skladniowa wyrazen;
poprawnosc wyrazen w postaci INF sprawdzana jest z wykorzystaniem automatu skonczonego podanego w tresci zadania, poprawnosc wyrazen
w postaci ONP sprawdzana jest w taki sposob aby zapewnic obliczalnosc wyrazenia; konwertuje wyrazenia arytmetyczne i instrukcje przypisania
z notacji INF do ONP; konwertuje wyrazenie arytmetyczne i instrukcje przypisania z ONP do INF. W tym celu utworzone zostaly odpowienie metody
oraz struktury, tak zwane stosy. */

public class Source { // Implementacja glownej klasy.
    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;

    public static void main(String[] args) {

        int numberOfsets; // zmienna okreslajaca liczbe linii zawierajacych wyrazenia arytmetyczne lub instrukcje przypisania ktorych opisy wystepuja kolejno po sobie
        String attributiveString = ""; // zmienna ktora przechowuje napis poprzedzajacy wyrazenie lub instrukcje przypisania (ONP:  lub INF:)
        String data = ""; // zmienna przechowujaca ciag znakowy wpisywany na konsolowe wejcie

        numberOfsets = scanner.nextInt(); // wczytywanie liczby okreslajacej liczbe linni zwierajacych wyrazenia

        for (int k = 1; k <= numberOfsets; k++) {
            attributiveString = scanner.next(); // wczytywanie napisu ONP: lub INF:
            data = scanner.nextLine(); // wczytywanie ciagu znakowego skladajacego sie na wyrazenie arytmetyczne lub instrukcje przypisania

            data = data.replaceAll("\\s+",""); // usuniecie wszystkich spacji, ktore sa jednymi z symboli, ktore nie powinny wystepowac w zadanych wyrazeniach

            if(attributiveString.equals("ONP:")) {
                data = data.replaceAll("[^a-z~!^*/%+\\-<>?&|=]", ""); // usuniecie wszystkich znakow, ktore nie powinny wystepowac w wyrazeniach w notacji ONP

                String Tab[] = new String[256]; // implementacja tablicy o max 256 elementach

                for (int i = 0; i < data.length(); i++) {

                    Tab[i] = Character.toString(data.charAt(i)); // do tablicy przypisywane sa kolejno elementy wpisywanego na wejscie ciagu znakowego
                }

                int length = 0;
                /*zmienna zliczajca liczbe elementow ciagu znakowego, wykorzystywana jest po to aby usunac niepotrzebne, puste pola, ktore nie zostaly
                wykorzystane, poniewaz tablica byla zaimplementowana na max 256 znakow.*/

                for (int j = 0; j < Tab.length; j++) {
                    if (Tab[j] != null) {
                        length++;
                    }
                }

                String tmpTab[] = new String[length]; // implementacja tablicy do ktorej przypisane sa elementy, po usuniecie niepotrzebnych znakow, spacji oraz pustych pol

                for (int l = 0; l < length; l++) { // tablica musi miec dokladnie taka sama dlugosc jak wprowadzony ciag znakowy
                    tmpTab[l] = Tab[l];
                }

                if (checkCalculability(tmpTab) == 1) {System.out.println("INF: " + "error");} // wywolanie metody sprawdzajacej poprawnosc wyrazenia, jezeli wyrazenie jest niepoprawne w wyniku otrzymujemy error
                else {convertToINF(tmpTab);} // jezeli wyrazenie jest poprawne nastepuje wywolanie metody, ktora przeprowadza konwersje wyrazenie do notacji INF
            }
            else if (attributiveString.equals("INF:")) {
                data = data.replaceAll("[^a-z~!^*/%+\\-<>?&|=()]", ""); // usuniecie wszystkich znakow, ktore nie powinny wystepowac w wyrazeniach w notacji INF

                String Tab2[] = new String[256]; // implementacja tablicy o max 256 elementach
                for (int i = 0; i < data.length(); i++) {

                    Tab2[i] = Character.toString(data.charAt(i)); // do tablicy przypisywane sa kolejno elementy wpisywanego na wejscie ciagu znakowego
                }

                int length = 0;
                /*zmienna zliczajca liczbe elementow ciagu znakowego, wykorzystywana jest po to aby usunac niepotrzebne, puste pola, ktore nie zostaly
                  wykorzystane, poniewaz tablica byla zaimplementowana na max 256 znakow.*/

                for (int j = 0; j < Tab2.length; j++) {
                    if (Tab2[j] != null) {
                        length++;
                    }
                }

                String tmpTab2[] = new String[length]; // implementacja tablicy do ktorej przypisane sa elementy, po usuniecie niepotrzebnych zankow, spacji oraz pustych pol

                for (int l = 0; l < length; l++) {  // tablica musi miec dokladnie taka sama dlugosc jak wprowadzony ciag znakowy
                    tmpTab2[l] = Tab2[l];
                }
                if (checkCorrectness(tmpTab2) == 1) {System.out.println("ONP: " + "error");} // wywolanie metody sprawdzajacej poprawnosc wyrazenia, jezeli wyrazenie jest niepoprawne w wyniku otrzymujemy error
                else {convertToONP(tmpTab2);} // jezeli wyrazenie jest poprawne nastepuje wywolanie metody, ktora przeprowadza konwersje wyrazenie do notacji ONP
            }
        }
    }
 /* Implementacja metody zwracajajacej odpowiednie cyfry w zaleznosci od priorytetu znaku, najzwyzsza wartosc cyfry oznacza najwyzszy priorytet.
 Metoda miedzy innymi zwraca nam informacje o priorytecie  odpowiedniego dzialania arytmetycznego zgodnie z kolejnoscia wykonywania dzialan. */
    static int priorityFunction(String sign) {
        if (sign.equals("="))
            return 1;
        else if (sign.equals("|"))
            return 2;
        else if (sign.equals("&"))
            return 3;
        else if (sign.equals("?"))
            return 4;
        else if (sign.equals("<") || sign.equals(">"))
            return 5;
        else if (sign.equals("+") || sign.equals("-"))
            return 6;
        else if (sign.equals("*") || sign.equals("/") || sign.equals("%"))
            return 7;
        else if (sign.equals("^"))
            return 8;
        else if (sign.equals("!") || sign.equals("~"))
            return 9;
        else if (sign.charAt(0) >= 'a' && sign.charAt(0) <= 'z')
            return 10;
        return 0; // jesli doszlo do bledu metoda zwroci 0
    }
/* Implementacja metody, ktora zwraca odpowiednie cyfry w zaleznosci od typu lacznosci operatorow. Jezeli operatory sa lewostronnie laczne
 metoda zwroci 1, jezeli operatory sa prawostronnie laczne metoda zwroci wartosc 2.  */
    static int connectionFunction (String sign) {
        if (sign.equals("*") || sign.equals("/") || sign.equals("%") ||
                sign.equals("+") || sign.equals("-") || sign.equals("<") || sign.equals(">") || sign.equals("?") ||
                sign.equals("&") || sign.equals("|"))
            return 1;
        else if (sign.equals("!") || sign.equals("~") || sign.equals("^") || sign.equals("="))
            return 2;
        return 0; // jesli doszlo do bledu metoda zwroci 0
    }
/* Implementacja metody sprawdzajacej poprawnosc wyrazenia w postaci ONP. Podczas przechodzenia po elementach tablicy (czyli elementach wyrazenia)
  metoda, jezeli element jest operandem doda 1 do zmiennej numberOfexpressions, jezeli element jest operatorem dwuargumentowym metoda odejmie 1
  od zmiennej numberOfexpressions. Za kazdym razem sprawdzamy czy zmienna nie jest mniejsza lub rowna zero, wtedy od razu metoda zwroci nam 1, co
   oznaczac bedzie bledne wyrazenie w notacji ONP. Po przejciu wszystkich elementow, jezeli zmienna numberOfexpressions jest rozna od jeden to oznacza
    ze wyrazenie jest bledne. W skrocie aby sprawdzic poprawnosc wyrazenia w notacji ONP sprawdzamy liczbe operatorow i operandow dwuargumentowych.
    Wyrazenie poprawne powinno miec o jeden wiecej operandow niz operatorow dwuargumentowych.Wyrazenie w notacji ONP ktore jest bledne nie bedzie przekazywane
    do metody konwertujacej wyrazenie do postaci INF, tylko od razu zostanie wyswietlony komunikat: "error"*/
    static int checkCalculability (String[] inputData) {
        int numberOfexpressions = 0;

        for (int i = 0; i < inputData.length; i++) {
            if(inputData[i].charAt(0) >= 'a' && inputData[i].charAt(0) <= 'z') {
                numberOfexpressions += 1;
            }
            else if (inputData[i].equals("^") || inputData[i].equals("*") || inputData[i].equals("/") ||
                    inputData[i].equals("%") || inputData[i].equals("+") || inputData[i].equals("-") ||
                    inputData[i].equals("<") || inputData[i].equals(">") || inputData[i].equals("?") ||
                    inputData[i].equals("&") || inputData[i].equals("|") || inputData[i].equals("=")) {
                numberOfexpressions -= 1;
            }
            if (numberOfexpressions <= 0) {return 1;}
        }
        if (numberOfexpressions != 1) {return 1;}
        return 0;
    }

/* Implementacja metody sprawdzajacej poprawnosc wyrazenia w postaci INF. W celu sprawdzenia poprawnosci wyrazenia skorzystano z automatu skonczonego, ktory
 jest szczegolnym przypadkiem maszyny Turinga. Wzor automatu skonczonego zostal przedstawiony w tresci zadania. Automat poczatkowo znajduje sie w
 stanie 0. Jesli automat po przeczytaniu wyrazenia znajdzie sie w stanie 1 wowczas akceptuje wyrazenie, czyli stwierdza ze jest poprawne.
  Dodatkowo uwzgledniono sytuacje, ktorych automat nie uwzglednial. W tym celu stworzono zmienna wrongDirection, ktora byla zwiekszana w momenice wystapienia stanu nieprzewidywanego przez
 automat. Jezeli po przejsciu po wszystkich elementach wyrazenia zmienna wrongDirection bedzie rozna od zera to oznacza ze wyrazenie jest bledne.
 Dodatkowo metoda kontroluje sytuacje w ktorych wystepuja niesparowane nawiasy lub ich zla kolejnosc. Do tego wykorzystano zmienna numberOfParantheses.
 Jezeli przechodzac po kolejnych elementach tablicy (elementach wyrazenia) napotkamy na nawias otwierajacy "(" zmienna numberOfParentheses
 zostaje zwiekszono o jeden, jezeli napotkamy na nawias zamykajacy to zmienna numberOfParentheses zostaje zmiejszona o jeden. Za kazdym razem sprawdzamy
  czy zmienna numberOfParentheses jest mniejsza od zera, jezeli zmienna jest mniejsza od zera to swiadczy o zlej kolejnosci nawiasow, metoda w tym wypadku
   zwrocic jeden co swiadczy o blednym wyrazeniu. Jezeli nawiasy sa sparowane poprawnie zmienna numerOfParenthese powinna wynosic 0. Po przejsciu wszystkich elementow,
   jezeli znajdujemy sie w stanie innym niz jeden lub zmienna numberOfParentheses jest rozna od 0 lub zmienna wrongDirection jest rozna od zera metoda zwroci wartosc 1, ktora
   daje nam informacje o blednym wyrazniu.*/
    static int checkCorrectness (String[] inputData) {

        int condition = 0; // stan, poziom okreslony w automacie
        int wrongDirection = 0; // zmienna oznaczajaca zly zapis, wystapienie stanu nieprzewidywanego przez automat
        int numberOfParentheses = 0; // zmienna przechowujaca dane o liczbie nawiasow

        for (int i = 0; i < inputData.length; i++) {
            switch(condition) {
                case 0:
                    if(inputData[i].equals("(")) {numberOfParentheses += 1; condition = 0;}
                    else if (inputData[i].equals("~") || inputData[i].equals("!")) condition = 2;
                    else if (inputData[i].charAt(0) >= 'a' && inputData[i].charAt(0) <= 'z') condition = 1;
                    else wrongDirection+=1;
                    if (numberOfParentheses < 0) {return 1;}
                    continue;
                case 1:
                    if(inputData[i].equals(")")) {numberOfParentheses -=1;condition = 1;}
                    else if (inputData[i].equals("^") || inputData[i].equals("*") || inputData[i].equals("/") ||
                            inputData[i].equals("%") || inputData[i].equals("+") || inputData[i].equals("-") ||
                            inputData[i].equals("<") || inputData[i].equals(">") || inputData[i].equals("?") ||
                            inputData[i].equals("&") || inputData[i].equals("|") || inputData[i].equals("=")) condition = 0;
                    else wrongDirection +=1;
                    if (numberOfParentheses < 0) {return 1;}
                    continue;
                case 2:
                    if(inputData[i].equals("(")) {numberOfParentheses += 1; condition = 0;}
                    else if (inputData[i].equals("~") || inputData[i].equals("!")) condition = 2;
                    else if (inputData[i].charAt(0) >= 'a' && inputData[i].charAt(0) <= 'z') condition = 1;
                    else wrongDirection+=1;
                    if (numberOfParentheses < 0) {return 1;}
                    continue;
            }
        }
        if(condition != 1 || numberOfParentheses !=0 || wrongDirection !=0) {
            return 1;
        }
        return 0;
    }
/* Implementacja metody, ktora po usunieciu niepotrzebnych znakow oraz sprawdzeniu poprawnosci wyrazenia dokonuje konwersji wyrazenia
   w postaci INF do postaci ONP. */
    static void convertToONP(String[] inputData) {

        String stringToOutput = ""; // zmienna przechowujaca ciag znakowy na wyjscie

        stackArray Stack = new stackArray(inputData.length); // utworzenie nowego obiektu/stosu przechowujacego elementy wyrazenia

        for (int i = 0; i < inputData.length; i++) {  // przechodzimy po kazdym elemencie tablicy
            if(inputData[i].charAt(0) >= 'a' && inputData[i].charAt(0) <= 'z') { // jezeli element jest operandem dodajemy go na koniec wyjsciowego ciagu znakowego
                stringToOutput += inputData[i];

            }
            else {

                if(inputData[i].equals("(")) // jezeli element jest nawiasem otwierajacym to dodajemy go nas stos
                    Stack.push(inputData[i]);
                else if (!inputData[i].equals("(")) { // jezeli element nie jest nawiasem otwierajacym oraz stos jest pusty to wstawiamy ten element na stos (szczyt stosu)
                    if (Stack.IsEmpty()) {
                        Stack.push(inputData[i]);
                    }
                    else {
                        if (connectionFunction(inputData[i]) == 1) { // jezeli element jest operatorem lewostronnym to zdejmujemy ze stosu wszystkie operatory o priorytecie >= niz priorytet operatora obecnie sprawdzanego i dopisujemy je na wyjscie
                            while (!Stack.IsEmpty() && priorityFunction(Stack.top()) >= priorityFunction(inputData[i])) {
                                stringToOutput += Stack.pop();
                            }
                            Stack.push(inputData[i]); // wstawiamy obecnie sprawdzany operator na stos
                        }
                        else if (connectionFunction(inputData[i]) == 2) { // jezeli element jest operatorem prawostronnym to zdejmujemy ze stosu wszystkie operatory o priorytecie > niz priorytet operatora obecnie sprawdzanego i dopisujemy je na wyjscie
                            while (!Stack.IsEmpty() && priorityFunction(Stack.top()) > priorityFunction(inputData[i])) {
                                stringToOutput += Stack.pop();
                            }
                            Stack.push(inputData[i]); // wstawiamy obecnie sprawdzany operator na stos
                        }
                        else if (inputData[i].equals(")")) { // jezeli element jest nawiasem zamykajacym to zdejmujemy ze stosu i dopisujemy na wyjscie wszystkie operatory az do napotkania nawiasu otwierajacego, ktory pomijamy
                            while (!Stack.top().equals("(")) {
                                stringToOutput += Stack.pop();
                            }
                            Stack.pop(); // pomijamy nawias otwierajacy
                        }
                    }
                }
            }
        }
        while (!Stack.IsEmpty()) { // po zakonczeniu przechodzenia po wszystkich elmentach wyrazenia lub instrukcji przypisania przepisujemy ze stosu na wyjscie wszystko co na nim zostalo
            stringToOutput += Stack.pop();
        }

        stringToOutput = stringToOutput.replaceAll(""," "); // wszystkie puste pola zastepujemy spacjami
        stringToOutput = stringToOutput.trim(); // usuwamy wszystkie biale znaki na koncu i na poczatku wyjsciowego stringa

        System.out.println("ONP: " + stringToOutput);
    }
    /* Implementacja metody, ktora po usunieciu niepotrzebnych znakow oraz sprawdzeniu poprawnosci wyrazenia dokonuje konwersji wyrazenia
       w postaci ONP do postaci INF zapewniajac minimalna liczbe nawiasow, gwarantujaca taka kolejnosc operacji jak w wyrazeniu ONP. */
    static void convertToINF(String[] inputData2) {

        stackArray Stack2 = new stackArray(inputData2.length); // utworzenie nowego obiektu/stosu przechowujacego odpowienie elementy wyrazenia
        stackArrayInt StackPriority2 = new stackArrayInt(inputData2.length); // // utworzenie nowego obiektu/stosu przechowujacego priorytety elementow/czesci wyrazenia

        String stringToOutput = ""; // zmienna przechowujaca ciag znakowy na wyjscie

        for (int i = 0; i < inputData2.length; i++) { // przechodzimy po kazdym elemencie tablicy
            if (!inputData2[i].equals("(") && !inputData2[i].equals(")"))
                if (inputData2[i].charAt(0) >= 'a' && inputData2[i].charAt(0) <= 'z') { // jezeli element jest operandem dodajemy go na stos
                    Stack2.push(inputData2[i]);
                    StackPriority2.push(priorityFunction(inputData2[i])); // na stos priorytetow dodajemy priorytet obecnie sprawdzanego elementu

                } else { // element jest operatorem
                    stringToOutput = "";
                    if (!inputData2[i].equals("~") && !inputData2[i].equals("!")) {  // jezeli element nie jest operandem ani operatorem jednoargumentowym

                        if (inputData2[i].equals("=") || inputData2[i].equals("^")) { // jezeli element jest operatorem dwuargumentowym i prawostronnym
                            if ((StackPriority2.top() < priorityFunction(inputData2[i]))) { // jezeli priorytet obecnie sprawdzanego operatora jest wiekszy niz priorytet prawej czesci wyrazenia (prawego potomka)
                                stringToOutput = "(" + Stack2.pop() + ")"; // to element obecnie znajdujacy sie na szczycie stosu (podwyrazenie w prawym potomku) otaczamy nawiasem
                            } else { // jezeli priorytet obecnie sprawdzanego operatora nie jest wiekszy niz priorytet prawej czesci wyrazenia (prawego potomka)
                                stringToOutput = Stack2.pop(); // to element obecnie znajdujacy sie na szczycie stosu nie otaczamy nawiasem. Do wyjsciowego stringa wstawiamy element pobrany ze szczytu stosu
                            }
                            StackPriority2.pop();
                            if ((StackPriority2.top() < priorityFunction(inputData2[i]))) { // jezeli priorytet obecnie sprawdzanego operatora jest wiekszy niz priorytet lewej czesci wyrazenia (lewego potomka)
                                stringToOutput = "(" + Stack2.pop() + ")" + inputData2[i] + stringToOutput; // to element obecnie znajdujacy sie na szczycie stosu (podwyrazenie w lewym potomku) otaczamy nawiasem
                            } else { // jezeli priorytet obecnie sprawdzanego operatora nie jest wiekszy niz priorytet lewej czesci wyrazenia (lewego potomka)
                                stringToOutput = Stack2.pop() + inputData2[i] + stringToOutput; // to element obecnie znajdujacy sie na szczycie stosu nie otaczamy nawiasem. Do wyjsciowego stringa wstawiamy: drugi pobrany element + dzialanie arytmetyczne, ktore wczytalismy + pierwszy pobrany element
                            }
                            StackPriority2.pop();
                        } else { // jezeli element jest operatorem dwuargumentowym i lewostronnym
                        if ((StackPriority2.top() <= priorityFunction(inputData2[i]))) { // jezeli priorytet obecnie sprawdzanego operatora jest wiekszy lub rowny niz priorytet prawej czesci wyrazenia (prawego potomka)
                            stringToOutput = "(" + Stack2.pop() + ")"; // to element obecnie znajdujacy sie na szczycie stosu (podwyrazenie w prawym potomku) otaczamy nawiasem
                        } else { // jezeli priorytet obecnie sprawdzanego operatora nie jest wiekszy lub rowny niz priorytet prawej czesci wyrazenia (prawego potomka)
                            stringToOutput = Stack2.pop(); // to element obecnie znajdujacy sie na szczycie stosu nie otaczamy nawiasem. Do wyjsciowego stringa wstawiamy element pobrany ze szczytu stosu
                        }
                        StackPriority2.pop();
                        if ((StackPriority2.top() < priorityFunction(inputData2[i]))) { // jezeli priorytet obecnie sprawdzanego operatora jest wiekszy niz priorytet lewej czesci wyrazenia (lewego potomka)
                            stringToOutput = "(" + Stack2.pop() + ")" + inputData2[i] + stringToOutput; // to element obecnie znajdujacy sie na szczycie stosu (podwyrazenie w lewym potomku) otaczamy nawiasem
                        } else { // jezeli priorytet obecnie sprawdzanego operatora nie jest wiekszy niz priorytet lewej czesci wyrazenia (lewego potomka)
                            stringToOutput = Stack2.pop() + inputData2[i] + stringToOutput; // to element obecnie znajdujacy sie na szczycie stosu nie otaczamy nawiasem. Do wyjsciowego stringa wstawiamy: drugi pobrany element + dzialanie arytmetyczne, ktore wczytalismy + pierwszy pobrany element
                        }
                        StackPriority2.pop();
                        }
                    }
                    else { // jezeli element nie jest operandem ale operatorem jednoargumentowym
                        if (StackPriority2.top() < priorityFunction(inputData2[i])) {  // jezeli priorytet obecnie sprawdzanego operatora jest wiekszy niz priorytet elementu znajdujacego sie obecnie na szczycie stosu
                            stringToOutput = inputData2[i] + "(" + Stack2.pop() + ")"; // to element obecnie znajdujacy sie na szczycie stosu otaczamy nawiasem. Do wyjsciowego stringa wstawiamy:  operator ktory wczytalismy + pobrany element
                        } else { // jezeli priorytet obecnie sprawdzanego operatora nie jest wiekszy niz priorytet elementu znajdujacego sie obecnie na szczycie stosu
                            stringToOutput = inputData2[i] + Stack2.pop(); // to element obecnie znajdujacy sie na szczycie stosu nie otaczamy nawiasem. Do wyjsciowego stringa wstawiamy:  operator ktory wczytalismy + pobrany element
                        }
                        StackPriority2.pop();
                    }

                    Stack2.push(stringToOutput); // na stos wstawiamy otrzymany ciag znakowy
                    StackPriority2.push(priorityFunction(inputData2[i])); // na stos priorytetow wstawiamy priorytet obecnie sprawdzanego elementu
                }
        }

        String stackPop = Stack2.pop(); // wynikiem jest wyrazenie/ciag znakowy znajdujacy sie na szycie stosu po przejciu wszystkich elementow, po przejsciu calego wyrazenia lub instrukcji przypisania
        stackPop = stackPop.replaceAll(""," "); // wszystkie puste pola zastepujemy spacjami
        stackPop = stackPop.trim(); // usuwamy wszystkie biale znaki na koncu i na poczatku wyjsciowego stringa

        System.out.println("INF: " + stackPop);
    }
}
/* Implementacja stosu wykorzystywanego do konwersji wyrazen. Stos docelowo przechowuje elementy wyrazen lub instrukcji przypisania*/
class stackArray {
    private int MaxSize; // rozmiar tablicy zawierajacej stos
    private String[] Elements; // tablica zawierajaca stos
    private int TopElementIndex; // indeks szczytu stosu

    public stackArray(int Size) { // konstruktor
        MaxSize = Size; // ustawiamy rozmiar tablicy
        Elements = new String[MaxSize]; // tworzymy tablice dla elementow
        TopElementIndex = MaxSize; // na poczatku nie ma elementow (stos rosnie w gore)
    }
    public boolean IsEmpty() { // metoda zwraca informacje czy stos jest obecnie pusty
        return (TopElementIndex == MaxSize);
    } // metoda okresla czy stos jest pusty
    public boolean IsFull() { // metoda zwraca informacje czy stos jest pelny
        return (TopElementIndex == 0);
    } // metoda okresla czy stos jest pelny
    public String top() { // metoda zwraca element, ktory jest na szczycie stosu

        if (IsEmpty())
            return "";
        else
            return Elements[TopElementIndex];
    }
    public void push(String x) { //metoda wstawia element na szczyt stosu
        if (!IsFull())
            Elements[--TopElementIndex] = x;
    }
    public String pop() { // jezeli stos nie jest pusty metoda usuwa element ze szczytu stosu oraz zwraca go
        if (IsEmpty())
            return "";
        else
            return Elements[TopElementIndex++];
    }
}

/* Implementacja stosu wykorzystywanego do konwersji wyrazen. Stos docelowo przechowuje wartosci liczbowe priorytetow*/
class stackArrayInt {
    private int MaxSize; // rozmiar tablicy zawierajacej stos
    private int[] Elements; // tablica zawierajaca stos
    private int TopElementIndex; // indeks szczytu stosu

    public stackArrayInt(int Size) { // konstruktor
        MaxSize = Size; // ustawiamy rozmiar tablicy
        Elements = new int[MaxSize]; // tworzymy tablice dla elementow
        TopElementIndex = MaxSize; // na poczatku nie ma elementow (stos rosnie w gore)
    }
    public boolean IsEmpty() { // metoda zwraca informacje czy stos jest obecnie pusty
        return (TopElementIndex == MaxSize);
    } // metoda okresla czy stos jest pusty
    public boolean IsFull() { // metoda zwraca informacje czy stos jest pelny
        return (TopElementIndex == 0);
    } // metoda okresla czy stos jest pelny
    public int top() { // metoda zwraca wartosc, ktora jest na szczycie stosu
        if (IsEmpty())
            return 0;
        else
            return Elements[TopElementIndex];
    }
    public void push(int x) { //metoda wstawia element na szczyt stosu
        if (!IsFull())
            Elements[--TopElementIndex] = x;
    }
    public int pop() { // jezeli stos nie jest pusty metoda usuwa element ze szczytu stosu oraz zwraca go
        if (IsEmpty())
            return 0;
        else
            return Elements[TopElementIndex++];
    }
}

/* Przykladowe testy:
IN:
10
INF: a=~b*a/c-d+f%~g
INF: ( ( a*c)) +b
INF: .. a+b+(  !a-a)
INF: xsdf+++db+++
INF: ab+a~a-+
INF: c*d/ ( a + b*c)
INF: c)*(b
INF: (a+b)/c+d
INF: <..> c*d + a*b
INF: a-b*(b +x^y - y) / a+b/(~p)

OUT:
ONP: a b ~ a * c / d - f g ~ % + =
ONP: a c * b +
ONP: a b + a ! a - +
ONP: error
ONP: error
ONP: c d * a b c * + /
ONP: error
ONP: a b + c / d +
ONP: error
ONP: a b b x y ^ + y - * a / - b p ~ / +

IN:
10
ONP: a-bc*cd
ONP: ab+cd++
ONP: abc-+def
ONP: ab*cd**
ONP: abcdefghj-+/*%^-+
ONP: ~~c
ONP: e!!
ONP: c~d~~+c~/~d~*~e~
ONP: ca+a~b-+
ONP: abcd-e++=

OUT:
INF: error
INF: a + b + ( c + d )
INF: error
INF: a * b * ( c * d )
INF: a + ( b - c ^ ( d % ( e * ( f / ( g + ( h - j ) ) ) ) ) )
INF: error
INF: ! ! e
INF: error
INF: c + a + ( ~ a - b )
INF: a = b + ( c - d + e )
 */






