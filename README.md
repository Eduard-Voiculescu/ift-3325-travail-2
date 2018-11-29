### Travail 2 - IFT 3325 Téléinformatique

#### Travail fait par 
#### EID Alain - 20061065
#### VOICULESCU Eduard - 20078235

**Objectif** : Implémentation d'une version simplifiée de protocole HDLC. 

### Instructions

1) Compiler le Sender et le Receiver .
- javac Sender.java  
- javac Receiver.java
2) Lancer le programme.
- java Receiver num_de_port_desire
- java Sender 127.0.0.1 num_de_port_desire fichier_desire.txt 0

**Prenez note** <br>
Nous avons fait plusieurs classes de Test. Les tests sont spécifiquement des JUnit. Nous avons testé beaucoup de fonctionnalités particulières du programme. <br>
Parmi nos tests, vous trouverez : 
- CharacterConversionTest (Convertir des charactères ou des chaines de caractères en binaire et vice-versa).
- CheckSumTest (plusieurs tests pour vérifier la robustesse de notre algorithme du calcul de CRC).
- TestBitStuffinTest (plusieurs tests sur l'envoye de bits en appliquant un bit stuff, sur la réception de bits en applicant un "dé-bit stuff" et des tests sur l'égalité de lorsque ces derniers sont appliqués simultanément).
- ErrorTestingTest : Cette classe de tests contient des fonctions qui tests la robustesse du flux et du contrôle de nos trames. Malheureusement, pour pouvoir forcer des erreurs, on doit uncomment certaines parties du code pour induire des erreurs. 