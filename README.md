# File-Manager
EN: We would like to create a File Management Tool to describe, classify and search for users' favorite files on a given machine. This file manager uses Tags (also called tags or keywords) to describe or mark files that users want to remember and find easily. It is a program that presents a graphical interface (JavaFX), uses a relational database for the management of users' favorite files, using tags, and essentially provides the following functionalities:

1.Creation of a user account & Identification
This tool must allow a new user to create an account. The new user must provide information such as name, surname, email, login and password. All registered users (with an account) must identify themselves each time they use the tool, giving their login and password.

2.Marking favorite files
This tool must allow an identified user to select files among those existing on the machine in question, to mark them as favorite files. It must indeed allow the user to mark each selected file, by associating a description of its content, its importance, its usefulness, ... It is therefore a question of filling in for each selected file, a set of descriptive fields , such as :
- Author (the user can associate an author to the file)
- Title* (the user must assign a title representative of the contents of the file)
-Tags* (here, the user must introduce a set of keywords (called tags) characteristic of the content of the file, which will allow him to search and easily find this file later)
-Summary (he can also add a small summary of the contents of this file)
-Comments (in this field, the user can add comments about the file)
NB: only fields with * are mandatory.

Example 1: Marking the file “C:\mybooks\book2.pdf” as a favorite file:
-Author: Steven Halim
-Title*: Competitive Programming
-Tags*: competition; algorithmic; programming; data structure; complexity
-Summary :
-Comments: Very important: to consult for the preparation of the next Programming Challenge!

Example 2: Marking the file “C:\courses\java\chap6.ppt” as a favorite file:
-Author :
-Title*: Exception management
-Tags*: java, exceptions; try; catch; throw; throws
-Summary: Operation of the mechanism for handling checked and unchecked, predefined and undefined, exceptions in the java language.
-Comments: review examples 3 and 4 in particular and test them on the machine!

2.Updating favorite files
The program must allow an identified user to update a file already marked as
favourite, and which consists of the possibility of:
-modify the fields associated with this file.
-remove markup from this file. This implies that this file will no longer be part of the
user's favorite files.

