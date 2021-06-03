## INTRO
remarques du cours établis par Anass AIT BEN EL ARBI pendant les séances de TP (manipulation de IDL - CORBA en Java)
## TP 1: approche par heritage
### étapes utilisés: serveur
- init ORB
- récupérer RootPOA
- instancier la casse d'implémentation
- publier l'object précedent `c_impl._this(orb)`
- création d'un fichier pour enregistrer le IOR
### étapes utilisés: client
- init ORB
- lecture du fichier contenant l'IOR
- convertion de la chaine de caratère en object ORB
- création de l'instance de l'object avec la méthode `narrow()`
## TP 2: approche par délégation

## TP 3: utilisation de NameSpace
Before running the code execute the following code : 

`java Server -ORBInitRef`

then run the Server and the Client 

 