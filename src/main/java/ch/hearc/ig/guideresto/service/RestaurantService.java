package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.*;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class RestaurantService {
    //Création
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("guideRestoPersistenceUnit");

    public City insertCity(String cityZipCode, String cityName) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
            City city = new City(cityZipCode, cityName);
            entityManager.persist(city);
        transaction.commit();
        entityManager.close(); //Rend la connexion au pool de connexion
        return city;
    }

    public BasicEvaluation insertBasicEvaluation(BasicEvaluation basicEvaluation) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
         entityManager.persist(basicEvaluation);
        transaction.commit();
        entityManager.close(); //Rend la connexion au pool de connexion
        return basicEvaluation;
    }

    public Set<Restaurant> researchAllRestaurants() {
        Set<Restaurant> restaurants = new HashSet<>();

        EntityManager entityManager = emf.createEntityManager();
       // EntityTransaction transaction = entityManager.getTransaction();
       // Pas besoin de transaction car on est sur un SELECT
        //Le JPQL est toujours fait sur les classes
        String queryString = "SELECT r FROM Restaurant r";
        restaurants = new HashSet<>(entityManager.createQuery(queryString, Restaurant.class).getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurants;
    }

    public Restaurant researchById(Integer restaurantId) {
        EntityManager entityManager = emf.createEntityManager();
        //EntityTransaction transaction = entityManager.getTransaction()
        //Restaurant restaurant = entityManager.find(Restaurant.class, 1);
        //QUESTION : Finalement quand utiliser le find?
      //REPONSE : Dans tous les cas où la recherche par identité technique (@Id) est suffisante. C'est le cas ici:
      //find (class, type de la PK)
      return entityManager.find(Restaurant.class, 1);
    }

    public Set<Restaurant> researchByName(String name) {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT r FROM Restaurant r where r.name=:name" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        query.setParameter("name", name);
        restaurants =  new HashSet<>(query.getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurants;
    }

    public Set<Restaurant> researchByCity(String cityName) {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : Est-ce un bon adressage pour la navigation dans l'objet ?? Oui juste.
      //Attention, la navigation dans l'objet ne fonctionne que pour les attributs de type simple et non complexe comme les listes.
        String queryString = "SELECT r FROM Restaurant r where r.address.city.cityName = "+ cityName ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        restaurants =  new HashSet<>(query.getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurants;
    }

    public Set<City> researchAllCities() {
        Set<City> cities = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT c FROM City c";
        cities = new HashSet<>(entityManager.createQuery(queryString, City.class).getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return cities;
    }

    public Set<RestaurantType> researchAllRestaurantTypes() {
        Set<RestaurantType> restaurantTypes= new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : Dois-ton prendre les annotions hybernate ou celles de javax.persistance?? Toujours  JAVAX persistance car c'est JPA
        restaurantTypes = new HashSet<>(entityManager.createNativeQuery("RestaurantsTypeList").getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurantTypes;
    }

    public Restaurant insertRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
            //Remonter dans la méthode pour voir dans quel état il est
            entityManager.persist(restaurant);
        transaction.commit();
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurant;
    }

    public Set<EvaluationCriteria> researchAllEvaluationCritierias() {
        Set<EvaluationCriteria> evaluationCriteria = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : Je ne comprend pas pourquoi est-ce faux ?
      ////REPONSE: Ce n'est pas faux. C'est IntellIJ qui tente intelligemment de trouver la NamedQuery CritereEvaluationList.
      //Il ne la trouve pas avec le paramétrage actuel de ton IDE, projet. C'est juste un warning, pas une erreur.
      //Si tu veux la corriger, il faut configurer IntelliJ
        evaluationCriteria = new HashSet<>(entityManager.createNamedQuery("CritereEvaluationList", EvaluationCriteria.class).getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return evaluationCriteria;
    }

    public CompleteEvaluation insertCompleteEvaluation(CompleteEvaluation completeEvaluation) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
            entityManager.persist(completeEvaluation);
        transaction.commit();
        entityManager.close(); //Rend la connexion au pool de connexion
        return completeEvaluation;
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //QUESTION : la logique du update est présente dans le code source de l'application. Doit-on la merger ici ? Si non, quelle démarche entreprendre ?
          //REPONSE: Ca dépend de l'architecture de ton application. A mon sens, si tu gères correctement ta transaction,
           //ton entité arrive ici à l'état détaché. Le plus juste est effectivement de la merger.
           // entityManager.detach(restaurant); Pas besoin
           // restaurant.setName("Nom modifié"); Déjà fait dans l'application
            entityManager.merge(restaurant);
        transaction.commit();
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurant;
    }

    public void deleteRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
                //QUESTION : Est-ce que cette vérification est nécessaire ?
            //Vérification de l'existance du restaurant
      //REPONSE: Dans l'idéale, il faudrait rajouter un contrôle effectivement, et retourner une Exception e si restaurant n'existe pas
        //Car dès qu'on a fermé l'entity manager, tous les objetc qui sortent de restaurant servie sont detache implicitement.
      //L'entité en arrivant ici est DETACH, donc on peut faire ceci,le code suivant est donc envisageable
      //entityManager.merge(restaurant);
      //entityManager.remove(restaurant);

            Restaurant restaurantExist = this.researchById(restaurant.getId());
           // QUESTION : entityManager.detach(restaurant); a-t-on besoin de cette méthode ?
            // REPONSE: Tu vas rarement l'utiliser toi-même. Par contre, dès le moment ou entityManager.close() est appelé,
            // toutes les entités liés à cet EntityManager vont automatiquement être détachées.
            entityManager.remove(restaurantExist);
        transaction.commit();
        entityManager.close(); //Rend la connexion au pool de connexion
    }

    public Set<Restaurant> researchByType(RestaurantType type) {
        //Bindé un objet
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT r FROM Restaurant r where r.type=?1" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        query.setParameter(1, type);
        restaurants =  new HashSet<>(query.getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurants;
    }

    public Set<Restaurant> researchRestaurantWhitoutEvaluation() {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT r FROM Restaurant r where r.type is null" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        restaurants =  new HashSet<>(query.getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurants;
    }

    public Set<Restaurant> researchRestaurantByGrade() {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : vous validez ?? NON
        //FAUX String queryString = "SELECT r FROM Restaurant r LEFT JOIN evaluation eva where eva.grade.garde > (SELECT AVG(g.grade) FROM grade g)";
      //Il faut passer par les attributs qui join et attention à la Case Sensitive des nom des classes
      //La navigation par objet ne fonctionne pas sur les
          //REPONSE: Le mieux est de le tester, mais en l'état, ce n'est pas juste.
        String queryString = " SELECT r FROM Restaurant r LEFT JOIN r.evaluations eva LEFT JOIN eva.grades gra WHERE gra.grade > (SELECT AVG(g.grade) FROM Grade g)";
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        restaurants =  new HashSet<>(query.getResultList());
        entityManager.close(); //Rend la connexion au pool de connexion
        return restaurants;
    }

    public int averageGrade() {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : va-t-il retrouver le complete evaluation ?
      //REPONSE: Non, car tu as sélectionné le Restaurant et la moyenne des Grade, c'est tout
       // String queryString = "SELECT r, AVG(g.grade) FROM Restaurant r LEFT JOIN evaluation ev JOIN Grade g ORDER BY r.name";
        // QUESTION : On ne peut pas mettre de type primitif comme résultat ??
      //REPONSE: Pas de cette manière, ton type de retour est une List<Object[2]>
      // [0] ==> Restaurant
      // [1] ==> Integer

      String queryString = "SELECT r, ev, AVG(g.grade) FROM Restaurant r LEFT JOIN r.evaluations ev JOIN ev.gardes g ORDER BY r.name";
        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        //Restaurant restaurant = (Restaurant) query.getSingleResult()[0];  Pour le restaurant
        Integer result = (Integer) query.getSingleResult()[2];
        entityManager.close(); //Rend la connexion au pool de connexion
        return result;
    }

}
