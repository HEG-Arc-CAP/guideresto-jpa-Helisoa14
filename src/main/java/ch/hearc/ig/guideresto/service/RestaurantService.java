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

        return city;
    }

    public BasicEvaluation insertBasicEvaluation(BasicEvaluation basicEvaluation) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
         entityManager.persist(basicEvaluation);
        transaction.commit();
        return basicEvaluation;
    }

    public Set<Restaurant> researchAllRestaurants() {
        Set<Restaurant> restaurants = new HashSet<>();

        EntityManager entityManager = emf.createEntityManager();
       // EntityTransaction transaction = entityManager.getTransaction();
       // Pas besoin de transaction car on est sur un SELECT
        //find (class, type de la PK)
        //Le JPQL est toujours fait sur les classes
        String queryString = "SELECT r FROM Restaurant r";
        restaurants = new HashSet<>(entityManager.createQuery(queryString, Restaurant.class).getResultList());

        return restaurants;
    }

    public Restaurant researchById(Integer restaurantId) {
        EntityManager entityManager = emf.createEntityManager();
        //EntityTransaction transaction = entityManager.getTransaction()
        //Restaurant restaurant = entityManager.find(Restaurant.class, 1);
        //QUESTION : Finalement quand utiliser le find?
        String queryString = "SELECT r FROM Restaurant r where r.id=?1" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        query.setParameter(1, restaurantId);
        Restaurant restaurant = query.getSingleResult();
        return restaurant;
    }

    public Set<Restaurant> researchByName(String name) {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT r FROM Restaurant r where r.name=:name" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        query.setParameter("name", name);
        restaurants =  new HashSet<>(query.getResultList());
        return restaurants;
    }

    public Set<Restaurant> researchByCity(String cityName) {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : Est-ce un bon adressage pour la navigation dans l'objet ??
        String queryString = "SELECT r FROM Restaurant r where r.address.city.cityName = "+ cityName ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        restaurants =  new HashSet<>(query.getResultList());
        return restaurants;
    }

    public Set<City> researchAllCities() {
        Set<City> cities = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT c FROM City c";
        cities = new HashSet<>(entityManager.createQuery(queryString, City.class).getResultList());

        return cities;
    }

    public Set<RestaurantType> researchAllRestaurantTypes() {
        Set<RestaurantType> restaurantTypes= new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : Dois-ton prendre les annotions hybernate ou celles de javax.persistance??
        restaurantTypes = new HashSet<>(entityManager.createNativeQuery("RestaurantsTypeList").getResultList());

        return restaurantTypes;
    }

    public Restaurant insertRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
            //Remonter dans la méthode pour voir dans quel état il est
            entityManager.persist(restaurant);
        transaction.commit();

        return restaurant;
    }

    public Set<EvaluationCriteria> researchAllEvaluationCritierias() {
        Set<EvaluationCriteria> evaluationCriteria = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : Je ne comprend pas pourquoi est-ce faux ?
        evaluationCriteria = new HashSet<>(entityManager.createNamedQuery("CritereEvaluationList", EvaluationCriteria.class).getResultList());

        return evaluationCriteria;
    }

    public CompleteEvaluation insertCompleteEvaluation(CompleteEvaluation completeEvaluation) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
            entityManager.persist(completeEvaluation);
        transaction.commit();

        return completeEvaluation;
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //QUESTION : la logique du update est présente dans le code source de l'application. Doit-on la merger ici ? Si non, quelle démarche entreprendre ?
            entityManager.detach(restaurant);
            restaurant.setName("Nom modifié");
            entityManager.merge(restaurant);
        transaction.commit();
        return null;
    }

    public void deleteRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
                //QUESTION : Est-ce que cette vérification est nécessaire ?
            //Vérification de l'existance du restaurant
            Restaurant restaurantExist = this.researchById(restaurant.getId());
           // QUESTION : entityManager.detach(restaurant); a-t-on besoin de cette méthode ?
            entityManager.remove(restaurantExist);
        transaction.commit();
    }

    public Set<Restaurant> researchByType(RestaurantType type) {
        //QUESTION : je n'ai pas réussi à bindé un objet
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT r FROM Restaurant r where r.type=?1" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        query.setParameter(1, type);
        restaurants =  new HashSet<>(query.getResultList());
        return restaurants;
    }

    public Set<Restaurant> researchRestaurantWhitoutEvaluation() {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        String queryString = "SELECT r FROM Restaurant r where r.type is null" ;
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        restaurants =  new HashSet<>(query.getResultList());
        return restaurants;
    }

    public Set<Restaurant> researchRestaurantByGrade() {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : vous validez ??
        String queryString = "SELECT r FROM Restaurant r LEFT JOIN evaluation eva where eva.grade.garde > (SELECT AVG(g.grade) FROM grade g)";
        TypedQuery<Restaurant> query = entityManager.createQuery(queryString, Restaurant.class);
        restaurants =  new HashSet<>(query.getResultList());
        return restaurants;
    }

    public int averageGrade() {
        Set<Restaurant> restaurants = new HashSet<>();
        EntityManager entityManager = emf.createEntityManager();
        //QUESTION : va-t-il retrouver le complete evaluation ?
        String queryString = "SELECT r, AVG(g.grade) FROM Restaurant r LEFT JOIN evaluation ev JOIN Grade g ORDER BY r.name";
        // QUESTION : On ne peut pas mettre de type primitif comme résultat ??
        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
        int result = query.getSingleResult();
        return result;
    }

}
