# PROJETS RÉALISÉS EN JAVAFX

## Projet Système de Gestion d'Employés

- Ce projet a été développé à des fins éducatives pour démontrer JavaFX, l'intégration MySQL et les fonctionnalités CRUD (Créer, Lire, Mettre à jour, Supprimer) de base dans un système de gestion des employés.

## Projet d'Exécution Script Python

- Ce projet a été développé afin de pouvoir exécuter un script python depuis un programme Java, en mettant en place des
bonnes pratiques de Développement, tout en respectant un certains nombres de specifications fonctionnelles, ayant pour but
d'assurer la protection envers de potentiels failles de sécurités
- Chemin a spécifié pour exécuter le script python : ```python generate_graphs.py data.xlsx output```

## Projet de Gestion des Raccourcis

- Ce projet a été développé afin de pouvoir définir des raccourcis paramétrable depuis un programme Java, en mettant en place des bonnes pratiques de Développement, tout en respectant un certains nombres de specifications fonctionnelles, ayant pour but d'assurer la protection envers de potentiels failles de sécurités

## Arborescence du Projet de Gestion d'Employés

```
EmployeeManagementSystem
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

## Arborescence du Projet d'Exécution de Script Python

```
ExecutePythonScript
├───src
│   ├───main
│   │   ├───java
│   │   │   └───pythonScript
│   │   └───resources
│   │       └───pythonScript
│   │           └───output
│   └───test
│       └───java
│           └───pythonScript
```

## Arborescence du Projet de Gestion des Raccourcis

```
ShortcutManagerFX
├───src
│   ├───main
│   │   ├───java
│   │   │   └───shortcutManagerFX
│   │   │       ├───controller
│   │   │       ├───model
│   │   │       └───util
│   │   └───resources
│   │       └───shortcutManagerFX
│   └───test
│       └───java
│           └───shortcutManagerFX
```

## Commandes Maven a connaitre

- `mvn clean install` : Supprime tous les fichiers générés par le build précédent + Compile le projet, exécute les tests et installe le fichier JAR du projet
- `mvn compile` : Compile le code source du projet & génère les fichiers du build en cours
- `mvn test` : Compile le code source & exécute les tests unitaires
- `mvn exec:java -Dexec.mainClass="com.taskmanager.Main"` : Exécute une classe java spécifique
- `mvn exec:java -Dexec.mainClass="com.taskmanager.Main" -X` : Exécute une classe java spécifique en utilisant le débogage
- `mvn archetype:generate -D archetypeArtefactId=maven-archetype-quickstart` : Génère un nouveau projet Maven avec l'archtetype de base
