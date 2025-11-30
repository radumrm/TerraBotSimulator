Tema1 323CA Marincea Radu-Mihail

# OVERVIEW
___________________________
Aceasta tema implementeaza o simulare a unui ecosistem autonom gestionat de
un robot numit TerraBot. Programul simuleaza interactiuniile dinamnice intre
diverse entitati intre ele si interactiuniile dintre robot si entitati pe baza
unei harti si a comenziilor date robotului

# PACHETE
___________________________
Programul a fost impartit in urmatoarele pachete:
## entites:
    Pachet in care se afla tot despre entitati. Contine:
  * **Clasa abstracta Entity**: aceasta este clasa mostenita de fiecare entitate specifica (Animal 
    , Soil, Water, Plant si Air). Contine date comune si esentiale pentru fiecare entitate: coordonate
    , nume, masa, tip, minutul la care au fost scanate. Aceasta mai are si o metoda improveEnvironment() pentru
    comanda improveEnvironment suprascria doar de entiatiile care o folosesc: Animal, Water si Plant

  * **Pachetul environment**: In acest pachet se afla alte 2 pachete, air si soil dar si clasa water, reprezinta
    entitatiile mediului (sau fara viata)
    * **Pachetul air**:
      * Clasa abstracta Air: retine campurile valabile pentru fiecare tip de air. Metodele abstracte de
        getAirQuality() si getMaxScore(), care difera intre tipurile de air, astfel acestea au fiecare o
        implementare pentru acestea. Metoda abstracta addSpecificFieldsToNode(), care returneaza campul
        specific fiecarui tip de aer. Retine timpul la care s-a produs changeWeather si calculeaza toxicitatea
      * Clasele: DesertAir, MountainAir, PolarAir, TemperateAir, TropicalAir contin implementarea metodelor
        abstracte din Air si au in plus variabilele specifice tipului sau ex: DesertAir -- DustParticles

    * **Pachetul soil**:
      * Clasa abstracta Soil, retine campurile valabile pentru fiecare tip se soil si metoadele abstracte
        possibilityToGetStuckInSoil(), getSoilQuality() de a ramane blocat si metoda addSpecificFieldsToNode care returneaza
        variabila specifica campului
      * Clasele: DesertSoil, ForestSoil, GrasslandSoil, SwampSoil, TundraSoil contin implementarea metodelor
        abstracte si retin variabilele specifice lor

    * **Clasa Water**:
        retine campurile specifice pentru entiatiile tip water, are metoda getWaterQuality si improveEnvironment

    * **Pachetul animals**:
      * Clasa abstracta Animal, retine campurile specifice pentru entitatiile de tip animal. Metodele abstracte
        getAnimalPossibilityToAttack() si eat(). Si metodele: possibilityToBeAttackedByAnimal(), executeMove(), move(), setDead()
        eatPlantOrWater() si improveEnvironment().
        * Metoda move(): pentru a determina urmatorea patratica pe care se va misca animalul, ne folosim de algoritmul
            din enunt. Pentru implementarea acestuia am creat 2 liste ce contin entitatiile scanate de Water si Plant
            in ordinea cautarii din enunt, astfel primul element din Water si primul element din Plant corespund cu
            primul vecin in ordinea cautarii (cel de sus), daca un vecin este out of bounds, punem null in liste
            pentu a pastra ordinea de cautare. Dupa, algoritmul implementat este cel din enunt, singura precizare este
            ca am adaugat o noua conditie peste tot algoritmul, sa nu mai fie un alt animal pe acea casuta deoarece
            se vor suprascire. Aceasta metoda a fost problema cea mai grea intampinata de mine in tema deoarece enuntul
            este foarte vad, insa precizeaza un lucru concret: nu se pot afla mai multe entitati de ac. fel pe o
            patratica. In intrebarile de pe forum pe 29, cu o zi inainte de deadline aflam ca aparent 2 animale pot
            sta pe ac casuta (timp de un timestamp din ce am inteles eu), pana cand unda se mananca pe cealalta in
            ordinea scanarii, astfel am implementat doar miscarea de la animalele care nu pot manca si alte animale
        * Metoda eatPlantOrWater(): aceasta este o metoda in care animalul manca plante si/sau water, se incearca
          consumarea ambelor entitati, iar la final se verifica daca s-au putut manca amandoua, una sau niciuna si
          se adauga organicMatter cat trebuie.
        * Metoda abstracta eat: este suprascrisa de fiecare tip de animal, in Detrivores, Herbivores si Omnivores
          se apeleaza eatPlantOrWater(), iar in Carnivores si Parasites se verifica si posibilitatea de a manca
          un alt animla, daca nu se poate manca un alt animal, se apeleaza eatPlantOrWater(). Dupa cum am scris mai sus
          miscarea este doar a unui animal ce nu poate manca alte animale, astfel nu se vor manca niciodata
          doua animale si se va apela doar eatPlantOrWater().
    
    * **Clasa CreateEntity**:
      * Aceasta are metodele createAir, createSoil etc... pentru a creea obiectele primite de AirInput, SoilInput etc
    
