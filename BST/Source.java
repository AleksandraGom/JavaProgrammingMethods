// Aleksandra Gomolka - 8
import java.util.Scanner;

/*Celem zadania bylo zaimplementowanie uniwersalnego algorytmu kolejkujacego osoby ubiegajace sie o posade w firmie. Implementacja kolejki
wykorzystuje drzewo BST zgodnie z trescia zadania. Program sklada sie z trzech modulow kolejkowania, edycji oraz raportowania. Dzialanie
poszczegolnych modulow zostalo przedstawione i zaimplementowane w postaci odpowiednich metod uzytych w zadaniu. Metody wymagaja, badz nie
argumentow w zaleznosci od zastosowania metody. Szczegolowy opis metod zostanie przedstawiony ponizej. Na wyjscie, kazdy test powinien zaczynac
sie od wypisania ciagu "ZESTAW n" a wynik kazdej operacji w zestawie powinien byc wypisany w jednej linii.*/

public class Source {

    public static Scanner scanner = new Scanner(System.in); // Do operacji wprowadzania danych wykorzystano klase Scanner z pakietu java.util. Nalezy wtedy dodac na poczatku aplikacji: import java.util.Scanner;

    public static void main(String[] args) {

        int numberOfsets; // liczba zestawow
        int numberOfcommands; // liczba komend do wykonania w kolejce
        String commandName; // nazwa operacji
        String orderType; // typ order dla CREATE (POSTORDER lub PREORDER)
        // skladowe osoby
        int priority; // zmienna okreslajaca priorytet danej osoby/elementu
        String name; // zmienna okreslajaca imie danej osoby/ elementu
        String surname; // zmienna okreslajace nazwisko danej osoby/elementu
        numberOfsets = scanner.nextInt(); // wczytanie liczby okreslajacej liczbe zestawow danych

        for (int k = 1; k <= numberOfsets; k++) {
            numberOfcommands = scanner.nextInt(); // wczytanie liczby okreslajacej liczbe komend
            BST bstTree = new BST(); // utworzenie nowego obiektu, drzewa
            System.out.println("ZESTAW " + k); // wypisanie na wyjscie numeru zestawu

            for (int j = 1; j <= numberOfcommands; j++) {
                commandName = scanner.next(); // wczytywanie nazwy polecenia
                switch (commandName) { // W zaleznosci od wczytanego polecenia nastepuje wywolanie odpowiedniej metody z odpowiednia dla nadej metody liczba argumentow
                    case "ENQUE":
                        priority = scanner.nextInt(); // wczytywanie priorytetu dodawanej osoby
                        name = scanner.next(); // wczytanie imienia dodawanej osoby
                        surname = scanner.next(); // wczytanie nazwiska dodawanej osoby
                        enque(bstTree, priority, name, surname);
                        continue;
                    case "DEQUEMAX":
                        dequemax(bstTree);
                        continue;
                    case "DEQUEMIN":
                        dequemin(bstTree);
                        continue;
                    case "NEXT":
                        int argNext = scanner.nextInt();
                        next(bstTree, argNext);
                        continue;
                    case "PREV":
                        int argPrev = scanner.nextInt();
                        prev(bstTree, argPrev);
                        continue;
                    case "CREATE":
                        orderType = scanner.next(); // wczytywanie typu order (POSTORDER lub PREORDER)
                        int numberOfelem = scanner.nextInt(); // liczba osob/elementow z ktorych nalezy utworzyc drzewo kolejki
                        Person[] persons = new Person[numberOfelem]; // utworzenie tablicy typu Person o zadanej liczbie elementow,
                        // tablica przechowuje dane o kazdej osobie tzn. priorytet, imie, nazwisko z bazy osob
                        for(int i = 0; i < numberOfelem; ++i) {
                            priority = scanner.nextInt(); // wczytanie priorytetu danej osoby
                            name = scanner.next(); // wczytanie imienia danej osoby
                            surname = scanner.next(); // wczytanie nazwiska danej osoby
                            persons[i] = new Person(priority, name, surname);
                        }
                        // tworzenie drzewa
                        bstTree = create(orderType, persons);
                        continue;
                    case "DELETE":
                        int arg = scanner.nextInt();
                        delete(bstTree, arg);
                        continue;
                    case "PREORDER":
                        preorder(bstTree);
                        continue;
                    case "INORDER":
                        inorder(bstTree);
                        continue;
                    case "POSTORDER":
                        postorder(bstTree);
                        continue;
                    case "HEIGHT":
                        height(bstTree);
                }
            }
        }
    }
/* Utworzenie metod potrzebnych do wypisania na wyjscie odpowiednich danych. Po wykonaniu kazdej operacji na wyjscie powinnysmy wyswietlic
nazwe wywolanej operacji, jezeli to potrzebne argumenty danej operacji oraz wynik.*/

    static void enque(BST bst, int priority, String name, String surname) {
        bst.enque(new Person(priority, name, surname));
    }

    static void dequemax(BST bstTree) {
        Person p = bstTree.dequemax();
        System.out.println("DEQUEMAX: " + (p != null ? p : "")); // jezeli nie znaleziony osoby o najwyzszym priorytecie np. puste drzewo
        // zwracamy pusty ciag znakowy, w przeciwnym wypadku zwracamy wynik uzyskany przy wykorzystaniu metody "dequemax"
    }

    static void dequemin(BST bstTree) {
        Person p = bstTree.dequemin();
        System.out.println("DEQUEMIN: " + (p != null ? p : "")); // jezeli nie znaleziono osoby o najnizszym priorytecie np. puste drzewo
        // zwracamy pusty ciag znakowy, w przeciwnym wypadku zwracamy wynik uzyskany przy wykorzystaniu metody "dequemin"
    }