3.List favorite files
The program must allow an identified user to list all of his favorite files. This consists of displaying all the marked files with the details of the descriptions associated with them. The listing can be done (depending on the user's choice) either on screen or in a text file.

3.Search for files
The program must allow an identified user, different types of search in his favorite files: by author, by title, by tag (keyword). The search result must provide, for each file found, its title as well as its physical name (with the path of its location).
-Apart from the favorite files, add the possibility of marking web pages (by giving their link).

4.General Information
This feature is only accessible by the application administrator. It provides different types of information about users and their favorite files, such as:
-List of registered users.
-Number of favorite files per user
-List of different tags (keywords) used by a given user to mark his files. This list will in fact represent all of the interests of this user.
-List of different tags (of all users) and Number of users per tag. This will allow you to see the most popular themes.
-List of users for a given tag. This will help identify users with the same interests.
The listing can be done (depending on the choice of the administrator) either on screen or in a text file.
-Manage the possibility of modifying user's password and cases of forgotten passwords

--------------------------------------------------------------------------------------------------------------------------------------------------

FR: On voudrait réaliser un Outil de Gestion de Fichiers permettant de décrire, classer et rechercher les fichiers favoris des utilisateurs sur une machine donnée. Ce gestionnaire de fichiers utilise des Tags (appelés encore étiquettes ou bien mots clés) pour décrire ou marquer les fichiers dont les utilisateurs souhaitent se souvenir et retrouver facilement. Il s’agit d’un programme qui présente une interface graphique (JavaFX), utilise une BD relationnelle pour la gestion des fichiers favoris des utilisateurs, à l’aide de tags, et assure essentiellement les fonctionnalités suivantes :

1.Création d’un compte utilisateur & Identification
Cet outil doit permettre à un nouvel utilisateur de créer un compte. Le nouvel utilisateur doit fournir des informations comme le nom, prénom, email, login et mot de passe. Tous les utilisateurs inscrits (possédant un compte), doivent s’identifier à chaque utilisation de l’outil, en donnant leur login et mot de passe.

2.Marquage de fichiers favoris
Cet outil doit permettre à un utilisateur identifié, de sélectionner des fichiers parmi ceux existants sur la machine en question, pour les marquer comme fichiers favoris. Il doit en effet permettre à l’utilisateur de marquer chaque fichier sélectionné, en lui associant une description sur son contenu, son importance, son utilité, ... Il s’agit donc de remplir pour chaque fichier sélectionné, un ensemble de champs descriptifs, tels que :
-Auteur (l’utilisateur peut associer un auteur au fichier)
-Titre* (l’utilisateur doit affecter un titre représentatif du contenu du fichier)
-Tags* (ici, l’utilisateur doit introduire un ensemble de mots clés (appelés tags) caractéristiques du contenu du fichier, ce qui lui permettra de rechercher et retrouver facilement ce fichier plus tard)
-Résumé (il peut aussi ajouter un petit résumé du contenu de ce fichier)
-Commentaires (dans ce champs, l’utilisateur pourra ajouter des commentaires à propos du fichier)
NB : seuls les champs avec * sont obligatoires.

Exemple 1 : Marquage du fichier « C:\meslivres\livre2.pdf » comme un fichier favori :
-Auteur : Steven Halim
-Titre* : Competitive Programming
-Tags* : compétition; algorithmique; programmation; structure de données; complexité
-Résumé :
-Commentaires : Très important : à consulter pour la préparation du prochain Programming Challenge !

Exemple 2 : Marquage du fichier « C:\cours\java\chap6.ppt » comme un fichier favori :
-Auteur :
-Titre* : Gestion des exceptions
-Tags* : java, exceptions; try; catch; throw; throws
-Résumé : fonctionnement du mécanisme de gestion des exceptions contrôlées et non contrôlées, prédéfinies et non prédéfinies, dans le langage java.
-Commentaires : revoir en particulier les exemples 3 et 4 et les tester sur machine !

2.Mise à jour des fichiers favoris
Le programme doit permettre à un utilisateur identifié, la mise à jour d’un fichier déjà marqué comme
favori, et qui consiste en la possibilité de :
-modifier les champs associés à ce fichier.
-supprimer le marquage de ce fichier. Cela implique que ce fichier ne fera plus partie des
fichiers favoris de l’utilisateur.

3.Listage des fichiers favoris
Le programme doit permettre à un utilisateur identifié, le listage de l’ensemble de ses fichiers favoris. Ceci consiste à afficher la totalité des fichiers marqués avec les détails des descriptions qui leurs sont associées. Le listage peut se faire (selon le choix de l’utilisateur) soit sur écran, soit dans un fichier texte.

3.Recherhe de fichiers
Le programme doit permettre à un utilisateur identifié, différents types de recherche dans ses fichiers favoris : par auteur, par titre, par tag (mot clé). Le résultat de la recherche doit fournir, pour chaque fichier trouvé, son titre ainsi que son nom physique (avec le chemin de son emplacement).
-A part les fichiers favoris, ajouter la possibilité de marquer des pages web (en donnant leur lien).

4.Informations Générales
Cette fonctionnalité n’est accessible que par l’administrateur de l’application. Elle fournit différents types d’information concernant les utilisateurs et leurs fichiers favoris, telles que :
-Liste des utilisateurs inscrits.
-Nombre de fichiers favoris par utilisateur
-Liste des tags (mots clés) différents utilisés par un utilisateur donné, pour marquer ses fichiers. Cette liste représentera en fait, l’ensemble des centres d’intérêt de cet utilisateur.
-Liste des tags différents (de tous les utilisateurs) et Nombre d’utilisateurs par tag. Cela permettra de voir les thèmes les plus populaires.
-Liste des utilisateurs pour un tag donnée. Cela permettra de repérer les utilisateurs ayant les mêmes centres d’intérêt.
Le listage peut se faire (selon le choix de l’administrateur) soit sur écran, soit dans un fichier texte.
-Gérer la possibilité de modifier les mots de passe des utilisateurs et les cas d’oublis de mots de passe
