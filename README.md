# Application de Gestion des Employés

## Présentation du Projet
Ce projet est une application web permettant de gérer les employés d'une organisation. Il inclut des fonctionnalités d'inscription, de connexion et de gestion des employés, avec un accent sur la sécurité et la simplicité d'utilisation.

---

## Stack Technique
- **Backend** : Java 17 avec Spring Boot
- **Frontend** : Vite, React (TypeScript), et Tailwind CSS
- **Base de données** : PostgreSQL
- **Infrastructure** : Docker pour le déploiement et l'orchestration

---

## Prérequis
Avant de commencer, assurez-vous que les éléments suivants sont installés sur votre machine :
- Docker et Docker Compose
- Git

---

## Instructions d’Installation
1. Clonez le dépôt :
   ```bash
   git clone <url-du-dépôt>
   ```
2. Rendez-vous à la racine du projet :
   ```bash
   cd <nom-du-répertoire>
   ```
3. Lancez les conteneurs Docker :
   ```bash
   docker-compose up -d
   ```
4. Une fois les conteneurs démarrés, accédez à l’application via [URL à préciser].

---

## Structure du Projet
- **docker-compose.yaml** : Fichier de configuration pour déployer les services avec Docker.
- **front** : Le frontend fournissant l’interface utilisateur.
- **back** : Le backend exposant des API REST pour les opérations sur les employés.

---

## Fonctionnalités Clés

### 1. Inscription
- **URL** : [http://localhost:5173/signup]
- **Champs requis** :
  - Email : Une adresse valide est obligatoire.
  - Mot de passe :
    - Minimum 8 caractères
    - Au moins une majuscule, un chiffre et un caractère spécial
- **Redirection** : En cas de succès, redirection vers la page de connexion.

### 2. Connexion
- **URL** : [http://localhost:5173/signin]
- **Champs requis** :
  - Email
  - Mot de passe
- **Redirection** : En cas de succès, redirection vers la page de liste des employés.

### 3. Gestion des Employés (connexion requise)
#### Liste des Employés
- **URL** : [http://localhost:5173/employee]
- Affiche la liste des employés enregistrés.

#### Création d’un Employé
- **URL** : [http://localhost:5173/employee/create]
- **Champs requis** :
  - Nom : Unique et obligatoire
  - Date de naissance : L’âge doit être d’au moins 18 ans
- **Redirection** : En cas de succès, retour à la liste des employés.

#### Modification d’un Employé
- Mêmes contraintes que pour la création.

#### Suppression d’un Employé
- Une boîte de dialogue permet de confirmer la suppression.

### 4. Déconnexion
- Permet de terminer la session en toute sécurité.

---

## Configuration des Ports
- **front** : `5173`
- **back** : `8080`
- **Base de données** : `5432`

---

## Données Initiales
Pour faciliter vos tests, trois employés sont préchargés dans la base de données.

---

## Exigences Non Fonctionnelles
- **Journalisation** :
  - Les événements importants, y compris les erreurs et les accès aux API, sont consignés dans les logs pour assurer une traçabilité efficace.
- **Sécurité** :
  - Les API sont protégées par des tokens JWT, garantissant que seules les requêtes authentifiées sont acceptées.


---

## Scénarios de Test
1. **Inscription** :
   - Tester avec des données valides et invalides.
   - Confirmer la redirection vers la page de connexion.

2. **Connexion** :
   - Vérifier que l’accès est refusé pour des identifiants incorrects.
   - Accéder aux pages protégées après une connexion réussie.

3. **Gestion des Employés** :
   - Ajouter, modifier et supprimer des employés tout en respectant les contraintes.

Merci de tester et d’apporter vos retours pour améliorer ce projet. Bon test !