    static void next(BST bstTree, int argNext) {
        Node n = bstTree.findNext(bstTree.root, argNext); // jezeli argument metody NEXT nie istnieje, nalezy zwrocic odpowiedni komunikat
        System.out.println("NEXT " + argNext + ": " + (n != null ? n.info : "BRAK"));
    }

    static void prev(BST bstTree, int argPrev) {
        Node n = bstTree.findPrev(bstTree.root, argPrev); // jezeli argument metody PREV nie istnieje, nalezy zwrocic odpowiedni komunikat
        System.out.println("PREV " + argPrev + ": " + (n != null ? n.info : "BRAK"));
    }

    static BST create(String orderType, Person[] persons) {
        BST bstTree;
        if(orderType.compareTo("POSTORDER") == 0) { // porownanie dwoch ciagow, metoda zwroci zero jezeli ciagi sa sobie rowne
            bstTree = new BST(
                    BST.createFromPostorder(persons),
                    persons.length
            );
        } else if (orderType.compareTo("PREORDER") == 0) { // porownanie dwoch ciagow, metoda zwroci zero jezeli ciagi sa sobie rowne
            bstTree = new BST(
                    BST.createFromPreorder(persons),
                    persons.length
            );
        } else { // potrzebne to poprawnego dzialania programu, poniewaz program nie zaklada, ze istnieja tylko dwa typy PREORDER i POSTORDER
            bstTree = new BST();
        }
        return bstTree;
    }

    static void delete(BST bstTree, int arg) {
        int nodesCnt = bstTree.nodesCnt; // ilosc wezlow przed usunieciem w drzewie
        Node changedRoot = bstTree.deleteNodeIteratively(bstTree.root, arg); // metoda "deleteNodeIteratively" zwraca roota
        if(nodesCnt == bstTree.nodesCnt) { // sprawdzanie czy zostal usuniety wezel nastepuje przez sprawdzenie ilosci wezlow
            System.out.println("DELETE " + arg + ": BRAK"); // jezeli wezel nie zostal usuniety, np. nie odnaleziono wezla o szukanym priorytecie
            // nalezy zwrocic odpowiedni komunikat
        } else { // jezeli wezel zostal usuniety, kolokwialnie mowiac podmieniam drzewo na nowe (posiadamy referencje do zmienionego drzewa)
            bstTree.root = changedRoot;
        }
    }

    static void preorder(BST bstTree) {
        System.out.println("PREORDER: " + bstTree.preorderIterative());
    }

    static void inorder(BST bstTree) {
        System.out.println("INORDER: " + bstTree.inorderIterative());
    }

    static void postorder(BST bstTree) {
        System.out.println("POSTORDER: " + bstTree.postorderIterative(bstTree.root));
    }

    static void height(BST bstTree) {
        System.out.println("HEIGHT: " + bstTree.height());
    }
}

class BST {
    public Node root = null; // obecnie drzewo jest puste
    public int nodesCnt = 0; // ilosc elementow w drzewie
    public StringBuilder sb; // potrzebny do wypisywania danych w odpowiednim formacie na wyjscie
    static private int preOrderPos; // indeks elementu wybieranego w metodzie "createFromPreorder"
    static private int postOrderPos; // indeks elementu wybieranego w metodzie "createFromPostorder"

    // konstruktor
    public BST() {this.sb = new StringBuilder();}
    public BST(Node root, int nodesCnt) {
        this.sb = new StringBuilder();
        this.root = root;
        this.nodesCnt = nodesCnt;
    }

    /*Implementacja metody, ktora tworzy drzewo kolejki na podstawie listy n kluczy podanych w porzadku wyznaczonym przez drugi argument,
    w tym przypadku PREORDER. Jesli kolejka istnieje zostaje zastapiona nowa. Korzeniem drzewa bedzie pierwszy element w tablicy preorder.
     Metoda tworzy drzewo z tablicy preorder Person[] rekurencyjnie oraz uzywa globalnego preOrderPos. Zlozonosc czasowa metody wynosi O(n).
     Ustawiamy zakres {min,max} dla kazdego wezla. Jesli biezacy element Person[] jest w zakresie, to oznacza, ze jest czescia obecnego poddrzewa.
     Metoda opiera sie na rekurencyjnym wyszukaniu prawego i lewego potomka danego elementu, przy zmieniajacych sie wartosciach przedzialu min i max.
     Podczas szukania prawego potomka, wartosc min okresla wartosc priorytetu elementu dla ktorego szukamy potomka a wartosc max pozostaje bez zmian.
     Podczas szukania lewego potomka, wartosc max okresla wartosc priorytetu elementu dla ktorego szukamy potomka a wartosc min pozostaje bez zmian.
     Takie dzialanie wykonujemy z uwagi na budowe drzewa BST, taka gdzie elementu polozone na lewo od korzenia sa od niego mniejsze a elementu polozone
     na prawo od korzenia sa od niego wieksze.*/
    static Node createFromPreorder(Person[] persons) {
        preOrderPos = 0; // indeks wybieranego elementu, w postaci globalnej aby rekurencja nie zmieniala jego wartosci
        return createFromPreorder(persons, -1, -1, persons.length);
    }
    private static Node createFromPreorder(Person[] persons, int min, int max, int size) {
        if(preOrderPos >= size) return null; // standardowy case, po to aby uniknac wyjscia poza zakres tablicy

        Node root = null;
        Person currData = persons[preOrderPos];

        // sprawdzenie czy aktualnie sprawdzany element miesci sie w ustawionym zakresie. Jezeli element miesci sie w zakresie to dodajemy go do drzewa
        boolean inLeftRange  = (min == -1 || currData.priority > min);
        boolean inRightRange = (max == -1 || currData.priority < max);

        if (inLeftRange && inRightRange) {
            root = new Node(currData); // lokowanie pamieci dla korzenia tego poddrzewa oraz inkrementacja indeksu
            preOrderPos++; // zwiekszamy indeks, aby sprawdzac kolejne elementy a idziemy od poczatku tablicy

            // konstruujemy lewe i prawe poddrzewo wezla glownego
            // poniewaz preOrderPos jest globalne, kazde wywolanie lewego podrzewa zmienia jego wartosc zanim wywolanie do prawego drzewa jest wykonywane
            // wszystkie elementy w lewym poddrzewie sa mniejsze od wartosci korzenia, uruchamia rekurencje na odpowiednim zakresie
            root.left  = createFromPreorder(persons, min, currData.priority, size);
            // wszystkie elementy w prawym poddrzewie sa wieksze od wartosci korzenia, uruchamia rekurencje na odpowiedniem zakresie
            root.right = createFromPreorder(persons, currData.priority, max, size);
        }
        return root;
    }

