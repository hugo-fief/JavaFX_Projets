# PROJETS RÉALISÉS EN JAVAFX

## Projet Système de Gestion d'Employés

- Ce projet a été développé à des fins éducatives pour démontrer JavaFX, l'intégration MySQL et les fonctionnalités CRUD (Créer, Lire, Mettre à jour, Supprimer) de base dans un système de gestion des employés.


## Arborescence du Projet

```
employee-management-system-main
├───src
│   ├───main
│   │   ├───java
│   │   │   └───employeeManagementSystem
│   │   │       ├───bdd
│   │   │       ├───controller
│   │   │       ├───model
│   │   │       └───service
│   │   └───resources
│   │       └───employeeManagementSystem
│   │           └───view
│   └───test
│       └───java
```

## Commandes Maven a connaitre

- `mvn clean install` : Supprime tous les fichiers générés par le build précédent + Compile le projet, exécute les tests et installe le fichier JAR du projet
- `mvn compile` : Compile le code source du projet & génère les fichiers du build en cours
- `mvn test` : Compile le code source & exécute les tests unitaires
- `mvn exec:java -Dexec.mainClass="com.taskmanager.Main"` : Exécute une classe java spécifique
- `mvn exec:java -Dexec.mainClass="com.taskmanager.Main" -X` : Exécute une classe java spécifique en utilisant le débogage
- `mvn archetype:generate -D archetypeArtefactId=maven-archetype-quickstart` : Génère un nouveau projet Maven avec l'archtetype de base
