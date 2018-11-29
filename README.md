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
- TestBitStuffinTest (plusieurs tests sur l'envoye de bits en appliquant un bit stuff, sur la réception de bits en applicant un "dé-bit stuff" et des tests sur l'égalité lorsque ces derniers sont appliqués simultanément).
- ErrorTestingTest : Cette classe de tests contient des fonctions qui tests la robustesse du flux et du contrôle de nos trames. Malheureusement, pour pouvoir forcer des erreurs, on doit uncomment certaines parties du code pour induire des erreurs. 

**Prenez note 2** <br>
Il y a plusieurs fichiers txt dans au niveau de la racine du projet. Malheureusement, il se peut que vous aillez une erreur au début spécifiant qu'un fichier "....txt" ne peut être lu. Simplement mettre le fichier au même niveau que vous avez compilé Sender et Receiver. Il se peut bien que vous ne voyez pas cette erreur du tout. Pour une raison quelconque, dépendamment de la version de Java que vous avez l'erreur peut être présente.