## map:
    Pachet care gestioneaza atat harta cat si fiecare patratica individuala. Contine:
  * **Clasa Box**:
    *  retine fiecare entitate ce se afla pe ea. Contine metoda getCost() care calculeaza costul casutei 
      (necesar pentru moveRobot)
  * **Clasa SimulationMap**:
    * retine harta, adica matricea de box-uri, dimensiunile acesteia. Are metodele GetBox(), ce returneaza
    box-ul gasit la coordonatele date si populateMap(), ce primeste TerritorySectionParamsInput si populeaza
    harta, adaugand fiecare entitate in box-ul ei specific

## robot:
    Pachet care gestioneaza tot ce tine de terraBot
  * **Clasa TerraBot**
    * retine coordonatele, energia ramasa. Tot odata retine timestamp-ul la care robotul va termina incarcarea,
      inventarul tip lista cu obiectele scanate, dar si scannedEntities tot o lista ce tine minte obiectele scanate
      diferenta fiind ca atunci cand terraBot-ul da improveEnv, obiectul se va sterge din lista, dar va ramane
      in scannedEntities. Si un Map ce tine corespondenta dintre numele unui obiect (care este unic) si fact-urile
      invatate despre acesta. De asemenea, si variabila finishedChargeTimpestamp, care retine timpul la care se va
      termina de incarcat
    * isCharging(): primeste ca parametrul timestamp-ul global al programului si returneaza daca se incarca sau nu
    * recharge(): adauga energie robotului si modifica finishedChargeTimpestamp
    * addToInventory(): adaugam in inventar entitate scanata, dar si in scannedEntities
    * getWaterFromInventory(): returnam entitatiea tip Water de la coordonatele x si y daca este scanata si in inventar
    * getPlantFromInventory(): idem doar ca cu plante
    * hasEntityScanned(): verificam daca mai avem o entitate scanata cu ac. nume
    * addFact(): daca exista deja o lista cu fact-uri pentru acel name al enitatii, il adaugam, daca nu
      creem o lista si adaugam
    * hasFactsAbout(): verificam daca avem fact-uri despre un nume de entitate
    * getEntityFromInventory(): cautam o entitate dupa nume in inventar si o returnam
    * getEntityFromInventory(): stergem o entitate dupa nume din inventar (de la comanda improveEnv)

## utils:
    Contine clasa finala MaginNumer, pentru checkstyle