    /*Implementacja metody, ktora tworzy drzewo kolejki na podstawie listy n kluczy podanych w porzadku wyznaczonym przez drugi argument,
    w tym przypadku POSTORDER. Jesli kolejka istnieje zostaje zastapiona nowa. Korzeniem drzewa bedzie ostatni element w tablicy postorder.
    Metoda tworzy drzewo z tablicy postorder Person[] rekurencyjnie oraz uzywa globalnego postOrderPos. Zlozonosc czasowa metody wynosi O(n).
    Ustawiamy zakres {min,max} dla kazdego wezla. Wezel glowny (root) i jego dzieci maja priortety znajdujace sie wlasnie w tym przedziale.
    Jesli biezacy element Person[] jest w zakresie,to oznacza, ze jest czescia obecnego poddrzewa.
    Metoda opiera sie na rekurencyjnym wyszukaniu prawego i lewego potomka danego elementu, przy zmieniajacych sie wartosciach przedzialu min i max.
     Podczas szukania prawego potomka, wartosc min okresla wartosc priorytetu elementu dla ktorego szukamy potomka a wartosc max pozostaje bez zmian.
     Podczas szukania lewego potomka, wartosc max okresla wartosc priorytetu elementu dla ktorego szukamy potomka a wartosc min pozostaje bez zmian.
     Takie dzialanie wykonujemy z uwagi na budowe drzewa BST, taka gdzie elementu polozone na lewo od korzenia sa od niego mniejsze a elementu polozone
     na prawo od korzenia sa od niego wieksze.*/
    static Node createFromPostorder(Person[] persons) {
        postOrderPos = persons.length-1; // indeks wybieranego elementu, w postaci globalnej aby rekurencja nie zmieniala jego wartosci
        return createFromPostorder(persons, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private static Node createFromPostorder(Person[] persons, int min, int max) {
        if (postOrderPos < 0) { // standardowy case, po to aby uniknac wyjscia poza zakres tablicy
            return null;
        }
        // sprawdzenie czy aktualnie sprawdzany element miesci sie w ustawionym zakresie. Jezeli element nie jest w odpowiednim zakresie, zwracamy null
        // Jezeli element miesci sie w zakresie to dodajemy go do drzewa
        int curr = persons[postOrderPos].priority;
        if (curr < min || curr > max) {
            return null;
        }
        // lokowanie pamieci dla korzenia tego poddrzewa oraz dekrementacja indeksu
        Node root = new Node(persons[postOrderPos]); // uzupelnienie wezla danymi, nowy wezel
        postOrderPos--; // zmniejszamy indeks, aby sprawdzac kolejne elementy

        // konstruujemy lewe i prawe poddrzewo wezla glownego
        // budujemy prawe podrzewo przed lewym poddrzewem poniewaz wartosci w sekwencji postorder sa czytanie od konca
        // wszystkie elementy w prawym poddrzewie sa wieksze od wartosci korzenia, ustawia zakres na [curr+1, max] i uruchamia rekurencje
        root.right = createFromPostorder(persons, curr + 1, max);
        // wszystkie elementy w lewym poddrzewie sa mniejsze od wartosci korzenia, ustawia zakres na  [min, curr-1] i uruchamia rekurencje
        root.left = createFromPostorder(persons, min, curr - 1);

        return root;
    }

    /*Implementacja metody, ktora dodaje osobe o zadanym jako argument priorytecie, imieniu i nazwisku (obiekt klasy Person).
    Z uwagi na budowe drzewa BST musimy odnalezc pozycje nowego, dodawanego elementu w drzewie. Elementy mniejsze od korzenia, znajduja sie
    w lewym poddrzewie a elementy wieksze od korzenia znajduja sie w prawym poddrzewie. Po znalezieniu odpowiedniego miejsca dla nowego elementu,
    nalezy ustawic go w polozeniu zgodnie z jego priorytetem wzgledem jego rodzica. Jezeli drzewo jest puste, nowy element stanie sie korzeniem.*/
    void enque(Person elem) {
        Node parent = null;
        Node curr = root; // ustawienie korzenia jako obecny wezel
        // szukamy miejsca do wstawienia podlaczajac na lewo lub na prawo, przy jednoczesnej aktualizacji polozenia rodzica
        while (curr != null) {
            if (curr.info.priority > elem.priority) { // jezeli priorytet dodawanego elementu jest mniejszy od obecnego wezla,
                // bedzie on umieszczony w lewym poddrzewie
                parent = curr;
                curr = curr.left;
            }
            else if (curr.info.priority <= elem.priority) { // jezeli priorytet dodawanego elementu jest wiekszy od obecnego wezla, badz rowny
                // bedzie on umieszczony w prawym poddrzewie
                parent = curr;
                curr = curr.right;
            }
        }
        // drzewo jest puste - wstawiamy korzen
        if (parent == null) {
            root = new Node(elem);
        }
        // jezeli priorytet dodawanego elementu jest mniejszy od priorytetu, wstawiamy po lewej
        else if (elem.priority < parent.info.priority) {
            parent.left = new Node(elem);
        }
        //jezeli priorytet dodawanego elementu jest wiekszy od priorytetu, badz rowny, wstawiamy po prawej
        else {
            parent.right = new Node(elem);
        }
        ++this.nodesCnt; // zwiekszamy ilosc elementow drzewa
    }

    /*Implementacja metody, ktora zwraca najblizsza osobe o priorytecie wiekszym od priorytetu podanego jako argument.
    Metoda zwraca wartosc nastepnika wystepujacego po elemencie o przekazanej wartosci lub null jesli nie istnieje.*/
    public Node findNext(Node root, int priority) {
        if(root == null) return null; // jezeli drzewo jest puste
        Node next = null;

        while (true) {
            // jezeli priorytet jest mniejszy od korzenia, odwiedzamy lewe poddrzewo
            if (priority < root.info.priority) {
                // nastepnik zostaje zaktualizowany na biezacy wezel zanim odwiedzimy lewe podrzewo
                next = root;
                root = root.left;
            }
            // jezeli priorytet jest wiekszy od korzenia, odwiedzamy prawe poddrzewo
            else if (priority > root.info.priority) {
                root = root.right;
            }
            // jezeli znaleziono wezel o podanym piorytecie, nastepnikiem jest minimalna wartosc w jego prawym podrzewie (jesli istnieje)
            else {
                if (root.right != null) {
                    next = this.findMinimum(root.right);
                }
                break;
            }
            // priorytet nie istnieje w drzewie
            if (root == null) {
                return null;
            }
        }
        // zwraca nastepnika, jesli istnieje
        return next;
    }

    /*Implementacja metody, ktora zwraca najblizsza osobe o priorytecie mniejszym od priorytetu podanego jako argument.
    Zwraca wartosc poprzednika wystepujacego przed elementem o przekazanej wartosci lub null jesli nie istnieje. */
    public Node findPrev(Node root, int priority) {
        if(root == null) return null; // jezeli drzewo jest puste
        Node prev = null;

        while (true) {
            // jesli szukany priorytet jest mniejszy niz korzen, odwiedzamy lewe podrzewo
            if (priority < root.info.priority) {
                root = root.left;
            }
            // jesli szukany priorytet jest wiekszy niz korzen, odwiedzamy prawe podrzewo
            else if (priority > root.info.priority) {
                // poprzednik zostaje zaktualizowany na biezacy wezel zanim odwiedzimy prawe podrzewo
                prev = root;
                root = root.right;
            }
            // jezeli znaleziono szukana wartosc, poprzednikiem jest maksymalna wartosc w jego lewym poddrzewie (jesli istnieje)
            else {
                if (root.left != null) {
                    prev = findMaximum(root.left);
                }
                break;
            }
            // szukany priorytet nie istnieje w drzewie
            if (root == null) {
                return null;
            }
        }
        // zwraca poprzednika jesli istnieje
        return prev;
    }

    /*Implementacja metody, ktora zwraca wezel o najwyzszym priorytecie.*/
    private Node findMaximum(Node node) {
        while (node.right!= null) { // przechodzimy po prawy poddrzewie, z uwagi na budowe drzewa BST elementy wieksze od korzenia znajduja sie na prawo
            node = node.right;
        }
        return node; // metoda zwraca wezel o najwiekszym priorytecie
    }

    /*Implementacja metody, ktora zwraca wezel o najnizszym priorytecie.*/
    private Node findMinimum(Node node) {
        while (node.left != null) { // przechodzimy po lewym poddrzewie, z uwagi na budowe drzewa BST elementy mniejsze od korzenia znajduja sie na lewo
            node = node.left;
        }
        return node; // metoda zwraca wezel o najmniejszym priorytecie
    }

    /*Implementacja metody, ktora zwraca oraz usuwa osobe o najwyzszym priorytecie. W tym celu wykorzystano metode "findMaximum", ktora
    wyszukuje osoby o najwyzszym priorytecie.*/
    Person dequemax() {
        Node found = findMaximum(this.root); // wyszukanie osoby o najwyzszym priorytecie
        if(found == null) { // jezeli nie znaleziony szukanej osoby o najwyzszym priorytecie
            return null;
        }
        Person person = found.info.copy(); // zapamietanie danych osoby, w celu ich wyswietlenia
        this.root = deleteNodeIteratively(this.root, found.info.priority); // usuniecie danego elementu/osoby
        // delete zwraca referencje na zmienione drzewo, musimy przypisac do this.root, bo inaczej wewnetrzny stan obiektu sie nie zmieni
        return person; // metoda zwraca usuwana osobe
    }

    /*Implementacja metody, ktora zwraca oraz usuwa osobe o najnizszym priorytecie. W tym celu wykorzystano metode "findMinimum", ktora
    wyszukuje osoby o najwyzszym priorytecie.*/
    Person dequemin() {
        Node found = findMinimum(this.root); // wyszukanie osoby o najnizszym priorytecie
        if(found == null) {
            return null;
        }
        Person person = found.info.copy(); // zapamietanie danych osoby, w celu ich wyswietlenia
        this.root = deleteNodeIteratively(this.root, found.info.priority); // usuniecie danego elementu/osoby
        // delete zwraca referencje na zmienione drzewo, musimy przypisac do this.root, bo inaczej wewnetrzny stan obiektu sie nie zmieni
        return person; // metoda zwraca usuwana osobe
    }

    /*Implemetacja metod koniecznych do usuniecia osoby o priorytecie podanym jako argument. W przypadku, gdy w drzewie usuwany wezel ma
    dwoch potomkow, zamienia go z jego nastepnikiem. Metoda usuwania wezla dzieli sie na 3 przypadki. Kiedy usuwany wezel posiada oba poddrzewa
    puste (nie ma dzieci), w takim przypadku rodzic usuwanego wezla nie posiada juz dzieci, wskazuje na null. W przypadku, w ktorym
    usuwany wezel posiada dokladnie jedno poddrzewo niepuste (posiada jedno dziecko, prawe lub lewe), rodzic usuwanego wezla bedzie posiadal
    dzieci tego usuwanego wezla (element na lewo i analogicznie na prawo od rodzica to bedzie dziecko usuwanego wezla). Jezeli usuwany wezel posiada oba poddrzewa
    niepuste (posiada dwoje dzieci, prawe i lewe), nalezy wyznaczyc wezel bedacy nastepnikiem po usuwanym wezle. Aby odnalezc nastepnik, nalezy
    od usuwanego wezla przejsc raz na prawo a nastepnie do konca w lewo. Nastepnie nalezy dokonac zamiany usuwanego wezla z jego nastepnikiem
    i dokonac przepiecia dzieci w zaleznosci od tego, czy takowe istnieja.*/
    public Node deleteNodeIteratively(Node root, int priority) {
        Node parent = null, current = root;
        boolean hasLeft = false;

        if (root == null)
            return root;

        /*Wyszukiwanie elementu, ktory chcemy usunac wraz z jednoczesnym aktualizowaniem pozycji jego rodzica oraz informacji, czy dany element
        posiada element polozony na lewo od niego*/
        while (current != null) {
            if (current.info.priority == priority) { // znaleziono element przeznaczony do usuniecia
                break;
            }
            parent = current; // aktualizacja rodzica
            if (priority < current.info.priority) { // jezeli priorytet jest mniejszy od priorytetu elementu przeznaczonego na usuniecie
                hasLeft = true; // obecny wezel posiada jakies elementy polozone na lewo od niego
                current = current.left;
            } else { // jezeli priorytet jest wiekszy od priorytetu elementu przeznaczonego na usuniecie
                hasLeft = false; // obecny wezel nie posiada jakis elementow polozonych na lewo od niego
                current = current.right;
            }
        }
        if (parent == null) { // jezeli rodzic nie istnieje (usuwamy korzen)
            return deleteNodeIteratively(current);
        }
        if (hasLeft) { // jezeli istnieja elementy na lewo, dokonujemy usuwania oraz przepinamy lewe dziecko rodzica
            parent.left = deleteNodeIteratively(current);
        } else { // jezeli nie istnieja elementy na lewo, dokonujemy usuwania oraz przepinamy prawe dziecko rodzica
            parent.right = deleteNodeIteratively(current);
        }
        return root;
    }

    private Node deleteNodeIteratively(Node node) { // usuwanie wezla/elementu

        if (node != null) { // jezeli wezel jest null, w wyniku zwracamy null, jezeli nie jest rowny null zmniejszamy liczbe elementow drzewa
            --nodesCnt; // oznaczamy usuniecie wezla, zmniejszenie liczby elementow drzewa
            if (node.left == null && node.right == null) { // usuwany wezel nie ma dzieci (dokladnie dwa drzewa puste),
                // odpinamy wezel, ktory chcemy usunac
                return null;
            }
            if (node.left != null && node.right != null) { // oba poddrzewa wezla sa niepuste, nastepuje wyszukanie nastepnika w poddrzewie
                Node inOrderSuccessor = deleteInOrderSuccessorDuplicate(node);
                node.info = inOrderSuccessor.info; // zamiana usuwanego wezla na nastepujacy
            } else if (node.left != null) { // usuwany wezel ma dokladnie jedno poddrzewo niepuste (lewe poddrzewo niepuste)
                node = node.left;
            } else { // usuwany wezel ma dokladnie jedno poddrzewo niepuste (prawe poddrzewo niepuste)
                node = node.right;
            }
        }
        return node;
    }

    private Node deleteInOrderSuccessorDuplicate(Node node) {
        Node parent = node;
        node = node.right; // wybieramy kolejny wezel na prawo
        boolean rightChild = (node.left == null);

        while (node.left != null) { // wyszukiwanie nastepnika, najmniejszego z poddrzewa (przechodzimy po lewym poddrzewie)
            parent = node;
            node = node.left;
        }
        /*Jezeli przejdziemy do ostatniego elementu lewego poddrzewa (nastepnika usuwanego wezla) sprawdzamy,
        czy ten nastepnik posiada dziecko, jezeli tak nalezy podpiac to dziecko do rodzica nastepnika usuwanego wezla,
        poniewaz dokonujemy zamiany usuwanego wezla z jego nastepnikiem.*/
        if (rightChild) {
            parent.right = node.right;
        } else {
            parent.left = node.right;
        }
        node.right = null;
        return node;
    }

/*Implementacja metody, ktora wypisuje liste osob w porzadku inorder. Metoda opiera sie na stosie, w ktorym zapamietujemy
 wywolania rekurencyjne. Innymi slowy mowiac stos wykorzystujemy do symulacji rekurencji. Najpierw wykonywane jest dzialanie na jednym
 z synow, nastepnie na rodzicu i na koncu na drugim synu. Przechodzac w ten sposob drzewo poszukiwan binarnych, otrzymuje sie posortowane
 wartosci wszystkich wezlow. Dzieje sie tak dlatego, ze w drzewie poszukiwan binarnych wartosci lewego syna wezla n oraz wszystkich jego
 potomkow sa mniejsze od wartosci n, a wartosci prawego syna i jego potomkow wisksze od wartosci n. Metoda tworzy pusty stos oraz inicjuje
 biezacy wezel jako root. Nastepnie dopoki aktualny wezel nie bedzie rowny NULL wkladamy aktualny wezel na stos a nastepnie ustawiamy
 aktualny wezel na element polozony na lewo od obecnie aktualnego wezla. Jezeli aktualny wezel ma wartosc NULL a stos nie jest pusty to
 zdejmujemy gorny element ze stosu oraz wypisujemy go na wyjscie a nastepnie ustawiamy aktualny wezel na element polozony na prawo od obecnie
 sciagnietego ze stosu elementu. Po tej operacji ponownie wykonujemy kroki zwiazane ze wstawianiem elementow na stos i przechodzeniu po lewym
 poddrzewie od danego elementu do uzyskania wezla rownego NULL. Jezeli obecny wezel ma wartosc NULL a stos jest pusty to swiadczy o zakonczeniu
 operacji. Zlozonosc czasowa metody wynosi O(n).*/
    String inorderIterative() {
        if (root == null) sb.toString(); // jezeli drzewo jest puste

        Stack stack = new Stack(this.nodesCnt); // utworzenie stosu
        Node curr = root; // ustawienie korzenia jako obecnego wezla

        while (curr != null || !stack.IsEmpty()) { // dopoki stos nie jest pusty lub wezel nie jest null
            while (curr != null) { // dopoki wezel nie jest null
                stack.push(curr); // wkladam obecny wezel na stos
                curr = curr.left; // ustawiam nowy wezel, przechodzimy po lewym poddrzewie
            }
            curr = stack.pop(); // zdejmuje gorny element ze stosu

            sb.append(curr.info.toString()).append(", "); // wypisuje zdejmowany element na wyjscie, wraz z potrzebnymi danymi charakteryzujacymi dana osobe
            curr = curr.right; // ustawiam nowy wezel, przechodzimy po prawym poddrzewie
        }

        // jezeli StringBuilder nie jest pusty to odcinamy dwa ostatnie znaki, poniewaz na koncu nie wypisujemy przecinka ani spacji
        if(sb.length()>0) sb.setLength(sb.length()-2);
        String result = sb.toString(); // zapisujemy ostateczny wynik do zmiennej "result"
        sb.setLength(0); // po wykonaniu metody i przypisaniu wyniku do zmiennej "result" nastepuje ustawianie dlugosci sekwencji znakow rownej zero
        return result; // zwracamy wynik
    }

/*Implementacja metody, ktora wypisuje liste osob w porzadku preorder. Metoda opiera sie na stosie, w ktorym zapamietujemy
 wywolania rekurencyjne. Innymi slowy mowiac stos wykorzystujemy do symulacji rekurencji. Dzialanie jest wykonywane najpierw
 na rodzicu, nastepnie na synach. Metoda tworzy pusty stos oraz ustawia korzen jako obecny wezel. Dopoki stos nie jest pusty metoda wypisuje
 na wyjscie dzieci lezace na lewo. Jezeli istnieje prawy potomek obecnego wezla, nalezy element lezacy na prawo od wezla umiescic na stosie.
 Innymi slowy mowiac dzieci lezace na prawo umieszczamy na stosie. Po przejsciu lewego poddrzewa (jezeli curr jest NULL) oraz
 jezeli stos nie jest pusty, zdejmujemy gorny element ze stosu (prawe dziecko) i wypisujemy go na wyjscie. Jezeli obecny wezel ma wartosc
  NULL a stos jest pusty to swiadczy o zakonczeniu operacji. Zlozonosc czasowa metody wynosi O(n).*/
    String preorderIterative() {
        return preorderIterative(this.root);
    }

    String preorderIterative(Node node) {

        if (node == null) {
            return sb.toString();
        }
        Stack stack = new Stack(this.nodesCnt); // utworzenie stosu
        Node curr = node; // poczatkowo nastepuje ustawienie korzenia jako obecnego wezla

        while (curr != null || !stack.IsEmpty()) { // dopoki stos nie jest pusty lub wezel nie jest null
            while (curr != null) { // dopoki wezel nie jest null
                sb.append(curr.info.toString()).append(", "); // wypisuje element na wyjscie, wraz z potrzebnymi danymi charakteryzujacymi dana osobe
                if (curr.right != null) stack.push(curr.right); // jezeli wezel na prawo od obecnego wezla nie jest rowny null nalezy wlozyc go na stos
                curr = curr.left; // ustawiam nowy wezel, przechodzimy po lewym poddrzewie
            }
            if (!stack.IsEmpty()) { // jezeli stos nie jest pusty
                curr = stack.pop(); // zdejmujemy ze stosu gorny element
            }
        }
        // jezeli StringBuilder nie jest pusty to odcinamy dwa ostatnie znaki, poniwaz na koncu nie wypisujemy przecinka ani spacji
        if(sb.length()>0) sb.setLength(sb.length()-2);
        String result = sb.toString(); // zapisujemy ostateczny wynik do zmiennej "result"
        sb.setLength(0); // po wykonaniu metody i przypisaniu wyniku do zmiennej "result" nastepuje ustawianie dlugosci sekwencji znakow rownej zero
        return result; // zwracamy wynik
    }

    /*Implementacja metody, ktora wypisuje liste osob w porzadku postorder. Metoda opiera sie na dwoch stosach, w ktorych zapamietujemy
  wywolania rekurencyjne. Innymi slowy mowiac stosy wykorzystujemy do symulacji rekurencji. Dzialanie jest wykonywane najpierw na wszystkich
  synach, na koncu na rodzicu. Poczatkowo umieszczamy korzen w pierwszym stosie. Dopoki pierwszy stos nie jest pusty dokonujemy w petli
  nastepujace czynnosci: zdejmujemy wezel z pierwszego stosu i przenosimy do na drugi stos oraz umieszczamy lewe a nastepnie prawe
  elementy potomne danego wezla w pierwszym stosie. Kiedy pierwszy stos bedzie pusty, nalezy wypisac jako wynik zawartosc drugiego stosu.
   Zlozonosc czasowa metody wynosi O(n).*/
    String postorderIterative(Node node) {
        Stack stack1, stack2;
        stack1 = new Stack(this.nodesCnt); // utworzenie stosu
        stack2 = new Stack(this.nodesCnt); // utworzenie stosu

        if (node == null) {
            return sb.toString();
        }
        stack1.push(node); // poczatkowo umieszczamy korzen na pierwszym stosie
        while (!stack1.IsEmpty()) {
            Node temp = stack1.pop(); // pobieramy gorny element z pierwszego stosu i umieszczamy ten element na drugim stosie
            stack2.push(temp);

            // umieszczamy lewe i prawe dziecko usuwanego elementu na stosie
            if (temp.left != null) stack1.push(temp.left);
            if (temp.right != null) stack1.push(temp.right);
        }
        // wypisywanie wszystkich elementow z drugiego stosu
        while (!stack2.IsEmpty()) {
            Node temp = stack2.pop();
            sb.append(temp.info.toString()).append(", ");
        }
        // jezeli StringBuilder nie jest pusty to odcinamy dwa ostatnie znaki, poniwaz na koncu nie wypisujemy przecinka ani spacji
        if(sb.length()>0) sb.setLength(sb.length()-2);
        String result = sb.toString(); // zapisujemy ostateczny wynik do zmiennej "result"
        sb.setLength(0); // po wykonaniu metody i przypisaniu wyniku do zmiennej "result" nastepuje ustawianie dlugosci sekwencji znakow rownej zero
        return result; // zwracamy wynik
    }

    /*Implementaja metody, ktora zwraca wysokosc drzewa. Wysokosc drzewa to dlugosc sciezki od korzenia do najglebszego wezla drzewa.*/
    int height() {
        if(this.root == null) return 0; // jezeli drzewo jest puste, wysokosc drzewa wynosi zero
        return height(this.root);
    }
    private int height(Node node) {
        if (node == null) return -1; // jezeli nie ma wezla, zwracamy -1, poniewaz na koncu dodajemy jeden do wyniku
        // dokonujemy obliczenia wysokosci dla lewego i prawego poddrzewa
        int max = max(height(node.left), height(node.right)); // zwraca dlugosc najdluzszej sciezki (wieksze prawe lub lewe poddrzewo)
        // do wyniku dodajemy jeden, poniewaz przechodzimy do kolejnych poziomów w dol. Jezeli kolejnego poziomu nie ma (wezel jest nullem)
        // nastepuje korekta w postaci -1, gdybysmy nie dodali 1 rekurencja w zadnym miejscu nie zwracalaby wartosci.
        return max + 1;
    }
    private int max(int a, int b) { // metoda zwracajaca wiekszy element z dwoch elementow podanych jako argument
        return (a >= b ? a : b);
    }
}

/*Implementacja klasy Node zgodnie ze specyfikacja podana w tresci zadania.*/
class Node {
    public Person info; // element danych (klucz)
    public Node left; // lewy potomek wezla
    public Node right; // prawy potomek wezla

    Node(Person info) { // konstruktor
        this.info = info;
    }
}

/* Implementacja klasy Person zgodnie ze specyfikacja podana w tresci zadania.*/
class Person {
    public int priority; // priorytet danej osoby
    public String name; // imie danej osoby
    public String surname; // nazwisko danej osoby

    Person(int priority, String name, String surname) { // konstruktor
        this.priority = priority;
        this.name = name;
        this.surname = surname;
    }

    public Person copy() { // metoda wykorzystywana do zapamietania danych dotyczacych usuwanej osoby, wykorzystywana do operacji DEQUEMIN oraz DEQUEMAX
        return new Person(this.priority, this.name, this.surname);
    }

    public String toString() { // metoda wykorzystywana do odpowiedniego wypisywania danych dotyczacych zadanej osoby
        return this.priority + " - " + this.name + " " + this.surname;
    }
}

/*Implementacja Stosu wykorzystywanego do operacji PREORDER, INORDER oraz POSTORDER */
class Stack {
    private int MaxSize; // rozmiar tablicy zawierajacej stos
    private Node[] Elements; // tablica zawierajaca stos
    private int TopElementIndex; // indeks szczytu stosu

    public Stack(int Size) { // konstruktor
        MaxSize = Size; // ustawiamy rozmiar tablicy
        Elements = new Node[MaxSize]; // tworzymy tablice dla elementow
        TopElementIndex = 0; // na poczatku nie ma elementow
    }

    public boolean IsEmpty() { // metoda zwraca informacje czy stos jest obecnie pusty
        return (TopElementIndex == 0);
    }

    public boolean IsFull() { // metoda zwraca informacje czy stos jest pelny
        return (TopElementIndex == MaxSize);
    }

    public Node top() { // metoda zwraca wartosc, ktora jest na szczycie stosu
        if (IsEmpty())
            return null;
        else
            return Elements[TopElementIndex];
    }

    public void push(Node x) { //metoda wstawia element na szczyt stosu
        if (!IsFull())
            Elements[TopElementIndex++] = x;
    }

    public Node pop() { // jezeli stos nie jest pusty metoda usuwa element ze szczytu stosu oraz zwraca go
        if (IsEmpty())
            return null;
        else
            return Elements[--TopElementIndex];
    }
}

/*Przykladowe testy:

IN:
1
37
CREATE PREORDER 11 45 Marzenna Kowaluk 42 Stanislaw Wierzbicki 36 Samanta Sulej 30 Daria Debowa 13 Laura Moniuszki
32 Malgorzata Lewczuk 39 Gabriela Rewers 49 Ilona Pliszko 48 Anna Lewandowska 51 Zbyszek Bogdaniec 52 Zosia Jurga
INORDER
PREORDER
POSTORDER
DEQUEMIN
INORDER
DEQUEMAX
NEXT 30
NEXT 48
NEXT 12
PREV 39
PREV 51
PREV 35
HEIGHT
DELETE 36
HEIGHT
DELETE 35
INORDER
PREORDER
POSTORDER
NEXT 51
PREV 33
ENQUE 14 Zosia Lupicka
ENQUE 54 Marek Stepien
PREV 14
NEXT 38
DELETE 31
DELETE 12
INORDER
POSTORDER
ENQUE 45 Ola Majos
ENQUE 33 Luiza Maurycy
PREV 45
NEXT 40
DEQUEMIN
DEQUEMAX
HEIGHT

OUT:
ZESTAW 1
INORDER: 13 - Laura Moniuszki, 30 - Daria Debowa, 32 - Malgorzata Lewczuk, 36 - Samanta Sulej, 39 - Gabriela Rewers, 42 - Stanislaw Wierzbicki, 45 - Marzenna Kowaluk, 48 - Anna Lewandowska, 49 - Ilona Pliszko, 51 - Zbyszek Bogdaniec, 52 - Zosia Jurga
PREORDER: 45 - Marzenna Kowaluk, 42 - Stanislaw Wierzbicki, 36 - Samanta Sulej, 30 - Daria Debowa, 13 - Laura Moniuszki, 32 - Malgorzata Lewczuk, 39 - Gabriela Rewers, 49 - Ilona Pliszko, 48 - Anna Lewandowska, 51 - Zbyszek Bogdaniec, 52 - Zosia Jurga
POSTORDER: 13 - Laura Moniuszki, 32 - Malgorzata Lewczuk, 30 - Daria Debowa, 39 - Gabriela Rewers, 36 - Samanta Sulej, 42 - Stanislaw Wierzbicki, 48 - Anna Lewandowska, 52 - Zosia Jurga, 51 - Zbyszek Bogdaniec, 49 - Ilona Pliszko, 45 - Marzenna Kowaluk
DEQUEMIN: 13 - Laura Moniuszki
INORDER: 30 - Daria Debowa, 32 - Malgorzata Lewczuk, 36 - Samanta Sulej, 39 - Gabriela Rewers, 42 - Stanislaw Wierzbicki, 45 - Marzenna Kowaluk, 48 - Anna Lewandowska, 49 - Ilona Pliszko, 51 - Zbyszek Bogdaniec, 52 - Zosia Jurga
DEQUEMAX: 52 - Zosia Jurga
NEXT 30: 32 - Malgorzata Lewczuk
NEXT 48: 49 - Ilona Pliszko
NEXT 12: BRAK
PREV 39: 36 - Samanta Sulej
PREV 51: 49 - Ilona Pliszko
PREV 35: BRAK
HEIGHT: 4
HEIGHT: 4
DELETE 35: BRAK
INORDER: 30 - Daria Debowa, 32 - Malgorzata Lewczuk, 39 - Gabriela Rewers, 42 - Stanislaw Wierzbicki, 45 - Marzenna Kowaluk, 48 - Anna Lewandowska, 49 - Ilona Pliszko, 51 - Zbyszek Bogdaniec
PREORDER: 45 - Marzenna Kowaluk, 42 - Stanislaw Wierzbicki, 39 - Gabriela Rewers, 30 - Daria Debowa, 32 - Malgorzata Lewczuk, 49 - Ilona Pliszko, 48 - Anna Lewandowska, 51 - Zbyszek Bogdaniec
POSTORDER: 32 - Malgorzata Lewczuk, 30 - Daria Debowa, 39 - Gabriela Rewers, 42 - Stanislaw Wierzbicki, 48 - Anna Lewandowska, 51 - Zbyszek Bogdaniec, 49 - Ilona Pliszko, 45 - Marzenna Kowaluk
NEXT 51: BRAK
PREV 33: BRAK
PREV 14: BRAK
NEXT 38: BRAK
DELETE 31: BRAK
DELETE 12: BRAK
INORDER: 14 - Zosia Lupicka, 30 - Daria Debowa, 32 - Malgorzata Lewczuk, 39 - Gabriela Rewers, 42 - Stanislaw Wierzbicki, 45 - Marzenna Kowaluk, 48 - Anna Lewandowska, 49 - Ilona Pliszko, 51 - Zbyszek Bogdaniec, 54 - Marek Stepien
POSTORDER: 14 - Zosia Lupicka, 32 - Malgorzata Lewczuk, 30 - Daria Debowa, 39 - Gabriela Rewers, 42 - Stanislaw Wierzbicki, 48 - Anna Lewandowska, 54 - Marek Stepien, 51 - Zbyszek Bogdaniec, 49 - Ilona Pliszko, 45 - Marzenna Kowaluk
PREV 45: 42 - Stanislaw Wierzbicki
NEXT 40: BRAK
DEQUEMIN: 14 - Zosia Lupicka
DEQUEMAX: 54 - Marek Stepien
HEIGHT: 5
*/