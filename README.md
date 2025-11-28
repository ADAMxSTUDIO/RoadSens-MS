# RoadSense–MS — Analyse automatique des routes
## Contexte : 
Les infrastructures routières se détériorent rapidement sous l'effet du trafic et des conditions climatiques. L’inspection manuelle des routes est coûteuse et peu fréquente, ce qui entraîne des retards dans les réparations et une allocation inefficace des ressources publiques. Une solution automatisée permet de détecter les dégradations à partir de vidéos et d’images, d’évaluer leur gravité et de planifier la maintenance de manière proactive.

---
## Objectif :
Développer un système intelligent capable d’analyser automatiquement les routes à partir de vidéos embarquées (dashcam ou drone) pour identifier les fissures, nids-de-poule et autres dégradations, de les géolocaliser avec précision et de générer une carte de priorisation des interventions.

---
## Architecture (microservices)
1. IngestionVideo

### Rôle :
Extraire les images, les métadonnées GPS et l’orientation à partir des vidéos brutes captées par des véhicules ou drones.

### Entrées/Sorties :
Vidéos (MP4, AVI) → frames JPEG + données GPS/IMU.

### API / Communication :
POST /video/upload pour dépôt et extraction automatique.

### Technologies :
Go (pour performance), GStreamer, FFmpeg pour la découpe.

### Base de données :
MinIO pour stocker les images extraites.

### Description :
Ce service décompose les vidéos en images exploitables, horodate et synchronise avec les coordonnées GPS afin d’assurer la traçabilité des défauts détectés.

---
2. DetectionFissures

### Rôle :
Identifier automatiquement les fissures, trous ou dégradations à partir des images extraites.

### Entrées/Sorties :
Images JPEG → boîtes de détection (bounding boxes) + masques de segmentation.

### API / Communication :
POST /detect pour lancer la détection.

### Technologies :
PyTorch (modèles YOLOv8, Mask R-CNN), CUDA pour accélération GPU.

### Base de données :
MinIO pour stocker les images annotées et les résultats.

### Description :
Ce service applique des modèles de vision par ordinateur pour repérer les anomalies visibles sur la chaussée et attribue un score de confiance à chaque détection.

---
3. GeoRef

### Rôle :
Associer chaque défaut détecté à une position géographique précise sur le réseau routier.

### Entrées/Sorties :
Fissures détectées + coordonnées GPS → entités géospatiales (tronçons).

### API / Communication :
POST /georef pour géoréférencer les anomalies.

### Technologies :
Python (OSRM, Shapely, GeoPandas).

### Base de données :
PostGIS (extension spatiale de PostgreSQL).

### Description :
Ce microservice effectue le “map-matching” des détections avec le graphe routier et permet de visualiser chaque défaut sur une carte.

---
4. ScoreGravite

### Rôle :
Calculer la gravité de chaque défaut selon sa taille, sa profondeur et la densité locale des anomalies.

### Entrées/Sorties :
Données de détection → scores de gravité.

### API / Communication :
POST /severity/compute pour calculer le score.

### Technologies :
Python (XGBoost, Scikit-learn).

### Base de données :
PostgreSQL pour stocker les scores et rapports.

### Description :
Ce module traduit les observations visuelles en un score de risque utilisable pour la priorisation des réparations.

---
5. Priorisation

### Rôle :
Classer les tronçons de route selon leur niveau de dégradation et l’importance du trafic.

### Entrées/Sorties :
Scores de gravité + trafic → liste priorisée.

### API / Communication :
GET /priorities pour accéder au résultat.

### Technologies :
Node.js/NestJS pour logique métier et orchestration.

### Base de données :
PostgreSQL.

### Description :
Ce microservice combine plusieurs critères (coût, gravité, accessibilité, importance du tronçon) pour générer un plan d’intervention optimal.

---
6. ExportSIG

### Rôle :
Exporter les résultats (défauts et priorités) sous formats compatibles avec les systèmes d’information géographique (SIG).

### Entrées/Sorties :
Données PostGIS → GeoJSON, WMS, WFS, MBTiles.

### API / Communication :
GET /export/map pour générer les couches SIG.

### Technologies :
GeoServer, GDAL.

### Base de données :
PostGIS.

### Description :
Ce service rend les données exploitables dans des logiciels SIG externes (QGIS, ArcGIS) pour faciliter les rapports techniques et la planification.

---
7. Dashboard

### Rôle :
Interface web pour visualiser l’état des routes, les détections, les scores et les priorités.

### Entrées/Sorties :
API agrégées → tableaux et cartes interactives.

### API / Communication :
GET /dashboard (données agrégées).

### Technologies :
React.js, Leaflet/MapLibre pour les cartes, Chart.js pour les statistiques.

### Base de données :
PostgreSQL (requêtes agrégées).

### Description :
Le tableau de bord fournit une vision d’ensemble : état du réseau, zones critiques, statistiques par région, et rapports exportables.

---
## Résultat attendu :

Un système complet, déployable via Docker, capable d’analyser automatiquement les routes à partir de vidéos, de détecter les défauts, d’évaluer leur gravité et de produire une cartographie dynamique de la maintenance.