## process_commands:
    Proceseaza comenziile, apeleaza metodele aferente comenzii si returneaza output-ul ei
  * **Clasa CommandProcessor**
    * Retine referinte catre, harta, robot si ObjectMapper pentru afisaere, in plus retine si o variabila boolean
      simulationStarted care indica daca o simulare a inceput sau nu. Aceasta clasa este nucleul programului aici se
      intampla toate operatiile, aici se apeleaza fiecare CommandInput primit si fiecare metoda din celelalte clase
      ale programului. 
    * processCommand(): Metoda principala. Intoarce un obiect tip ObjectNode in main, dupa care acesta este adaugat
      la output, astfel toate metodele care apeleaza o commanda, dau return la un obiect ObjectNode catre processCommand,
      iar acesta il trimite in main (processCommand este apelat in main). Este un switch mare care verifica ce comanda
      se doreste sa se apeleze, iar el o va apela mai deprarte, daca nu gaseste comanda, returneaza null (conditie 
      verificata in main). Apeleaza metodele cu ac nume ca comanda.
    * Metode Helper: aceste metode returneaza un ObjectNode, doar afisari, fara alta logica.
      * createNode(command, message), returneaza nodul cu campurile command, message si timestamp
      * createSoilNode(), createAirNode() etc returneaza un ObjectNode pentru comenziile printMap si printEnv
    * getQualityAsString(): primeste o valoare si returneaza daca aceasta este good, moderate sau poor
    * checkStartAndCharge(): aceasta verifica cele mai frecvente erori, daca simularea nu a inceput
      sau robotul se incarca, se apeleaza in aproape toate metodele
    * startSimulation(): seteaza campul simulationStarted cu true
    * endSimulation(): seteaza campul simulationStarted cu flase
    * printEnvConditions(): printeaza proprietatiile boxului in care se afla robotul
    * printMap(): printeaza box cu box toata harta
    * moveRobot(): verificab care dintre casutele vecine ale robotului are costul cel mai mic, apeleaza box.getCost()
    , dupa ce se determina cea mai buna casuta, se verifca daca robotul are baterie cel putin cat costul,
      cu terraBot.getEnergy(), daca are se muta si se schimba nivelul bateriei
    * getEnergyStatus(): intoarce nivelul de energie al robotului, apeleaza terraBot.getEnergy()
    * rechargeBattery(): incepe reincarcarea robotului, apeleaza terraBot.recharge() si verifica diferenta de timp 
      dintre timpestamp-ul curent si la cel care se termina incarcarea, si se fac atatea updateEnv()
    * changeWeatherContition(): determina tipul furtunii, trece prin toata harta si cand gaseste tipul de air
      pentru care se apeleaza comanda, apeleaza air.set...
    * scanObject(): se verifica daca avem baterie, se determina tipul obiectului scanat dupa cei trei parametrii
      are sunet -> animal, are smell sau culoare -> plant, nu are nimic -> apa. Se iau coordonatele robotului
      cu terraBot.getX .getY si se verifica daca tipul obiectlui pe care dorim sa il scanam se afla pe acel box
      , daca este il scanam
    * learnFact(): se verifica daca robotul are destula energie, se verifica daca numele enitatii despre care
      dorim sa invatam un fac a fost scanata in trecut cu terraBot.hasEnityScanned, daca a fost scanat, adugam
      fact-ul cu terraBot.addFact
    * printKnowledgeBase(): trecem prin toate entitatiile scanate, iar pentru fiecare printam fact-urile invatata
    * improveEnvironment(): se verifica bateria, se determina tipul de improve pe care dorim sa il facem plantVegetation
      , fertilizeSoil, increaseHumidity si increaseMoisture. Se verifica daca avem o entitate in inventar cu acel
      nume, daca avem, se produce improve-ul cu entity.improveEnvironment si se scoate entitatea din inventar
    * updateEnvironment(): Dificultatea principala a temei, mai ales pentru entitatiile de tip animal. Respectand
      ordinea in care trebuie facute update-urile entitatiilor astfel facem update-urile in aceasta ordine:
      * Pentru toate plantele din lista scannedEntities: planta aceasta va creste cu plant.grow()
        se verifica daca aceasta este dead, daca este dispare de pe harta, daca nu este se apeleaza metoda
        plant.oxygenLevel() si ii adaugam lui air prin air.addOxygenLevel()
      * Pentru toate soilurile din lista scannedEntities: calculam varsta, cand a fost scanata - timpul curent
        , la fiecare daca o planta este langa apa primeste plant.grow(), iar odata la 2 iteratii, airHumidity
        si waterRetention cresc
      * Pentru fiecare animal din lista scannedEntities: verificam daca animalul este dead, daca nu este ii calculam
        varsta, ca la soil, si odata la 2 iteratii vom va manca cu apelul animal.eat si se va muta cu apelul 
        animal.move, animal.move schimba coordonatele entitatii dar nu si harta, asa ca pe urmatoarea linie,
        il punem si pe harta

## main:
    Initializarea hartii, a robotului si a procesorului de comenzi, afisarea output-urile
  * **Clasa Main**
    * avem variabila statica timestamp, pentru un ceas global
    * in metoda action():
      * salvam comneziile in lista commands, salvam simulariile in lista simulations si initializam o variabila
        currentSimulations cu 0, ce reprezinta indexul simularii curente. Se initializeaza harta, robotul, dar si
        procesorul de comenzi. La fiecare comanda se face updateEnv, se trimite comanda catre classa CommandProcessor
        cu processor.processCommand(ComandInput) si se adauga output-ul comenzii la output-ul progamului, aditional
        se verifica daca comanda este endSimulation si output-ul este "Simulation has ended.", iar in acest caz se 
        verifica daca mai sunt dispoinibile si alte simulari, vedem daca indexul simularii curente este mai mic
        ca numarul de simulari (simulations.size()) - 1, daca este indexul creste, iar harta, robotul si processorul
        de comenzi sunt reintializati